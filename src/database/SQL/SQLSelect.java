package database.SQL;

import database.Exception.SyntaxErrorException;
import database.Values;

import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/28.
 */
public class SQLSelect extends SQL {

    private String mTableName; //被查询的表的名字
    private ArrayList<String> mColumnNames; //查询的列
    private ArrayList<SQLWhere> mWheres; //查询键值对


    public SQLSelect(ArrayList<String> SQLs) throws SyntaxErrorException {
        mWheres = new ArrayList<>();
        mColumnNames = new ArrayList<>();
        Parse(SQLs);
    }

    public ArrayList<String> getmColumnNames() {
        return mColumnNames;
    }

    public void setmColumnNames(ArrayList<String> mColumnNames) {
        this.mColumnNames = mColumnNames;
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


    public void Parse(ArrayList<String> SQLs) throws SyntaxErrorException{
        //System.out.println(SQLs);
        setSQLType(Values.SQL_SELECT);
        int pos = 1;
        if (SQLs.size() <= 1){
            throw new SyntaxErrorException();
        }
        if ((SQLs.get(pos).equals("*"))){
            pos++;
        } else {
            while (pos < SQLs.size()){
                String s0 = SQLs.get(pos);
                getmColumnNames().add(s0);
                pos++;
                if (pos >= SQLs.size()){
                    throw new SyntaxErrorException();
                }
                String s1 = SQLs.get(pos);
                if (s1.equals(",")){
                    pos++;
                }
                else {
                    break;
                }

            }
        }
        String s0 = SQLs.get(pos).toLowerCase();
        //System.out.print("!" + s0 + "!\n");
        if (s0.equals("from")){
            pos++;
        } else {
            throw new SyntaxErrorException();
        }
        setmTableName(SQLs.get(pos));
        pos++;
        if (SQLs.size() == pos){
            //System.out.println(getmColumnNames());
            //System.out.println(getmWheres());
            return;
        }
        if (pos >= SQLs.size()){
            throw new SyntaxErrorException();
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
        //System.out.println(getmColumnNames());
        //System.out.println(getmWheres());
    }
}
