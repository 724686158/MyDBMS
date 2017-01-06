package database.SQL;

import database.Exception.SyntaxErrorException;
import database.Values;

import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/27.
 */
public class SQLCreateIndex extends SQL {

    private String mIndexName;
    private String mTableName;
    private String mColumnName;

    public SQLCreateIndex(ArrayList<String> SQLs) throws SyntaxErrorException {
        Parse(SQLs);
    }

    public String getmIndexName() {
        return mIndexName;
    }

    public void setmIndexName(String mIndexName) {
        this.mIndexName = mIndexName;
    }

    public String getmTableName() {
        return mTableName;
    }

    public void setmTableName(String mTableName) {
        this.mTableName = mTableName;
    }

    public String getmColumnName() {
        return mColumnName;
    }

    public void setmColumnName(String mColumnName) {
        this.mColumnName = mColumnName;
    }


    public void Parse(ArrayList<String> SQLs) throws SyntaxErrorException {
        this.setSQLType(Values.SQL_CREATEINDEX);
        int pos = 2;
        if (SQLs.size() <= pos) {
            throw new SyntaxErrorException();
        }
        setmIndexName(SQLs.get(pos));
        pos++;
        String s1 = SQLs.get(pos).toLowerCase();
        if (!(s1.equals("on"))){
            throw new SyntaxErrorException();
        }
        pos++;
        setmTableName(SQLs.get(pos));
        pos++;
        if (!(SQLs.get(pos).equals("("))){
            throw new SyntaxErrorException();
        }
        pos++;
        setmColumnName(SQLs.get(pos));
        pos++;
        if (!(SQLs.get(pos).equals(")"))){
            throw new SyntaxErrorException();
        }
        pos++;
    }

}
