<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增/修改</title>
</head>
<body>
 	<%@ include file="/views/common/perform.jsp"%>
	<div class="pagetit">系统弹出选择框
		<c:if test="${empty entity.id}">新增</c:if>
		<c:if test="${not empty entity.id}">修改</c:if>
	</div>
	<form class="layui-form layui-w140" action="${basePath }/crud/save/sys_selectky" method="post">
	<input name="id" type="hidden"  value="${entity.id}">
	<div class="detail-page">
		<div class="layui-row">
			 <div class="layui-col-md12">
		    	<label class="layui-form-label"><em class="must"></em>FastListDAO方法名：</label>
		    	<div class="layui-input-block">
		     	 <input name="qsql" placeholder="findSysMenulist" required lay-verify="required" value="${entity.qsql }" autocomplete="off" class="layui-input" type="text">
		    	</div>
		 	 </div>
		  </div>
		  <div class="layui-row">
			 <div class="layui-col-md12">
		    	<label class="layui-form-label"><em class="must"></em>展示字段：</label>
		    	<div class="layui-input-block">
		     	 <input name="fields" placeholder="id,name" required lay-verify="required" value="${entity.fields }" autocomplete="off" class="layui-input" type="text">
		    	</div>
		 	 </div>
		  </div>
		  <div class="layui-row">
			 <div class="layui-col-md12">
		    	<label class="layui-form-label"><em class="must"></em>展示字段名：</label>
		    	<div class="layui-input-block">
		     	 <input name="fieldnames" placeholder="主键,名称" required lay-verify="required" value="${entity.fieldnames }" autocomplete="off" class="layui-input" type="text">
		    	</div>
		 	 </div>
		  </div>
		  <div class="layui-row">
			 <div class="layui-col-md12">
		    	<label class="layui-form-label"><em class="must"></em>键对应：</label>
		    	<div class="layui-input-block">
		     	 <input name="keyto" placeholder="id,sid" required lay-verify="required" value="${entity.keyto }" autocomplete="off" class="layui-input" type="text">
		    	</div>
		 	 </div>
		  </div>
		  <div class="layui-row">
			 <div class="layui-col-md12">
		    	<label class="layui-form-label"><em class="must"></em>值对应：</label>
		    	<div class="layui-input-block">
		     	 <input name="valueto" placeholder="name,menuname" required lay-verify="required" value="${entity.valueto }" autocomplete="off" class="layui-input" type="text">
		    	</div>
		 	 </div>
		  </div>
		  <div class="layui-row">
			 <div class="layui-col-md12">
		    	<label class="layui-form-label"><em class="must"></em>搜索信息：</label>
		    	<div class="layui-input-block">
		     	 <input name="searchinfo" placeholder="name:名称,id:主键" required lay-verify="required" value="${entity.searchinfo }" autocomplete="off" class="layui-input" type="text">
		    	</div>
		 	 </div>
		  </div>
		  <div class="layui-row">
			 <div class="layui-col-md12">
		    	<label class="layui-form-label"><em class="must"></em>备注：</label>
		    	<div class="layui-input-block">
		     	 <input name="remark" required lay-verify="required" value="${entity.remark }" autocomplete="off" class="layui-input" type="text">
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
</body>
<script type="text/javascript" src="${staticPath }/layui/layui.all.js"></script>
</html>