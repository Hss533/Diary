package per.hss.web;

import per.hss.dao.DiaryDao;
import per.hss.dao.DiaryTypeDao;
import per.hss.model.DiaryType;
import per.hss.util.DbUtil;
import per.hss.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;


public class DiaryTypeServlet extends HttpServlet {

    DbUtil dbUtil=new DbUtil();
    DiaryTypeDao diaryTypeDao=new DiaryTypeDao();
    DiaryDao diaryDao=new DiaryDao();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action=request.getParameter("action");
        if("list".equals(action))
        {
            diaryTypeList(request,response);
        }else if("preSave".equals(action))
        {
            diaryTypePreSave(request,response);
        }else if("save".equals(action))
        {
            diaryTypeSave(request,response);
        }else if("delete".equals(action))
        {
            diaryTypeDelete(request,response);
        }

    }
    private void diaryTypeList(HttpServletRequest request,HttpServletResponse response)throws  ServletException,IOException
    {
        Connection con=null;
        try{
            con=dbUtil.getCon();
            List<DiaryType> diaryTypeList=diaryTypeDao.diaryTypeList(con);
            request.setAttribute("diaryTypeList",diaryTypeList);
            request.setAttribute("mainPage","diaryType/diaryTypeList.jsp");
            request.getRequestDispatcher("mainTemp.jsp").forward(request,response);

        }catch(Exception e)
        {
            e.printStackTrace();
        }finally {
            try{
                dbUtil.colseCon(con);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private void diaryTypePreSave(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        Connection con=null;
        String diaryTypeId=request.getParameter("diaryTypeId");
        try
        {
            con=dbUtil.getCon();
            DiaryType diaryType=diaryTypeDao.diaryTypeShow(con,diaryTypeId);
            request.setAttribute("diaryType",diaryType);
        }catch(Exception e)
        {
            e.printStackTrace();
        }finally {
            try{
                dbUtil.colseCon(con);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        request.setAttribute("mainPage","diaryType/diaryTypeSave.jsp");
        request.getRequestDispatcher("mainTemp.jsp").forward(request,response);
    }
    private void diaryTypeSave(HttpServletRequest request,HttpServletResponse response)throws  ServletException,IOException {

        StringUtil stringUtil = new StringUtil();
        String diaryTypeId = request.getParameter("diaryTypeId");
        String typeName = request.getParameter("typeName");
        DiaryType diaryType = new DiaryType();
        if (stringUtil.isNotEmpty(diaryTypeId)) {
            //这个是修改
            diaryType.setDiaryTypeId(Integer.parseInt(diaryTypeId));
        }
        diaryType.setTypeName(typeName);
        Connection con = null;

        try {
            con = dbUtil.getCon();
            int saveNum ;
            if (StringUtil.isNotEmpty(diaryTypeId)) {
                saveNum = diaryTypeDao.diaryTypeUpdate(con, diaryType);

            } else {
                saveNum = diaryTypeDao.diaryTypeAdd(con, diaryType);
            }
            if (saveNum > 0) {
                //保存好了之后就要显示的是文章类别列表
                request.getRequestDispatcher("diaryType?action=list").forward(request, response);
            } else {
                request.setAttribute("diaryType", diaryType);
                request.setAttribute("error", "保存失败");
                request.setAttribute("mainPage", "diaryType/diaryTypeSave.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.colseCon(con);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void diaryTypeDelete(HttpServletRequest request,HttpServletResponse response) throws  ServletException ,IOException
    {
            String diaryTypeId=request.getParameter("diaryTypeId");
            Connection con=null;
            try{
                con=dbUtil.getCon();
                if(diaryDao.existDiaryWithTypeId(con,diaryTypeId))
                {
                    request.setAttribute("error","该日志下面有日志，不能删除该类别");
                }
                else{
                    diaryTypeDao.diaryTypeDelete(con,diaryTypeId);
                }
                //删除完成之后又回到列表界面
                request.getRequestDispatcher("diaryType?action=list").forward(request,response);
            }catch(Exception e)
            {
                e.printStackTrace();
            }finally{
                try {
                    dbUtil.colseCon(con);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

