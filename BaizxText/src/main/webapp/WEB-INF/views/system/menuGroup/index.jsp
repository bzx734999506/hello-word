<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/include/tagIncluded.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<title>对组别进行添加删除修改，对组别</title>
<jsp:include page="/WEB-INF/views/common/include/include.jsp"></jsp:include>

<script src="${pageContext.request.contextPath}/resources/easyui/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<style type="text/css">
	.likeEasyuiTitleNormal{
		font-weight: bold;font-size: 14px;color: #777;line-height: 20px;padding: 0px 20px;
	}
	.likeEasyuiTitleRed{
		font-weight: bold;font-size: 14px;color: Red;line-height: 20px;padding: 0px 20px;
	}
</style>
<script type="text/javascript">

	var menuTreeData;
	var groupTreeData;
	var groupId=null;
	var groupName=null;
	var queryGroupUrl='${pageContext.request.contextPath}'+'/group/queryGroup';
	var queryMenuUrl='${pageContext.request.contextPath}'+'/menuGroup/loadMenuByGroupId';
	var rootGroup=null;
	
	//点击组，代表选定一个组
	function onClickGroup(node){
		document.getElementById("myChoiceGroupMessage").innerHTML=node.text;
		groupId=node.id;
		groupName=node.text;	
		fatherGroupId=node.fatherGroupId;
		$.ajax({
			url:queryMenuUrl,
			type:'post',
			dataType:'json',
			contentType:'application/json',
			data:JSON.stringify({groupId:groupId,fatherGroupId:fatherGroupId}),
			//删除，然后重新更新 delete All and Insert all
			success:function(data){	
		 		console.log(data);
				if(groupId===rootGroup){					
					$('#ttMenu').tree({data:data,checkbox:false});
				}
				else{
					$('#ttMenu').tree({data:data,checkbox:true,cascadeCheck:false});
				}
				
			}
		});
	}
	
	function onBeforeCheckSet(node, checked){
		if('0'===node.needAuthFlag && !checked)
		{
			alert("此功能是系统必须的功能，无法去除！");
			return false;
		}
		return true;
	}
	
	
	function saveTreeMenu(url){
		var nodes = $('#ttMenu').tree('getChecked');	
		console.log(nodes);
		var data=[];
		for(var i=0;i<nodes.length;i++){
			if('1'===nodes[i].needAuthFlag){
				data.push(nodes[i].id);
			}			
		}
		$.ajax({
			url:'${pageContext.request.contextPath}'+url,
			type:'post',
			dataType:'json',
			contentType:'application/json',
			data:JSON.stringify({groupId:groupId,data:data}),
			success:function(data){			
				if('1'==data){
					alert("修改成功！");
				}
			}
		});
		
	}
	
	function loadGroupTree(){
		$.ajax({
			url:queryGroupUrl,
			type:'post',
			dataType:'json',
			contentType:'application/json',
			data:JSON.stringify({}),
			success:function(data){
				document.getElementById("myGroupMessage").innerHTML=data[0].text;	
				rootGroup=data[0].id;
				$('#tt').tree("loadData",data);
				
			}
		});
	}
	jQuery(function($){	
		loadGroupTree();
	});
	
	//添加组
	function append(url){
		
		$('#dialogIdAdd').dialog({  
	        left:"450px",
	        top:"200px",
	        title: '新增组',
	        inline:false,
	        closed:true,
	        width: 350,    
	        height: 150,                
	        cache: false,
	        shadow:false,
	        modal: true ,
	        buttons:'#dialogButtonDivAdd',
	        href:'${pageContext.request.contextPath}'+url,
			onLoad:function(){
	        	$('#groupNameAdd').textbox('setValue','');
	        	$('#groupIconAdd').combobox('setValue','');        	
	        }
	    });
		$('#dialogIdAdd').dialog("open");
		
		
	}

	//更新组
	function updateit(url){	
		$('#dialogIdUpdate').dialog({  
	        left:"450px",
	        top:"200px",
	        title: '更新组',
	        inline:false,
	        closed:true,
	        width: 350,    
	        height: 150,                
	        cache: false,
	        shadow:false,
	        modal: true ,
	        buttons:'#dialogButtonDivUpdate',
	        href:'${pageContext.request.contextPath}'+url,
	        onLoad:function(){
	        	
	        	var t = $('#tt');
	        	var node = t.tree('getSelected');
	        	var groupName=node.text;
	        	var groupIcon=node.iconCls;	        	
	        	if(null!==groupName){
	        		$('#groupNameUpdate').textbox('setValue',groupName);
	        	}	        	
	        	if(null!==groupIcon){
	        		$('#groupIconUpdate').combobox('setText',groupIcon);
	            	$('#groupIconUpdate').combobox('setValue',groupIcon);
	        	}	        	       	
	        }
	    });
		$('#dialogIdUpdate').dialog("open");
		
		
	}


	//删除菜单
	function removeit(url){
		var t = $('#tt');
		var node = t.tree('getSelected');
		var groupId=node.id;
		var removeData={groupId:groupId};
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
	
	//弹出窗口点击确定处理函数，新增组
	function addGroupAction(url)
	{
		openProgress();
		var t = $('#tt');
		var node = t.tree('getSelected');
		var parentMenu=node.text;
		var groupName=$('#groupNameAdd').val();		
		var groupIcon=$('#groupIconAdd').val();
		var fatherGroupId=node.id;
		var AddData={'groupName':groupName,groupIcon:groupIcon,fatherGroupId:fatherGroupId};
		
		$.ajax({
			type:'post',
			dataType:'json',
			url:'${pageContext.request.contextPath}'+url,
			contentType: "application/json", 
			data:JSON.stringify(AddData),
			success:function(data){
				
				t.tree('append', {
					parent: (node?node.target:null),
					data: [{text:groupName,iconCls:groupIcon,id:data,fatherGroupId:fatherGroupId}]
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


	//弹出窗口点击确定处理函数，更新组
	function updateGroupAction(url)
	{
		openProgress();
		var t = $('#tt');
		var node = t.tree('getSelected');

		var groupName=$('#groupNameUpdate').val();
		var groupIcon=$('#groupIconUpdate').val();
		var groupId=node.id;
		var updateData={groupName:groupName,groupIcon:groupIcon,groupId:groupId};

		$.ajax({
			type:'post',
			dataType:'json',
			url:'${pageContext.request.contextPath}'+url,
			contentType: "application/json", 
			data:JSON.stringify(updateData),
			success:function(data){

	        	var node = t.tree('getSelected');
	        	  	
	        	t.tree("update",{target:node.target,text:groupName,iconCls:groupIcon});
	        	console.log($('#tt').tree('getSelected'));
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
	
	<div data-options="region:'center',title:'组别管理',split:true" style="width:50%;height:100%;">
    	<div class="easyui-layout" data-options="fit:true">

			<div data-options="region:'center'" style="height:100%;">
				<div style="width:100%;height:3%;" >
					<p ><span class="likeEasyuiTitleNormal">我的组别信息&nbsp;&nbsp;&nbsp;&nbsp;：</span>
				   	<span id="myGroupMessage" class="likeEasyuiTitleRed"></span>
					</p>
				</div>
				<div style="width:100%;height:1%;" >
					<HR SIZE=1>
				</div>
				<div style="width:100%;height:3%;" >
					<p><span class="likeEasyuiTitleNormal">当前选定组信息：</span>
				   <span id="myChoiceGroupMessage" class="likeEasyuiTitleRed"></span>
					</p>
				</div>
				<div style="width:100%;height:1%;" >
					<HR SIZE=1>
				</div>
				<div style="width:100%;height:3%;" >
					<p style="text-align:center"><span class="likeEasyuiTitleRed">我的组别结构图</span></p>
				</div>
				<div style="width:100%;height:81%;" >
					<div  class="easyui-panel" style="padding:5px;width:100%; height:100%;color:#444444;border=1px;">
						<ul id="tt" class="easyui-tree"  data-options="
								data: groupTreeData,
								animate: true,
								onClick:onClickGroup,
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
				
			</div>
			
		</div>
    </div>

	<div data-options="region:'east',title:'我的菜单及URL',split:true" style="width:50%;height:100%;">
		<div class="easyui-layout,split:true"  style="width:100%;height:100%;">
			<div data-options="region:'north'" style="width:100%;height:5%;">
				<table style="width:100%;">
					<tr >			
						<td style="text-align:center;">
							<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="saveTreeMenu('{url}')" url="/menuGroup/updateMenuTree" name="保存"></PermissionTag:Alink>	
						</td>		
					</tr>
				</table>
			</div>
		
		    <div data-options="region:'center',title:'center'" style="width:100%;height:95%;">
		    	<div  class="easyui-panel" style="padding:5px;width:100%; height:100%;color:#444444;border=1px;">		
					<ul id="ttMenu" class="easyui-tree"  data-options="
							data: menuTreeData,
							animate: true,
							checkbox:true,
							onBeforeCheck:onBeforeCheckSet
						"></ul>
				</div>
		    </div>
		</div>
		
	</div>
	
	<div id="rightClickMenu" class="easyui-menu" style="width:120px;display:none">
		<PermissionTag:DivLink name="添加" url="/group/addShow" dataOptions="iconCls:'icon-add'" onclick="append('{url}')"></PermissionTag:DivLink>
		<PermissionTag:DivLink name="更新" url="/group/updateShow" dataOptions="iconCls:'icon-edit'" onclick="updateit('{url}')"></PermissionTag:DivLink>
		<PermissionTag:DivLink name="删除" url="/group/delete" dataOptions="iconCls:'icon-remove'" onclick="removeit('{url}')"></PermissionTag:DivLink>
		<div class="menu-sep"></div>
		<div onclick="expand()">展开</div>
		<div onclick="collapse()">收起</div>
	</div>	

	<div id="dialogIdAdd" style="display:none">
		
	</div>
	
	<div id="dialogIdUpdate" style="display:none">
		
	</div>

	<div id="dialogButtonDivAdd" style="display:none">
		<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="addGroupAction('{url}')" url="/group/add" name="新增组"></PermissionTag:Alink>	
	</div>
	
	<div id="dialogButtonDivUpdate" style="display:none">
		<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="updateGroupAction('{url}')" url="/group/update" name="更新组"></PermissionTag:Alink>	
	</div>
	
</body>
</html>