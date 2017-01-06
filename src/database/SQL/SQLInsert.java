package database.SQL;

import database.Exception.SyntaxErrorException;
import database.Values;

import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/28.
 */
public class SQLInsert extends SQL{
    private String mName;
    private ArrayList<SQLValue> mValues;

    public SQLInsert(ArrayList<String> SQLs) throws SyntaxErrorException {
        mValues = new ArrayList<>();
        Parse(SQLs);
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public ArrayList<SQLValue> getmValues() {
        return mValues;
    }

    public void setmValues(ArrayList<SQLValue> mValues) {
        this.mValues = mValues;
    }
    public void Parse(ArrayList<String> SQLs) throws SyntaxErrorException {
        //System.out.println("!INSERT!" + SQLs);
        setSQLType(Values.SQL_INSERT);
        int pos = 1;
        boolean isAttribute = true;
        String s0 = SQLs.get(pos).toLowerCase();
        if (!(s0.equals("into"))){
            throw new SyntaxErrorException();
        }
        pos++;
        setmName(SQLs.get(pos));
        pos++;
        String s1 = SQLs.get(pos).toLowerCase();
        if (!(s1.equals("values"))){
            throw new SyntaxErrorException();
        }
        pos++;
        if (!(SQLs.get(pos).equals("("))){
            throw new SyntaxErrorException();
        }
        pos++;
        while (isAttribute){
            isAttribute = false;
            SQLValue sqlValue = new SQLValue();
            String val = SQLs.get(pos);
            if (val.charAt(0) == '\'' || val.charAt(0) == '\"'){
                val = val.substring(1, val.length() - 1);
                sqlValue.dataType = (Values.DATA_CHAR);
            } else {
                //在不存在字符串的情况下含有小数点，就判断为浮点类型
                if (val.contains(".")){
                    sqlValue.dataType = (Values.DATA_FLOAT);
                } else {
                    sqlValue.dataType = (Values.DATA_INT);
                }
            }
            sqlValue.value = val;
            getmValues().add(sqlValue);
            pos++;
            if (!(SQLs.get(pos).equals(")"))){
                isAttribute = true;
            }
            pos++;
        }
    }
}
