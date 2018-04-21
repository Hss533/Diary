package per.hss.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Picture  {

    public static void main(String[] args) throws Exception
    {
        //从输入流中读取数据
        FileInputStream in=new FileInputStream("C:/Users/hu/Desktop/2.png");
        //从输出流中写入数据
        FileOutputStream out=new FileOutputStream("C:/Users/hu/Desktop/tmp.png");
        byte[] buffer=new byte[10240];
        int size=0;
        while((size=in.read(buffer))!=-1)
        {
            out.write(buffer,0,size);
        }
        out.close();
        in.close();
    }
}
