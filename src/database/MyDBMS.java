package database;

import database.Exception.PasswordWrongException;
import database.Exception.UsernameWrongException;

import java.util.Scanner;

/**
 * Created by 离子态狍子 on 2016/12/11.
 */
public class MyDBMS {
    public static final String dataPath = "data/";//数据的存储路径

    public static void Start(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("欢迎使用MyDBMS");
        System.out.println("请输入用户名:");
        String username = scanner.nextLine();
        System.out.println("请输入密码:");
        String password = scanner.nextLine();

        String sql;			//sql statement
        String line;        //input line
        int end;         //end pos
        Interpreter interpreter = null;
        try {
            interpreter = new Interpreter(dataPath, username, password);
            System.out.println("登录成功！");
            while (true){
                System.out.print("\n" + username + "#>");
                line = scanner.nextLine();
                sql = line;
                if (line.length() >= 4)
                    if (line.substring(0, 4).toLowerCase().equals("exit") || line.substring(0, 4).toLowerCase().equals("quit")){
                        System.out.println("结束使用MyDBMS， 欢迎您再次使用");
                        break;
                    }
                while (!(line.contains(";"))){
                    System.out.print("  ->");
                    line = scanner.nextLine();
                    if (line.equals("")){
                        continue;
                    } else {
                        sql = sql + '\n' + line;
                    }
                }
                interpreter.ExecSQL(sql);
            }
        } catch (UsernameWrongException e) {
            //e.printStackTrace();
            System.out.println("用户名不存在！无法进入系统");
        } catch (PasswordWrongException e) {
            //e.printStackTrace();
            System.out.println("密码错误！无法进入系统");
        }

    }
}
