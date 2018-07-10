<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/views/common/system.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增/修改</title>
</head>
<body>
 	<%@ include file="/views/common/perform.jsp"%>
	<div class="pagetit">权限节点
		<c:if test="${empty entity.id}">新增</c:if>
		<c:if test="${not empty entity.id}">修改</c:if>
	</div>
	<form class="layui-form layui-w140" action="${basePath }/crud/save/sys_permission" method="post">
	<input name="id" type="hidden"  value="${entity.id}">
	<input name="parentid" type="hidden"  value="${entity.parentid}">
		<div class="detail-page">
		<div class="layui-row">
			<div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
			    <label class="layui-form-label"><em class="must"></em>权限名称（对应按钮名称）：</label>
			    <div class="layui-input-block">
					<input name="name" value="${entity.name }" required lay-verify="required"    class="layui-input" type="text">
			    </div>
			</div>
			<div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
			    <label class="layui-form-label">权限备注：</label>
			    <div class="layui-input-block">
					<input name="remark" value="${entity.remark }"  class="layui-input" type="text">
			    </div>
			</div>
		</div>
		<div class="layui-row">

			<div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
				<label class="layui-form-label"><em class="must"></em>权限字符串：</label>
				<div class="layui-input-block">
					<input name="permission" required lay-verify="required" value="${entity.permission }"  class="layui-input" type="text">
				</div>
			</div>

            <div class="layui-col-xs8 layui-col-sm6 layui-col-md6">
                <label class="layui-form-label"><em class="must"></em>权限控制：</label>
                <div class="layui-input-block">
                    <select name="type">
                        <c:forEach items="${entity.typekvs }" var="kv">
                            <c:choose>
                                <c:when  test="${kv.key eq entity.type}">
                                    <option value="${kv.key }" selected>${kv.value }</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${kv.key}">${kv.value}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>
			   
		</div>
			 
		<div class="layui-row detail-btn topline">
		    <div class="layui-input-block">
		      <button type="button" onclick="cancel()" class="laycancel layui-btn layui-btn-small layui-btn-warm"> 取 消 </button>
		      <button lay-submit="" class="layui-btn layui-btn-small layui-btn-normal"> 确 定 </button>
		    </div>
		  </div>
		 </div>
	</form>
</body>
<script type="text/javascript" src="${staticPath }/layui/layui.all.js"></script>
</html>