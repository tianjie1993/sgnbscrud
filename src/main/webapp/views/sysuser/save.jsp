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
			<form class="layui-form" action="${basePath }/user/save">
			<input type="hidden"  name="uuid" value="${user.uuid }">
			<input type="hidden"  name="roleIds" value="${user.roleIds }">
			<input type="hidden"  name="status" value="${user.status }">
			  <div class="layui-row">
				 <div class="layui-col-md12">
			    	<label class="layui-form-label"><em class="must"></em>用户名：</label>
			    	<div class="layui-input-block">
			     	 <input name="name" required lay-verify="required" value="${user.name }" autocomplete="off" class="layui-input" type="text">
			    	</div>
			 	 </div>
			  </div>
			  <div class="layui-row">
				 <div class="layui-col-md12">
			    	<label class="layui-form-label"><em class="must"></em>手机号：</label>
			    	<div class="layui-input-block">
			     	 <input name="phonenum" required lay-verify="phone" value="${user.phonenum }" autocomplete="off" class="layui-input" type="text">
			    	</div>
			 	 </div>
			  </div>
			  <div class="layui-row">
				 <div class="layui-col-md12">
			    <label class="layui-form-label"><em class="must"></em>登录账号：</label>
			    <div class="layui-input-block">
			      <input name="login" onchange="checkLogin()" id="login" required lay-verify="required" value="${user.login }" autocomplete="off" class="layui-input" type="text">
			    </div>
			  </div>
			  </div>
			  <div class="layui-row">
				 <div class="layui-col-md12">
			    <label class="layui-form-label"><em class="must"></em>用户所在组织架构：</label>
			    <div class="layui-input-block">
			   		<input type="hidden" name="treecode" id="treecode" value="${user.treecode }">
			        <input  id="orgname" value="${user.groupOrg.orgname }" required autocomplete="off" onfocus="chooseOrg()" class="layui-input" type="text">
			    </div>
			  </div>
			  </div>
			 <%--  <div class="layui-row">
				 <div class="layui-col-md12">
			    <label class="layui-form-label"><em class="must"></em>用户部门：</label>
			    <div class="layui-input-block">
			   		<input type="hidden" name="departId" id="departId" value="${user.departId }">
			        <input  id="departame" value="${user.userdepart.departName }" required autocomplete="off" onclick="choseDepart()" class="layui-input" type="text">
			    </div>
			  </div>
			  </div> --%>
			  <c:if test="${empty user.uuid }">
			    <div class="layui-row">
					<div class="layui-col-md12">
				    <label class="layui-form-label"></label>
				    <div class="layui-input-block">
				   		<p style="color:red">初始化密码为: xgzc123,请用户在第一次登录的时候及时修改！</p>
				    </div>
				  </div>
			 	 </div>
			  </c:if>
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
<script type="text/javascript">
	function checkLogin(){
		 $.ajax({
				type: "GET", 
	            dataType:"json",
	            url: "${basePath}/user/checkLogin", 
	            data:{
	            	login:$("#login").val(),
	            },
	            success: function(data){
	            	if('201'==data.code){
	            		layer.msg(data.desc);
	            		$("#login").val("");
	            	}
	            }
			});
	}
	
	function cancel(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	
	function chooseOrg(){
		layer.open({
			  title: '选择组织架构',				
			  type: 2,
			  area: ['400px', '400px'],
			  shadeClose : true, 
			  maxmin: true,
			  content: basePath+ '/user/tochooseorg'
		});
	 }
	
	function choseDepart(){
		var treecode = $("#treecode").val();
		if(!treecode){
			layer.msg('请先选择组织架构！', { anim: 6});
			return ;
		}
		layer.open({
			  title: '选择组织架构',				
			  type: 2,
			  area: ['400px', '400px'],
			  shadeClose : true, 
			  maxmin: true,
			  content: basePath+ '/user/tochoosedepart?treecode='+treecode
		});
		
	}

</script>
</html>