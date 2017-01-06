package database;

import database.Exception.CannotInsertException;
import database.Exception.ColumnCannotFoundException;
import database.SQL.*;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/28.
 */
public class BufferManager {

    private String mPath;
    private String mDatabaseName;

    public BufferManager(String path, String databaseName) {
        this.mPath = path;
        this.mDatabaseName = databaseName;
    }

    public String getmDatabaseName() {
        return mDatabaseName;
    }

    public void setmDatabaseName(String mDatabaseName) {
        this.mDatabaseName = mDatabaseName;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public void CreateDataFileOfTable(Table table) throws IOException {
        Columns columns = new Columns(table);
        FileOutputStream fos = new FileOutputStream(mPath + getmDatabaseName() + "/" + table.getmName() + ".data");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(columns);
        oos.flush();
        oos.close();
    }

    public void SaveData(Columns columns) throws IOException {
        FileOutputStream fos = new FileOutputStream(mPath + getmDatabaseName() + "/" + columns.getmName() + ".data");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(columns);
    }

    public void ClearData(String tableName) throws IOException, ClassNotFoundException {
        Columns columns = ReadTableData(tableName);
        for(Column column : columns.getmColumns()){
            column.getmDatas().clear();
        }
        FileOutputStream fos = new FileOutputStream(mPath + getmDatabaseName() + "/" + tableName + ".data");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(columns);
    }

    public Columns ReadTableData(String tableName) throws IOException, ClassNotFoundException {
        Columns columns;
        FileInputStream fis = new FileInputStream(mPath + getmDatabaseName() + "/" + tableName + ".data");
        ObjectInputStream ois = new ObjectInputStream(fis);
        columns = (Columns) ois.readObject();
        ois.close();
        return columns;
    }



    public ArrayList<ArrayList<String>> ChangeToAA(ArrayList<Column> columns){

        int cnum = columns.size();
        int lnum = columns.get(0).getmDatas().size();
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (int i = 0; i < lnum; i++){
            data.add(new ArrayList<>());
            for (Column column : columns){
                data.get(i).add(column.getmDatas().get(i));
            }
        }
        return data;
    }

    public void ShowColumns(ArrayList<Column> columns){
        ArrayList<ArrayList<String>> data = ChangeToAA(columns);
        System.out.println("-------------------------------------------------");
        System.out.print("-" + "*" + "- |");
        for (int j = 0; j < columns.size(); j++){
            System.out.print(columns.get(j).getmName() + "|");
        }
        System.out.print("\n");
        for (int i = 0; i < data.size(); i++){
            System.out.print("-" + i + "- |");
            for (int j = 0; j < data.get(i).size(); j++){
                System.out.print(data.get(i).get(j) + "|");
            }
            System.out.print("\n");
        }
        System.out.println("-------------------------------------------------");
    }

    public void ShowTableData(String tableName) throws IOException, ClassNotFoundException {
        Columns columns = ReadTableData(tableName);
        if (columns.getmColumns().size() == 0){
            System.out.println("表" + tableName + "为空");
        } else {
            ArrayList<ArrayList<String>> data = ChangeToAA(columns.getmColumns());
            System.out.println("-------------------------------------------------");
            System.out.print("-" + "*" + "- |");
            for (int j = 0; j < columns.getmColumns().size(); j++){
                System.out.print(columns.getmColumns().get(j).getmName() + "|");
            }
            System.out.print("\n");
            for (int i = 0; i < data.size(); i++){
                System.out.print("-" + i + "- |");
                for (int j = 0; j < data.get(i).size(); j++){
                    System.out.print(data.get(i).get(j) + "|");
                }
                System.out.print("\n");
            }
            System.out.println("-------------------------------------------------");
        }
    }

    public void InsertData(SQLInsert SQL) throws CannotInsertException, IOException, ClassNotFoundException {
        Columns columns = ReadTableData(SQL.getmName());
        ArrayList<SQLValue> values = SQL.getmValues();
        int csize = columns.getmColumns().size();
        int vsize = values.size();
        if (csize != vsize){
            throw new CannotInsertException();
        } else {
            for(int i = 0; i < csize; i++){
                if (columns.getmColumns().get(i).getmDataType() != values.get(i).getDataType()){
                    throw new CannotInsertException();
                }
            }
            for(int i = 0; i < csize; i++){
                //向每列中插入一个数据
                columns.getmColumns().get(i).getmDatas().add(values.get(i).getValue());
            }
        }
        SaveData(columns);//将修改好的数据保存起来
    }
    public void SelectData(SQLSelect SQL) throws IOException, ClassNotFoundException, ColumnCannotFoundException {
        Columns columns = ReadTableData(SQL.getmTableName());
        ArrayList<Column> needColumns = new ArrayList<>();
        if (SQL.getmColumnNames().size() == 0){
            needColumns = columns.getmColumns();
        } else {
            boolean columnsAllRight = true;
            for (String columnName : SQL.getmColumnNames()){
                boolean find = false;
                for (Column column : columns.getmColumns()){
                    if (column.getmName().equals(columnName)){
                        find = true;
                        needColumns.add(column);
                    }
                }
                if (!find){
                    columnsAllRight = false;
                }
            }
            if (!columnsAllRight){
                throw new ColumnCannotFoundException();
            }
        }
        if (SQL.getmWheres().size() == 0){
            ShowColumns(needColumns);
        } else {
            ShowColumns(GetColumnsAfterSelect(needColumns, SQL.getmWheres()));
        }
    }

    public void DeleteData(SQLDelete SQL) throws IOException, ClassNotFoundException {
        Columns columns = ReadTableData(SQL.getmTableName());
        ArrayList<Column> needColumns = new ArrayList<>();
        needColumns = columns.getmColumns();

        if (SQL.getmWheres().size() == 0){
            ClearData(SQL.getmTableName());
        } else {

            Columns nowColumns = new Columns(SQL.getmTableName(), GetColumnsAfterDelete(needColumns, SQL.getmWheres()));
            SaveData(nowColumns);
        }
    }

    public void UpdateData(SQLUpdate SQL) throws IOException, ClassNotFoundException {
        Columns columns = ReadTableData(SQL.getmTableName());
        ArrayList<Column> needColumns = new ArrayList<>();
        ArrayList<Column> ansColumns = new ArrayList<>();
        if (SQL.getmWheres().size() == 0){
            needColumns = columns.getmColumns();
            ansColumns = ChangeData(columns.getmColumns(), needColumns, SQL.getmKeyValues());
            Columns nowColumns = new Columns(SQL.getmTableName(), ansColumns);
            SaveData(nowColumns);
        } else {
            needColumns = columns.getmColumns();
            ansColumns = ChangeData(columns.getmColumns(), GetColumnsAfterSelect(needColumns, SQL.getmWheres()), SQL.getmKeyValues());
            Columns nowColumns = new Columns(SQL.getmTableName(), ansColumns);
            SaveData(nowColumns);
        }
    }

    public ArrayList<Column> ChangeData(ArrayList<Column> allColumns, ArrayList<Column> needColumns, ArrayList<SQLKeyValue> keyValues){
        //ystem.out.println(allColumns);
        //System.out.println(needColumns);
        //System.out.println(keyValues);
        if (allColumns.size() != 0){
            int lnum = allColumns.get(0).getmDatas().size();
            for (int i = 0; i < lnum; i++){
                boolean allright = true;
                for (int j = 0; j < needColumns.size(); j++){
                    boolean find = false;
                    for(int k = 0; k < allColumns.size(); k++){
                        if (needColumns.get(j).getmName().equals(allColumns.get(k).getmName())){
                            if (needColumns.get(j).getmDatas().contains(allColumns.get(k).getmDatas().get(i))){
                                find = true;
                            }
                        }
                    }
                    if (!find){
                        allright = false;
                    }
                }
                if (allright){
                    //System.out.println("ALLRIGHT");
                    for (int j = 0; j < allColumns.size(); j++)
                    {
                        for (int k = 0; k < keyValues.size(); k++){
                            if (allColumns.get(j).getmName().equals(keyValues.get(k).getKey())){
                                allColumns.get(j).getmDatas().set(i, keyValues.get(k).getValue());
                            }
                        }
                    }
                }
            }
        }
        return allColumns;
    }

    public ArrayList<Column> GetColumnsAfterDelete(ArrayList<Column> columns, ArrayList<SQLWhere> wheres){
        //System.out.println(columns);
        ArrayList<Column> ansColumns = new ArrayList<>();
        ArrayList<Column> rightColumns = new ArrayList<>();
        for(Column column : columns){
            ansColumns.add(new Column(column)); //先把列信息拷给答案。
            for (SQLWhere sqlWhere : wheres){
                if (sqlWhere.key.equals(column.getmName())){
                    rightColumns.add(GetColunmAfterSelect(column, sqlWhere));
                } else {
                    rightColumns.add(column);
                }
            }
        }
        if (columns.size() != 0){
            for (int i = 0; i < columns.get(0).getmDatas().size(); i++){
                boolean allright = true;
                for (int j = 0; j < columns.size(); j++){
                    if (!(rightColumns.get(j).getmDatas().contains(columns.get(j).getmDatas().get(i)))){
                        allright = false;
                    }
                }
                if (!allright){
                    for (int j = 0; j < columns.size(); j++)
                    {
                        ansColumns.get(j).getmDatas().add(columns.get(j).getmDatas().get(i));
                    }
                }
            }
        }
        return ansColumns;
    }

    public ArrayList<Column> GetColumnsAfterSelect(ArrayList<Column> columns, ArrayList<SQLWhere> wheres){
        //System.out.println(columns);
        ArrayList<Column> ansColumns = new ArrayList<>();
        ArrayList<Column> rightColumns = new ArrayList<>();
        for(Column column : columns){
            ansColumns.add(new Column(column)); //先把列信息拷给答案。
            for (SQLWhere sqlWhere : wheres){
                if (sqlWhere.key.equals(column.getmName())){
                    rightColumns.add(GetColunmAfterSelect(column, sqlWhere));
                } else {
                    rightColumns.add(column);
                }
            }
        }
        if (columns.size() != 0){
            for (int i = 0; i < columns.get(0).getmDatas().size(); i++){
                boolean allright = true;
                for (int j = 0; j < columns.size(); j++){
                    if (!(rightColumns.get(j).getmDatas().contains(columns.get(j).getmDatas().get(i)))){
                        allright = false;
                    }
                }
                if (allright){
                    for (int j = 0; j < columns.size(); j++)
                    {
                        ansColumns.get(j).getmDatas().add(columns.get(j).getmDatas().get(i));
                    }
                }
            }
        }
        return ansColumns;
    }


    private Column GetColunmAfterSelect(Column column, SQLWhere where){
        //System.out.println(column);
        Column ansColumn = new Column(column);
        if (column.getmDataType() == Values.DATA_INT){
            if (where.getType() == Values.SIGN_EQ){
                //System.out.println("jinlaile");
                //System.out.println(column);
                for (String str : column.getmDatas()){
                    int num1 = Integer.parseInt(str);
                    int num2 = Integer.parseInt(where.value);
                    //System.out.println(num1 + ":" + num2);
                    if (num1 == num2){
                        //System.out.println("他们相等");
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_GE){
                for (String str : column.getmDatas()){
                    int num1 = Integer.parseInt(str);
                    int num2 = Integer.parseInt(where.value);
                    if (num1 >= num2){
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_GT){
                for (String str : column.getmDatas()){
                    int num1 = Integer.parseInt(str);
                    int num2 = Integer.parseInt(where.value);
                    if (num1 > num2){
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_LE){
                for (String str : column.getmDatas()){
                    int num1 = Integer.parseInt(str);
                    int num2 = Integer.parseInt(where.value);
                    if (num1 <= num2){
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_NE){
                for (String str : column.getmDatas()){
                    int num1 = Integer.parseInt(str);
                    int num2 = Integer.parseInt(where.value);
                    if (num1 != num2){
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_LT){
                for (String str : column.getmDatas()){
                    int num1 = Integer.parseInt(str);
                    int num2 = Integer.parseInt(where.value);
                    if (num1 < num2){
                        ansColumn.getmDatas().add(str);
                    }
                }
            }
        } else if (column.getmDataType() == Values.DATA_FLOAT){
            if (where.getType() == Values.SIGN_EQ){
                for (String str : column.getmDatas()){
                    float num1 = Float.parseFloat(str);
                    float num2 = Float.parseFloat(where.value);
                    if (num1 == num2){
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_GE){
                for (String str : column.getmDatas()){
                    float num1 = Float.parseFloat(str);
                    float num2 = Float.parseFloat(where.value);
                    if (num1 >= num2){
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_GT){
                for (String str : column.getmDatas()){
                    float num1 = Float.parseFloat(str);
                    float num2 = Float.parseFloat(where.value);
                    if (num1 > num2){
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_NE){
                for (String str : column.getmDatas()){
                    float num1 = Float.parseFloat(str);
                    float num2 = Float.parseFloat(where.value);
                    if (num1 != num2){
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_LE){
                for (String str : column.getmDatas()){
                    float num1 = Float.parseFloat(str);
                    float num2 = Float.parseFloat(where.value);
                    if (num1 <= num2){
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_LT){
                for (String str : column.getmDatas()){
                    float num1 = Float.parseFloat(str);
                    float num2 = Float.parseFloat(where.value);
                    if (num1 < num2){
                        ansColumn.getmDatas().add(str);
                    }
                }
            }
        } else if (column.getmDataType() == Values.DATA_CHAR) {
            if (where.getType() == Values.SIGN_EQ){
                for (String str : column.getmDatas()){

                    String str1 = str;
                    String str2 = where.value;
                    int result = str1.compareTo(str2);
                    if (result == 0){
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_GT){
                for (String str : column.getmDatas()){

                    String str1 = str;
                    String str2 = where.value;
                    int result = str1.compareTo(str2);
                    if (result > 0){
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_GE){
                for (String str : column.getmDatas()){

                    String str1 = str;
                    String str2 = where.value;
                    int result = str1.compareTo(str2);
                    if (result >= 0){
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_LE){
                for (String str : column.getmDatas()){

                    String str1 = str;
                    String str2 = where.value;
                    int result = str1.compareTo(str2);
                    if (result <= 0){
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_LT){
                for (String str : column.getmDatas()){

                    String str1 = str;
                    String str2 = where.value;
                    int result = str1.compareTo(str2);
                    if (result < 0){
                        ansColumn.getmDatas().add(str);
                    }
                }
            } else if (where.getType() == Values.SIGN_NE){
                for (String str : column.getmDatas()){

                    String str1 = str;
                    String str2 = where.value;
                    int result = str1.compareTo(str2);
                    if (result != 0){
                        ansColumn.getmDatas().add(str);
                    }
                }
            }
        }
        //System.out.println("!!!!" + ansColumn);
        return ansColumn;
    }
}
