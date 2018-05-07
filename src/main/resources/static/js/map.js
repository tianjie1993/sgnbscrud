var map
var hlist =[];
var llist = [];
var contractlist = [];
var housemass1;//房屋麻点1
var housemass2;//房屋麻点2
var housemass3;//房屋麻点3
var landmass1;//土地麻点1
var landmass2;//土地麻点2
var landmass3;//土地麻点3
var marker;
var count=0;
var polygon;//坐标集
var pokys=[];//图形集
function loadBaseData(){
	count++;
	$.ajax({
		type: "POST", 
        dataType:"json",
        url: basePath +"/mapinfo/getmaplist", 
        data:{
        	"citycode":$("#citycode").val(),
        	"orgcode":$("#departId").val(),
        	"cityname":$("#cityname").val(),
        	"departame":$("#departame").val()
        },
        success: function(data){
        	if('200'==data.code){
        		hlist = data.result.houselist;
        		llist = data.result.landlist;
        		contractlist = data.result.contractinfo;
        		if(count==1||housemarker.checked){
        			loadHouseMarker(data.result.houselist);
        		}
        		if(count==1||landmarker.checked){
        			loadLandMarker(data.result.landlist);
        		}
        	}else{
        		layer.msg("数据加载错误，请重试");
        	}
        }
	}); 
}

function loadHouseMarker(houselist){
	if(undefined!=housemass1){
		housemass1.hide();
	}
	if(undefined!=housemass2){
		housemass2.hide();
	}
	if(undefined!=housemass3){
		housemass3.hide();
	}
	var housepos=[];
	var housepos1=[];
	var housepos2=[];
	var housepos3=[];
	for(var i = 0;i<houselist.length;i++){
		if(houselist[i].xpoint != null && houselist[i].ypoint != null){
			var pos = new AMap.LngLat(parseFloat(houselist[i].xpoint),parseFloat(houselist[i].ypoint));
			var allArea = houselist[i].canRentArea;
			var leaveArea = houselist[i].residualArea;
			if(leaveArea == 0){
				var lng = {
					lnglat: pos,
					title: "项目编号："+houselist[i].projectNum,
					resId: houselist[i].id,
					pathType : "1"
				}
			}else if(leaveArea == allArea){
				var lng = {
					lnglat: pos,
					title: "项目编号："+houselist[i].projectNum,
					resId: houselist[i].id,
					pathType : "2"
				}
			}else{
				var lng = {
					lnglat: pos,
					title: "项目编号："+houselist[i].projectNum,
					resId: houselist[i].id,
					pathType : "3"
				}
			}
			
			housepos.push(lng);
		}
	}
	for(var i = 0;i<housepos.length;i++){
		if(housepos[i].pathType == "1"){
			housepos1.push(housepos[i]);
		}else if(housepos[i].pathType == "2"){
			housepos2.push(housepos[i]);
		}else if(housepos[i].pathType == "3"){
			housepos3.push(housepos[i]);
		}
	}
	housemass1 = createMass(housepos1,basePath+"/imgs/zuobiao1.png");
	housemass2 = createMass(housepos2,basePath+"/imgs/zuobiao2.png");
	housemass3 = createMass(housepos3,basePath+"/imgs/zuobiao3.png");
	housemass1.on('mouseover', function(e) {
		openInfo(e.data.resId);
		chooseContract();
	})
	housemass2.on('mouseover', function(e) {
		openInfo(e.data.resId);
		chooseContract();
	})
	housemass3.on('mouseover', function(e) {
		openInfo(e.data.resId);
		chooseContract();
	})
//	housemass.on('mouseout', function(e) {
//		$("#infoma").hide();
//	})
	
	housemass1.setMap(map);
	housemass2.setMap(map);
	housemass3.setMap(map);
	if(count==1){
		housemass1.hide();
	}
	if(count==1){
		housemass2.hide();
	}
	if(count==1){
		housemass3.hide();
	}
	
}

function loadLandMarker(landlist){
	if(undefined!=pokys){
		for(var j = 0;j< pokys.length; j++){
			pokys[j].hide();
		}
	}
	if(undefined!=landmass1){
		landmass1.hide();
	}
	if(undefined!=landmass2){
		landmass2.hide();
	}
	if(undefined!=landmass3){
		landmass3.hide();
	}
	var landpos = [];//土地坐标数组
	var landpos1 = [];//土地坐标数组1
	var landpos2 = [];//土地坐标数组2
	var landpos3 = [];//土地坐标数组3
	var landpoly = [];
	var markImgPath = "";
	for(var i = 0;i<landlist.length;i++){
		if(landlist[i].xpoint != null && landlist[i].ypoint != null){
			var pos = [parseFloat(landlist[i].xpoint),parseFloat(landlist[i].ypoint)];
			var allArea = landlist[i].canRentArea;
			var leaveArea = landlist[i].residualArea;
			if(leaveArea == 0){
				//全部租出去了，图标使用1
				var lng = {
					lnglat: pos,
					title: "项目编号："+landlist[i].projectNum,
					resId: landlist[i].id,
					pathType : "1"
				}
			}else if(leaveArea == allArea){
				//全没租出去，图标使用2
				var lng = {
					lnglat: pos,
					title: "项目编号："+landlist[i].projectNum,
					resId: landlist[i].id,
					pathType : "2"
				}
			}else{
				//部分出租，使用3号图标
				var lng = {
					lnglat: pos,
					title: "项目编号："+landlist[i].projectNum,
					resId: landlist[i].id,
					pathType : "3"
				}
			}
			landpos.push(lng);
		}
		var bounds=landlist[i].points.split(';');
		var points=[];
		var projectnum=landlist[i].projectNum;
		var id=landlist[i].id;
		if (bounds) {
            for (var j = 0;j< bounds.length; j++) {
            	var point=bounds[j].split(',');
            	var pos1 = new AMap.LngLat(parseFloat(point[0]),parseFloat(point[1]));
            	points.push(pos1);
            }
        }
		landpoly.push(points);
	}
	for(var i = 0;i<landpos.length;i++){
		if(landpos[i].pathType == "1"){
			landpos1.push(landpos[i]);
		}else if(landpos[i].pathType == "2"){
			landpos2.push(landpos[i]);
		}else if(landpos[i].pathType == "3"){
			landpos3.push(landpos[i]);
		}
	}
	landmass1 = createMass(landpos1,basePath+"/imgs/zuobiao1.png");
	landmass2 = createMass(landpos2,basePath+"/imgs/zuobiao2.png");
	landmass3 = createMass(landpos3,basePath+"/imgs/zuobiao3.png");

	  if (landpoly) {
		  for (var j = 0;j< landpoly.length; j++) {
	           polygon = new AMap.Polygon({
	              map: map,
	              strokeWeight: 1,
	              strokeColor: '#FF0033',
	              fillColor: '#3333FF',
	              fillOpacity: 0.5,
	              path: landpoly[j]
	          });
	           pokys.push(polygon);
		  }
	      map.setFitView();//地图自适应
	  }
	  landmass1.on('mouseover', function(e) {
		openInfo(e.data.resId);
		chooseContract();
	  })
	  landmass2.on('mouseover', function(e) {
		  openInfo(e.data.resId);
		  chooseContract();
	  })
	  landmass3.on('mouseover', function(e) {
		  openInfo(e.data.resId);
		  chooseContract();
	  })
	//	  landmass.on('mouseout', function(e) {
	//		  $("#infoma").hide();
	//	  })
	  landmass1.setMap(map);
	  landmass2.setMap(map);
	  landmass3.setMap(map);
	if(count==1){
		for(var j = 0;j< pokys.length; j++){
			pokys[j].hide();
		}
		landmass1.hide();
		landmass2.hide();
		landmass3.hide();
	}
}

//将创建mass图层对象的方法抽出来返回一个mass图层对象
function createMass(set,imagePath) {
	var mass = new AMap.MassMarks(set, {
		url: imagePath,
		anchor: new AMap.Pixel(3, 7),
		size: new AMap.Size(26, 36),
		opacity: 0.9,
		cursor: 'pointer',
		zIndex: 11
	});
	return mass;
}

function openInfo(id){
	var title = "";
	var address = "";
	var propertyUnitName = "";
	var acreage = "";
	var lon = "";
	var lat = "";
	var contract = [];
	var xiangmubianhao = "";
	var xiangmumingcheng = "";
	var diqu = "";
	var dizhi = "";
	var chanquandanwei = "";
	var fangchanxingzhi = "";
	var zongmianji = "";
	var zhengzaimianji = "";
	var wuzhengmianji = "";
	var weituo = "";
	var erweima = "";
	var zulinmianji = "";
	var tupianxinxi = "";
	var ziyongmianji = "";
	var wuzhengzulin = "";
	var zhigongmianji = "";
	var qitashiyong = "";
	var tupiannames = "";
	var daichuzu = "";
	var yichuzu = "";
	var coninfo = [];
	for(var i = 0;i<hlist.length;i++){
		if(hlist[i].id == id){
			address = hlist[i].address;
			propertyUnitName = hlist[i].propertyUnitName;
			title = "项目编号：" + hlist[i].projectNum;
			acreage = hlist[i].acreage;
			lon = hlist[i].xpoint;
			lat = hlist[i].ypoint;
			
			xiangmubianhao = hlist[i].projectNum;
			xiangmumingcheng = hlist[i].projectName;
			diqu = hlist[i].area;
			dizhi = hlist[i].address;
			chanquandanwei = hlist[i].propertyUnitName;
			fangchanxingzhi = hlist[i].quality;
			zongmianji = hlist[i].acreage;
			zhengzaimianji = hlist[i].floorarea;
			wuzhengmianji = hlist[i].noCrtfSelfarea;
			if(hlist[i].isentrust == "1"){
				weituo = "是";
			}else{
				weituo = "否";
			}
			erweima = hlist[i].qrcode;
			zulinmianji = hlist[i].canRentArea;
			tupianxinxi = hlist[i].picids;
			ziyongmianji = hlist[i].personalUseArea;
			wuzhengzulin = hlist[i].noCrtfRentarea;
			zhigongmianji = hlist[i].livingArea;
			qitashiyong = hlist[i].otheruserArea;
			tupianids = hlist[i].pidnames;
			daichuzu = hlist[i].residualArea;
			yichuzu = zulinmianji - daichuzu;
			coninfo = contractlist[id];
		}
	}
	if(title == ""){
		for(var i = 0;i<llist.length;i++){
			if(llist[i].id == id){
				address = llist[i].address;
				propertyUnitName = llist[i].propertyUnitName;
				acreage = llist[i].acreage;
				lon = llist[i].xpoint;
				lat = llist[i].ypoint;
				qrcode = llist[i].qrcode;
				
				xiangmumingcheng = llist[i].projectName;
				if(llist[i].isentrust == "1"){
					weituo = "是";
				}else{
					weituo = "否";
				}
				dizhi = llist[i].address;
				chanquandanwei = llist[i].propertyUnitName;
				erweima = llist[i].qrcode;
				zongmianji = llist[i].acreage;
				zhengzaimianji = llist[i].floorarea;
				wuzhengmianji = llist[i].noCrtfSelfarea;
				wuzhengzulin = llist[i].noCrtfRentarea;
				tupianids = llist[i].pidnames;
				ziyongmianji = llist[i].personalUseArea;
				zulinmianji = llist[i].canRentArea;
				zhigongmianji = llist[i].livingArea;
				qitashiyong = llist[i].otheruserArea;
				daichuzu = llist[i].residualArea;
				yichuzu = zulinmianji - daichuzu;
				coninfo = contractlist[id];
			}
		}
	}
	var info = [];
	//基本信息
	info += '<div class="rightop"><div class="rightbase"><p class="tit">基本信息</p><div>'+xiangmumingcheng+'<a target="_blank" style="float:right;margin-left:120px;color:orange" href="'+basePath+'/houseinfo/todetail?id='+id+'">查看详情</a></div><ul><li>是否委托：<span>'+weituo+'</span></li>';
	info += '<li>详细地址：<span>'+dizhi+'</span></li><li>产权单位：<span>'+chanquandanwei+'</span></li></ul>';
	info += '<img style="height:70px" src="'+basePath+'/qrcode/'+erweima+'" alt="" /><ol><li class="li1"><em>总面积(㎡)</em><span>'+zongmianji+'</span></li><li class="li2"><em>证载面积(㎡)</em><span>'+zhengzaimianji+'</span></li>';
	info += '<li class="li3"><em>无证面积(㎡)</em><span>'+wuzhengmianji+'</span></li><li class="li4"><em>租赁面积(㎡)</em><span>'+wuzhengzulin+'</span></li></ol></div>';
	
	//图片信息
	info += '<div class="rightpic"><p class="tit">图片信息</p><ol>';
	var names = [];
	if(tupianids && tupianids.length > 0){
		names = tupianids.split(",");
		if(names.length > 3){
			info += '<li><img src="'+basePath+'/uploadfile/'+names[0]+'" alt="" /></li>';
			info += '<li><img src="'+basePath+'/uploadfile/'+names[1]+'" alt="" /></li>';
			info += '<li><img src="'+basePath+'/uploadfile/'+names[2]+'" alt="" /></li>';
		}else{
			for(var i = 0 ;i<names.length-1;i++){
				info += '<li><img src="'+basePath+'/uploadfile/'+names[i]+'" alt="" /></li>';
			}
		}
	}else{
		info += '<li>暂无图片信息</li>';
	}
	info +='</ol></div>';
	
	//租用信息
	info += '<div class="rightrent"><p class="tit">租用使用信息</p><ul><li><span>'+ziyongmianji+'</span>自用面积(㎡)</li><li><span>'+zulinmianji+'</span>出租面积(㎡)</li>';
	if(title != ""){
		info += '<li><span>'+zhigongmianji+'</span>职工宿舍面积(㎡)</li>'
	}
	info += '<li><span>'+qitashiyong+'</span>其他使用面积(㎡)</li><li><span>'+daichuzu+'</span>待出租面积(㎡)</li><li><span>'+yichuzu+'</span>已出租面积(㎡)</li></ul></div>';
	
	if(coninfo.length > 0){
		//合同信息
		info += '<div class="rightpack"><p class="tit">合同信息<span>0 / 0</span></p><div class="pactinfolist">';
		for(var i = 0;i<coninfo.length;i++){
			var c = coninfo[i].contract;
			info += '<div class="pactinfo"><div class="heitong"><div class="lft">合同编号：<span class="gre">'+c.contractNum+'</span></div>';
			info += '<div class="rit">总金额：<span class="gre">'+c.allrent+'</span></div></div>';
			info += '<ul class="yezhu"><li class="li1">租赁业主：<span>'+c.owner.ownername+'</span></li><li class="li2">联系方式：<span>137725452525</span></li><li class="li3">合同开始日期-结束日期：';
			info += '<span>'+c.startdate+'~'+c.enddate+'</span></li><li class="li4">总面积：<span class="gre">'+c.rentalarea+'㎡</span></li></ul>';
			info += '<ol class="xiangqing"><li><a target="_blank" href="'+basePath+'/contract/todetail?id='+c.id+'">查看详情</a></li></ol></div>';
		}
		info += '</div><a class="prev-pactinfo" href="javascript:;"><i></i></a><a class="next-pactinfo" href="javascript:;"><i></i></a></div></div>';
	}
	info += '<div class="photo"><div class="returnbtn">返回</div><div class="photoview"><div class="prev-cont"><div class="prev-pic"><i></i></div></div>';
	if(names.length > 0){
		info +='<div class="viewport"><div><img class="cur-view-pic" src="'+basePath+'/uploadfile/'+names[0]+'" alt="" /></div></div>';
	}else{
		info +='<div class="viewport"><div><img class="cur-view-pic" src="" alt="" /></div></div>';
	}
	info +='<div class="next-cont"><div class="next-pic"><i></i></div></div></div>';
	info +='<div class="photolist"><a class="prev-pics" href="javascript:;"><i></i></a><a class="next-pics" href="javascript:;"><i></i></a>';
	info +='<div><ul class="scroll-list">';
		for(var i = 0 ;i<names.length-1;i++){
			info += '<li><img src="'+basePath+'/uploadfile/'+names[i]+'" alt="" /></li>';
		}
	info +='</ul></div></div></div>';
	$("#infoma").html(info);
	$("#infoma").show();
}
//初始化地图
var satellLayer;
function initMap() {
	map = new AMap.Map('map', {
		center: [118.774108, 32.019715],
        level:11,
		zoom: 14
	});
	satellLayer= new AMap.TileLayer.Satellite({zIndex:10}); //实例化卫星图
	satellLayer.setMap(map); //在map中添加卫星图
	satellLayer.hide();
	loadBaseData();
}
function changemap(){
	if($("#changemap").html()=='切换卫星图'){
		satellLayer.show();
		$("#changemap").html('切换二维地图');
	}else{
		satellLayer.hide();
		$("#changemap").html('切换卫星图');
	}
}


function chooseContract(){
	/*------合同信息列表切换------*/
	var pactinfoLen = $('.pactinfolist').find('.pactinfo').length;
	var initPactIdx = 1;
	var pactinfoHeight = 255;
	$('.pactinfolist').css('margin-top',(initPactIdx-1)*pactinfoHeight);
	$('.rightpack .tit span').text(initPactIdx+' / '+pactinfoLen);
	
	$('.rightpack .prev-pactinfo').click(function(){
		initPactIdx --;
		if(initPactIdx < 1){
			initPactIdx = pactinfoLen;
		}
		$('.pactinfolist').css('margin-top', -(initPactIdx-1)*pactinfoHeight);
		$('.rightpack .tit span').text(initPactIdx+' / '+pactinfoLen);
	})
	
	$('.rightpack .next-pactinfo').click(function(){
		initPactIdx ++;
		if(initPactIdx > pactinfoLen){
			initPactIdx = 1;
		}
		$('.pactinfolist').css('margin-top', -(initPactIdx-1)*pactinfoHeight);
		$('.rightpack .tit span').text(initPactIdx+' / '+pactinfoLen);
	})
	
	
	
	$('.rightpack').mouseover(function(){
		$('.rightpack .prev-pactinfo').show();
		$('.rightpack .next-pactinfo').show();
	}).mouseout(function(){
		$('.rightpack .prev-pactinfo').hide();
		$('.rightpack .next-pactinfo').hide();
	})
	
	/*------图片查看功能------*/
    //图片查看器的显示和隐藏
	$('.rightpic ol').click(function(){
		$('.photo').show();
	})
	
	$('.returnbtn').click(function(){
		$('.photo').hide();
	})
	
	//记录当前浏览的图片的index
	var thisPicIdx = 0;
	
	
	//点击缩略图，显示大图
    $('.scroll-list').on('click','li',function(){
      thisPicIdx = $(this).index();
      $(this).addClass('cur-img').siblings().removeClass('cur-img');
      var thisPicSrc = $(this).find('img').attr('src');
      $('.cur-view-pic').attr('src',thisPicSrc);
    })
	
	//设置图片列表容器初始的宽度、left值
	var oneLiWidth = $('.scroll-list li').eq(0).outerWidth() + 10;
	var liLen = $('.scroll-list li').length;
	$('.scroll-list').width(oneLiWidth*liLen);
	$('.scroll-list').css('left',0);
	
	
	
	//动态计算图片窗口的宽度，是浏览器宽度的90%
    var winWidth = $(window).width()*0.9;
    var showLiLen = parseInt(winWidth/oneLiWidth);
    var changeCount = Math.ceil( liLen / showLiLen );

    $(window).resize(function(){
      winWidth = $(window).width()*0.9;
      showLiLen = parseInt(winWidth/oneLiWidth);
      changeCount = Math.ceil( liLen / showLiLen );
    })
    
    
    //点击小图两边的上一系列、下一系列，切换图片列表
    var thisChangCount=0;
    var ulLeft;
    //点击小图两边的上一系列、下一系列图片，切换列表图片
    $('.prev-pics').click(function(){
      thisChangCount--;
      ulLeft = $('.scroll-list').css('left');
      if(thisChangCount<=0){
        thisChangCount=0;
      }
      $('.scroll-list').css('left', - thisChangCount*showLiLen*oneLiWidth);
    })
    
    $('.next-pics').click(function(){
      thisChangCount++;
      if(thisChangCount>changeCount-1){
        thisChangCount = changeCount-1;
      }
      $('.scroll-list').css('left', - thisChangCount*showLiLen*oneLiWidth);
      
    })




    //点击大图两边的上一张、下一张，切换图片
    //上一张
    $('.prev-pic').click(function(){
      //对图片列表进行切换
      if(thisPicIdx%showLiLen==0){
        thisChangCount = parseInt(thisPicIdx/showLiLen)-1;
        if(thisChangCount<0){
          return;
        }
        $('.scroll-list').css('left', -thisChangCount*showLiLen*oneLiWidth);
      }
      
      //对当前大图进行显示，小缩略图进行选中
      thisPicIdx--;
      if(thisPicIdx<=0){
        thisPicIdx=0;
      }
      $('.scroll-list li').eq(thisPicIdx).addClass('cur-img').siblings().removeClass('cur-img');
      var thisPicSrc = $('.scroll-list li').eq(thisPicIdx).find('img').attr('src');
      $('.cur-view-pic').attr('src',thisPicSrc);
      
    })
    
    //下一张
    $('.next-pic').click(function(){
      //对当前大图进行显示，小缩略图进行选中
      thisPicIdx++;
      if(thisPicIdx>=liLen-1){
        thisPicIdx=liLen-1;
      }             
      $('.scroll-list li').eq(thisPicIdx).addClass('cur-img').siblings().removeClass('cur-img');
      var thisPicSrc = $('.scroll-list li').eq(thisPicIdx).find('img').attr('src');
      $('.cur-view-pic').attr('src',thisPicSrc);
      
      //对图片列表进行切换
      if(thisPicIdx%showLiLen==0){
        thisChangCount = parseInt(thisPicIdx/showLiLen);
        $('.scroll-list').css('left', -thisChangCount*showLiLen*oneLiWidth);
      }
    })
}