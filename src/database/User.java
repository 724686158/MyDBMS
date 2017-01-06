package database;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/12/20.
 */
public class User implements Serializable {
    private String mName;
    private String mPassword;
    private ArrayList<String> mKeys;

    public User(String mName, String mPassword) {
        this.mName = mName;
        this.mPassword = mPassword;
        mKeys = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "mName='" + mName + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mKeys=" + mKeys +
                '}';
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public ArrayList<String> getmKeys() {
        return mKeys;
    }

    public void setmKeys(ArrayList<String> mKeys) {
        this.mKeys = mKeys;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
