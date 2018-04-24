<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/include/tagIncluded.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<title>404</title>
<jsp:include page="/WEB-INF/views/common/include/include.jsp"></jsp:include>

<script src="${pageContext.request.contextPath}/resources/easyui/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/easyui/jquery.easyui.min.js" type="text/javascript"></script>

<script type="text/javascript">
	alert("未授权！");
</script>
</head>

</html>