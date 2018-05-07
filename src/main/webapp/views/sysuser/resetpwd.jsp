<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
</head>
<body>
 	<%@ include file="/views/common/perform.jsp"%>
		<div class="detail-page">
			<form class="layui-form" action="${basePath }/user/resetpwd" method="post">
			  <div class="layui-row">
				 <div class="layui-col-md12">
			    	<label class="layui-form-label"><em class="must"></em>原始密码：</label>
			    	<div class="layui-input-block">
			     	 <input name="pwd" required   class="layui-input" type="password">
			    	</div>
			 	 </div>
			  </div>
			  <div class="layui-row">
				 <div class="layui-col-md12">
			    <label class="layui-form-label"><em class="must"></em>新密码：</label>
			    <div class="layui-input-block">
			     	 <input name="newpwd" required   class="layui-input" type="password">
			    </div>
			  </div>
			  </div>
			  <div class="layui-row">
				 <div class="layui-col-md12">
			    <label class="layui-form-label"><em class="must"></em>密码确认：</label>
			    <div class="layui-input-block">
			    	<input name="confirmpwd" required   class="layui-input" type="password">
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
<script type="text/javascript">
	function cancel(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}

</script>
</html>