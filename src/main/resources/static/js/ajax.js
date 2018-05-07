/*----------------ajax----------------*/
$ajax = function(url, data, successfn, errorfn) {
	$.ajax({
		type: 'POST',
		data: data,
		url: url,
		dataType:"json",
		beforeSend:function(){
		},
		success: function(d){
			successfn(d);
		},
		complete:function(){ 
		},
		error: function(e){
			errorfn(e);
		}
	});
};