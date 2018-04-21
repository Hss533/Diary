<%--
  Created by IntelliJ IDEA.
  User: hu
  Date: 2018/3/24
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" import="per.hss.model.User" %>
<%
    if(request.getParameter("user")==null)
    {
        String userName=null;
        String password=null;
        //根据cookie获取姓名和密码
        Cookie[] cookies=request.getCookies();
        for(int i=0;cookies!=null&&i<cookies.length;i++)
        {
            if(cookies[i].getName().equals("user"))
            {
                userName=cookies[i].getValue().split("-")[0];
                password=cookies[i].getValue().split("-")[1];
            }
        }
        if(userName==null)
        {
            userName="";
        }
        if(password==null)
        {
            password="";
        }
        pageContext.setAttribute("user",new User(userName,password));
    }
%>
<html lang="zh">
<head>
    <title>个人日记本登陆</title>
    <script>
        function checkForm() {
            var userName=document.getElementById("userName").value;
            var password=document.getElementById("password").value;
            if(userName==null||userName=="")
            {
                document.getElementById("error").innerHTML="用户名不能为空";
                return false;
            }
            if(password==null||password=="")
            {
                document.getElementById("error").innerHTML="密码不能为空";
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<form action="Login" method="post" onsubmit="return checkForm()">
    <h2>个人日记本</h2>
    用户名<input id="userName" name="userName" value="${user.userName}" type="text">
    密码<input id="password" name="password" value="${user.password}" type="password">
    <label class="checkbox">
        <input class="remember" name="remember" type="checkbox" value="remember-me">记住我
        <span style="color: red" id="error">${error}</span>
    </label>
    <button type="submit" >登陆</button>
    <button type="button"> 重置</button>
</form>
</body>
</html>
