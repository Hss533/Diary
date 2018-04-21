package per.hss.dao;
import java.sql.*;

import per.hss.model.Diary;
import per.hss.model.DiaryType;
import per.hss.util.DbUtil;

import java.util.*;
public class DiaryTypeDao {
    //
    public List<DiaryType> diaryTypeCountList(Connection con)throws Exception{
        List<DiaryType> diaryTypeCountList=new ArrayList<DiaryType>();
        String sql="SELECT diaryTypeId,typeName,COUNT(diaryId) as diaryCount FROM t_diary RIGHT JOIN t_diaryType ON t_diary.typeId=t_diaryType.diaryTypeId GROUP BY typeName;";
        PreparedStatement pstmt=con.prepareStatement(sql);
        ResultSet rs=pstmt.executeQuery();
        while(rs.next()){
            DiaryType diaryType=new DiaryType();
            diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
            diaryType.setTypeName(rs.getString("typeName"));
            diaryType.setDiaryCount(rs.getInt("diaryCount"));

            diaryTypeCountList.add(diaryType);
        }
        return diaryTypeCountList;
    }
    public List<DiaryType> diaryTypeList(Connection con){
        List<DiaryType> diaryTypeList=new ArrayList<>();
        String sql="select * from t_diaryType";

        try {
            PreparedStatement ptmt=con.prepareStatement(sql);
            ResultSet re=ptmt.executeQuery();
            while(re.next())
            {
                DiaryType diaryType=new DiaryType();
                diaryType.setDiaryTypeId(re.getInt("diaryTypeId"));
                diaryType.setTypeName(re.getString("typeName"));
                diaryTypeList.add(diaryType);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diaryTypeList;
    }
    public int diaryTypeAdd(Connection con,DiaryType diaryType)
    {
        String sql="insert into t_diaryType VALUES (NULL ,?)";
        int num=0;
        try {
            PreparedStatement ptmt=con.prepareStatement(sql);
            ptmt.setString(1,diaryType.getTypeName());
            num=ptmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }
    public int diaryTypeUpdate(Connection con,DiaryType diaryType)throws Exception{
        String sql="update t_diaryType set typeName=? where diaryTypeId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, diaryType.getTypeName());
        pstmt.setInt(2, diaryType.getDiaryTypeId());
        return pstmt.executeUpdate();
    }
    public DiaryType diaryTypeShow(Connection con,String diaryTypeId)throws Exception{
        String sql="SELECT * from t_diaryType where diaryTypeId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, diaryTypeId);
        ResultSet rs=pstmt.executeQuery();
        DiaryType diaryType=new DiaryType();
        if(rs.next()){
            diaryType.setDiaryTypeId(rs.getInt("diaryTypeId"));
            diaryType.setTypeName(rs.getString("typeName"));
        }
        return diaryType;
    }

    public int diaryTypeDelete(Connection con,String diaryTypeId)throws Exception{
        String sql="delete from t_diaryType where diaryTypeId=?";
        PreparedStatement pstmt=con.prepareStatement(sql);
        pstmt.setString(1, diaryTypeId);
        return pstmt.executeUpdate();
    }
    public static void main(String args[])throws  Exception
    {
        DiaryTypeDao test=new DiaryTypeDao();
        DbUtil dbUtil=new DbUtil();

        List<DiaryType> list=test.diaryTypeList(dbUtil.getCon());
        for (DiaryType d:list
             ) {
            System.out.println();
            System.out.println(d.getDiaryCount());
            System.out.println(d.getDiaryTypeId());
            System.out.println(d.getTypeName());

        }
    }
}
