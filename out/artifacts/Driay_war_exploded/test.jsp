<%--
  Created by IntelliJ IDEA.
  User: hu
  Date: 2018/4/6
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>图片上传test</title>
</head>
<body>
    <form action="test" enctype="multipart/form-data" method="post">
        图片：<input type="file" name="image">
        <input type="submit" value="提交">
    </form>
</body>
</html>
