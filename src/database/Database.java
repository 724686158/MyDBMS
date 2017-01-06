package database;

import com.sun.org.apache.xpath.internal.operations.Bool;
import database.SQL.SQLCreateTable;
import database.SQL.SQLDropIndex;
import database.SQL.SQLDropTable;
import javafx.scene.control.Tab;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/25.
 */
public class Database implements Serializable {
    private String mName;
    ArrayList<Table> mTables;
    ArrayList<View> mViews;
    public Database(String mName) {
        this.mName = mName;
        this.mTables = new ArrayList<>();
        this.mViews = new ArrayList<>();
    }

    public ArrayList<View> getmViews() {
        return mViews;
    }

    public void setmViews(ArrayList<View> mViews) {
        this.mViews = mViews;
    }



    public String getName() {
        return mName;
    }

    public ArrayList<Table> getTables() {
        return mTables;
    }

    public Table getTableByName(String name) {
        for(Table t : this.mTables){
            if (t.getmName().equals(name)){
                return t;
            }
        }
        return null;
    }

    public View getViewByName(String name){
        for (View view : this.mViews){
            if (view.getmViewName().equals(name)){
                return view;
            }
        }
        return null;
    }

    public void CreateTable(SQLCreateTable SQL){
        int recordLength = 0;
        Table table = new Table();
        ArrayList<Attribute> attributes = SQL.getmAttributes();
        for (Attribute a : attributes){
            table.AddAtribute(a);
            recordLength += a.getmLenght();
        }
        table.setmName(SQL.getmTableName());
        table.setmLength(recordLength);
        getTables().add(table);

    }

    public void DropTable(SQLDropTable SQL){
        Table deleteTB = null;
        for (Table table : getTables()){
            if (table.getmName().equals(SQL.getmName())){
                deleteTB = table;
            }
        }
        getTables().remove(deleteTB);
    }

    public void DropIndex(SQLDropIndex SQL){
        Index deleteID = null;
        for (Table table : getTables()){
            for (Index index : table.getIndexs()){
                if (index.getmName().equals(SQL.getmName())){
                    deleteID = index;
                }
            }
            table.getIndexs().remove(deleteID);
        }
    }

    public boolean CheckIfIndexExists(String indexName){
        boolean exists = false;
        for (Table t : this.mTables){
            for(Index i : t.getIndexs()){
                if (i.getmName() == indexName){
                    exists = true;
                    return exists;
                }
            }
        }
        return exists;
    }
}
