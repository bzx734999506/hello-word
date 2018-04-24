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
<!-- load CSS -->
<link rel="stylesheet" type="text/css" href="resources/easyui/themes/<%=TheamAndLocalHolder.getThemeThatCurrUserUsing(request)%>/login.css"/>
<link rel="stylesheet" type="text/css" href="resources/easyui/themes/<%=TheamAndLocalHolder.getThemeThatCurrUserUsing(request)%>/easyui/easyui.css" />
<jsp:include page="/WEB-INF/views/common/include/include.jsp"></jsp:include>


<script type="text/javascript">
require(['JSEncrypt','easyui-base'],function(JSEncrypt){
	
	//key down
	$(document).on("keydown", function(e) {
		var e = e || event;
		var keycode = e.which || e.keyCode;
		if (keycode == 13) {
			toVaild();
		}
	});
	
	var form = '#loginform';
	var toVaild = function(){
		
		var orginPwd=$.trim($('#passwordDisplay').val());
    	var finalPassword=orginPwd; 
		<%if("true".equals(MyProperties.getMyProperties().getProperty("login.transfer.encrypt.flag","false"))){%> 
		$.ajax({
             url:"${pageContext.request.contextPath}" + "/DefaultPublicKey",
             success:function(data) {         	     		          	 
            	 var encrypt = new JSEncrypt.JSEncrypt();
                 encrypt.setPublicKey(data.publicKey);                
                 var encryptedPwd = encrypt.encrypt(orginPwd);
                 finalPassword=encryptedPwd;
                
                 //console.log(encryptedPwd);
            	 $("#password").val(finalPassword);
				 $("#passwordDisplay").val("******");
				 $("#loginform").submit();
             }
          });
		 <%}else{%>
		 	$("#password").val(finalPassword);
		 	$("#passwordDisplay").val("******");
		 	$("#loginform").submit();	 
		<%}%>
	};
	

	jQuery(function($){
		$('#passwordDisplay').val("");
		$('#password').val("");

		$(form).find('div.login-button input:only-child').on('click', toVaild);
		$("#accountNo").focus();
		
	});
});


</script>
</head>
<body>
	  <div class="max-div">
        <div class="top"></div>
        <div class="center">
	        <div class="top-content1">
	        	<div class="top-content" style="height:61px;width:120px;margin-top:-30px"></div>
				<div style="font-family:'微软雅黑';line-height:80px;font-size:30px;font-weight:bold;margin-left:130px;margin-top:-8px"><spring:message code="company"/></div>         
	        </div>
            <div class="center-content">
                <div class="left">
                <!--登录面板区域-->
                <div>
                <div class="login-header"></div>
                    <div class="login-header2">
                    	<h2 style="color:#d6063d"><spring:message code="login.title"/></h2>                   	
                    </div>
                    <form name="loginform" id="loginform" action="${pageContext.request.contextPath}/login" method="POST"> 
                    
                         <div class="textbox-user" >
                         	<img src="resources/images/default/use.png"/>&nbsp;&nbsp;
                            <input type="text" name="accountNo" id="accountNo" class="input" placeholder="<spring:message code="login.userId.tips"/>"/>
                        </div>

                        <div class="textbox-pwd">
                       		<img src="resources/images/default/psw.png"/>&nbsp;&nbsp;
                        	<input type="password" name="passwordDisplay" id="passwordDisplay" 
                            	placeholder="<spring:message code="login.password.tips"/>"class="input"/>
                            	<input type="hidden"  name="password" id="password"/>
                        </div>
                        <div class="login-button">
                            <input type="button" value="<spring:message code="login.submit"/>" class="button button-pill button-primary" />
                        </div>

                    </form>
                </div>
            </div>
            </div>
        </div>
       
        <div class="footer" >
             <spring:message code="login.copyright" htmlEscape="false"></spring:message>
    	</div>
    </div>
    
</body>
</html>
