package database.SQL;

import database.Attribute;
import database.Exception.SyntaxErrorException;
import database.Values;

import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/27.
 */
public class SQLCreateTable extends SQL {
    private String mTableName;
    private ArrayList<Attribute> mAttributes;

    public SQLCreateTable(ArrayList<String> SQLs) throws SyntaxErrorException {
        mTableName = "";
        mAttributes = new ArrayList<>();
        Parse(SQLs);
    }


    public String getmTableName() {
        return mTableName;
    }

    public void setmTableName(String mTableName) {
        this.mTableName = mTableName;
    }

    public ArrayList<Attribute> getmAttributes() {
        return mAttributes;
    }

    public void setmAttributes(ArrayList<Attribute> mAttributes) {
        this.mAttributes = mAttributes;
    }

    public void Parse(ArrayList<String> SQLs) throws SyntaxErrorException {
        setSQLType(Values.SQL_CREATETABLE);
        int pos = 2;
        if (SQLs.size() <= pos){
            throw new SyntaxErrorException();
        }
        setmTableName(SQLs.get(pos));
        pos++;
        if (!(SQLs.get(pos).equals("("))) {
            throw new SyntaxErrorException();
        }
        pos++;
        boolean isAttribute = true;
        boolean hasPrimaryKey = false;
        while (isAttribute){
            isAttribute = false;
            //当属性是主键时
            if (SQLs.get(pos).equals("primary")){
                    if (hasPrimaryKey){
                    throw new SyntaxErrorException();
                }
                pos++;
                if (!(SQLs.get(pos).equals("key"))){
                    throw new SyntaxErrorException();
                }
                pos++;
                if (!(SQLs.get(pos).equals("("))){
                    throw new SyntaxErrorException();
                }
                pos++;
                for (Attribute a : this.getmAttributes()){
                    if (a.getmName().equals(SQLs.get(pos))){
                        a.setmAttributeType(Values.ATTRIBUTE_PRIMARY);
                        hasPrimaryKey = true;
                    }
                }
                pos++;
                if (!(SQLs.get(pos).equals(")"))){
                    throw new SyntaxErrorException();
                }
            } else {
                Attribute attribute = new Attribute();
                attribute.setmName((SQLs.get(pos)));
                isAttribute = true;
                pos++;
                pos = ParseDataType(SQLs, attribute, pos);
                this.getmAttributes().add(attribute);
                if (SQLs.get(pos).equals(")")){
                    isAttribute = false;
                }
            }
        }
    }

}
