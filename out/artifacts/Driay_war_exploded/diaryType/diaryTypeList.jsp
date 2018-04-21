<%--
  Created by IntelliJ IDEA.
  User: hu
  Date: 2018/3/29
  Time: 9:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>日志类别列表</title>
    <script type="text/javascript">
        function diaryTypeDelete(diaryTypeId){
            if(confirm("您确定要删除这个日志类别吗？")){
                window.location="diaryType?action=delete&diaryTypeId="+diaryTypeId;
            }
        }
    </script>
</head>
<body>
    <div class="data_list">
        <div class="data_list_title">
            <img src="${pageContext.request.contextPath}/images/list_icon.png">
            日记类别列表
            <span>
                <!--window.location进行页面之间的跳转-->
                <button class="btn btn-mini btn-success" type="button" onclick="javascript:window.location='diaryType?action=preSave'">添加日志类别</button>
            </span>
        </div>
        <div>
            <table class="table table-hover table-stripes">
                <tr>
                    <th>编号</th>
                    <th>类别名称</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="diaryType" items="${diaryTypeList}">
                    <tr>
                        <td>${diaryType.diaryTypeId}</td>
                        <td>${diaryType.typeName}</td>
                        <td>
                            <button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='diaryType?action=preSave&diaryTypeId=${diaryType.diaryTypeId }'">
                                修改
                            </button>
                            &nbsp;
                            <button class="btn btn-mini btn-danger" type="button" onclick="diaryTypeDelete(${diaryType.diaryTypeId })">
                                删除
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div align="center" style="color: red">${error}</div>
    </div>
</body>
</html>
