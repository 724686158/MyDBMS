package database;

/**
 * Created by 离子态狍子 on 2016/11/27.
 */
public class Values {
    /**
     * 未定义
     */
    public static final int NOT_DEFINE = -10;

    /**
     * 文件类型
     */
    public static final int FILE_RECORD = 0;
    public static final int FILE_INDEX = 1;

    /**
     * 数据类型
     */
    public static final int DATA_INT = 10;
    public static final int DATA_FLOAT = 11;
    public static final int DATA_CHAR = 12;


    /**
     * 比较符号 = <> < > <= >=
     */
    public static final int SIGN_EQ = 20;
    public static final int SIGN_NE = 21;
    public static final int SIGN_LT = 22;
    public static final int SIGN_GT = 23;
    public static final int SIGN_LE = 24;
    public static final int SIGN_GE = 25;

    /**
     * SQL语句类型
     */
    public static final int SQL_CREATEDATABASE = 30;
    public static final int SQL_CREATETABLE = 31;
    public static final int SQL_CREATEINDEX = 32;
    public static final int SQL_DROPDATABASE = 33;
    public static final int SQL_DROPTABLE = 34;
    public static final int SQL_DROPINDEX = 35;
    public static final int SQL_USE = 36;
    public static final int SQL_EXEC = 37;
    public static final int SQL_INSERT = 38;
    public static final int SQL_SELECT = 39;
    public static final int SQL_DELETE = 40;
    public static final int SQL_UPDATE = 41;
    public static final int SQL_QUIT = 42;
    public static final int SQL_HELP = 43;
    public static final int SQL_SHOWDATABASES = 44;
    public static final int SQL_SHOWTABLES = 45;
    public static final int SQL_HELPDATABASE = 46;
    public static final int SQL_HELPTABLE = 47;
    public static final int SQL_HELPVIEW = 48;
    public static final int SQL_HELPINDEX = 49;
    public static final int SQL_GRANT = 50;
    public static final int SQL_REVOKE = 51;



    /**
     * 权限类型
     */
    public static final int PRIVILEGE_CREATESCHEMA = 60;
    public static final int PRIVILEGE_CREATETABLE = 61;
    public static final int PRIVILEGE_ALTERTABLE = 62;
    public static final int PRIVILEGE_CREATEVIEW = 63;
    public static final int PRIVILEGE_CREATEINDEX = 64;
    public static final int PRIVILEGE_SELECT = 65;
    public static final int PRIVILEGE_INSERT = 66;
    public static final int PRIVILEGE_UPDATE = 67;
    public static final int PRIVILEGE_DELETE = 68;
    public static final int PRIVILEGE_REFERENCES = 69;
    public static final int PRIVILEGE_ALL = 70;
    public static final int PRIVILEGE_GRANT = 71;
    public static final int PRIVILEGE_REVOKE = 72;


    /**
     * 属性类型
     */
    public static final int ATTRIBUTE_PRIMARY = 80;
    public static final int ATTRIBUTE_NOTPRIMARY = 81;
}
