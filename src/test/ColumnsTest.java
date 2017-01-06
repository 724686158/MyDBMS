package test;

import database.Column;
import database.Columns;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by 离子态狍子 on 2016/11/20.
 */
public class ColumnsTest {

    private final static Logger LOGGER = Logger.getLogger(ColumnsTest.class.getName());
    //对于Columns的序列化和反序列化测试
    @org.junit.Test
    public void Serl(){
       /* try {


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Columns cs;
            FileInputStream fis = new FileInputStream("Columns");
            ObjectInputStream ois = new ObjectInputStream(fis);
            cs = (Columns) ois.readObject();
            ois.close();
            LOGGER.log(Level.WARNING, cs.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}