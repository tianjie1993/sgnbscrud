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
				    <label class="layui-form-label">备注：</label>
				    <div class="layui-input-inline">
				        <input id="remark"   autocomplete="off" class="layui-input" type="text">
				    </div>
				  </div>
				  <div class="layui-inline">
				      <input type="button" value="搜索" class="layui-btn layui-btn-small" id="searchbtn">
				  </div>
				</form>
			</div>
			<div class="list-btns">
				<button class="layui-btn layui-btn-small layui-btn-primary" onclick="openwin('新建菜单','/crud/tosave/sys_selectky','1000px,700px','2','2')"><i class="layui-icon">&#xe654;</i>新增</button>
				<button class="layui-btn layui-btn-small layui-btn-primary" onclick="openwin('编辑菜单','/crud/tosave/sys_selectky','1000px,700px','2','1')"><i class="layui-icon">&#xe642;</i>修改</button>
				<button class="layui-btn layui-btn-small layui-btn-primary" onclick="ajaxreq('/crud/physicald/sys_selectky','1','1')"><i class="layui-icon">&#xe640;</i>删除</button>
			</div>
		</div>
			
		<div class="layui-form">	
			<div class="layui-form">
			  <table class="layui-table" lay-data="{height:500, url:'${basePath}/crud/list/sys_selectky', page:true,limit:10,limits:[10,50,100,200,500] ,id:'comontable'}" lay-filter="test">
		  <thead>
		    <tr>
		    	    <th lay-data="{checkbox:true}"></th>
		    		<th  lay-data="{field:'id', width:80, sort: true}">主键</th>
		    		<th  lay-data="{field:'qsql', width:200, sort: true}">FastListDAO方法名</th>
		   		    <th  lay-data="{field:'fields', width:200, sort: true}">展示列字段</th>
		    		<th  lay-data="{field:'fieldnames', width:200, sort: true}">展示列字段名</th>
		   		    <th  lay-data="{field:'keyto', width:200, sort: true}">键对应</th>
		   		    <th  lay-data="{field:'valueto', width:200, sort: true}">值对应</th>
		   		   	<th  lay-data="{field:'searchinfo', width:200, sort: true}">所有条件</th>
		   		    <th  lay-data="{field:'remark', width:200, sort: true}">备注</th>
		    </tr>
		  </thead>
		</table>
		</div>
		</div>
		
 <input type="hidden" id="reload"  onchange="reload()">
</body>
<script type="text/javascript" src="${staticPath }/layui/layui.all.js"></script>
<script>
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
//跳转到空白页
function toBlank(url,canbatch,needid){
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
	window.open( basePath+url);
}

</script>
</html>	