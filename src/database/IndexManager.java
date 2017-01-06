package database;

import database.Exception.IndexMustBeCreatedOnPrimaryKeyException;
import database.Exception.OneIndexEachTableException;
import database.Exception.TableNotExistException;
import database.SQL.SQLCreateIndex;

/**
 * Created by 离子态狍子 on 2016/12/2.
 */
public class IndexManager {
    private CatalogManager mCatalogManager;
    private BufferManager mBufferManager;
    private String databaseName;

    public IndexManager(CatalogManager mCatalogManager, BufferManager mBufferManager, String databaseName) {
        this.mCatalogManager = mCatalogManager;
        this.mBufferManager = mBufferManager;
        this.databaseName = databaseName;
    }

    public void CreateIndex(SQLCreateIndex SQL) throws TableNotExistException, OneIndexEachTableException, IndexMustBeCreatedOnPrimaryKeyException {
        //获取表以创建索引
        String tableName = SQL.getmTableName();
        Table table = getmCatalogManager().getDatabaseByName(databaseName).getTableByName(tableName);
        if (table == null){
            throw new TableNotExistException();
        } else {
            if (table.getIndexNumber() != 0){
                //每个表应当只有一个索引
                throw  new OneIndexEachTableException();
            }
            Attribute attribute = table.GetAttributeByName(SQL.getmColumnName());
            if (attribute.getmAttributeType() != Values.ATTRIBUTE_PRIMARY){
                //索引必须是主键索引
                throw new IndexMustBeCreatedOnPrimaryKeyException();
            } else {
                String fileName = getmCatalogManager().getmPath() + getDatabaseName() + "/" + SQL.getmIndexName() + ".index";

            }

            //未完待续
        }

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

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }



}
