package database;


import database.Exception.*;
import database.SQL.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 离子态狍子 on 2016/11/25.
 */
public class API {

    private String mPath;
    private String mCurrentDatabase;
    private String mCurrentUserName;
    private CatalogManager mCatalogManager;
    private BufferManager mBufferManager;
    private UserManager mUserManager;

    public API(String mPath, String username, String password) throws PasswordWrongException, UsernameWrongException {
        this.mPath = mPath;
        this.mCurrentDatabase = "";
        this.mCurrentUserName = "";
        this.mCatalogManager = new CatalogManager(mPath);
        this.mUserManager = new UserManager(mPath);
        /**
         *
         */
        String dirname = mPath.substring(0, mPath.length() - 1);
        File file = new File(dirname);
        if (!file.exists()){
            FileUtil.CreateDir(dirname);
            this.mCatalogManager.Save();
            this.mUserManager.Save();
        }
        this.mCatalogManager.Load();
        this.mUserManager.Load();

        if (mUserManager.HasUser(username)){
            if (mUserManager.Login(username, password)){
                this.setmCurrentUserName(username);
            } else {
                throw new PasswordWrongException();
            }
        } else {
            throw new UsernameWrongException();
        }
    }

    /**
     * 退出
     */
    public void Quit(){
        System.out.println("再见");
    }

    /**
     * 帮助
     */
    @Test
    public void Help(){
        System.out.println("----------------------MyDBMS------------------------");
        System.out.println("常用操作：");
        System.out.println("[1]:  HELP                   获取帮助信息");
        System.out.println("[2]:  EXIT                   退出MyDBMS");
        System.out.println("[3]:  QUIT                   退出MyDBMS");
        System.out.println("[4]:  SHOW DATABASES         查看全部数据库");
        System.out.println("[5]:  SHOW TABLES            查看当前数据库中的全部表");
        System.out.println("[6]:  USE                    使用数据库");
        System.out.println("[7]:  CREATE DATABASE        创建数据库");
        System.out.println("[8]:  CREATE TABLE           创建表");
        System.out.println("[9]:  CREATE INDEX           创建索引");
        System.out.println("[10]: DROP DATABASE          删除数据库");
        System.out.println("[11]: DROP TABLE             删除表");
        System.out.println("[12]: DROP INDEX             删除索引");
        System.out.println("[13]: SELECT                 在表中查询记录");
        System.out.println("[14]: INSERT                 在表中插入新纪录");
        System.out.println("[15]: DELETE                 从表中删除记录");
        System.out.println("[16]: UPDATE                 替换表中的记录");
        System.out.println("[17]: HELP DATABASE          显示数据库的详细信息");
        System.out.println("[18]: HELP TABLE             显示表的详细信息");
        System.out.println("[19]: HELP VIEW              显示视图的详细信息");
        System.out.println("[20]: HELP INDEX             显示索引的详细信息");
        System.out.println("[21]: GRANT                  赋予权限");
        System.out.println("[22]: REVOKE                 收回权限");
        System.out.println("----------------------------------------------------");

    }

    /**
     * 创建数据库
     * @param SQL
     */
    public void CreateDatabase(SQLCreateDatabase SQL) throws DatabaseAlreadyExistsException {
        System.out.println("创建数据库：" + SQL.getmDatabaseName());
        String folderName = mPath + SQL.getmDatabaseName();
        //检测数据库是否已经创建
        if (getmCatalogManager().getDatabaseByName(SQL.getmDatabaseName()) != null){
            System.out.println("已存在同名数据库");
            throw new DatabaseAlreadyExistsException();
        }
        //为数据库创建文件目录
        if (FileUtil.CreateDir(folderName)){
            System.out.println("数据库文件目录创建成功");
        } else {
            System.out.println("数据库文件目录创建失败");
        }
        getmCatalogManager().CreateDatabase(SQL.getmDatabaseName());
        getmCatalogManager().Save();//保存变更
    }

    /**
     * 创建表
     * @param SQL
     */
    public void CreateTable(SQLCreateTable SQL) throws NoDatabaseSelectedException, TableAlreadyExistsException, DatabaseNotExistException, IOException {
        System.out.println("创建表：" + SQL.getmTableName());
        if (getmCurrentDatabase().length() == 0){
            System.out.println("当前未使用数据库，无法创建表");
            throw new NoDatabaseSelectedException();
        }
        String databaseName = getmCurrentDatabase();
        Database currentDB =  getmCatalogManager().getDatabaseByName(databaseName);
        if (currentDB == null){
            throw new DatabaseNotExistException();
        }
        if (currentDB.getTableByName(SQL.getmTableName()) != null){
            System.out.println("已存在同名表");
            throw new TableAlreadyExistsException();
        }
        String fileName = getmPath() + getmCurrentDatabase() + "/" + SQL.getmTableName() + ".records";
        if (FileUtil.CreateFile(fileName)){
            currentDB.CreateTable(SQL);
        }
        getmCatalogManager().Save();//保存变更
        getmBufferManager().CreateDataFileOfTable(currentDB.getTableByName(SQL.getmTableName()));
    }

    /**
     * 创建索引
     * @param SQL
     */
    public void CreateIndex(SQLCreateIndex SQL) throws NoDatabaseSelectedException, DatabaseNotExistException, IndexAlreadyExistsException, TableNotExistException {
        System.out.println("创建索引：" + SQL.getmIndexName());
        if (getmCurrentDatabase().length() == 0){
            System.out.println("当前未使用数据库，无法创建表");
            throw new NoDatabaseSelectedException();
        }
        String databaseName = getmCurrentDatabase();
        Database currentDB =  getmCatalogManager().getDatabaseByName(databaseName);
        if (currentDB == null){
            throw new DatabaseNotExistException();
        }
        if (currentDB.getTableByName(SQL.getmTableName()) == null){
            System.out.println("相关的表不存在");
            throw new TableNotExistException();
        }
        if (currentDB.CheckIfIndexExists(SQL.getmIndexName())){
            System.out.println("已存在同名索引");
            throw new IndexAlreadyExistsException();
        }
        IndexManager indexManager = new IndexManager(getmCatalogManager(), getmBufferManager(), getmCurrentDatabase());
        try {
            indexManager.CreateIndex(SQL);
        } catch (OneIndexEachTableException e) {
            e.printStackTrace();
        } catch (IndexMustBeCreatedOnPrimaryKeyException e) {
            e.printStackTrace();
        }
        //未完待续

    }

    /**
     * 展示全部数据库
     */
    public void ShowDatabases(){
        ArrayList<Database> databases = getmCatalogManager().getmDatabases();
        if (databases.size() == 0){
            System.out.println("当前不存在数据库");
            System.out.println("请使用 CREATE DATABASE 创建一个数据库");
        } else {
            System.out.println("当前存在" + databases.size() + "个数据库");
            System.out.println("+-----------------------+");
            for (Database database : databases){
                System.out.println("    " + database.getName());
            }
            System.out.println("+-----------------------+");
        }
    }

    /**
     * 显示数据库的详细信息
     */
    public void HelpDatabase(SQLHelpDatabase SQL) throws DatabaseNotExistException {
        Database database = getmCatalogManager().getDatabaseByName(SQL.getmName());
        if (database == null){
            throw new DatabaseNotExistException();
        } else {
            System.out.println("在名为" + SQL.getmName() + "的数据库中");
            System.out.println("含有以下表：");
            for (Table table : database.getTables()){
                System.out.println(table.getmName());
            }
            System.out.println("含有以下视图：");
            for (View view : database.getmViews()){
                System.out.println(view.getmViewName());
            }
            System.out.println("-------------------------------------------");
        }
    }

    /**
     * 显示表的详细信息
     */
    public void HelpTable(SQLHelpTable SQL) throws NoDatabaseSelectedException, DatabaseNotExistException, TableNotExistException {
        if(getmCurrentDatabase().length() == 0){
            throw new NoDatabaseSelectedException();
        } else {
            Database database = getmCatalogManager().getDatabaseByName(getmCurrentDatabase());
            if (database == null) {
                throw new DatabaseNotExistException();
            } else {
                Table table = database.getTableByName(SQL.getmName());
                if (table == null){
                    throw new TableNotExistException();
                } else {
                    System.out.println("在名为" + SQL.getmName() + "的表中");
                    System.out.println("含有以下属性：");
                    for (Attribute attribute : table.getAttributes()){
                        String headStr = "";
                        if(attribute.getmAttributeType() == Values.ATTRIBUTE_PRIMARY)
                        {
                            headStr = "[主键] ";
                        } else {
                            headStr = "      ";
                        }
                        String tailStr = "";
                        if (attribute.getmDataType() == Values.DATA_INT)
                        {
                            tailStr = "--整数类型";
                        } else if (attribute.getmDataType() == Values.DATA_FLOAT) {
                            tailStr = "--浮点类型";
                        } else if (attribute.getmDataType() == Values.DATA_CHAR) {
                            if (attribute.getmLenght() == 1){
                                tailStr = "--字符类型";
                            } else {
                                tailStr = "--字符串类型";
                            }
                        }
                        System.out.println(headStr + attribute.getmName() + tailStr);
                    }
                    System.out.println("含有以下索引：");
                    for (Index index : table.getIndexs()){
                        System.out.println(index.getmName());
                    }
                    System.out.println("-------------------------------------------");
                }
            }
        }
    }

    /**
     * 显示视图的详细信息
     */
    public void HelpView(SQLHelpView SQL){

    }

    /**
     * 显示索引的详细信息
     */
    public void HelpIndex(SQLHelpIndex SQL){

    }



    /**
     * 展示表
     */
    public void ShowTables() throws NoDatabaseSelectedException, DatabaseNotExistException {
        if(getmCurrentDatabase().length() == 0){
            throw new NoDatabaseSelectedException();
        } else {
            Database database = getmCatalogManager().getDatabaseByName(getmCurrentDatabase());
            if (database == null){
                throw new DatabaseNotExistException();
            } else {
                if (database.getTables().size() == 0){
                    System.out.println("当前数据库还不存在表");
                    System.out.println("请使用 CREATE TABLE 创建一个表");
                } else {
                    System.out.println("当前数据库中共有" + database.getTables().size() + "个表");
                    System.out.println("+-----------------------+");
                    for (Table table : database.getTables()){
                        System.out.println("    " + table.getmName());
                    }
                    System.out.println("+-----------------------+");
                }
            }

        }

    }

    /**
     * 删除数据库
     * @param SQL
     */
    public void DropDatabase(SQLDropDatabase SQL) throws DatabaseNotExistException {
        boolean found = false;
        System.out.println("尝试删除数据库" + SQL.getmName());
        ArrayList<Database> databases = getmCatalogManager().getmDatabases();
        for (Database database : databases){
            if(database.getName().equals(SQL.getmName())){
                found = true;
            }
        }
        if (!found){
            throw new DatabaseNotExistException();
        } else {
            String filename = getmPath() + SQL.getmName();
            File file = new File(filename);
            if (file.exists()){
                FileUtil.DelFolder(filename);
                System.out.println("数据库文件夹已被删除");
                Database db;
                getmCatalogManager().DeleteDatabase(SQL.getmName());
                getmCatalogManager().Save();
                if (getmCurrentDatabase().equals(SQL.getmName())){
                    setmCurrentDatabase("");
                    System.out.println("当前使用的数据库已被删除");
                } else {
                    System.out.println("数据库 " + SQL.getmName() + " 删除成功");
                }

            } else {
                System.out.println("数据库文件夹丢失");
            }
        }
    }

    /**
     * 删除表
     * @param SQL
     */
    public void DropTable(SQLDropTable SQL) throws NoDatabaseSelectedException, TableNotExistException {
        System.out.println("尝试删除数据表" + SQL.getmName());
        if (getmCurrentDatabase().length() == 0) {
            throw new NoDatabaseSelectedException();
        } else {
            Database database = getmCatalogManager().getDatabaseByName(getmCurrentDatabase());
            Table table = database.getTableByName(SQL.getmName());
            if (table == null){
                throw new TableNotExistException();
            } else {
                String filename = getmPath() + getmCurrentDatabase() + "/" + SQL.getmName() + ".records";
                File file = new File(filename);
                if (file.exists()){
                    file.delete();
                    System.out.println("表删除成功");
                } else {
                    System.out.println("表文件丢失");
                }
                System.out.println("尝试清除有关索引");
                for (int i = 0; i < table.getIndexNumber(); i++){
                    String indexFileName = getmPath() + getmCurrentDatabase() + "/" + table.getIndexs().get(i) + ".index";
                    File indexFile = new File(indexFileName);
                    if (indexFile.exists()){
                        indexFile.delete();
                        System.out.println("相关索引文件删除成功");
                    } else {
                        System.out.println("相关索引文件丢失");
                    }
                }
                database.DropTable(SQL);
                getmCatalogManager().Save();
            }
        }
    }

    /**
     * 删除索引
     * @param SQL
     */
    public void DropIndex(SQLDropIndex SQL) throws NoDatabaseSelectedException, DatabaseNotExistException, IndexNotExistException {
        System.out.println("尝试删除索引" + SQL.getmName());
        if (getmCurrentDatabase().length() == 0) {
            throw new NoDatabaseSelectedException();
        } else {
            Database database = getmCatalogManager().getDatabaseByName(getmCurrentDatabase());
            if (database == null){
                throw new DatabaseNotExistException();
            } else {
                if (database.CheckIfIndexExists(SQL.getmName())){
                    String indexFileName = getmPath() + getmCurrentDatabase() + "/" + SQL.getmName() + ".index";
                    File indexFile = new File(indexFileName);
                    if (indexFile.exists()){
                        indexFile.delete();
                        System.out.println("相关索引文件删除成功");
                    } else {
                        System.out.println("相关索引文件丢失");
                    }
                    database.DropIndex(SQL);
                    getmCatalogManager().Save();

                } else {
                    throw new IndexNotExistException();
                }
            }

        }

    }

    /**
     * 选用
     * @param SQL
     */
    public void Use(SQLUse SQL) throws DatabaseNotExistException {
        System.out.println("使用数据库：" + SQL.getmName());
        Database database = getmCatalogManager().getDatabaseByName(SQL.getmName());
        if (database == null){
            throw new DatabaseNotExistException();
        } else {
            if (getmCurrentDatabase().length() != 0){
                System.out.println("关闭当前数据库：" + getmCurrentDatabase());

            }
            setmCurrentDatabase(SQL.getmName());
            setmBufferManager(new BufferManager(getmPath(), getmCurrentDatabase()));
            getmCatalogManager().Save();
        }
    }

    /**
     * 插入数据
     * @param SQL
     */
    public void Insert(SQLInsert SQL) throws NoDatabaseSelectedException, DatabaseNotExistException, CannotInsertException, IOException, ClassNotFoundException {
        if (getmCurrentDatabase().length() == 0){
            throw new NoDatabaseSelectedException();
        } else {
            Database database = getmCatalogManager().getDatabaseByName(getmCurrentDatabase());
            if (database == null){
                throw new DatabaseNotExistException();
            } else {
                getmBufferManager().InsertData(SQL);
                //getmBufferManager().ShowTableData(SQL.getmName());
            }
        }

    }

    /**
     * 查询
     * @param SQL
     */
    public void Select(SQLSelect SQL) throws NoDatabaseSelectedException, TableNotExistException, IOException, ClassNotFoundException, ColumnCannotFoundException {
        if (getmCurrentDatabase().length() == 0){
            throw new NoDatabaseSelectedException();
        } else {
            Table table = getmCatalogManager().getDatabaseByName(getmCurrentDatabase()).getTableByName(SQL.getmTableName());
            if (table == null){
                throw new TableNotExistException();
            } else {
                getmBufferManager().SelectData(SQL);
            }
        }
    }

    /**
     * 删除
     * @param SQL
     */
    public void Delete(SQLDelete SQL) throws NoDatabaseSelectedException, TableNotExistException, IOException, ClassNotFoundException {
        if (getmCurrentDatabase().length() == 0){
            throw new NoDatabaseSelectedException();
        } else {
            Table table = getmCatalogManager().getDatabaseByName(getmCurrentDatabase()).getTableByName(SQL.getmTableName());
            if (table == null){
                throw new TableNotExistException();
            } else {
                getmBufferManager().DeleteData(SQL);
            }
        }
    }

    /**
     * 更新数据
     * @param SQL
     */
    public void Update(SQLUpdate SQL) throws NoDatabaseSelectedException, TableNotExistException, IOException, ClassNotFoundException {
        if (getmCurrentDatabase().length() == 0){
            throw new NoDatabaseSelectedException();
        } else {
            Table table = getmCatalogManager().getDatabaseByName(getmCurrentDatabase()).getTableByName(SQL.getmTableName());
            if (table == null){
                throw new TableNotExistException();
            } else {
                getmBufferManager().UpdateData(SQL);
            }
        }
    }

    /**
     * 授予权限
     * @param SQL
     */
    public void Grant(SQLGrant SQL) throws LoginErrorException {
        if (getmCurrentUserName().length() == 0){
            throw new LoginErrorException();
        } else {
            if (getmUserManager().IfHasPrivilege(SQL.getmUserName(), SQL.getmName() + SQL.getmType())){
                System.out.println(SQL.getmUserName() + "已拥有此权限。无需授予");
            } else {
                getmUserManager().Grant(SQL.getmUserName(), SQL.getmName() + SQL.getmType());
            }
        }
    }

    /**
     * 收回权限
     * @param SQL
     */
    public void Revoke(SQLRevoke SQL) throws LoginErrorException {
        if (getmCurrentUserName().length() == 0){
            throw new LoginErrorException();
        } else {
            if (!(getmUserManager().IfHasPrivilege(SQL.getmUserName(), SQL.getmName() + SQL.getmType()))){
                System.out.println(SQL.getmUserName() + "并不具有此权限。无需收回");
            } else {
                getmUserManager().Revoke(SQL.getmUserName(), SQL.getmName() + SQL.getmType());
            }
        }
    }


    public String getmCurrentUserName() {
        return mCurrentUserName;
    }

    public void setmCurrentUserName(String mCurrentUserName) {
        this.mCurrentUserName = mCurrentUserName;
    }

    public UserManager getmUserManager() {
        return mUserManager;
    }

    public void setmUserManager(UserManager mUserManager) {
        this.mUserManager = mUserManager;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public String getmCurrentDatabase() {
        return mCurrentDatabase;
    }

    public void setmCurrentDatabase(String mCurrentDatabase) {
        this.mCurrentDatabase = mCurrentDatabase;
    }

    public CatalogManager getmCatalogManager() {
        return mCatalogManager;
    }

    public void setmCatalogManager(CatalogManager mCatalogManager) {
        this.mCatalogManager = mCatalogManager;
    }

    public BufferManager getmBufferManager() {
        return mBufferManager;
    }

    public void setmBufferManager(BufferManager mBufferManager) {
        this.mBufferManager = mBufferManager;
    }
}
