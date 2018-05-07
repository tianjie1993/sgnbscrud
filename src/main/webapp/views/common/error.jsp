<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<title>错误信息</title>
		<style type="text/css">
			.sys-error{
				position: absolute;
				width:600px;
				height:500px;
				top:50%;
				margin-top:-250px;
				left: 50%;
				margin-left: -300px;
				padding:20px 0;
				overflow: hidden;
			}
			
			.sys-error>img{
				width:300px;
				height: auto;
				float: left;
			}
			.error-text{
				width:280px;
				float: right;
			}
			.error-text>h3{
				margin-top:40px;
				color: #3398af;
				margin-bottom: 10px;
			}
			.error-text>span{
				font-size: 16px;
				color: #666;
			}
			.error-text>div h6{
				font-size: 14px;
				font-weight: normal;
				margin:40px 0 0;
			}
			.error-text>div p{
				margin:5px 0;
				font-size: 12px;
				line-height: 1.6em;
			}
		</style>
	</head>
	<body>
		<div class="sys-error">
			<img src="${staticPath }/imgs/error.jpg"/>
			<div class="error-text">
				<h3>抱歉！</h3>
				<span>您访问的页面出问题了…</span>
				<div>
					<h6>错误详情：</h6>
					<p>${error }</p>
				</div>
			</div>
		</div>
	</body>
</html>