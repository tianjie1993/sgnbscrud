<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>头部</title>
</head>
<body>
	<div class="layui-header header">
<%-- 		<div class="layui-logo"><img width="230" src="${staticPath }/imgs/logo.png"/></div>
 --%>		
		
		<!-- 头部区域 -->
		<div class="admin-side-toggle">
			<i class="iconfont">&#xe604;</i>
		</div>
		
		<%-- <ul class="header-rit layui-layout-right layui-nav">
			<li>
				欢迎您，<span>${user.name }</span>！
			</li>
			<li>
			 <li class="layui-nav-item">
			    <a href=""><img src="http://t.cn/RCzsdCq" class="layui-nav-img">我</a>
			    <dl class="layui-nav-child">
			      <dd><a href="javascript:;">修改信息</a></dd>
			      <dd><a href="javascript:;">安全管理</a></dd>
			      <dd><a class="quit" href="${basePath }/logout">退出 <i class="iconfont if14">&#xe612;</i></a></dd>
			    </dl>
			  </li>
		</ul> --%>
		<ul class="layui-layout-right layui-nav">
		  <li class="layui-nav-item">
		    <a href="javascript:;">${user.name }</a>
		    <dl class="layui-nav-child">
		      <dd onclick="resetpwd()"><a href="javascript:;" >修改密码</a></dd>
		      <dd><a class="quit" href="${basePath }/logout">退出 </a></dd>
		    </dl>
		  </li>
		  		  <li class="layui-nav-item" style="width: 30px">
		  </li>
		</ul>
	</div>
</body>
<script type="text/javascript">
 function resetpwd(){
	layer.open({
		  title: '修改密码',				
		  type: 2,
		  area: ['500px', '300px'],
		  shadeClose : true, 
		  maxmin: true,
		  content: basePath+ '/user/toresetpwd'
	});
 }
 function changeorg(){
		layer.open({
			  title: '切换组织架构',				
			  type: 2,
			  area: ['400px', '600px'],
			  shadeClose : true, 
			  maxmin: true,
			  content: basePath+ '/user/tochangeorg'
		});
	 }
</script>
</html>