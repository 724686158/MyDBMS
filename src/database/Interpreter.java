package database;

import database.Exception.*;
import database.SQL.*;
import test.ColumnsTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by 离子态狍子 on 2016/11/25.
 */
public class Interpreter {
    private final static Logger LOGGER = Logger.getLogger(Interpreter.class.getName());
    //
    private API mAPI;
    private String mSQLStatement;
    private ArrayList<String> mSQLs;
    private int mSQLType;

    public Interpreter(String dataPath, String username, String password) throws UsernameWrongException, PasswordWrongException {
        mSQLType = (Values.NOT_DEFINE);
        mAPI = new API(dataPath, username, password);
    }

    public API getmAPI() {
        return mAPI;
    }

    public void setmAPI(API mAPI) {
        this.mAPI = mAPI;
    }

    public String getmSQLStatement() {
        return mSQLStatement;
    }

    public void setmSQLStatement(String mSQLStatement) {
        this.mSQLStatement = mSQLStatement;
    }

    public ArrayList<String> getmSQLs() {
        return mSQLs;
    }

    public void setmSQLs(ArrayList<String> mSQLs) {
        this.mSQLs = mSQLs;
    }

    public int getmSQLType() {
        return mSQLType;
    }

    public void setmSQLType(int mSQLType) {
        this.mSQLType = mSQLType;
    }


    /**
     * 执行
     * @param statement 需要执行的SQL语句
     */
    public void ExecSQL(String statement){
        System.out.println("正在执行SQL语句: " + statement);
        setmSQLStatement(statement);//获取SQL语句
        GeneralizeSQL();//格式化SQL语句
        GetSQLType();   //分析SQL语句类型
        try {
            ParseSQL(); //构造SQL对象并传递个API
        } catch (DatabaseAlreadyExistsException e) {
            System.out.println("执行异常：已存在同名数据库，请勿重复创建");
            //e.printStackTrace();
        } catch (DatabaseNotExistException e) {
            System.out.println("执行异常：数据库不存在");
            //e.printStackTrace();
        } catch (NoDatabaseSelectedException e) {
            System.out.println("执行异常：当前没有数据库被选中，请使用USE指令选择数据库");
            //e.printStackTrace();
        } catch (TableAlreadyExistsException e) {
            System.out.println("执行异常：当前数据库中已存在同名表，请勿重复创建");
            //e.printStackTrace();
        } catch (TableNotExistException e) {
            System.out.println("执行异常：表不存在");
            //e.printStackTrace();
        } catch (IndexAlreadyExistsException e) {
            System.out.println("执行异常：已存在同名索引，请勿重复创建");
            //e.printStackTrace();
        } catch (IndexNotExistException e) {
            System.out.println("执行异常：索引不存在");
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("执行异常：插入时文件读写出错");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (CannotInsertException e) {
            e.printStackTrace();
        } catch (ColumnCannotFoundException e) {
            System.out.println("执行异常：您选择的列不存在");
            e.printStackTrace();
        } catch (LoginErrorException e) {
            System.out.println("执行异常：当前用户登录状态失效");
            e.printStackTrace();
        }
        System.out.println("执行完成.");
    }


    /**
     * 拆分SQL
     * @param statement 需要执行的SQL语句
     * @param separator 用于切割SQL语句元素的标志符
     * @return
     */
    public ArrayList<String> SplitSQL(String statement, String separator){
        ArrayList<String> sqls = new ArrayList<>();
        String[] strings = statement.split(separator);
        for(String s : strings){
            sqls.add(s);
        }
        return sqls;
    }

    /**
     *格式化SQL语句
     */
    public void GeneralizeSQL(){
        String gs = getmSQLStatement();
        if (gs.contains(";")){//清除';'之后的内容
            int dix = gs.indexOf(';');
            gs = gs.substring(0, dix);
        }
        gs = gs.replace(';', ' '); //清除';'
        gs = gs.replace('\n', ' '); //清除回车符
        gs = gs.replace('\r', ' '); //清除换行符
        gs = gs.replace('\t', ' '); //清除水平制表符
        gs = gs.replace('\t', ' '); //清除水平制表符
        gs = gs.trim(); //清除前后空格
        gs = DeleteDuplicateSpaces(gs);//去掉字符串中的重复空格
        gs = InsertSpaces(gs);
        setmSQLStatement(gs);
        setmSQLs(SplitSQL(gs, " "));
    }

    /**
     * 去掉字符串中的重复空格
     * @param str
     * @return
     */
    public String DeleteDuplicateSpaces(String str){
        StringBuilder result = new StringBuilder();
        boolean space = false;// 前一个是否为空格，默认第一个不是
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {//当前不是空格
                space = false;
                result.append(str.charAt(i));
            }
            else if (!space) {//str，但前一个不是空格
                space = true;
                result.append(str.charAt(i));
            } else {//当前是空格，前一个也是空格
                //丢掉这个空格
            }
        }
        return result.toString();
    }

    /**
     * 在 ( ) , = <> < > 之前及之后添加空格
     * @param str
     * @return
     */
    public String InsertSpaces(String str){
        String result;
        result = str.replaceAll(" ?(\\(|\\)|,|=|(<>)|<|>) ?", " $1 ");
        result = result.replaceAll("< *>", "<>");
        result = result.replaceAll("< *=", "<=");
        result = result.replaceAll("> *=", ">=");
        return result;
    }

    /**
     * 解析SQL
     */
    private void ParseSQL() throws DatabaseAlreadyExistsException, DatabaseNotExistException, NoDatabaseSelectedException, TableAlreadyExistsException, TableNotExistException, IndexAlreadyExistsException, IndexNotExistException, IOException, ClassNotFoundException, CannotInsertException, ColumnCannotFoundException, LoginErrorException {
        switch (getmSQLType()){
            case Values.SQL_QUIT:
            {
                mAPI.Quit();
            }
                break;
            case Values.SQL_HELP:
            {
                mAPI.Help();
            }
                break;
            case Values.SQL_CREATEDATABASE:
            {
                SQLCreateDatabase sql = null;
                try {
                    sql = new SQLCreateDatabase(getmSQLs());
                    mAPI.CreateDatabase(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }

            }
                break;
            case Values.SQL_CREATETABLE:
            {
                SQLCreateTable sql = null;
                try {
                    sql = new SQLCreateTable(getmSQLs());
                    mAPI.CreateTable(sql);
                } catch (SyntaxErrorException e) {
                    //e.printStackTrace();
                    System.out.println("语法异常，无法执行");
                }

            }
                break;
            case Values.SQL_CREATEINDEX:
            {
                SQLCreateIndex sql = null;
                try {
                    sql = new SQLCreateIndex(getmSQLs());
                    mAPI.CreateIndex(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }

            }
                break;
            case Values.SQL_SHOWDATABASES:
            {
                mAPI.ShowDatabases();
            }
                break;
            case Values.SQL_SHOWTABLES:
            {
                mAPI.ShowTables();
            }
                break;
            case Values.SQL_DROPDATABASE:
            {
                SQLDropDatabase sql = null;
                try {
                    sql = new SQLDropDatabase(getmSQLs());
                    mAPI.DropDatabase(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }

            }
                break;
            case Values.SQL_DROPTABLE:
            {
                SQLDropTable sql = null;
                try {
                    sql = new SQLDropTable(getmSQLs());
                    mAPI.DropTable(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }

            }
                break;
            case Values.SQL_DROPINDEX:
            {
                SQLDropIndex sql = null;
                try {
                    sql = new SQLDropIndex(getmSQLs());
                    mAPI.DropIndex(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }

            }
                break;
            case Values.SQL_HELPDATABASE:
            {
                SQLHelpDatabase sql = null;
                try {
                    sql = new SQLHelpDatabase(getmSQLs());
                    mAPI.HelpDatabase(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }
            }
                break;
            case Values.SQL_HELPTABLE:
            {
                SQLHelpTable sql = null;
                try {
                    sql = new SQLHelpTable(getmSQLs());
                    mAPI.HelpTable(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }
            }
                break;
            case Values.SQL_HELPVIEW:
            {
                SQLHelpView sql = null;
                try {
                    sql = new SQLHelpView(getmSQLs());
                    mAPI.HelpView(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }
            }
                break;
            case Values.SQL_HELPINDEX:
            {
                SQLHelpIndex sql = null;
                try {
                    sql = new SQLHelpIndex(getmSQLs());
                    mAPI.HelpIndex(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }
            }
                break;
            case Values.SQL_USE:
            {
                SQLUse sql = null;
                try {
                    sql = new SQLUse(getmSQLs());
                    mAPI.Use(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }

            }
                break;
            case Values.SQL_INSERT:
            {
                SQLInsert sql = null;
                try {
                    sql = new SQLInsert(getmSQLs());
                    mAPI.Insert(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }

            }
                break;

            case Values.SQL_SELECT:
            {
                SQLSelect sql = null;
                try {
                    sql = new SQLSelect(getmSQLs());
                    mAPI.Select(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }
            }
                break;

            case Values.SQL_DELETE:
            {
                SQLDelete sql = null;
                try {
                    sql = new SQLDelete(getmSQLs());
                    mAPI.Delete(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }

            }
                break;
            case Values.SQL_UPDATE:
            {
                SQLUpdate sql = null;
                try {
                    sql = new SQLUpdate(getmSQLs());
                    mAPI.Update(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }
            }
                break;

            case Values.SQL_GRANT:
            {
                SQLGrant sql = null;
                try {
                    sql = new SQLGrant(getmSQLs());
                    mAPI.Grant(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }
            }
            break;

            case Values.SQL_REVOKE:
            {
                SQLRevoke sql = null;
                try {
                    sql = new SQLRevoke(getmSQLs());
                    mAPI.Revoke(sql);
                } catch (SyntaxErrorException e) {
                    e.printStackTrace();
                }
            }
            break;
            default:
                break;
        }

    }

    public void GetSQLType(){
        if (getmSQLs().size() == 0){
            setmSQLType(Values.NOT_DEFINE);
            LOGGER.log(Level.WARNING, "SQL TYPE IS EMPTY");
        } else {
            String s0 = getmSQLs().get(0).toLowerCase();
            if (s0.equals("quit") || s0.equals("\\q") || s0.equals("\\e")) {
                setmSQLType(Values.SQL_QUIT);
            } else if (s0.equals("help") || s0.equals("\\h") || s0.equals("\\?") || s0.equals("?")) {
                if (getmSQLs().size() <= 1){//单纯的获取帮助文件
                    setmSQLType(Values.SQL_HELP);
                } else {
                    String s1 = getmSQLs().get(1).toLowerCase();
                    if (s1.equals("database")) {
                        setmSQLType(Values.SQL_HELPDATABASE);
                    } else if (s1.equals("table")) {
                        setmSQLType(Values.SQL_HELPTABLE);
                    } else if (s1.equals("view")) {
                        setmSQLType(Values.SQL_HELPVIEW);
                    } else if (s1.equals("index")) {
                        setmSQLType(Values.SQL_HELPINDEX);
                    } else {
                        setmSQLType(Values.NOT_DEFINE);
                        LOGGER.log(Level.WARNING, "无效的创建类型（请使用 'help;' 指令获取帮助）");
                    }
                }
            } else if (s0.equals("create")) {
                if (getmSQLs().size() <= 1) {
                    setmSQLType(Values.NOT_DEFINE);
                    LOGGER.log(Level.WARNING, "无法正确解析（请使用 'help;' 指令获取帮助）");
                } else {
                    String s1 = getmSQLs().get(1).toLowerCase();
                    if (s1.equals("database")) {
                        setmSQLType(Values.SQL_CREATEDATABASE);
                    } else if (s1.equals("table")) {
                        setmSQLType(Values.SQL_CREATETABLE);
                    } else if (s1.equals("index")) {
                        setmSQLType(Values.SQL_CREATEINDEX);
                    } else {
                        setmSQLType(Values.NOT_DEFINE);
                        LOGGER.log(Level.WARNING, "无效的创建类型（请使用 'help;' 指令获取帮助）");
                    }
                }
            } else if (s0.equals("show")) {
                if (getmSQLs().size() <= 1) {
                    setmSQLType(Values.NOT_DEFINE);
                    LOGGER.log(Level.WARNING, "无法正确解析（请使用 'help;' 指令获取帮助）");
                } else {
                    String s1 = getmSQLs().get(1).toLowerCase();
                    if (s1.equals("databases")) {
                        setmSQLType(Values.SQL_SHOWDATABASES);
                    } else if (s1.equals("tables")) {
                        setmSQLType(Values.SQL_SHOWTABLES);
                    } else {
                        setmSQLType(Values.NOT_DEFINE);
                        LOGGER.log(Level.WARNING, "无效的显示类型（请使用 'help;' 指令获取帮助）");
                    }
                }
            } else if (s0.equals("drop")) {
                if (getmSQLs().size() <= 1) {
                    setmSQLType(Values.NOT_DEFINE);
                    LOGGER.log(Level.WARNING, "无法正确解析（请使用 'help;' 指令获取帮助）");
                } else {
                    String s1 = getmSQLs().get(1).toLowerCase();
                    if (s1.equals("database")) {
                        setmSQLType(Values.SQL_DROPDATABASE);
                    } else if (s1.equals("table")) {
                        setmSQLType(Values.SQL_DROPTABLE);
                    } else if (s1.equals("index")) {
                        setmSQLType(Values.SQL_DROPINDEX);
                    } else {
                        setmSQLType(Values.NOT_DEFINE);
                        LOGGER.log(Level.WARNING, "无效的删除类型（请使用 'help;' 指令获取帮助）");
                    }
                }
            } else if (s0.equals("use")){
                setmSQLType(Values.SQL_USE);
            } else if (s0.equals("insert")){
                setmSQLType(Values.SQL_INSERT);
            } else if (s0.equals("exec")){
                setmSQLType(Values.SQL_EXEC);
            } else if (s0.equals("select")){
                setmSQLType(Values.SQL_SELECT);
            } else if (s0.equals("delete")){
                setmSQLType(Values.SQL_DELETE);
            } else if (s0.equals("update")){
                setmSQLType(Values.SQL_UPDATE);
            } else if (s0.equals("grant")){
                setmSQLType(Values.SQL_GRANT);
            } else if (s0.equals("revoke")){
                setmSQLType(Values.SQL_REVOKE);
            } else {
                setmSQLType(Values.NOT_DEFINE);
                LOGGER.log(Level.WARNING, "不支持的语句类型（请使用 'help;' 指令获取帮助）");
            }
        }
    }
}
