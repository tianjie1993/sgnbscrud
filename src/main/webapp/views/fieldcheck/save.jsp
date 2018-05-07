<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户新增/修改</title>
</head>
<body>
 	<%@ include file="/views/common/perform.jsp"%>
		<div class="detail-page">
			<form class="layui-form" action="${basePath }/crud/save/fieldcheck" method="post">
			<input type="hidden"  name="id" value="${entity.id }">
			  <div class="layui-row">
				 <div class="layui-col-md12">
			    	<label class="layui-form-label"><em class="must"></em>表名：</label>
			    	<div class="layui-input-block">
			     	 <input name="tablename" required lay-verify="required" value="${entity.tablename }" autocomplete="off" class="layui-input" type="text">
			    	</div>
			 	 </div>
			  </div>
			  <div class="layui-row">
				 <div class="layui-col-md12">
			    	<label class="layui-form-label"><em class="must"></em>字段名：</label>
			    	<div class="layui-input-block">
			     	 <input name="fieldname" required lay-verify="required" value="${entity.fieldname }" autocomplete="off" class="layui-input" type="text">
			    	</div>
			 	 </div>
			  </div>
			  <div class="layui-row">
				 <div class="layui-col-md12">
			    <label class="layui-form-label"><em class="must"></em>枚举项：</label>
			    <div class="layui-input-block">
				    <textarea name="eums" placeholder="1=xx,2=xxx"   class="layui-textarea">${entity.eums }</textarea>
			    </div>
			  </div>
			  </div>
				<div class="layui-row detail-btn topline">
			    <div class="layui-input-block">
			      <button type="button" onclick="cancel()" class="laycancel layui-btn layui-btn-small layui-btn-warm"> 取 消 </button>
			      <button type="submit" class="layui-btn layui-btn-small layui-btn-normal"> 确 定 </button>
			    </div>
			  </div>
			</form>
		</div>
</body>
<script type="text/javascript" src="${staticPath }/layui/layui.all.js"></script>
</html>