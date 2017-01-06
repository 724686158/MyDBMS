package database;

import java.io.File;
import java.io.IOException;

/**
 * Created by 离子态狍子 on 2016/12/1.
 */
public class FileUtil {
    public static boolean CreateFile(String destFileName) {
        File file = new File(destFileName);
        if(file.exists()) {
            //System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            //System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if(!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            //System.out.println("目标文件所在目录不存在，准备创建它！");
            if(!file.getParentFile().mkdirs()) {
                //System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                //System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                //System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            //System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }
    }


    public static boolean CreateDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            //System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            //System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            //System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }


    /**
     * 删除文件夹及其中全部文件
     * @param folderPath
     */
    public static void DelFolder(String folderPath) {
        try {
            DelAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            //System.out.println("文件夹已清空");
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
            //System.out.println("空文件夹已被删除");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件夹中的全部文件
     * @param path
     * @return
     */
    public static boolean DelAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
                System.out.println("名为" + tempList[i] + "的文件已删除");
            }
            if (temp.isDirectory()) {
                DelAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                DelFolder(path + "/" + tempList[i]);//再删除空文件夹
                System.out.println("名为" + tempList[i] + "的文件夹已删除");
                flag = true;
            }

        }
        return flag;
    }

}
