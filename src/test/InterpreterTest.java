package test;

import database.Exception.PasswordWrongException;
import database.Exception.UsernameWrongException;
import database.Interpreter;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by 离子态狍子 on 2016/11/30.
 */
public class InterpreterTest {
    @Test
    public void splitSQL() throws Exception {
        Interpreter interpreter = new Interpreter("data/", "admin", "123456");
        ArrayList<String> SQLs = new ArrayList<>();
        SQLs = interpreter.SplitSQL("I LOVE     YOU:DO; YOULOVE_ ME ?     ", " ");
        for (String s : SQLs){
            System.out.println(s);
        }
    }
    @Test
    public void DeleteDuplicateSpaces(){
        String keywords = "大               家  好胖 啊";
        StringBuilder result = new StringBuilder();
        boolean space = false;// 前一个是否为空格，默认第一个不是
        for (int i = 0; i < keywords.length(); i++) {
            if (keywords.charAt(i) != ' ') {
                space = false;
                result.append(keywords.charAt(i));
            } //end 当前不是空格
            else if (!space) {
                space = true;
                result.append(keywords.charAt(i));
            }//end 当前是空格，但前一个不是空格
            //没有else了，省略了当前是空格，前一个也是空格，当然不用理它了
        }
        System.out.println(keywords);
        System.out.println(result.toString());
    }

    @Test
    public void GeneralizeSQL() throws UsernameWrongException, PasswordWrongException {
        Interpreter interpreter = new Interpreter("data/", "admin", "123456");
        //样例1： 标准输入
        interpreter.setmSQLStatement("Create TAble SC;");
        interpreter.GeneralizeSQL();
        System.out.println(interpreter.getmSQLStatement());
        System.out.println(interpreter.getmSQLs());
        System.out.println();
        //样例2： ;后含有字符
        interpreter.setmSQLStatement("Create TAble SC;ssa dasdwq");
        interpreter.GeneralizeSQL();
        System.out.println(interpreter.getmSQLStatement());
        System.out.println(interpreter.getmSQLs());
        System.out.println();
        //样例3： 前后含有多余空格
        interpreter.setmSQLStatement("       Create TAble SC;        ssa dasdwq   ");
        interpreter.GeneralizeSQL();
        System.out.println(interpreter.getmSQLStatement());
        System.out.println(interpreter.getmSQLs());
        System.out.println();

        //样例4： 含有重复空格
        interpreter.setmSQLStatement("Create TAble  STUDENT;");
        interpreter.GeneralizeSQL();
        System.out.println(interpreter.getmSQLStatement());
        System.out.println(interpreter.getmSQLs());
        System.out.println();

        //样例5： 含有回车换行
        interpreter.setmSQLStatement("Create TAble \nSTUDENT;");
        System.out.println(interpreter.getmSQLStatement());
        interpreter.GeneralizeSQL();
        System.out.println(interpreter.getmSQLStatement());
        System.out.println(interpreter.getmSQLs());
        System.out.println();

        //样例6： 含有需要添加空格的特殊符号
        interpreter.setmSQLStatement("Create table Course(Cno Char(4) PRIMARY KEY, Cpno ChAR(4));");
        System.out.println(interpreter.getmSQLStatement());
        interpreter.GeneralizeSQL();
        System.out.println(interpreter.getmSQLStatement());
        System.out.println(interpreter.getmSQLs());
        System.out.println();

    }

    @Test
    public void ArrayListTest(){
        String s0 = "111";
        String s1 = "222";
        String s2 = "333";
        ArrayList<String> ss = new ArrayList<>();
        ss.add(s0);
        ss.add(s1);
        ss.add(s2);
        System.out.println(ss);
    }
}