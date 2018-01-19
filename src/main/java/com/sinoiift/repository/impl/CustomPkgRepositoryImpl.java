package com.sinoiift.repository.impl;

import com.sinoiift.domain.view.PkgInfo;
import com.sinoiift.repository.CustomPkgRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

/**
 * Created by xg on 2018/1/16.
 */
@Repository
public class CustomPkgRepositoryImpl implements CustomPkgRepository {
    @Autowired
    private EntityManager entityManager;


    @Override
    public Page<PkgInfo> listPkgInfos(Pageable pageable, String bug, String pkg, Long envId) {
        String countSql = "select count(d.pkgId) from DeployPlan d, Pkg p, Env e where d.pkgId = p.id and d.envId = e.id";
        Query query = genQuery(countSql, bug, pkg, envId);
        long total = (long) query.getSingleResult();

        String baseSql = "select new com.sinoiift.domain.view.PkgInfo(d.pkgId, d.envId, e.name, d.isPrd, p.bug, p.author, p.name, p.ctime, p.version) from DeployPlan d, Pkg p, Env e where d.pkgId = p.id and d.envId = e.id";

        Query pageQuery = genQuery(baseSql, bug, pkg, envId);

        int pageSize = pageable.getPageSize();
        pageQuery.setFirstResult(pageable.getPageNumber() * pageSize);
        pageQuery.setMaxResults(pageSize);
        List<PkgInfo> content = pageQuery.getResultList();

        Page<PkgInfo> pages = new PageImpl(content, pageable, total);
        return pages;
    }

    private Query genQuery(String sql, String bug, String pkg, Long envId) {
        if (StringUtils.isNotBlank(bug)) {
            sql += " and p.bug like :bug";
        }
        if (StringUtils.isNotBlank(pkg)) {
            sql += " and p.name like :pkg";
        }

        if (envId != null) {
            sql += " and d.envId = :envId";
        }
        Query query = entityManager.createQuery(sql);
        if (StringUtils.isNotBlank(bug)) {
            query.setParameter("bug", "%" + bug + "%");
        }
        if (StringUtils.isNotBlank(pkg)) {
            query.setParameter("pkg", "%" + pkg + "%");
        }

        if (envId != null) {
            query.setParameter("envId", envId);
        }

        return query;
    }
}
