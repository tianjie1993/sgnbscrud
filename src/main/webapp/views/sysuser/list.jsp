<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>查询列表</title>
</head>
<body>
	<div class="search-area">
			<div class="search">
				<form class="layui-form" action="">
				  <div class="layui-inline layui-form-sm">
				  	 <div class="layui-inline">
  						<label class="layui-form-label">用户名：</label>
				    <div class="layui-input-inline">
				        <input id="name"   autocomplete="off" placeholder="请输入用户名" class="layui-input" type="text">
				    </div>
				  	 </div>
				  </div>
				  <div class="layui-inline">
				      <input type="button" value="搜索" class="layui-btn layui-btn-small" id="searchbtn">
				  </div>
				</form>
			</div>
			<div class="list-btns">

				<shiro:hasPermission name="user:add">
					<button class="layui-btn layui-btn-small layui-btn-primary" onclick="openwin('新增','/crud/tosave/sys_user','600px,600px','2','2')"><i class="layui-icon">&#xe654;</i>新增</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="user:edit">
					<button class="layui-btn layui-btn-small layui-btn-primary" onclick="openwin('编辑','/crud/tosave/sys_user','600px,600px','2','1')"><i class="layui-icon">&#xe654;</i>编辑</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="user:del">
					<button class="layui-btn layui-btn-small layui-btn-primary" onclick="ajaxreq('/crud/physicald/sys_user','1','1')"><i class="layui-icon">&#xe640;</i>删除</button>
				</shiro:hasPermission>
				<shiro:hasPermission name="user:allotrole">
					<button class="layui-btn layui-btn-small layui-btn-primary" onclick="openwin('分配角色','/user/toallotrole','500px,500px','2','1')"><i class="layui-icon">&#xe613;</i>分配角色</button>
				</shiro:hasPermission>


			</div>
		</div>
			
		<div class="layui-form">	
			<div class="layui-form">
			  <table class="layui-table" lay-data="{height:500, url:'${basePath}/crud/list/sys_user', page:true,limit:100,limits:[100,200,500] ,id:'comontable'}" lay-filter="test">
		  <thead>
		    <tr>
		    	    <th lay-data="{checkbox:true}"></th>
		    		<th  lay-data="{field:'name', width:120, sort: true}">用户名</th>
		    		<th  lay-data="{field:'login', width:150, sort: true}">登录名</th>
		    		<th  lay-data="{field:'roleIds', width:200, sort: true}">用户角色</th>
		    		<th  lay-data="{field:'createtime', width:200, sort: true}">创建时间</th>
		    </tr>
		  </thead>
		</table>
		</div>
		</div>
		<!-- <div class="jgbox">
			<div></div>
			<span>选择部门</span>
		</div>
		<div class="jgcont">
			组织架构内容
		</div> -->
 <input type="hidden" id="reload"  onchange="reload()">
 </body>
<script type="text/javascript" src="${staticPath }/layui/layui.all.js"></script>
<script>
$('.jgbox').click(function(){
	$('.jgcont').fadeIn();
})

$('.jgcont').click(function(){
	$('.jgcont').fadeOut();
})
layui.use('table', function(){
   var table = layui.table;
  
});
	var table = layui.table;
	$("#searchbtn").on('click',function(){
		loadData();
	})
	function reload(){
		loadData();
	 }
	function  loadData(){
		var obj = new Object();
		 $(".layui-input-inline").each(function(){
			 var key = $(this).children(":first-child").attr("id");
			 obj[key] = $("#"+key).val();
		 })
		  table.reload('comontable', {
			  where:obj //设定异步数据接口的额外参数
		});
	}
	
	var ids = "";
	table.on('checkbox(test)', function(obj){
		var checkStatus = table.checkStatus('comontable'); //test即为基础参数id对应的值
		var checkeddoms = checkStatus.data;
		var idstemp ="";
		for(var i=0;i<checkeddoms.length;i++){
			if(i==checkeddoms.length-1)
				idstemp +=checkeddoms[i].id;
			else
				idstemp +=checkeddoms[i].id+",";
		}
		ids = idstemp;
	});
			
	//高级搜索的显示与隐藏
	$('#gaojiSearch').click(function(){
		if($(this).find('i').hasClass('open')){
			$(this).find('i').removeClass('open');
			$('.allsearch').slideUp(200);
		}else{
			$(this).find('i').addClass('open');
			$('.allsearch').slideDown(300);
		}
		
	})
	
	var form = layui.form;
	form.on('checkbox(allChoose)', function(data){  
           var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');  
           child.each(function(index, item){  
             item.checked = data.elem.checked;  
           });  
           form.render('checkbox');  
       }); 
	
	//操作弹出框
	 function openwin(title,url,winxy,canbatch,needid){
		if('1'==needid){
			if(!ids){
	    		layer.msg('请勾选行！', { anim: 6});
	    		return;
			}
			if(ids.split(',').length>1 && canbatch =='2'){
				layer.msg('不支持批量操作，请选择一行！', { anim: 6});
	    		return;
			}
			url = url+"?id="+ids;
		}
		var xyarry = winxy.split(",");
		console.log(url);
		layer.open({
			  title: title,				
			  type: 2,
			  area: xyarry,
			  shadeClose : true, 
			  maxmin: true,
			  content: '${basePath }'+url
		});
	} 
	//操作跳转地址
	function toLocation(url,canbatch,needid){
		if('1'==needid){
			if(!ids){
	    		layer.msg('请勾选行！', { anim: 6});
	    		return;
			}
			if(ids.split(',').length>1 && canbatch =='2'){
				layer.msg('不支持批量操作，请选择一行！', { anim: 6});
	    		return;
			}
			url = url+"?id="+ids;
		}
		window.location.href = '${basePath }'+ url;
	}
	//ajax 请求
	function ajaxreq(url,canbatch,needid){
		if('1'==needid){
			if(!ids){
	    		layer.msg('请勾选行！', { anim: 6});
	    		return;
			}
			if(ids.split(',').length>1 && canbatch =='2'){
				layer.msg('不支持批量操作，请选择一行！', { anim: 6});
	    		return;
			}
		}
		 layer.confirm('确定该操作吗？',function(){
			 $.ajax({
			   type: "POST", 
	           dataType:"json",
	           url: '${basePath }'+url, 
	           data:{
	        	   id:ids
	           },
	           success: function(data){
	           	if('200'==data.code){
	           		layer.msg("操作成功！", {icon:6});
	           		loadData();
	           	}else{
	           		layer.msg(data.desc);
	           	}
	           }
			}); 
		}); 
	}
	 function chooseorg(){
			layer.open({
				  title: '部门',				
				  type: 2,
				  offset:'20px',
				  area: ['400px', '500px'],
				  shadeClose : true, 
				  maxmin: true,
				  content: basePath+ '/user/tochoosedepart'
			});
		}
	 $('.tianj-hover').mouseenter(function(){
			$(this).find($('.tianj')).slideDown(100)
		}).mouseleave(function(){
			$(this).find($('.tianj')).slideUp()
		})
		
		$('.tianj-hover input').click(function(){
			$('.tianj').hide(0)
		})

		</script>
	
</html>	