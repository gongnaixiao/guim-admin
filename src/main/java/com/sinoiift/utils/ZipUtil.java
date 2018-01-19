package com.sinoiift.utils;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;

import java.io.*;

/**
 * Created by xg on 2018/1/16.
 */

public class ZipUtil {
    public static void unZip(byte[] bytes, String saveFileDir) throws IOException {
        if (!saveFileDir.endsWith("\\") && !saveFileDir.endsWith("/")) {
            saveFileDir += File.separator;
        }
        File dir = new File(saveFileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        ZipArchiveInputStream zais = new ZipArchiveInputStream(new ByteArrayInputStream(bytes));
        ArchiveEntry archiveEntry = null;
        try {
            while ((archiveEntry = zais.getNextEntry()) != null) {
                // 获取文件名
                String entryFileName = archiveEntry.getName();
                // 构造解压出来的文件存放路径
                String entryFilePath = saveFileDir + entryFileName;
                OutputStream os = null;
                // 把解压出来的文件写到指定路径
                File entryFile = new File(entryFilePath);
                if (entryFileName.endsWith("/")) {
                    entryFile.mkdirs();
                } else {
                    os = new BufferedOutputStream(new FileOutputStream(
                            entryFile));
                    byte[] buffer = new byte[1024];
                    int len;
                    try {
                        while ((len = zais.read(buffer)) != -1) {
                            os.write(buffer, 0, len);
                        }
                    } finally {
                        os.close();
                    }
                }
            }
        } finally {
            zais.close();
        }
    }
}
