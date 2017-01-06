package database;

import database.User;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/12/20.
 */
public class UserManager implements Serializable{
    private String mPath;
    private ArrayList<User> mUsers;

    public UserManager(String mPath) {
        this.mPath = mPath;
        mUsers = new ArrayList<>();
        mUsers.add(new User("admin", "123456"));
        mUsers.add(new User("mengzicheng", "111111"));
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public ArrayList<User> getmUsers() {
        return mUsers;
    }

    public void setmUsers(ArrayList<User> mUsers) {
        this.mUsers = mUsers;
    }

    /**
     * 保存对象到序列化文件
     */
    public void Save(){
        String fileName = getmPath() + "users";
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
        String fileName = getmPath() + "users";
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            CloneWith((UserManager) ois.readObject());
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void CloneWith(UserManager userManagers){
        this.setmPath(userManagers.getmPath());
        this.setmUsers(userManagers.getmUsers());
    }

    public boolean Login(String username, String password){
        for(User user : getmUsers()){
            if (user.getmName().equals(username) && user.getmPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public boolean HasUser(String username){
        for(User user : getmUsers()){
            if (user.getmName().equals(username)){
                return true;
            }
        }
        return false;
    }

    public boolean IfHasPrivilege(String username, String privilege){
        for(User user : getmUsers()){
            if (user.getmName().equals(username) && user.getmKeys().contains(privilege)){
                return true;
            }
        }
        return false;
    }

    public void Grant(String username, String privilege){
        for(User user : getmUsers()){
            if (user.getmName().equals(username)){
                user.getmKeys().add(privilege);
            }
        }
    }

    public void Revoke(String username, String privilege){
        for(User user : getmUsers()){
            if (user.getmName().equals(username)){
                user.getmKeys().remove(privilege);
            }
        }
    }
}
