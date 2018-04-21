package per.hss.dao;

import per.hss.model.User;
import per.hss.util.MD5Util;
import per.hss.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    PropertiesUtil propertiesUtil=new PropertiesUtil();
    MD5Util md5Util=new MD5Util();
    public User login(Connection con, User user){
        User resultUser=null;
        String sql="select * from t_user where userName=? and password=?";
        try {
            PreparedStatement ptmt = con.prepareStatement(sql);
            ptmt.setString(1,user.getUserName());
            ptmt.setString(2,md5Util.EncodingPwdByMd5(user.getPassword()));
            ResultSet re=ptmt.executeQuery();
            while(re.next())
            {
                resultUser=new User();
                resultUser.setUserId(re.getInt("userId"));
                resultUser.setUserName(re.getString("userName"));
                resultUser.setPassword(re.getString("password"));
                resultUser.setNickName(re.getString("nickName"));
                resultUser.setImageName(propertiesUtil.getValue("imageFile")+re.getString("imageName"));
                resultUser.setMood(re.getString("mood"));
            }


        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return resultUser;
    }
    //修改个人信息
    public int userUpdate(Connection con,User user)
    {
        String sql="update t_user set nickName=?,imageName=?,mood=? where userId=?";
        int num=0;
        try{
            PreparedStatement ptmt=con.prepareStatement(sql);
            ptmt.setString(1,user.getNickName());
            ptmt.setString(2,user.getImageName());
            ptmt.setString(3,user.getMood());
            ptmt.setInt(4,user.getUserId());
            num=ptmt.executeUpdate();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return num;
    }
}
