<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta charset="UTF-8">
		<title>404</title>
		<style type="text/css">
			html,body{
				height:100%;
				position: relative;
				background:#e8dadf ;
			}
			.error404{
				width:500px;
				height:auto;
				position: absolute;
				top:0;
				right:0;
				bottom:0;
				left: 0;
				margin:auto;
			}
		</style>
	</head>
	<body>
		<img class="error404" src="${staticPath }/imgs/404.jpg" alt="" />
	</body>
</html>