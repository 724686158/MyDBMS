package database.SQL;

import database.Exception.SyntaxErrorException;
import database.Values;

import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/12/20.
 */
public class SQLRevoke extends SQL{
    private int mType;
    private String mName;
    private String mUserName;

    public SQLRevoke(ArrayList<String> SQLs) throws SyntaxErrorException {
        Parse(SQLs);
    }

    public void Parse(ArrayList<String> SQLs) throws SyntaxErrorException{
        setSQLType(Values.SQL_REVOKE);
        if (SQLs.size() < 5){
            throw new SyntaxErrorException();
        } else {
            int pos = 0;
            if (SQLs.get(1).toLowerCase().equals("create") && SQLs.get(2).toLowerCase().equals("schema")){
                pos = 2;
                setmType(Values.PRIVILEGE_CREATESCHEMA);
            } else if (SQLs.get(1).toLowerCase().equals("create") && SQLs.get(2).toLowerCase().equals("table")){
                pos = 2;
                setmType(Values.PRIVILEGE_CREATETABLE);
            } else if (SQLs.get(1).toLowerCase().equals("create") && SQLs.get(2).toLowerCase().equals("view")){
                setmType(Values.PRIVILEGE_CREATEVIEW);
            } else if (SQLs.get(1).toLowerCase().equals("create") && SQLs.get(2).toLowerCase().equals("index")){
                pos = 2;
                setmType(Values.PRIVILEGE_CREATEINDEX);
            } else if (SQLs.get(1).toLowerCase().equals("select")){
                pos = 1;
                setmType(Values.PRIVILEGE_SELECT);
            } else if (SQLs.get(1).toLowerCase().equals("insert")){
                pos = 1;
                setmType(Values.PRIVILEGE_INSERT);
            } else if (SQLs.get(1).toLowerCase().equals("update")){
                pos = 1;
                setmType(Values.PRIVILEGE_UPDATE);
            } else if (SQLs.get(1).toLowerCase().equals("delete")){
                pos = 1;
                setmType(Values.PRIVILEGE_DELETE);
            } else if (SQLs.get(1).toLowerCase().equals("references")){
                pos = 1;
                setmType(Values.PRIVILEGE_REFERENCES);
            } else if (SQLs.get(1).toLowerCase().equals("alter") && SQLs.get(2).toLowerCase().equals("table")){
                pos = 2;
                setmType(Values.PRIVILEGE_ALTERTABLE);
            } else if (SQLs.get(1).toLowerCase().equals("all") && SQLs.get(2).toLowerCase().equals("privileges")){
                pos = 2;
                setmType(Values.PRIVILEGE_ALL);
            }
            pos++;
            if (!(SQLs.get(pos).toLowerCase().equals("on"))){
                throw new SyntaxErrorException();
            }
            pos++;
            setmName(SQLs.get(pos));
            pos++;
            if (!(SQLs.get(pos).toLowerCase().equals("to"))){
                throw new SyntaxErrorException();
            }
            pos++;
            setmUserName(SQLs.get(pos));
            pos++;
            if (pos != SQLs.size()){
                throw new SyntaxErrorException();
            }
        }
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }
}
