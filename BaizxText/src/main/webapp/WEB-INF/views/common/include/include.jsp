<%@ page import="com.bankledger.holder.TheamAndLocalHolder" %>
<%@ include file="/WEB-INF/views/common/include/tagIncluded.jsp" %>
<%@ page import="com.bankledger.springBean.MyProperties" %>
<script type="text/javascript">
/**
 * 从服务器获取的常量
 */

var server_consts = {
	root     : '${pageContext.request.contextPath}',  // 项目的根路径
	locale   : '<%=TheamAndLocalHolder.getLocalStringThatCurrUserUsing(request)%>',//'zh_CN', // 和当前用户相关的本地化对象字符串
	theme    : '<%=TheamAndLocalHolder.getThemeThatCurrUserUsing(request)%>', //'boc-red', // 和当前用户相关的主题样式
	maxTabsNum : parseInt('7',10)					  // 可打开的标签页数量限制
};
var require = {urlArgs: 'v=1.0.0'};
</script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/easyui/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/easyui/themes/<%=TheamAndLocalHolder.getThemeThatCurrUserUsing(request)%>/easyui/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/easyui/themes/<%=TheamAndLocalHolder.getThemeThatCurrUserUsing(request)%>/config-icons.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/easyui/themes/<%=TheamAndLocalHolder.getThemeThatCurrUserUsing(request)%>/frame-theme.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/easyui/themes/<%=TheamAndLocalHolder.getThemeThatCurrUserUsing(request)%>/innerpage-theme.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/easyui/themes/<%=TheamAndLocalHolder.getThemeThatCurrUserUsing(request)%>/public-theme.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/easyui/themes/<%=TheamAndLocalHolder.getThemeThatCurrUserUsing(request)%>/login.css" />


<script src="${pageContext.request.contextPath}/resources/js/thirdparty/requirejs/require.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/frame/require-config.js" type="text/javascript"></script>
