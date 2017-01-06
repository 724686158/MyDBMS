package database;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/20.
 */
public class Column implements Serializable{

    public static final String Tag = "database.Column";

    private String mName;
    private int mDataType;
    private boolean mIdentity;
    private boolean mNullable;
    private ArrayList<String> mDatas;


    public Column(Attribute attribute){
        this.mName = attribute.getmName();
        this.mDataType = attribute.getmDataType();
        this.mDatas = new ArrayList<>();
        if (attribute.getmAttributeType() == Values.ATTRIBUTE_PRIMARY){
            this.mIdentity = true;
            this.mNullable = false;
        } else {
            this.mIdentity = false;
            this.mNullable = true;
        }
    }

    public Column(Column column){
        this.mName = column.getmName();
        this.mDataType = column.getmDataType();
        this.mIdentity = column.ismIdentity();
        this.mNullable = column.ismNullable();
        this.mDatas = new ArrayList<>();
    }


    public static String getTag() {
        return Tag;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmDataType() {
        return mDataType;
    }

    public void setmDataType(int mDataType) {
        this.mDataType = mDataType;
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

    public ArrayList<String> getmDatas() {
        return mDatas;
    }

    public void setmDatas(ArrayList<String> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public String toString() {
        return "Column{" +
                "mName='" + mName + '\'' +
                ", mDataType=" + mDataType +
                ", mIdentity=" + mIdentity +
                ", mNullable=" + mNullable +
                ", mDatas=" + mDatas +
                '}';
    }
}
