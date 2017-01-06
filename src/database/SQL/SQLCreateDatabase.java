package database.SQL;

import database.Exception.SyntaxErrorException;
import database.Values;

import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/27.
 */
public class SQLCreateDatabase extends SQL {
    private String mDatabaseName;

    public SQLCreateDatabase(ArrayList<String> SQLs) throws SyntaxErrorException {
        Parse(SQLs);
    }

    public String getmDatabaseName() {
        return mDatabaseName;
    }

    public void setmDatabaseName(String mDatabaseName) {
        this.mDatabaseName = mDatabaseName;
    }

    public void Parse(ArrayList<String> SQLs) throws SyntaxErrorException {
        setSQLType(Values.SQL_CREATEDATABASE);
        if (SQLs.size() <= 2){
            throw new SyntaxErrorException();
        } else {
            setmDatabaseName(SQLs.get(2));
        }
    }
}




