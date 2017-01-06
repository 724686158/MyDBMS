package database.SQL;

import database.Exception.SyntaxErrorException;
import database.Values;

import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/28.
 */
public class SQLDropTable extends SQL{
    private String mName;
    public SQLDropTable(ArrayList<String> SQLs) throws SyntaxErrorException {
        Parse(SQLs);
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void Parse(ArrayList<String> SQLs) throws SyntaxErrorException{
        setSQLType(Values.SQL_DROPTABLE);
        if (SQLs.size() <= 2){
            throw new SyntaxErrorException();
        } else {
            setmName(SQLs.get(2));
        }
    }
}
