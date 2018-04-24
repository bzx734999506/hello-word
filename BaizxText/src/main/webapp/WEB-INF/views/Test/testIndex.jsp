<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/include/tagIncluded.jsp"%>
<%@ page import="com.bankledger.holder.TheamAndLocalHolder"%>
<%@ page import="com.bankledger.springBean.MyProperties"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<title></title>

<jsp:include page="../../../WEB-INF/views/common/include/include.jsp"></jsp:include>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyui/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common/easyuiExtensions/validateExtends.js"></script>
</head>
<body>
	<script type="text/javascript">
		var settime = null;
		function redraw() {
			$('#wrap').layout('resize');
			var ttHeight = $('#tt').height();
			var ttWidth = $('#tt').width();
			var tabHeight = ttHeight - 31;
			var tabWidth = ttWidth - 2;
			var tabIfDivHeight = tabHeight - 20;
			if (tabHeight < 0) {
				tabHeight = 0;
			}
			if (tabWidth < 0) {
				tabWidth = 0;
			}
			if (tabIfDivHeight < 0) {
				tabIfDivHeight = 0;
			}
			$('.tabs-panels').css("height", tabHeight);
			$('.tabs-panels').css("width", tabWidth);
			$('.tabs-wrap').css("width", tabWidth);
			$('.tabs-panels>div').css("width", tabWidth);
			$('.tabs-panels>div>div').css("width", tabWidth);
			$('.tabs-panels>div>div').css("height", tabHeight);
			$('.tabs-panels>div>div>div').css("height", tabIfDivHeight);
		}
		$(function() {
			$(window).resize(function() {
				if (settime != null)
					clearTimeout(settime);
				settime = setTimeout("redraw()", 100);
			});
			var p1 = $('body').layout('panel', 'west').panel({
				onCollapse : function() {
					if (settime != null)
						clearTimeout(settime);
					settime = setTimeout("redraw()", 100);
				},
				onExpand : function() {
					if (settime != null)
						clearTimeout(settime);
					settime = setTimeout("redraw()", 100);
				},
				onResize : function(width, height) {
					if (settime != null)
						clearTimeout(settime);
					settime = setTimeout("redraw()", 100);
				}
			});
			var p2 = $('body').layout('panel', 'east').panel({
				onCollapse : function() {
					if (settime != null)
						clearTimeout(settime);
					settime = setTimeout("redraw()", 100);
				},
				onExpand : function() {
					if (settime != null)
						clearTimeout(settime);
					settime = setTimeout("redraw()", 100);
				},
				onResize : function(width, height) {
					if (settime != null)
						clearTimeout(settime);
					settime = setTimeout("redraw()", 100);
				}
			});
		});
	</script>
</head>
<body class="easyui-layout" data-options="'fit':true">
	<!-- 上部 -->
	<div data-options="region:'north'" style="height: 10%; padding-left: 0px;"></div>
	<!-- 左部 -->
	<div data-options="region:'west',split:true" title="横向" id="tit" style="width: 180px;">
		<div id="nav" class="easyui-accordion" data-options="border:false">
			<div title="纵向" align="center">
				<a href="javascript:void(0);"><div style="height: 30px;">测试页面1</div></a>
				<a href="javascript:void(0);"><div style="height: 30px;">测试页面2</div></a>
				<a href="javascript:void(0);"><div style="height: 30px;">测试页面3</div></a>
			</div>
		</div>
	</div>

	<!-- 右中部 -->
	<div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools', region:'center'">
		<table id='dg' class='easyui-datagrid' style='border-collapse:collapse;width:900px;'
		data-options=" title:'测试列表',singleSelect:true, nowrap:false, pageSize:50">
		<thead><tr style='font-weight:bold;color:#000;'>
		<th data-options="field:'userid'" align='center'><b>用户名</b></th>
		<th data-options="field:'password'" align='center'><b>密码</b></th>
		<th data-options="field:'public1'" align='center'><b>公用1</b></th>
		<th data-options="field:'public2'" align='center'><b>公用2</b></th>
		<th data-options="field:'public3'" align='center'><b>公用3</b></th>
		<th data-options="field:'public4'" align='center'><b>公用4</b></th>
		<th data-options="field:'public5'" align='center'><b>公用5</b></th>
		<th data-options="field:'public6'" align='center'><b>公用6</b></th>
		</tr></thead></table>
	</div>
	<!-- 下部 -->
	<div data-options="region:'south'" style="height: 51px;"></div>

</body>
<script type="text/javascript">

	$(function() {
		
		var table="";
		
		$("#nav a").on("click",function(){
			var titleName = $(this).text();
			console.log(titleName)
			if($('#tt').tabs("exists",titleName)){
				return ;
			}
			$('#tt').tabs('add', {
				title : titleName,
				closable : true,
				content : table
			})
			
			$('#dg').datagrid('loadData', selectList());		
		});
	});
	
	function selectList(){
		var rows;
		
		$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}' + "/queryIndex",
			dataType : 'json',
			contentType : 'application/json',
			data : JSON.stringify(),
			success : function(data) {
				var datas = data[0];
				rows.push({
					userid : datas.userid,
					password : datas.password,
					public1 : datas.public1,
					public2 : datas.public2,
					public3 : datas.public3,
					public4 : datas.public4,
					public5 : datas.public5,
					public6 : datas.public6
				});
			}
		});
		return rows;
	}
</script>
</html>
