package com.sinoiift.service.impl;

import com.sinoiift.domain.App;
import com.sinoiift.service.DeployService;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.transport.verification.HostKeyVerifier;
import net.schmizz.sshj.xfer.FileSystemFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

/**
 * Created by xg on 2018/1/18.
 */
@Service
public class DeployServiceImp implements DeployService {
    private static Logger log = LoggerFactory.getLogger(DeployServiceImp.class);

    public void executeDeploy(String file, App app) throws Exception {
        final SSHClient ssh = new SSHClient();
        //ssh.loadKnownHosts();
        ssh.addHostKeyVerifier(
                new HostKeyVerifier() {
                    public boolean verify(String arg0, int arg1, PublicKey arg2) {
                        return true; // don't bother verifying
                    }
                });
        try {
            ssh.connect(app.getAppIP());
            ssh.authPassword(app.getAppUsername(), app.getAppPassword());

            String workspace = app.getWorkspace();
            if (!workspace.endsWith("/")) {
                workspace += "/";
            }
            String apWarDir = app.getApWarDir();
            if (!apWarDir.endsWith("/")) {
                apWarDir += "/";
            }
            String upWarDir = app.getUpWarDir();
            if (!upWarDir.endsWith("/")) {
                upWarDir += "/";
            }

            //检查工作空间是否存在
            Session session = ssh.startSession();
            try {
                final Session.Command cmd1 = session.exec("test -d " + workspace + "&& echo ok");
                if (IOUtils.readFully(cmd1.getInputStream()).toString().equals("")) {
                    log.error("工作空间不存在");
                    throw new Exception("工作空间不存在");
                }
            } finally {
                session.close();
            }
            session = ssh.startSession();
            try {
                final Session.Command cmd2 = session.exec("test -d " + apWarDir + "&& echo ok");
                if (IOUtils.readFully(cmd2.getInputStream()).toString().equals("")) {
                    log.error("AP war包目录不存在");
                    throw new Exception("AP war包目录不存在");
                }
            } finally {
                session.close();
            }

            session = ssh.startSession();
            try {
                final Session.Command cmd3 = session.exec("test -d " + upWarDir + "&& echo ok");
                if (IOUtils.readFully(cmd3.getInputStream()).toString().equals("")) {
                    //throw new Exception("UP war包目录不存在");
                    log.warn("UP war包目录不存在");
                }
            } finally {
                session.close();
            }

            //上传补丁包
            ssh.useCompression();
            ssh.newSCPFileTransfer().upload(new FileSystemFile(file), workspace);
            log.info("上传文件" + file + "成功");

            //部署补丁包
            String deployFile = file.substring(file.lastIndexOf("/") + 1);
            session = ssh.startSession();
            try {
                final Session.Command cmd4 = session.exec("tar -xzf " + workspace + deployFile + " -C " + apWarDir);
                cmd4.join(60, TimeUnit.SECONDS);
                if (cmd4.getExitStatus() == null) {
                    log.error("部署AP失败");
                    throw new Exception("部署AP失败");
                }
            } finally {
                session.close();
            }

            session = ssh.startSession();
            try {
                final Session.Command cmd5 = session.exec("tar -xzf " + workspace + deployFile + " -C " + upWarDir);
                cmd5.join(60, TimeUnit.SECONDS);
                if (cmd5.getExitStatus() == null) {
                    log.error("部署UP失败");
                    throw new Exception("部署UP失败");
                }
            } finally {
                session.close();
            }

        } finally {
            ssh.disconnect();
        }
    }
}
