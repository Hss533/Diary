<%--
  Created by IntelliJ IDEA.
  User: hu
  Date: 2018/3/26
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="zh">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/style/diary.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/bootstrap/js/jQuery.js"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/ckeditor/ckeditor.js"></script>
    <title>个人日记本主页</title>
    <style type="text/css">
        body{
            padding-top: 60px;
            padding-bottom: 40px;
        }
    </style>
    <script type="text/javascript">
    </script>
</head>
<body>
<!--头部-->
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="brand" href="#">Ysmbddjglww's Diary Book</a>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="active"><a href="main?all=true"><i class="icon-home"></i>&nbsp;Index Page</a></li>
                    <li class="active"><a href="diary?action=preSave"><i class="icon-pencil"></i>&nbsp;Write Diary</a></li>
                    <li class="active"><a href="diaryType?action=list"><i class="icon-book"></i>&nbsp;Diarys Manager</a></li>
                    <li class="active"><a href="user?action=preSave"><i class="icon-user"></i>&nbsp;User</a></li>
                </ul>
            </div>
            <form name="myForm" class="navbgit ar-form pull-right" method="post" action="main?all=true">
                <input class="span2" id="s_title" name="s_title"  type="text" style="margin-top:5px;height:30px;" placeholder="Search...">
                <button type="submit" class="btn" onkeydown="if(event.keyCode==13) myForm.submit()"><i class="icon-search"></i>&nbsp;Search Dirays</button>
            </form>
        </div>
    </div>
</div>
<div class="container">
    <div class="row-fluid">
        <div class="span9">
            <jsp:include page="${mainPage}"></jsp:include>
        </div>
        <div class="span3">
            <div class="data_list">
                <div class="data_list_title">
                    <img src="${pageContext.request.contextPath}/images/user_icon.png"/>
                    个人中心
                </div>
                <div class="user_image">
                    <img src="${currentUser.imageName }"/>

                </div>
                <div class="nickName">${currentUser.nickName }</div>
                <div class="userSign">(${currentUser.mood })</div>
                <div >${currentUser.imageName}</div>
            </div>

            <div class="data_list">
                <div class="data_list_title">
                    <img src="${pageContext.request.contextPath}/images/byType_icon.png"/>
                    按日志类别
                </div>
                <div class="data">
                    <ul>
                        <c:forEach var="diaryTypeCount" items="${diaryTypeCountList }">
                            <li><span><a href="main?s_typeId=${diaryTypeCount.diaryTypeId }">${diaryTypeCount.typeName }(${diaryTypeCount.diaryCount })</a></span></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

            <div class="data_list">
                <div class="data_list_title">
                    <img src="${pageContext.request.contextPath}/images/byDate_icon.png"/>
                    按日志日期
                </div>
                <div class="datas">
                    <ul>
                        <c:forEach var="diaryCount" items="${diaryCountList }">
                            <li><span><a href="main?s_releaseDateStr=${diaryCount.releaseDateStr }">${diaryCount.releaseDateStr }(${diaryCount.diaryCount })</a></span></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>