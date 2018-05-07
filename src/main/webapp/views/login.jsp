<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html class="login-css2">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>登录系统</title>
</head>
<body>
	<div class="log-area">
<%-- 			<img class="logo" src="${staticPath }/imgs/logo02.png"/>
 --%>			<div class="log-contain">
				<div class="log-box">
					<ul>
						<li>
							<span class="user"></span>
							<input class="ipt" id="login" placeholder="请输入账号" type="text" />
						</li>
						<li>
							<span class="pass"></span>
							<input class="ipt" id="password"  placeholder="请输入密码" type="password" />
						</li>
						<li>
							<div class="lft">
								<span class="ma"></span>
							<input class="ipt" id="vcode" placeholder="请输入验证码" type="text" />
							</div>
							<div class="rit"><img src="${basePath}/vcode" alt="验证码" id="image"  title="看不清? 请点我" onclick="javascript:reload(this);" onmouseover="mouseover(this)"/></div>
						</li>
						<li class="log-btn">
							<button type="button" id="submit">登 录</button>
						</li>
					</ul>
					
				</div>
			</div>
		</div>
</body>
<script type="text/javascript">
if(window !=top){  
	 top.location.href=location.href;  
} 
$("#submit").click(function(){
	$.ajax({
		type: "POST", 
           dataType:"json",
           url: "${basePath}/login", //目标地址 
           data:{
           	login:$("#login").val(),
           	password:$("#password").val(),
           	vcode:$("#vcode").val()
           },
           success: function(data){
           	if('200'==data.code){
           		location.href="${basePath}/toIndex";
           	}else{
           		layer.msg(data.desc, { anim: 6});
           	}
           }
	});
})
function reload(obj){ 
	obj.src="${basePath}/vcode?"+Math.random(); 
    $("#vcode").val("");   // 将验证码清空  
}   
function mouseover(obj){  
    obj.style.cursor = "pointer";  
}
</script>
</html>