	var element = layui.element;
			var addTabArr=[];
			
			element.on('nav(adminnav)', function(data){
			  
				var thisAddTabBtn = data.find('a');
				var thisAddTabHref = data.find('a').attr('data-href');
				var thisAddTabId = data.find('a').attr('data-id');
				    
				//判断如果当前id已存在就不新增，而是定位
			    for(var i=0; i<=addTabArr.length;i++){
			    	if( addTabArr[i] == thisAddTabId){
			    		element.tabChange('admin-menu-tabs', thisAddTabId);
			    		var thisIdIndex=0;
			    		$("#admin-body .layui-tab-title li").each(function(idx){
							if($(this).attr("lay-id")==thisAddTabId){
								thisIdIndex = idx
							}
						});
			    		$('#admin-body .layui-tab-content div').eq(thisIdIndex).find("iframe").attr("src",thisAddTabHref)
			    		return;
			    	}
			    }
			    
			    //新增并定位
			    element.tabAdd('admin-menu-tabs', {
			        title: $(this).text() //用于演示
			        ,content: '<iframe src="'+thisAddTabHref+'"></iframe>'
			        ,id:thisAddTabId
			    })
			    element.tabChange('admin-menu-tabs', thisAddTabId);
			    addTabArr.push(thisAddTabId);
	    		
	    		
			});
			
			
			
			
			
			
			//监听切换选项卡时，遍历选项卡获取id，存入数组，让左侧nav数组与tab数组相等，便于操作切换与新增
			element.on('tab(admin-menu-tabs)', function(){
				var hasTabsArr = [];
			    setTimeout(function(){
			    	$('#admin-body .layui-tab ul').find('li[lay-id]').each(function(){
			    		var thisTabId = $(this).attr('lay-id');
			    		hasTabsArr.push(thisTabId);
		            })
			    	addTabArr = hasTabsArr;
			    },100)
			});
			
			
			
			
			
			//左侧导航的显示与隐藏
			$('.admin-side-toggle').on('click', function() {
				var sideWidth = $('#admin-side').width();
				if(sideWidth === 200) {
					$('#admin-body').animate({
						left: '0'
					}); //admin-footer
					$('#admin-footer').animate({
						left: '0'
					});
					$('#admin-side').animate({
						width: '0'
					});
				} else {
					$('#admin-body').animate({
						left: '200px'
					});
					$('#admin-footer').animate({
						left: '200px'
					});
					$('#admin-side').animate({
						width: '200px'
					});
				}
			});
			
			 
	if(window !=top){  
		 top.location.href=location.href;  
	} 
			
  

