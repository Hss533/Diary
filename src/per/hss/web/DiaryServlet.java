package per.hss.web;

import per.hss.dao.DiaryDao;
import per.hss.model.Diary;
import per.hss.util.DbUtil;
import per.hss.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "DiaryServlet")
public class DiaryServlet extends HttpServlet {

    DbUtil dbUtil=new DbUtil();
    DiaryDao diaryDao =new DiaryDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        String action=request.getParameter("action");
        if("show".equals(action)){
            //查看
            diaryShow(request,response);
        }else if("preSave".equals(action)){
            //预先保存
            diaryPreSave(request,response);
        }else if("save".equals(action)){
            //保存
            System.out.println("保存");
            diarySave(request,response);
        }else if("delete".equals(action)){
            //删除
            diaryDelete(request,response);
        }
    }

    /**
     * 查看日志
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void diaryShow(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException
    {
        String diaryId=request.getParameter("diaryId");
        Connection con=null;
        con=dbUtil.getCon();
        try {
            Diary diary=diaryDao.diaryShow(con,diaryId);
            request.setAttribute("diary",diary);
            request.setAttribute("mainPage","diary/diaryShow.jsp");
            request.getRequestDispatcher("mainTemp.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂存日志
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void diaryPreSave(HttpServletRequest request,HttpServletResponse response) throws  ServletException,IOException{

        System.out.println("进入到写日记界面");

        String diaryId=request.getParameter("diaryId");
        System.out.println(diaryId);
        StringUtil stringUtil=new StringUtil();
        Connection con=null;
        try{
            if(StringUtil.isNotEmpty(diaryId))
            {
                con=dbUtil.getCon();
                Diary diary=diaryDao.diaryShow(con,diaryId);
                request.setAttribute("diary",diary);
                System.out.println(diary);
            }
            request.setAttribute("mainPage","diary/diarySave.jsp");
            System.out.println("从写日记界面出去");
            request.getRequestDispatcher("mainTemp.jsp").forward(request, response);

        }catch(Exception e)
        {
            e.printStackTrace();
        }finally {
            try {
                if(con!=null)
                    dbUtil.colseCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存日记或者是更新日志
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void diarySave(HttpServletRequest request,HttpServletResponse response) throws  ServletException,IOException{


            StringUtil stringUtil=new StringUtil();

            String title=request.getParameter("title");
            String content=request.getParameter("content");
            String typedId=request.getParameter("typeId");
            String diaryId=request.getParameter("diaryId");
            System.out.println("ahhhhhhusahsah"+title + content);
            Diary diary=new Diary(title,content,Integer.parseInt(typedId));
            if(stringUtil.isNotEmpty(diaryId))
            {
                diary.setDiaryId(Integer.parseInt(diaryId));
            }
            Connection con=null;
            try{
                con=dbUtil.getCon();
                int saveNums;
                if(StringUtil.isNotEmpty(diaryId))
                {
                    //修改日志
                    saveNums=diaryDao.diaryUpdate(con,diary);
                }else{
                    saveNums=diaryDao.diaryAdd(con,diary);
                }
                if(saveNums>0)
                {
                    request.getRequestDispatcher("main?all=true").forward(request,response);
                }else{

                    request.setAttribute("diary",diary);
                    request.setAttribute("error","保存失败");
                    request.setAttribute("mainPage","diary/diarySave.jsp");
                    request.getRequestDispatcher("mainTemp.jsp").forward(request,response);
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                try {
                    if(con!=null)
                    dbUtil.colseCon(con);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
    }

    /**
     * 删除日记
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void diaryDelete(HttpServletRequest request,HttpServletResponse response) throws  ServletException,IOException{
        String diaryId=request.getParameter("diaryId");
        Connection con=null;
        try{
            con=dbUtil.getCon();
            diaryDao.diaryDelete(con,diaryId);
            request.getRequestDispatcher("main?all=true").forward(request,response);
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally {
            try{
                dbUtil.colseCon(con);
            }catch ( Exception e1)
            {
                e1.printStackTrace();
            }
        }
    }

}
