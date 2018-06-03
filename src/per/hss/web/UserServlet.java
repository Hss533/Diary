package per.hss.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import per.hss.dao.UserDao;
import per.hss.model.User;
import per.hss.util.DateUtil;
import per.hss.util.DbUtil;
import per.hss.util.PropertiesUtil;

import java.sql.Connection;
import java.util.*;
public class UserServlet extends HttpServlet {

    DateUtil dateUtil=new DateUtil();
    PropertiesUtil propertiesUtil=new PropertiesUtil();
    DbUtil dbUtil=new DbUtil();
    UserDao userDao=new UserDao();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String action=request.getParameter("action");
        if("preSave".equals(action))
        {
            userPreSave(request,response);
        }else if("save".equals(action))
        {
            userSave(request,response);
        }
    }
    protected void userPreSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("mainPage","user/userSave.jsp");
        request.getRequestDispatcher("mainTemp.jsp").forward(request,response);

    }

    protected void userSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        FileItemFactory factory=new DiskFileItemFactory();
        ServletFileUpload upload=new ServletFileUpload(factory);
        List<FileItem> items=null;
        try{

            items=upload.parseRequest(request);

        }catch(FileUploadException e)
        {
            e.printStackTrace();
        }
        Iterator<FileItem> itr=items.iterator();
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("currentUser");

        boolean imageChanges=false;
        while(itr.hasNext())
        {
            FileItem item=itr.next();
            if(item.isFormField())
            {
                String fileName=item.getFieldName();
                if("nickName".equals(fileName))
                {
                    user.setNickName(item.getString("utf-8"));
                }
                if("mood".equals(fileName))
                {
                    user.setMood(item.getString("utf-8"));
                }
            }else if(!"".equals(item.getName()))
            {
                try{
                    imageChanges=true;
                    String imageName= dateUtil.getCurrentDateStr();
                    user.setImageName(imageName+"."+item.getName().split("\\.")[1]);
                    String filePath=request.getSession().getServletContext().getRealPath("/userImages/")+imageName+"."+item.getName().split("\\.")[1];
                    System.out.println(filePath);
                    item.write(new File(filePath));
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        if(!imageChanges)
        {
            user.setImageName(user.getImageName().replaceFirst(propertiesUtil.getValue("imageFile"),""));
        }
        Connection con=null;
        try{
            con=dbUtil.getCon();
            int saveNums=userDao.userUpdate(con,user);//修改用户信息
            if(saveNums>0)
            {
                user.setImageName(propertiesUtil.getValue("imageFile")+user.getImageName());
                System.out.println("git"+user.getImageName()+"lalal");
                session.setAttribute("currentUser",user);
                request.getRequestDispatcher("main?all=true").forward(request,response);//保存成功后直接跳到主页
            }else{
                request.setAttribute("currentUser",user);
                System.out.println("保存失败");
                request.setAttribute("error","保存失败");
                request.setAttribute("mainPage","user/userSave.jsp");
                request.getRequestDispatcher("mainTemp.jsp").forward(request,response);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally{
            try{
                dbUtil.colseCon(con);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
