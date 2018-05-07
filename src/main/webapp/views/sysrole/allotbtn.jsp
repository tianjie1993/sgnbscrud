<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>分配操作按钮</title>
<link rel="stylesheet" href="${staticPath}/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="${staticPath}/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${staticPath}/ztree/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="${staticPath}/ztree/js/jquery.ztree.excheck.js"></script>
</head>
<body>
	<input type="hidden" id="roleid" value="${roleid }">
	<div class="detail-page">
		<div id="ztree" class="ztree"></div>
			<div class="layui-row detail-btn topline">
		    <div class="layui-input-block">
		      <button type="button" onclick="cancel()" class="laycancel layui-btn layui-btn-small layui-btn-warm"> 取 消 </button>
		      <button type="button" onclick="allotbtn()" class="layui-btn layui-btn-small layui-btn-normal"> 确 定 </button>
		    </div>
		  </div>
		</div>
	
</body>
<script type="text/javascript">
	$(function(){
		LoadTree();
	})
	function LoadTree(){
		var setting = {	
			view: {
				selectedMulti: false
			},
			 check: {
				enable: true
			}, 
			data: {
				simpleData: {
					enable: true,
					idKey:"id",
					pIdKey:"pid"
				}
			}
		}
		$.getJSON("${basePath}/action/LoadTree?id="+$("#roleid").val(),function(data){
			$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
		});
	}
	
	function cancel(){
		var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
		parent.layer.close(index);
	}
	
	function allotbtn(){
		var zTree = $.fn.zTree.getZTreeObj("ztree");
		var nodes = zTree.getCheckedNodes(true);
		console.log(nodes);
		if (nodes.length == 0) {
			layer.msg("请先选择一个节点");
			return;
		}
		var ids='';
		for(var i=0;i<nodes.length;i++){
			if(i==nodes.length-1)
				ids +=nodes[i].id
			else
				ids +=nodes[i].id+','
		}
		
		 $.ajax({
				type: "POST", 
	            dataType:"json",
	            url: "${basePath}/role/allotbtn", 
	            data:{
	            	btnids:ids,
	            	roleid :$("#roleid").val()
	            },
	            success: function(data){
	            	if('200'==data.code){
	            		parent.layer.msg("操作成功！", {icon:6});
	            		//触发父页面刷新
	        			parent.$('#reload').change();
	            		cancel();
	            	}else{
	            		layer.msg(data.desc);
	            	}
	            }
			});
	}
</script>
</html>