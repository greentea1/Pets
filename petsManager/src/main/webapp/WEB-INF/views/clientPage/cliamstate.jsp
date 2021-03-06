<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>领养申请</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="Gato Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template,
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
    <%@ include file="../client/css.jsp"%>
</head>
<body>
<div id="home" class="header">
    <jsp:include page="../client/header.jsp">
        <jsp:param name="menu" value="cliam"/>
    </jsp:include>
    <div class="banner two">
        <div class="container">
            <div class="pag-nav">
                <ul class="p-list">
                    <li><a href="/index">首页</a></li> &nbsp;&nbsp;/&nbsp;
                    <li class="act">&nbsp;领养申请</li>
                </ul>
            </div>
        </div>
    </div>
</div>
</div>
<div class="gallery services">
    <div class="container">
        <div class="port-head">
            <h3>领养申请信息查看</h3>
            <a href="/client/cliam" class="btn btn-success btn-sm pull-right"><i class="fa fa-plus"></i>返回</a>
        </div>
        <div class="portfolio-bottom">
            <br/>
            <div class="alert alert-success text-center"><p>默认显示当前登录用户记录</p></div>
            <c:if test="${empty cliamList}">
                <p class="text-info text-center">当前用户暂无记录</p>
            </c:if>
            <c:forEach items="${cliamList}" var="cliam">
                <div class="box">
                    <table class="table">
                        <tbody>
                        <tr>
                            <td class="text-muted text-center"><i>用户姓名:</i></td>
                            <td>${cliam.username}</td>
                            <td class="text-muted text-center"><i>认领宠物名称:</i></td>
                            <td>${cliam.petname}</td>
                            <td class="text-muted text-center"><i>联系电话:</i></td>
                            <td>${cliam.mobile}</td>
                        </tr>
                        <tr>
                            <td class="text-muted text-center"><i>申请状态:</i></td>
                            <td>${cliam.state}</td>
                            <td colspan="2"></td>
                            <td class="text-muted text-center"><i>申请时间:</i></td>
                            <td><fmt:formatDate value="${cliam.createTime}"  pattern='yyyy年MM月dd日'/></td>
                        </tr>
                        <tr>
                            <td class="text-muted text-center"><i>申请描述:</i></td>
                            <td>${cliam.content}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<%@ include file="../client/footer.jsp"%>
<%@ include file="../client/js.jsp"%>
</body>
</html>