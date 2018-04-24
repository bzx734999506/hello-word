<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/include/tagIncluded.jsp" %>
<%@ page import="com.bankledger.holder.TheamAndLocalHolder" %>
<%@ page import="com.bankledger.springBean.MyProperties" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<title>Login</title>

<jsp:include page="/WEB-INF/views/common/include/include.jsp"></jsp:include>

<script
	src="${pageContext.request.contextPath}/resources/easyui/jquery.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/easyui/jquery.easyui.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/common/easyuiExtensions/validateExtends.js"
	type="text/javascript"></script>
</head>
<body>
<button id="submit">请求</button>
<input id="text" type="text"  value="123"/>
	<div>
		<table width="100%" border="1" cellspacing="1" cellpadding="2">
			<tbody>
				<tr>
					<td style="width: 11%" id="userid">1</td>
					<td style="width: 11%" id="password">2</td>
					<td style="width: 11%" id="public1">3</td>
					<td style="width: 11%" id="public2">4</td>
					<td style="width: 11%" id="public3">5</td>
					<td style="width: 11%" id="public4">6</td>
					<td style="width: 11%" id="public5">7</td>
					<td style="width: 11%" id="public6">8</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}' + "/queryIndex",
			dataType : 'json',
			contentType : 'application/json',
			data : JSON.stringify(),
			success : function(data) {
				var datas = data[0];
				$('#userid').text(datas.userid);
				$('#password').text(datas.password);
				$('#public1').text(datas.public1);
				$('#public2').text(datas.public2);
				$('#public3').text(datas.public3);
				$('#public4').text(datas.public4);
				$('#public5').text(datas.public5);
				$('#public6').html(datas.public6);
			}
		});
	});
</script>
</html>
