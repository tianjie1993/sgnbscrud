<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单新增/修改</title>
</head>
<body>
<%@ include file="/views/common/perform.jsp"%>
	<div class="pagetit">操作按钮
		<c:if test="${empty entity.id}">新增</c:if>
		<c:if test="${not empty entity.id}">修改</c:if>
	</div>
	<form class="layui-form layui-w140" action="${basePath }/crud/save/sys_action" method="post">
	<input name="id" type="hidden"  value="${entity.id}">
	<div class="detail-page">
		<div class="layui-row">
			<div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
			    <label class="layui-form-label"><em class="must"></em>按钮名称：</label>
			    <div class="layui-input-block">
					<input name="name" value="${entity.name }" required lay-verify="required"    class="layui-input" type="text">
			    </div>
			</div>
			<div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
			    <label class="layui-form-label"><em class="must"></em>请求地址：</label>
			    <div class="layui-input-block">
					<input name="url" value="${entity.url }" required lay-verify="required"  class="layui-input" type="text">
			    </div>
			</div>
		</div>
		<div class="layui-row">
		<div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
		    <label class="layui-form-label"><em class="must"></em>所属菜单：</label>
		    <div class="layui-input-block">
					<input type="hidden" name="menuId" id="menuId" value="${entity.menuId}">
			      	<input name="menuname" onclick="openSelectKy('选择菜单','1')"  id="menuname" value="${entity.menuname }" autocomplete="off" class="layui-input" type="text">
			 </div>			 
			 </div>
			 <div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
				<label class="layui-form-label"><em class="must"></em>响应类型：</label>
			  	  <div class="layui-input-block">
					<select name="type">
			    	 	<c:forEach items="${entity.typekv }" var="kv">
			    	 		<c:choose>
			    	 			<c:when  test="${kv.key eq entity.type}">
			    	 				 <option value="${kv.key }" selected>${kv.value }</option>
			    	 			</c:when>
			    	 			<c:otherwise>
			    	 			 	 <option value="${kv.key}">${kv.value}</option>
			    	 			</c:otherwise>
			    	 		</c:choose>
			    	 	</c:forEach>
				      </select>			 
				     </div>
				   </div>
			 
		</div>
		
		<div class="layui-row">
		<div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
			    <label class="layui-form-label"><em class="must"></em>排序：</label>
			    <div class="layui-input-block">
					<input name="sort" value="${entity.sort }" required lay-verify="required"  class="layui-input" type="text">
			    </div>
			</div>
			 <div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
				<label class="layui-form-label"><em class="must"></em>支持批量操作：</label>
			  	  <div class="layui-input-block">
					<select name="canbatch">
			    	 	<c:forEach items="${entity.canbatchkv }" var="kv">
			    	 		<c:choose>
			    	 			<c:when  test="${kv.key eq entity.type}">
			    	 				 <option value="${kv.key }" selected>${kv.value }</option>
			    	 			</c:when>
			    	 			<c:otherwise>
			    	 			 	 <option value="${kv.key}">${kv.value}</option>
			    	 			</c:otherwise>
			    	 		</c:choose>
			    	 	</c:forEach>
				      </select>			 
				     </div>
				   </div>
			 
		</div>
		
		<div class="layui-row">
		<div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
		    <label class="layui-form-label"><em class="must"></em>权限设置：</label>
		    <div class="layui-input-block">
				<select name="isshow">
			    	 	<c:forEach items="${entity.isshowkv }" var="kv">
			    	 		<c:choose>
			    	 			<c:when  test="${kv.key eq entity.isshow}">
			    	 				 <option value="${kv.key }" selected>${kv.value }</option>
			    	 			</c:when>
			    	 			<c:otherwise>
			    	 			 	 <option value="${kv.key}">${kv.value}</option>
			    	 			</c:otherwise>
			    	 		</c:choose>
			    	 	</c:forEach>
				      </select>		
			    </div>			 
			 </div>
			 <div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
				<label class="layui-form-label"><em class="must"></em>是否需要id：</label>
			  	  <div class="layui-input-block">
					<select name="needid">
			    	 	<c:forEach items="${entity.needidkv }" var="kv">
			    	 		<c:choose>
			    	 			<c:when  test="${kv.key eq entity.needid}">
			    	 				 <option value="${kv.key }" selected>${kv.value }</option>
			    	 			</c:when>
			    	 			<c:otherwise>
			    	 			 	 <option value="${kv.key}">${kv.value}</option>
			    	 			</c:otherwise>
			    	 		</c:choose>
			    	 	</c:forEach>
				      </select>			 
				 </div>
			</div>
		 </div>
		<div class="layui-row">
			<div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
			    <label class="layui-form-label">弹出框XY值：</label>
			    <div class="layui-input-block">
					<input name="winxy" value="${entity.winxy }"  placeholder="500px,600px or 50%,50%"  class="layui-input" type="text">
			    </div>
			</div>
			<div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
			    <label class="layui-form-label">权限字符串：</label>
			    <div class="layui-input-block">
					<input name="permission" value="${entity.permission }" class="layui-input" type="text">
			    </div>
			</div>
		</div>
		<div class="layui-row">
			<div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
			    <label class="layui-form-label">菜单样式(只要写&#后面的)：</label>
			    <div class="layui-input-block">
					<input name="btnclass" value="${entity.btnclass }"  class="layui-input" type="text">
 					 	<input type="button"  class="layui-btn" value="参考地址" onclick="window.open('http://www.layui.com/doc/element/icon.html')">
		    	</div>
			</div>
		</div>
		<div class="layui-row detail-btn topline">
		    <div class="layui-input-block">
		      <button type="button" onclick="cancel()" class="laycancel layui-btn layui-btn-small layui-btn-warm"> 取 消 </button>
		      <button lay-submit="" class="layui-btn layui-btn-small layui-btn-normal"> 确 定 </button>
		    </div>
		  </div>
		 </div>
	</form>
</div>
</body>
<script type="text/javascript" src="${staticPath }/layui/layui.all.js"></script>
</html>