
//关闭layer-iframe
function closeLayIfram(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);	//关闭窗口
}
function cancel(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);
}
//加载按钮通用方法
function loadActionBtn(actions,entity){
	var datahtml = '';
	for(var k=0;k<actions.length;k++){
			var action = actions[k];
			var fields = action.fields;
			var fieldArray = fields.split(",");
			var url = action.url+"?";
			for(var j=0;j<fieldArray.length;j++){
				for(var de in entity){
 				var field= fieldArray[j];
 				if(field==de){
 					if(j==fieldArray.length-1)
 						url += (field+"="+entity[de]);
 					else
 						url += (field+"="+entity[de]+"&");
 				}
				}
			}
			if(action.type==1){
				datahtml +='	  <button onclick="toLocation(\''+url+'\')" class="layui-btn layui-btn-small '+action.btnclass+'">';
 			datahtml += action.name;
 			datahtml +='	  </button>';
			}
		if(action.type==2){
			datahtml +='	  <button onclick="openwin(\''+action.name+'\',\''+url+'\',\''+action.winxy+'\')" class="layui-btn layui-btn-small '+action.btnclass+'">';
 			datahtml += action.name;
 			datahtml +='	  </button>';
			}
		if(action.type==3){
			datahtml +='	  <button onclick="ajaxreq(\''+url+'\')" class="layui-btn layui-btn-small '+action.btnclass+'">';
 			datahtml += action.name;
 			datahtml +='	  </button>';
			}
		}
	return datahtml;
}
//选择弹出框
 function openSelectKy(title,id){
	layer.open({
		  title: title,				
		  type: 2,
		  offset:'20px',
		  area: ['700px', '500px'],
		  shadeClose : true, 
		  maxmin: true,
		  content: basePath+ '/common/tosysselectky?id='+id
	});
} 
//多选择弹出框
 function openSelectKyd(title,id){
	layer.open({
		  title: title,				
		  type: 2,
		  offset:'20px',
		  area: ['700px', '500px'],
		  shadeClose : true, 
		  maxmin: true,
		  content: basePath +'/common/tosysselectkyd?id='+id
	});
} 
 function NullTo(value){
	 if(!value){
		 return "";
	 }else{
		 return value;
	 }
		 
 }

