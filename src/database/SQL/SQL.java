package database.SQL;

import database.Attribute;
import database.Exception.SyntaxErrorException;
import database.Values;

import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/26.
 */
public class SQL {
    private int mSQLType;

    public SQL() {
    }

    public SQL(int mSQLType) {
        this.mSQLType = mSQLType;
    }

    public int getSQLType() {
        return mSQLType;
    }

    public void setSQLType(int mSQLType) {
        this.mSQLType = mSQLType;
    }

    public int ParseDataType(ArrayList<String> SQLs, Attribute attribute, int pos) throws SyntaxErrorException {
        boolean right = false;
        String type = SQLs.get(pos).toLowerCase();
        if (type.equals("int")){
            attribute.setmDataType(Values.DATA_INT);
            attribute.setmLenght(4);
            pos++;
            if (SQLs.get(pos).equals(",")){
                pos++;
                right = true;
            }
        } else if (type.equals("float")){
            attribute.setmDataType(Values.DATA_FLOAT);
            attribute.setmLenght(4);
            pos++;
            if (SQLs.get(pos).equals(",")){
                pos++;
                right = true;
            }
        } else if (type.equals("char")){
            attribute.setmDataType(Values.DATA_CHAR);
            right = true;
            pos++;
            if (SQLs.get(pos).equals("(")){
                right = false;
                pos++;
                attribute.setmLenght(Integer.parseInt(SQLs.get(pos)));
                pos++;
                if (SQLs.get(pos).equals(")")){
                    pos++;
                    pos++;//我解析部分会在后括号前解析出一个空字符串。不想改了......
                    if (SQLs.get(pos).equals(",")){
                        pos++;
                        right = true;
                    }
                }
            } else if (SQLs.get(pos).equals(",")){
                pos++;
                attribute.setmLenght(1);
            }
        }
        if (right)
        {
            return pos;
        } else {
            throw new SyntaxErrorException();
        }
    }

    public void Parse(ArrayList<String> SQLs) throws SyntaxErrorException {

    }
}
