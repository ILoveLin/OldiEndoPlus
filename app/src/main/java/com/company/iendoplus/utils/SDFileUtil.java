package com.company.iendoplus.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;


/**
 * LoveLin
 * <p>
 * Describe  删除SD卡图片，读取SMB文件到SD卡之中的工具类
 */
public class SDFileUtil {
    private static boolean flag;
    private static File file;

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param sPath 要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean DeleteFolder(String sPath) {
        flag = false;
        file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        flag = false;
        file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 已下是SMB文件读取到本地SD卡的下载工具
     *==========================================================================================
     */
    /**
     * 下载文件到指定文件夹
     *
     * @param remoteUrl
     * @param shareFolderPath
     * @param fileName
     * @param localDir
     */
    public static void downLoadFileToFolder(String remoteUrl, String shareFolderPath, String fileName,
                                            String localDir) {
        try {
            SmbFile remoteFile = new SmbFile(remoteUrl + shareFolderPath + fileName);
            File localFile = new File(localDir + "/" + fileName);
            BufferedInputStream in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(localFile));
            byte[] buffer = new byte[8 * 1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);

            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载（原）文件到指定文件夹
     *
     * @param remoteUrl
     * @param fileName
     * @param localDir
     */
    public static void downLoadReallyFileToFolder(String remoteUrl, String shareFolderPath, String fileName, String localDir) {
        try {
            SmbFile remoteFile = new SmbFile(remoteUrl + shareFolderPath + fileName);
            File localFile = new File(localDir + "/" + fileName);
            BufferedInputStream in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(localFile));
            byte[] buffer = new byte[10 * 1024];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
