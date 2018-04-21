package per.hss.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpSession session=request.getSession();
        Object object=session.getAttribute("currentUser");
        String path=request.getServletPath();
        System.out.println(path);
        if(object==null&&path.indexOf("login")<0&&path.indexOf("bootstrap")<0&&path.indexOf("image")<0)
        {
            //不是通过正常途径登陆且没有session
            request.getRequestDispatcher("login.jsp").forward(servletRequest,servletResponse);
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
