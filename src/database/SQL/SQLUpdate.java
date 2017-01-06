package database.SQL;

import database.Exception.SyntaxErrorException;
import database.Values;

import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/28.
 */
public class SQLUpdate extends SQL{
    private String mTableName;
    private ArrayList<SQLWhere> mWheres;
    private ArrayList<SQLKeyValue> mKeyValues;

    public SQLUpdate(ArrayList<String> SQLs) throws SyntaxErrorException {
        mWheres = new ArrayList<>();
        mKeyValues = new ArrayList<>();
        Parse(SQLs);
    }

    public String getmTableName() {
        return mTableName;
    }

    public void setmTableName(String mTableName) {
        this.mTableName = mTableName;
    }

    public ArrayList<SQLWhere> getmWheres() {
        return mWheres;
    }

    public void setmWheres(ArrayList<SQLWhere> mWheres) {
        this.mWheres = mWheres;
    }

    public ArrayList<SQLKeyValue> getmKeyValues() {
        return mKeyValues;
    }

    public void setmKeyValues(ArrayList<SQLKeyValue> mKeyValues) {
        this.mKeyValues = mKeyValues;
    }

    public void Parse(ArrayList<String> SQLs) throws SyntaxErrorException {
        //System.out.println(SQLs);
        setSQLType(Values.SQL_UPDATE);
        int pos = 1;
        if (SQLs.size() <= 1) {
            throw new SyntaxErrorException();
        }
        setmTableName(SQLs.get(pos));
        pos++;
        String s0 = SQLs.get(pos).toLowerCase();
        if (!(s0.equals("set"))){
            throw new SyntaxErrorException();
        }
        pos++;
        while (pos < SQLs.size()){
            SQLKeyValue sqlKeyValue = new SQLKeyValue();
            sqlKeyValue.key = SQLs.get(pos);
            pos++;
            if (!(SQLs.get(pos).equals("="))){
                throw new SyntaxErrorException();
            }
            pos++;
            sqlKeyValue.value = SQLs.get(pos);
            if (sqlKeyValue.value.charAt(0) == '\'' || sqlKeyValue.value.charAt(0) == '\"'){
                sqlKeyValue.value = sqlKeyValue.value.substring(1, sqlKeyValue.value.length() - 1);
            }
            getmKeyValues().add(sqlKeyValue);
            pos++;
            if (pos < SQLs.size()){
                if (SQLs.get(pos).equals(",")){
                    pos++;
                } else if (SQLs.get(pos).toLowerCase().equals("where")){
                    break;
                } else {
                    throw new SyntaxErrorException();
                }
            }
        }
        if (pos >= SQLs.size()){
            throw new SyntaxErrorException();
        }
        if (!(SQLs.get(pos).equals("where"))){
            throw new SyntaxErrorException();
        }
        pos++;
        while (pos < SQLs.size()){
            SQLWhere sqlWhere = new SQLWhere();
            sqlWhere.key = (SQLs.get(pos));
            pos++;
            //根据符号判断where的比较类型
            if (SQLs.get(pos).equals("=")){
                sqlWhere.type = (Values.SIGN_EQ);
            } else if (SQLs.get(pos).equals("<>")){
                sqlWhere.type = (Values.SIGN_NE);
            } else if (SQLs.get(pos).equals("<")){
                sqlWhere.type = (Values.SIGN_LT);
            } else if (SQLs.get(pos).equals(">")){
                sqlWhere.type = (Values.SIGN_GT);
            } else if (SQLs.get(pos).equals("<=")){
                sqlWhere.type = (Values.SIGN_LE);
            } else if (SQLs.get(pos).equals(">=")){
                sqlWhere.type = (Values.SIGN_GE);
            }
            pos++;
            if (pos >= SQLs.size()){
                throw new SyntaxErrorException();
            }
            String s2 = SQLs.get(pos);
            if (s2.charAt(0) == '\'' || s2.charAt(0) == '\"'){
                sqlWhere.value = s2.substring(1, s2.length() - 1);
            } else {
                sqlWhere.value = s2;
            }
            getmWheres().add(sqlWhere);
            pos++;
            if (pos >= SQLs.size()){
                break;
            } else {
                if (pos >= SQLs.size()){
                    throw new SyntaxErrorException();
                }
                String s3 = SQLs.get(pos).toLowerCase();
                if (!(s3.equals("and")) && pos != SQLs.size()){
                    throw new SyntaxErrorException();
                }
                pos++;
            }
        }
        //System.out.println(getmKeyValues());
        //System.out.println(getmWheres());

    }

}
