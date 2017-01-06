package test;

import database.SQLToken;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Created by 离子态狍子 on 2016/11/22.
 */
public class SQLTokenTest {
    private final static Logger LOGGER = Logger.getLogger(SQLTokenTest.class.getName());
    @Test
    public void gatName() throws Exception {
        String ss = "Create table SS";
        SQLToken st = new SQLToken(1, 7, 12);
        LOGGER.log(Level.WARNING, st.gatName(ss));
        LOGGER.log(Level.WARNING, st.getLength() + "");
    }

}