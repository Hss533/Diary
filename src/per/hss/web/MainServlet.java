package per.hss.web;

import per.hss.dao.DiaryDao;
import per.hss.dao.DiaryTypeDao;
import per.hss.model.Diary;
import per.hss.model.PageBean;
import per.hss.util.DbUtil;
import per.hss.util.PropertiesUtil;
import per.hss.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

public class MainServlet extends HttpServlet{

    DbUtil dbUtil=new DbUtil();
    DiaryDao diaryDao=new DiaryDao();
    DiaryTypeDao diaryTypeDao=new DiaryTypeDao();
    PropertiesUtil propertiesUtil=new PropertiesUtil();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       this.doPost(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置编码格式
//        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //设置session
        HttpSession session=request.getSession();
        //获取参数 这个参数应该是从mainTemp.jsp传过来的
        String s_typeId=request.getParameter("s_typeId");
        String s_releaseDateStr=request.getParameter("s_releaseDateStr");
        System.out.println("s_releaseDateStr="+s_releaseDateStr);
        String s_title=request.getParameter("s_title");
        String all=request.getParameter("all");
        String page=request.getParameter("page");
        Diary diary=new Diary();
        if("true".equals(all))
        {
            if(StringUtil.isNotEmpty(s_title))
            {
                diary.setTitle(s_title);
            }
            session.removeAttribute(":s_releaseDateStr");
            session.removeAttribute("s_typeId");
            session.setAttribute("s_title",s_title);
        }else
            {

                if(StringUtil.isNotEmpty(s_typeId)){
                    diary.setTypeId(Integer.parseInt(s_typeId));
                    session.setAttribute("s_typeId", s_typeId);
                    session.removeAttribute("s_releaseDateStr");
                    session.removeAttribute("s_title");
                }
                if(StringUtil.isNotEmpty(s_releaseDateStr)){
                    System.out.println("533533"+s_releaseDateStr);
                   // s_releaseDateStr=new String(s_releaseDateStr.getBytes("ISO-8859-1"),"UTF-8");
                    System.out.println("533533"+s_releaseDateStr);
                    diary.setReleaseDateStr(s_releaseDateStr);
                    session.setAttribute("s_releaseDateStr", s_releaseDateStr);
                    session.removeAttribute("s_typeId");
                    session.removeAttribute("s_title");
                }
                if(StringUtil.isEmpty(s_typeId)){
                    Object o=session.getAttribute("s_typeId");
                    if(o!=null){
                        diary.setTypeId(Integer.parseInt((String)o));
                    }
                }
                if(StringUtil.isEmpty(s_releaseDateStr)){
                    Object o=session.getAttribute("s_releaseDateStr");
                    if(o!=null){
                        diary.setReleaseDateStr((String)o);
                    }
                }
                if(StringUtil.isEmpty(s_title)){
                    Object o=session.getAttribute("s_title");
                    if(o!=null){
                        diary.setTitle((String)o);
                    }
                }
        }
        if(StringUtil.isEmpty(page))
        {
            page="1";
        }

        Connection con=null;
        PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(propertiesUtil.getValue("pageSize")));
        try{
            con=dbUtil.getCon();
            List<Diary> diaryList=diaryDao.diaryList(con,pageBean,diary);
            int total=diaryDao.diaryCount(con,diary);
            String pageCode=this.genPagation(total, Integer.parseInt(page), Integer.parseInt(propertiesUtil.getValue("pageSize")));
            request.setAttribute("pageCode",pageCode);
            request.setAttribute("diaryList",diaryList);
            System.out.println("diaryList"+diaryList);
            session.setAttribute("diaryTypeCountList", diaryTypeDao.diaryTypeCountList(con));
            session.setAttribute("diaryCountList", diaryDao.diaryCountList(con));

            request.setAttribute("mainPage", "diary/diaryList.jsp");
            request.getRequestDispatcher("mainTemp.jsp").forward(request,response);

        }catch(Exception e)
        {
             e.printStackTrace();

        }  try {

            dbUtil.colseCon(con);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    /**
     * 这个应该是页数
     * @param totalNum
     * @param currentPage
     * @param pageSize
     * @return
     */
    private String genPagation(int totalNum,int currentPage,int pageSize)
    {

        int totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
        StringBuffer pageCode=new StringBuffer();
        //首页
        pageCode.append("<li><a href='main?page=1'>首页</li>");
        //上一页
        if(currentPage==1){
            pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
        }else{
            pageCode.append("<li><a href='main?page="+(currentPage-1)+"'>上一页</a></li>");
        }
        //放四个
        for(int i=currentPage-2;i<=currentPage+2;i++){
            if(i<1||i>totalPage){
                continue;
            }
            if(i==currentPage){
                pageCode.append("<li class='active'><a href='#'>"+i+"</a></li>");
            }else{
                pageCode.append("<li><a href='main?page="+i+"'>"+i+"</a></li>");
            }
        }
        //下一页
        if(currentPage==totalPage){
            pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
        }else{
            pageCode.append("<li><a href='main?page="+(currentPage+1)+"'>下一页</a></li>");
        }
        //尾页
        pageCode.append("<li><a href='main?page="+totalPage+"'>尾页</a></li>");
        return pageCode.toString();
    }
}

