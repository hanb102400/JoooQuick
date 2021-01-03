package com.shawn.jooo.generator.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public static void zipCompress(String sourceFileName, String zipFileName) throws IOException {
        System.out.println(MsgFmt.getString("开始压缩目录{0}", sourceFileName));


        File zipFile = new File(sourceFileName, zipFileName);
        //创建zip输出流
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));

        //创建缓冲输出流
        BufferedOutputStream bos = new BufferedOutputStream(out);

        File sourceFile = new File(sourceFileName);

        //调用函数
        compress(out, bos, sourceFile);

        bos.close();
        out.close();
        System.out.println(MsgFmt.getString("压缩文件完成录{0}", zipFile.getAbsolutePath()));

    }

    private static void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile) throws IOException {
        compress(out, bos, sourceFile, sourceFile.getName());
    }

    private static void compress(ZipOutputStream zipStream, BufferedOutputStream outStream, File sourceFile, String path) throws IOException {
        if (sourceFile.isDirectory()) {
            File[] flist = sourceFile.listFiles();
            if (flist.length == 0) {
                //空目录
                zipStream.putNextEntry(new ZipEntry(path + "/"));
            } else {
                //非空目录
                for (int i = 0; i < flist.length; i++) {
                    compress(zipStream, outStream, flist[i], path + "/" + flist[i].getName());
                }
            }
        } else {
            zipStream.putNextEntry(new ZipEntry(path));
            BufferedInputStream buff = new BufferedInputStream(new FileInputStream(sourceFile));

            //将源文件写入到zip文件中
            int ch;
            while ((ch = buff.read()) != -1) {
                outStream.write(ch);
            }

            buff.close();

        }
    }
}
