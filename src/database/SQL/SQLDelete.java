package database.SQL;

import database.Exception.SyntaxErrorException;
import database.Values;

import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/28.
 */
public class SQLDelete extends SQL{
    private String mTableName;
    private ArrayList<SQLWhere> mWheres;

    public SQLDelete(ArrayList<String> SQLs) throws SyntaxErrorException {

        mTableName = "";
        mWheres = new ArrayList<>();
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

    public void Parse(ArrayList<String> SQLs) throws SyntaxErrorException {
        setSQLType(Values.SQL_DELETE);
        int pos = 1;
        if (SQLs.size() <= 1){
            throw new SyntaxErrorException();
        }
        String s0 = SQLs.get(pos).toLowerCase();
        if (!(s0.equals("from"))){
            throw new SyntaxErrorException();
        }
        pos++;
        setmTableName(SQLs.get(pos));
        pos++;
        if (SQLs.size() == pos){
            return;
        }
        String s1 = SQLs.get(pos).toLowerCase();
        if (!(s1.equals("where"))){
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
    }
}
