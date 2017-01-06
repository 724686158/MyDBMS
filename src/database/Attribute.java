package database;

import java.io.Serializable;

/**
 * Created by 离子态狍子 on 2016/11/26.
 */
public class Attribute implements Serializable {
    private String mName;
    private int mDataType;
    private int mLenght;
    private int mAttributeType;

    public Attribute() {
        this.mName = "";
        this.mDataType = Values.NOT_DEFINE;
        this.mLenght = Values.NOT_DEFINE;
        this.mAttributeType = Values.ATTRIBUTE_NOTPRIMARY;

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

    public int getmLenght() {
        return mLenght;
    }

    public void setmLenght(int mLenght) {
        this.mLenght = mLenght;
    }

    public int getmAttributeType() {
        return mAttributeType;
    }

    public void setmAttributeType(int mAttributeType) {
        this.mAttributeType = mAttributeType;
    }
}
