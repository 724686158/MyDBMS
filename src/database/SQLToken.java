package database;

/**
 * Created by 离子态狍子 on 2016/11/22.
 */

/**
 * sql语句关键词的提取工具
 */
public class SQLToken {
    private int mValue;
    private int mOffset;
    private int mLength;
    private String mName;

    public SQLToken(int mValue, int tokenStart, int tokenEnd) {
        this.mValue = mValue;
        this.mOffset = tokenStart;
        this.mLength = tokenEnd - tokenStart;
    }

    public SQLToken(String name, int mValue, int tokenStart, int tokenEnd) {
        this.mName = name;
        this.mValue = mValue;
        this.mOffset = tokenStart;
        this.mLength = tokenEnd - tokenStart;
    }

    public String gatName(String sql){
        if (mName != null) {
            return mName;
        } else {
            return sql.substring(mOffset, mOffset + mLength);
        }
    }

    public int getLength(){
        return this.mLength;
    }

}
