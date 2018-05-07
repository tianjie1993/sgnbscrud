<%@ page language="java" pageEncoding="UTF-8" %>
<%
request.setAttribute("basePath",   
		request.getScheme() + "://"+ 
		request.getServerName() + ":" +
		request.getServerPort()+ 
		request.getContextPath());
request.setAttribute("staticPath",   
		request.getScheme() + "://"+ 
		request.getServerName() + ":" +
		request.getServerPort()+ 
		request.getContextPath());

%>
<script>
var basePath = '<%=request.getAttribute("basePath")%>';
</script>
<!-- 通用控件 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="${staticPath}/layui/css/layui.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/css/reset.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/css/main.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/css/common.css"/>
<script type="text/javascript" src="${staticPath}/script/jquery.js"></script>
<script type="text/javascript" src="${staticPath }/layui/layui.all.js"></script>
<script type="text/javascript" src="${staticPath }/js/common.js"></script>


