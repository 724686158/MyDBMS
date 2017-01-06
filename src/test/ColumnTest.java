package test;

import database.Column;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by 离子态狍子 on 2016/11/20.
 */
public class ColumnTest {

    private final static Logger LOGGER = Logger.getLogger(ColumnTest.class.getName());

    @org.junit.Test
    public void copy() throws Exception {
        /*Column c1 = new Column("hsf", 4);
        Column c2 = c1.copy();
        assertEquals(c1.toString(), c2.toString());
        LOGGER.log(Level.WARNING, c1.toString());*/
    }

    //对于Column的序列化和反序列化测试
    @org.junit.Test
    public void Serl(){
        /*try {
            Column object1 = new Column();

            FileOutputStream fos = new FileOutputStream("Column");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object1);
            oos.flush();
            oos.close();
            LOGGER.log(Level.WARNING, object1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Column object2;
            FileInputStream fis = new FileInputStream("Column");
            ObjectInputStream ois = new ObjectInputStream(fis);
            object2 = (Column) ois.readObject();
            ois.close();
            LOGGER.log(Level.WARNING, object2.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}