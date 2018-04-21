package per.hss.web;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class TestServlet extends HttpServlet {

    public TestServlet()
    {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("test.jsp").forward(request,response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        DiskFileItemFactory factory=new DiskFileItemFactory();
        /**
         * 获取服务器下的工程文件中test的保存路径
         */
        String path="D:/IdeaWeb/Diary/web/test";
        System.out.println("文件保存路径"+path);
        /**
         * 将上传的文件暂存在存储室  这个存储室 可以和最终存储文件的
         */
        factory.setRepository(new File(path));
        //设置缓存的大小，当上传文件的容量超过该缓存是，直接放到暂时存储室
        factory.setSizeThreshold(1024*1024);
        //文件上传处理
        ServletFileUpload upload=new ServletFileUpload(factory);
        try{
            //可以一次上传多个文件
            List<FileItem> list=upload.parseRequest(request);
            for(FileItem item:list)
            {
                //获取表单的属性名字
                String name=item.getFieldName();
                //如果获取的表单信息是普通的文本信息
                if(item.isFormField())
                {
                    //获取用户具体输入的字符串
                    String value=item.getString();
                    request.setAttribute(name,value);
                }
                //传入的是非简单的字符串
                else{
                    //获取上传文件的名字
                    String value=item.getName();
                    int start=value.lastIndexOf("/");
                    //截取上传文件的名字，加一是去掉反斜杠
                    String filename=value.substring(start+1);
                    request.setAttribute(name,filename);
                    System.out.println(path);
                    System.out.println(filename);

                    //真正写到磁盘上
                    OutputStream out=new FileOutputStream(new File(path,filename));
                    InputStream in=item.getInputStream();
                    int length;
                    byte[] buf=new byte[10240];
                    System.out.println("获取上传文件的总共的容量"+item.getSize()+"文件名为"+path+"/"+filename);

                    //in.read(buf)每次读到的数据存放在buf数据中
                    while((length=in.read())!=-1)
                    {
                        out.write(buf,0,length);
                    }
                    //从输入流中读取数据
                    /*FileInputStream in=new FileInputStream(path+"/"+filename);
                    //从输出流中写入数据
                    FileOutputStream out=new FileOutputStream("");
                    byte[] buffer=new byte[10240];
                    int size=0;
                    while((size=in.read(buffer))!=-1)
                    {
                        out.write(buffer,0,size);
                    }
                    out.close();
                    in.close();*/
                    /**
                     * 向数据库中写入文件路径
                     * 略
                     */
                    in.close();
                    out.close();
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
