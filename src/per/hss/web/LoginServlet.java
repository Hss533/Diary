package per.hss.web;

import per.hss.dao.UserDao;
import per.hss.model.User;
import per.hss.util.DbUtil;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

public class LoginServlet extends HttpServlet {

    DbUtil dbUtil = new DbUtil();
    UserDao userDao = new UserDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        request.setCharacterEncoding("utf-8");
        HttpSession session=request.getSession();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        Connection con ;
        try {
            con = dbUtil.getCon();
            User user = new User(userName, password);
            User currentUser = userDao.login(con, user);
            if (currentUser == null) {
                request.setAttribute("user", user);
                request.setAttribute("error", "用户名或密码错误");
                request.getRequestDispatcher("logintest.jsp").forward(request, response);
            } else {
                if ("remember-me".equals(remember)) {
                    rememberMe(userName,password,response);
                }
                session.setAttribute("currentUser",currentUser);//登录成功之后要将信息放到session中去
                request.getRequestDispatcher("main").forward(request,response);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rememberMe(String userName, String password, HttpServletResponse response)
    {
        //设置cookie
        Cookie user=new Cookie("user",userName+"-"+password);
        user.setMaxAge(1*60*60*24*7);//一周
        response.addCookie(user);
}
}
