package database;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/20.
 */
public class Columns implements Serializable {

    private String mName;
    private ArrayList<Column> mColumns;

    public Columns(Table table){
        this.mName = table.getmName();
        this.mColumns = new ArrayList<>();
        for(Attribute attribute : table.getAttributes()){
            mColumns.add(new Column(attribute));
        }
    }

    public Columns(Columns columns){
        this.mName = columns.getmName();
        this.mColumns = new ArrayList<>();
    }

    public Columns(String mName, ArrayList<Column> mColumns) {
        this.mName = mName;
        this.mColumns = mColumns;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public ArrayList<Column> getmColumns() {
        return mColumns;
    }

    public void setmColumns(ArrayList<Column> mColumns) {
        this.mColumns = mColumns;
    }

    @Override
    public String toString() {
        return "Columns{" +
                "mName='" + mName + '\'' +
                ", mColumns=" + mColumns +
                '}';
    }
}
