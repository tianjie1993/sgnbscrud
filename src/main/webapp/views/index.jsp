<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理系统3.0</title>
</head>
<body class="layui-layout-body">
		<div class="layui-layout layui-layout-admin">
		<%@ include file="/views/common/header.jsp"%>

	<!-- 左侧导航区域 -->
	<div class="layui-side layui-bg-black" id="admin-side">
		<div class="layui-side-scroll">
			<ul class="layui-nav layui-nav-tree" lay-filter="adminnav">
				<c:forEach items="${menus }" var="menu" varStatus="status">
					<li class="layui-nav-item">
					<c:choose >
						<c:when test="${empty menu.url}">
							<a class="" href="javascript:;"><i class="layui-icon">&#${menu.icon }&nbsp;</i>${menu.name }</a>
						</c:when>
						<c:otherwise>
							<a data-id="${menu.id }" data-type="tabAdd" href="javascript:;" data-href="${basePath}/menu/entrance?id=${secmenu.id }">${menu.name }</a>
						</c:otherwise>
					</c:choose>
					<c:if test="${not empty menu.semenus}">
						<dl class="layui-nav-child">
					   		<c:forEach items="${menu.semenus }" var="secmenu">
								<dd>
									<a data-id="${secmenu.id }" data-type="tabAdd" href="javascript:;" data-href="${basePath}/menu/entrance?id=${secmenu.id }">${secmenu.name }</a>
								</dd>
					   		</c:forEach>
						</dl>
				   </c:if>
				   </li>
				</c:forEach>
				<c:if test="${active=='dev' }">
				<li class="layui-nav-item">
					<a class="" href="javascript:;"><i class="layui-icon">&#xe60f;</i>开发者选项</a>
					<dl class="layui-nav-child">
						<dd><a data-id="100" data-type="tabAdd" href="javascript:;" data-href="${basePath}/crud/tolist/fieldcheck">系统枚举项管理</a></dd>
						<dd><a data-id="101" data-type="tabAdd" href="javascript:;" data-href="${basePath}/crud/tolist/sys_selectky">选择弹出框管理</a></dd>
						<dd><a data-id="102" data-type="tabAdd" href="javascript:;" data-href="${basePath}/crud/tolist/sys_action">功能按钮管理</a></dd>
						<dd><a data-id="103" data-type="tabAdd" href="javascript:;" data-href="${basePath}/crud/tolist/sys_menu">系统菜单</a></dd>
					</dl>
				</li>
			</c:if>
			</ul>
		</div>
	</div>

			<!-- 内容主体区域 -->
			<div class="layui-body" id="admin-body">
				<div class="layui-tab" lay-filter="admin-menu-tabs" lay-allowClose="true" >
				  <ul class="layui-tab-title">
				    <li class="layui-this">首页</li>
				  </ul>
				  <div class="layui-tab-content">
				    <div class="layui-tab-item layui-show"  style="overflow: auto;">
				    	<iframe src="${basePath }/common/main"></iframe>
				    </div>
				  </div>
				</div>
			</div>

			<!-- 底部固定区域 -->
			<!-- <div class="layui-footer" id="admin-footer">
				© 南京松果网络科技有限公司
			</div> -->
		</div>
		<script type="text/javascript" src="${staticPath }/layui/layui.all.js"></script>
		<script type="text/javascript" src="${staticPath }/js/index.js"></script>
	</body>
</html>