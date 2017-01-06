package database;

import database.SQL.SQLSelect;

/**
 * Created by 离子态狍子 on 2016/12/11.
 */
public class View {
    private String mViewName;
    private SQLSelect mSqlSelect;

    public View(String mViewName) {
        this.mViewName = mViewName;
    }

    public String getmViewName() {
        return mViewName;
    }

    public void setmViewName(String mViewName) {
        this.mViewName = mViewName;
    }

    public SQLSelect getmSqlSelect() {
        return mSqlSelect;
    }

    public void setmSqlSelect(SQLSelect mSqlSelect) {
        this.mSqlSelect = mSqlSelect;
    }
}
