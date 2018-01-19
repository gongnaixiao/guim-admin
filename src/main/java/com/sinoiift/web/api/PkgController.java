package com.sinoiift.web.api;

import com.sinoiift.comm.aop.LoggerManage;
import com.sinoiift.domain.App;
import com.sinoiift.domain.DeployPlan;
import com.sinoiift.domain.Env;
import com.sinoiift.domain.Pkg;
import com.sinoiift.domain.result.Rest;
import com.sinoiift.domain.view.Deploy;
import com.sinoiift.domain.view.PkgInfo;
import com.sinoiift.repository.*;
import com.sinoiift.service.DeployService;
import com.sinoiift.service.ImpSqlFileService;
import com.sinoiift.utils.ZipUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * Created by xg on 2018/1/11.
 */

@RestController
public class PkgController {
    @Autowired
    PkgRepository pkgRepository;
    @Autowired
    AppRepository appRepository;
    @Autowired
    EnvRepository envRepository;
    @Autowired
    DeployPlanRepository deployPlanRepository;
    @Autowired
    CustomPkgRepository customPkgRepository;
    @Autowired
    ImpSqlFileService impSqlFileService;
    @Autowired
    DeployService deployService;

    @LoggerManage(description = "获取所有更新包")
    @GetMapping("/pkg/list")
    public Object list(@RequestParam int page, @RequestParam int limit, String bug, String pkg, Long envId) {
        Pageable pageable = new PageRequest(page - 1, limit);
        Page<PkgInfo> pages = customPkgRepository.listPkgInfos(pageable, bug, pkg, envId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", pages.getContent());
        map.put("total", pages.getTotalElements());
        return map;
    }

    @LoggerManage(description = "新增上传环境")
    @PostMapping("/pkg/delete")
    @Transactional()
    public Rest delete(@RequestParam("ids") long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return Rest.failure("客户端传入对象id为空");
        }
        for (long id : ids) {
            pkgRepository.delete(id);
        }
        return Rest.ok();
    }

    @LoggerManage(description = "新增上传包")
    @PostMapping("/pkg/add")
    @Transactional()
    public Rest add(@RequestParam("file") MultipartFile file, String bugId, String author) {
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!suffixName.equals("zip")) {
            return Rest.failure("必须为zip压缩文件");
        }
        long version = 0L;

        Pkg tmp = pkgRepository.findByName(fileName);
        if (tmp != null) {
            version = tmp.getVersion();
        }
        try {
            byte[] bytes = file.getBytes();
            Pkg pkg = new Pkg();
            pkg.setName(fileName);
            pkg.setAuthor(author);
            pkg.setBug(bugId);
            pkg.setContent(bytes);
            pkg.setCtime(new Date());
            pkg.setVersion(version + 1);
            pkgRepository.save(pkg);

            for (Env env : envRepository.findAll()) {
                DeployPlan plan = new DeployPlan();
                plan.setEnvId(env.getId());
                plan.setPkgId(pkg.getId());
                plan.setPrd(false);
                deployPlanRepository.save(plan);
            }

        } catch (Exception e) {
            return Rest.failure(e.getMessage());
        }

        return Rest.ok();
    }

    @LoggerManage(description = "部署上传包")
    @PostMapping("/pkg/deploy")
    @Transactional
    public Rest deploy(@RequestBody List<Deploy> deploys) {
        try {
            for (Deploy deploy : deploys) {
                List<App> apps = appRepository.findByEnvId(deploy.getEnvId());
                Pkg pkg = pkgRepository.findOne(deploy.getPkgId());
                for (App app : apps) {
                    deploy(app, pkg);
                }
            }
        } catch (Exception e) {
            return Rest.failure(e.getMessage());
        }
        return Rest.ok("部署成功");
    }

    private void deploy(App app, Pkg pkg) throws Exception {
        byte[] bytes = pkg.getContent();
        String workspace = "/tmp/";
        String saveFileDir = workspace + pkg.getName();
        try {
            ZipUtil.unZip(bytes, saveFileDir);
        } catch (IOException e) {
            throw new Exception("解压缩失败：" + pkg.getName());
        }
        File dir = new File(saveFileDir);
        List<String> sqls = new ArrayList();
        List<String> tars = new ArrayList();
        for (String fileName : dir.list()) {
            String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (suffixName.equals("sql")) {
                sqls.add(saveFileDir + "/" + fileName);
            }
            if (suffixName.equals("gz")) {
                tars.add(saveFileDir + "/" + fileName);
            }
        }
            try {
                impSqlFileService.executeSql(sqls, workspace, app);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("执行sql文件失败" );
            }

            try {
                for (String file : tars) {
                    deployService.executeDeploy(file, app);
                }
            } catch (Exception e) {
                throw new Exception("部署失败" );
            }
    }
}
