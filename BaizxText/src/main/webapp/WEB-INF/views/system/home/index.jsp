<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/include/tagIncluded.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<title>银链科技</title>
<jsp:include page="/WEB-INF/views/common/include/include.jsp"></jsp:include>

<script src="resources/easyui/jquery.min.js" type="text/javascript"></script>
<script src="resources/easyui/jquery.easyui.min.js" type="text/javascript"></script>

<script type="text/javascript">

//这个url 所有的用户均可以访问
var treeUrl='${pageContext.request.contextPath}'+'/loadMenu';

//添加菜单
function append(url){
	
	$('#dialogIdAdd').dialog({  
        left:"450px",
        top:"200px",
        title: '新增菜单',
        inline:false,
        closed:true,
        width: 350,    
        height: 250,                
        cache: false,
        shadow:false,
        modal: true ,
        buttons:'#dialogButtonDivAdd',
        href:'${pageContext.request.contextPath}'+url,
		onLoad:function(){

        	$('#menuNameAdd').textbox('setValue','');
        	$('#urlAdd').textbox('setValue','');
        	$('#menuIconAdd').combobox('setValue','');
        	$('#needAuthFlagAdd').combobox('setValue','');
        	$('#menuFlagAdd').combobox('setValue','');
        	
        }
    });
	$('#dialogIdAdd').dialog("open");
	
	
}

//更新菜单
function updateit(url){
	
	$('#dialogIdUpdate').dialog({  
        left:"450px",
        top:"200px",
        title: '更新菜单',
        inline:false,
        closed:true,
        width: 350,    
        height: 250,                
        cache: false,
        shadow:false,
        modal: true ,
        buttons:'#dialogButtonDivUpdate',
        href:'${pageContext.request.contextPath}'+url,
        onLoad:function(){
        	
        	var t = $('#tt');
        	var node = t.tree('getSelected');
        	var menuName=node.text;
        	var menuUrl=node.url;
        	var menuIcon=node.iconCls;
        	var needAuthFlag=node.needAuthFlag;
        	var menuFlag=node.menuFlag;
        	if(null!==menuName){
        		$('#menuNameUpdate').textbox('setValue',menuName);
        	}
        	if(null!==menuUrl)
        	{
        		$('#urlUpdate').textbox('setValue',menuUrl);
        	}
        	if(null!==menuIcon){
        		$('#menuIconUpdate').combobox('setText',menuIcon);
            	$('#menuIconUpdate').combobox('setValue',menuIcon);
        	}
        	if(null!==needAuthFlag){
        		$('#needAuthFlagUpdate').combobox('setValue',needAuthFlag);
        	}
        	if(null!==menuFlag){
        		$('#menuFlagUpdate').combobox('setValue',menuFlag);
        	}
        	
        	
        }
    });
	$('#dialogIdUpdate').dialog("open");
	
	
}

//点击菜单触发函数，可以打开一个页面
function onClickMenu(node){
	console.log(node);
	//console.log(node.url);
	if(null!==node.url && ''!==node.url && '1'===node.menuFlag)
	{
		var finalUrl='${pageContext.request.contextPath}'+node.url;
		
		$("#iframeId").attr("src", finalUrl); 
	}
	
	
}

//删除菜单
function removeit(url){
	var t = $('#tt');
	var node = t.tree('getSelected');
	var menuId=node.id;
	var removeData={menuId:menuId};
	$.ajax({
		type:'post',
		url:'${pageContext.request.contextPath}'+url,
		data:JSON.stringify(removeData),
		dataType:'json',
		contentType:'application/json',
		success:function(data){		
			var node = $('#tt').tree('getSelected');
			$('#tt').tree('remove', node.target);
		}
	});
	
}
//收起菜单
function collapse(){
	var node = $('#tt').tree('getSelected');
	$('#tt').tree('collapse',node.target);
}
//展开菜单
function expand(){
	var node = $('#tt').tree('getSelected');
	$('#tt').tree('expand',node.target);
}

//打开进度条
function openProgress(){
	$.messager.progress({
		msg:'正在执行',
	});
}

//关闭进度条
function closeProgress(){
	$.messager.progress('close');
}



//弹出窗口点击确定处理函数，新增菜单
function addMenuAction(url)
{
	openProgress();
	var t = $('#tt');
	var node = t.tree('getSelected');
	var parentMenu=node.text;
	var menuName=$('#menuNameAdd').val();
	var menuUrl=$('#urlAdd').val();
	var menuIcon=$('#menuIconAdd').val();
	var needAuthFlag=$('#needAuthFlagAdd').val();
	var menuFlag=$('#menuFlagAdd').val();
	var testName ='menuName'+server_consts.locale;
	var AddMenuData={'menuName':menuName,url:menuUrl,menuIcon:menuIcon,needAuthFlag:needAuthFlag,menuFlag:menuFlag,parentMenuId:node.id};
	
	$.ajax({
		type:'post',
		dataType:'json',
		url:'${pageContext.request.contextPath}'+url,
		contentType: "application/json", 
		data:JSON.stringify(AddMenuData),
		success:function(data){
			
			t.tree('append', {
				parent: (node?node.target:null),
				data: [{text:menuName,iconCls:menuIcon,id:data,url:menuUrl,needAuthFlag:needAuthFlag,menuFlag:menuFlag}]
			});

			$('#dialogIdAdd').dialog('close');
			closeProgress();
		},
		error:function(ajaxObject,errMsg){
			console.log(errMsg);
			closeProgress();
		}
	});
	
	
}


//弹出窗口点击确定处理函数，更新菜单
function updateMenuAction(url)
{
	openProgress();
	var t = $('#tt');
	var node = t.tree('getSelected');
	var parentMenu=node.text;
	var menuName=$('#menuNameUpdate').val();
	var menuUrl=$('#urlUpdate').val();
	var menuIcon=$('#menuIconUpdate').val();
	var needAuthFlag=$('#needAuthFlagUpdate').val();
	var menuFlag=$('#menuFlagUpdate').val();
	
	var updateMenuData={url:menuUrl,menuIcon:menuIcon,needAuthFlag:needAuthFlag,menuFlag:menuFlag,menuId:node.id};
	updateMenuData['menuName'+server_consts.locale]=menuName;
	$.ajax({
		type:'post',
		dataType:'json',
		url:'${pageContext.request.contextPath}'+url,
		contentType: "application/json", 
		data:JSON.stringify(updateMenuData),
		success:function(data){
			
        	var node = t.tree('getSelected');
        	
        	t.tree("update",{target:node.target,text:menuName,url:menuUrl,iconCls:menuIcon,needAuthFlag:needAuthFlag,menuFlag:menuFlag});

			$('#dialogIdUpdate').dialog('close');
			closeProgress();
		},
		error:function(ajaxObject,errMsg){
			console.log(errMsg);
			closeProgress();
		}
	});
	
	
}
	
</script>
</head>
<body class="easyui-layout">

    <div data-options="region:'west',title:'菜单'" style="width:250px;">
    	<div  class="easyui-panel" style="padding:5px;width:100%; height:100%;color:#444444;border=1px;">
			<ul id="tt" class="easyui-tree"  data-options="
					url: treeUrl,
					method: 'get',
					animate: true,
					onClick:onClickMenu,
					onContextMenu: function(e,node){
						e.preventDefault();
						$(this).tree('select',node.target);
						$('#rightClickMenu').menu('show',{
							left: e.pageX,
							top: e.pageY
						});
					}
				"></ul>
		</div>
    </div>
    <div data-options="region:'center'" style="padding:5px;background:#eee;">

			<iframe id="iframeId" name="iframeId" src=''  style='border: 0;height:100%;width:100%;'>
			</iframe>
		
    </div>

	
	<div id="dialogIdAdd" style="display:none">
		
	</div>
	
	<div id="dialogIdUpdate" style="display:none">
		
	</div>

	<div id="dialogButtonDivAdd" style="display:none">
		<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="addMenuAction('{url}')" url="/addMenu" name="确定"></PermissionTag:Alink>	
	</div>
	
	<div id="dialogButtonDivUpdate" style="display:none">
		<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="updateMenuAction('{url}')" url="/updateMenu" name="确定"></PermissionTag:Alink>	
	</div>
	
	<div id="rightClickMenu" class="easyui-menu" style="width:120px;display:none">
		<PermissionTag:DivLink name="添加" url="/addMenuShow" dataOptions="iconCls:'icon-add'" onclick="append('{url}')"></PermissionTag:DivLink>
		<PermissionTag:DivLink name="更新" url="/updateMenuShow" dataOptions="iconCls:'icon-edit'" onclick="updateit('{url}')"></PermissionTag:DivLink>
		<PermissionTag:DivLink name="删除" url="/deleteMenu" dataOptions="iconCls:'icon-remove'" onclick="removeit('{url}')"></PermissionTag:DivLink>
		<div class="menu-sep"></div>
		<div onclick="expand()">展开</div>
		<div onclick="collapse()">收起</div>
	</div>

</body>
</html>