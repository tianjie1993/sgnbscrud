<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
	<title>分配角色</title>
	<link rel="stylesheet" href="${staticPath}/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="${staticPath}/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${staticPath}/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${staticPath}/ztree/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="${staticPath}/ztree/js/jquery.ztree.excheck.js"></script>
	<script type="text/javascript" src="${staticPath}/ztree/js/jquery.ztree.exedit.js"></script>
	<style type="text/css">
		.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
</head>
<body>
<input type="hidden" id="userid" value="${userid }">
<div class="detail-page ">
	<div id="ztree" class="ztree"></div>
</div>
<input type="hidden" id="reload"  onchange="reload()">
</body>
<script type="text/javascript">
    function reload(){
        LoadTree();
    }
    $(function(){
        LoadTree();
    })
    function LoadTree(){
        var setting = {
            view: {
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            edit: {
                enable: true,
                editNameSelectAll: true,
                showRemoveBtn: false,
                showRenameBtn: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey:"id",
                    pIdKey:"pid"
                }
            },
        }
        $.getJSON("${basePath}/role/LoadPermissionTree?id="+$("#userid").val(),function(data){
            $.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
        });
    }

    function cancel(){
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.close(index);
    }

    function allotrole(){
        var zTree = $.fn.zTree.getZTreeObj("ztree");
        var nodes = zTree.getCheckedNodes(true);
        console.log(nodes);
        var ids='';
        if (nodes.length != 0) {
            for(var i=0;i<nodes.length;i++){
                if(i==nodes.length-1)
                    ids +=nodes[i].id
                else
                    ids +=nodes[i].id+','
            }
        }

        $.ajax({
            type: "POST",
            dataType:"json",
            url: "${basePath}/user/allotrole",
            data:{
                roleids:ids,
                userid :$("#userid").val()
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
    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
            + "' title='add node' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var addStr1 = "<span class='button edit' id='edit_" + treeNode.tId
            + "' title='edit node' onfocus='this.blur();'></span>";
        sObj.after(addStr1);
        var addStr2 = "<span class='button remove' id='remove_" + treeNode.tId
            + "' title='remove node' onfocus='this.blur();'></span>";
        sObj.after(addStr2);
        var btn = $("#addBtn_"+treeNode.tId);
        var btn1 = $("#edit_"+treeNode.tId);
        var btn2 = $("#remove_"+treeNode.tId);

        btn.bind("click", function(){
            var zTree = $.fn.zTree.getZTreeObj("ztree");
            layer.open({
                title: "新增节点",
                type: 2,
                area: ['700px','500px'],
                shadeClose : true,
                maxmin: true,
                content: '${basePath }/role/topermissionsave?pid='+treeNode.id
            });
        });
        btn1.bind("click", function(){
            var zTree = $.fn.zTree.getZTreeObj("ztree");
            if(checkNode(treeNode.id)){
                layer.open({
                    title: "编辑节点",
                    type: 2,
                    area: ['700px','500px'],
                    shadeClose : true,
                    maxmin: true,
                    content: '${basePath }/role/topermissionsave?id='+treeNode.id
                });
            }

        });

        btn2.bind("click", function(){
            if(checkNode(treeNode.id)){
                layer.confirm('确定该操作吗？',function(){
                $.ajax({
                    type: "POST",
                    dataType:"json",
                    url: '${basePath }/crud/physicald/sys_permission',
                    data:{
                        id:treeNode.id
                    },
                    success: function(data){
                        if('200'==data.code){
                            layer.msg("操作成功！", {icon:6});
                            LoadTree();
                        }else{
                            layer.msg(data.desc);
                        }
                    }
                });
            });
			}
        });
    };

    function removeHoverDom(treeId, treeNode){
        $("#edit_" + treeNode.tId).remove();
        $("#addBtn_" + treeNode.tId).remove();
        $("#remove_" + treeNode.tId).remove();

    }
    function checkNode(id){
        if(id.toString().indexOf('m')>=0){
            layer.alert('系统菜单在此处无法修改或删除！',{icon:2});
            return false;
		}
		return true;
	}
</script>
</html>