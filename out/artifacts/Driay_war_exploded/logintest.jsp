<%--
  Created by IntelliJ IDEA.
  User: hu
  Date: 2018/3/25
  Time: 23:03
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
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
    <style type="text/css">
        .form-horizontal
        {
            background: rgba(255,255,255,0.4);
            width:400px;
            margin-top: 180px;
            margin-left: 450px;
            position: relative;
            padding-right: 100px;
            padding-top: 60px;
            padding-bottom: 30px;
            border-radius:25px;
        }

    </style>
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
<body style="background: url('${pageContext.request.contextPath}/imageLogin/image1.jpg');background-size: cover;">

    <div >
        <form class="form-horizontal" action="Login" method="post" onsubmit="return checkForm()">
           <div class="control-group">
                <label class="control-label" for="userName">UserName</label>
                <div class="controls">
                    <input type="text" id="userName" placeholder="UserName" name="userName" value="${user.userName}">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="password" >Password</label>
                <div class="controls">
                    <input type="password" id="password" placeholder="Password" name="password" value="${user.password}">
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <label class="checkbox">
                        <input type="checkbox" name="remember" id="remember" value="remember-me">
                        Remember me
                        <span style="color: red;" id="error">${error}</span>
                    </label>
                    <button type="submit" class="btn" style="margin-top: 10px">Sign in</button>
                </div>
            </div>
        </form>
    </div>

</body>
</html>
