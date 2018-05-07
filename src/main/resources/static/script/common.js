

//关闭layer-iframe
function closeLayIfram(){
	var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	parent.layer.close(index);	//关闭窗口
}