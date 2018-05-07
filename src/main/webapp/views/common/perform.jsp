<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内容提醒</title>
</head>
<body>
<input id="code" type="hidden" value="${code}">
<input id="msg" type="hidden"  value="${msg}">
</body>
<script type="text/javascript">
	var code = document.getElementById("code").value;
	var msg = document.getElementById("msg").value;
	if('200'==code){
		parent.layer.msg(msg, {icon:6});
		setTimeout(function(){
			//出发父页面刷新
			parent.$('#reload').change();
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		},1)
	}
	if('201'==code){
		parent.layer.alert(msg,{icon:2});
	}
</script>
</html>