package test;

import database.CatalogManager;
import database.Database;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by 离子态狍子 on 2016/11/28.
 */
public class CatalogManagerTest {
    private final static Logger LOGGER = Logger.getLogger(CatalogManagerTest.class.getName());

    @org.junit.Test
    public void Test(){
        CatalogManager catalogManager = new CatalogManager("data");
        /**
         * 一定程度上的测试通过
         */
        catalogManager.Save();
        catalogManager.Load();

    }

}