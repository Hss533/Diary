package per.hss.util;
import java.sql.*;

public class DbUtil {

        private String url="jdbc:mysql://localhost:3306/db_diary?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        private String username="root";
        private String password="533533";
        private  String jdbcname="com.mysql.jdbc.Driver";
        public Connection getCon(){
            Connection con=null;

            try {

                Class.forName(jdbcname);
                    con = DriverManager.getConnection(url,username,password);
                System.out.println("数据库成功链接" +
                        "");
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            return con;

        }
        public void colseCon(Connection con)
        {
            try {

                con.close();
                System.out.println("数据库成功关闭");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static void main(String args[])
        {
            DbUtil db=new DbUtil();
            Connection con=db.getCon();
            db.colseCon(con);
        }
}
