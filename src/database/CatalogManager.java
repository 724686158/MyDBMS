package database;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Created by 离子态狍子 on 2016/11/25.
 */
public class CatalogManager implements Serializable {

    private String mPath;
    ArrayList<Database> mDatabases;

    public CatalogManager(String mPath) {
        this.mPath = mPath;
        mDatabases = new ArrayList<>();
    }

    public String getmPath() {
        return mPath;
    }

    public ArrayList<Database> getmDatabases() {
        return mDatabases;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public void setmDatabases(ArrayList<Database> mDatabases) {
        this.mDatabases = mDatabases;
    }

    public Database getDatabaseByName(String name){
        for (Database database : getmDatabases()){
            if (database.getName().equals(name)){
                return database;
            }
        }
        return null;

    }

    /**
     * 保存对象到序列化文件
     */
    public void Save(){
        String fileName = getmPath() + "catalog";
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从序列化文件获取数据到对象
     */
    public void Load(){
        String fileName = getmPath() + "catalog";
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            CloneWith((CatalogManager) ois.readObject());
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建数据库
     * @param name
     */
    public void CreateDatabase(String name){
        getmDatabases().add(new Database(name));
    }

    /**
     * 删除数据库
     * @param name
     */
    public void DeleteDatabase(String name){
        Database deleteDB = null;
        for (Database database : getmDatabases()){
            if (database.getName().equals(name)){
                deleteDB = database;
            }
        }
        getmDatabases().remove(deleteDB);

    }
    public void CloneWith(CatalogManager catalogManager){
        this.setmPath(catalogManager.getmPath());
        this.setmDatabases(catalogManager.getmDatabases());
    }

}
