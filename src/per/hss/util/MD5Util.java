package per.hss.util;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

public class MD5Util {
    //将密码进行md5加密
   public String EncodingPwdByMd5(String str){
       String string=null;
       try{
           MessageDigest md5=MessageDigest.getInstance("MD5");
           BASE64Encoder base64Encoder=new BASE64Encoder();
           string= base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
       }catch(Exception e)
       {
            e.printStackTrace();
       }
        return string;
   }
}
