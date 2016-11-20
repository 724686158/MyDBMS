package database;

import java.io.Serializable;

/**
 * Created by 离子态狍子 on 2016/11/20.
 */
public class Column implements Cloneable, Serializable{

    public static final String Tag = "database.Column";

    private String name;
    private int dataType;
    private boolean mIdentity = false;
    private boolean mNullable = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public boolean ismIdentity() {
        return mIdentity;
    }

    public void setmIdentity(boolean mIdentity) {
        this.mIdentity = mIdentity;
    }

    public boolean ismNullable() {
        return mNullable;
    }

    public void setmNullable(boolean mNullable) {
        this.mNullable = mNullable;
    }

    Column copy(){
        try {
            return (Column)clone();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
