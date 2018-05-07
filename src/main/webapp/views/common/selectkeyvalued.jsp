<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<title>选择弹出页面</title>
</head>
<body class="bodybg">
	<div class="containbox">
		<div class="tab-menu layui-clear">
			<form class="layui-form" action="">
			<div class="cdit-l">	<!--condition-->
				<div class="layui-form-item">
					<c:forEach items="${searchlist }" var="searchinfo">
						<div class="layui-input-inline" id="searchsection">
				        	<input type="text" id="${searchinfo.searchfield }" placeholder="${searchinfo.searchname}" autocomplete="off" class="layui-input">
				      	</div>
					</c:forEach>
				    <button type="button" id="searchEntitys" class="layui-btn dbluebg"><i class="layui-icon">&#xe615;</i></button>
				    <button type="button" onclick="cancel()" class="laycancel layui-btn">选完关闭</button>
				</div>
				
			</div>
			</form>
		</div>
			
			<div class="tab-cont">
				<table class="layui-table">
				  <colgroup>
				  	<c:forEach items="${fieldnames }" var="name">
				  		<col >
				  	</c:forEach>
				    <col >
				  </colgroup>
				  <thead>
				    <tr>
				      <c:forEach items="${fieldnames }" var="name">
				  		<th>${name }</th>
				  	</c:forEach>
				      <th>操作</th>
				    </tr> 
				  </thead>
				  <tbody id="databody">
				  </tbody>
				</table>
			</div>
			<div id="pagesection"></div>
			<input type="hidden" id="url" value="${url }">
			<input type="hidden" id="tablename" value="${tablename }">
			<input type="hidden" id="key" value="${key }">
			<input type="hidden" id="value" value="${value }">
			<input type="hidden" id="fields" value="${fields }">
			<input type="hidden" id="fieldnames" value="${fieldnames }">
			</div>
</body>
<script type="text/javascript">
	 var keyarray = $("#key").val().split(",");
	 var valuearray = $("#value").val().split(",");
	 var fieldsarray = $("#fields").val().split(",");
	 var page = 1;
	 var pnum=1;
	 $(function(){
		 loadData();
	 })
	 
	 $("#searchEntitys").click(function(){
		 loadData();
	 })
	 
	 function loadData(){
		 var index = layer.load(0, {
			  shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
		 var data = {};
		 data['page']=page;
		 data['pagesize']=5;
		 data['tablename']=$("#tablename").val();
		 //动态赋值参数
		 $("#searchsection input").each(function(){
			 var key = $(this).attr("id");
		 	 data[key] = $("#"+key).val();
		 })
		 console.log(data);
		 $.ajax({
				type: "GET", 
	            dataType:"json",
	            url: "${basePath}/common/sysselectky", 
	            data:data,
	            success: function(data){
	            	console.log(data);
	            	var page = data.result;
	            	if('200'==data.code){
	            		layer.close(index);
	            		loadPage(page.pageNum,page.total,page.pageSize);
	            		addStr(page.list);
	            	}else{
	            		layer.msg("请求失败！");
	            	}
	            }
			});
	 	}
		
	 function loadPage(current,totalRecord,size){
 		 var laypage = layui.laypage;
		 laypage.render({
		    elem: 'pagesection'
		    ,count: totalRecord
		    ,theme: '#215AB5' //自定义选中色值
		    ,curr:current
		    ,limit:size
		    ,layout: ['count', 'prev', 'page', 'next','skip']
		    ,jump: function(obj, first){
		    	if(!first){
			    	  page = obj.curr;
			    	  loadData();
			      }
		    }
		});
		}
	 	
	 	function addStr(list){
	 		var datahtml = "";
	 			 
	 		 for(var i=0;i<list.length;i++){
	 			datahtml +=' <tr>';
	 			var key,value;
	 			for(var j=0;j<fieldsarray.length;j++){
	 				for(var de in list[i]){
		 				var field= fieldsarray[j];
		 				if(field==de){
			 				datahtml +='  <td>'+list[i][de]+'</td>';
		 				}
	 				}
	 			}
	 			for(var de in list[i]){
	 				if(keyarray[0]==de){
	 					key = list[i][de];
	 				}
	 				if(valuearray[0]==de){
	 					value = list[i][de];
	 				}
		 		}
	 			datahtml +='  <td>';
	 			datahtml +='	  <button onclick="selectKeyValue(\''+key+'\',\''+value+'\')" class="layui-btn layui-btn-small layui-btn-warm">';
	 			datahtml +='	    <i class="iconfont">&#xe608;</i>选择';
	 			datahtml +='	  </button>';
	 			datahtml +='  </td>';
	 			datahtml +='  </tr>';
	 			
	 		}
	 		$("#databody").html(datahtml);
	 	}
	 	
	 	function selectKeyValue(id,name){
	 		if(1==pnum){
	 			parent.$("#"+keyarray[1]).val(id);
	 			parent.$("#"+valuearray[1]).val(name);
	 		}else{
		 		var pids = parent.$("#"+keyarray[1]).val();
		 		var values = parent.$("#"+valuearray[1]).val();
	 			var ids = pids+","+id;
	 			var names = values+","+name;
	 			parent.$("#"+keyarray[1]).val(ids);
	 			parent.$("#"+valuearray[1]).val(names);
	 		}
	 		layer.msg("已选：【"+parent.$("#"+valuearray[1]).val()+"】");
 			pnum++;
	 	}
	 	
</script>
</html>