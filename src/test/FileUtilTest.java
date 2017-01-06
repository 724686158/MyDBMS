package test;

import database.FileUtil;
import org.junit.Test;

/**
 * Created by 离子态狍子 on 2016/12/1.
 */
public class FileUtilTest {
    @Test
    public void main(){
        String dirName = "data/havefun";
        FileUtil.CreateDir(dirName);
        String fileName = dirName + "/text1.txt";
        FileUtil.CreateFile(fileName);
        FileUtil.DelAllFile(dirName);

    }

}