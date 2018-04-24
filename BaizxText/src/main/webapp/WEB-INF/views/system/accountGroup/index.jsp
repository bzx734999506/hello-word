<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/include/tagIncluded.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<title>权限组及账户管理</title>
<jsp:include page="/WEB-INF/views/common/include/include.jsp"></jsp:include>

<script src="${pageContext.request.contextPath}/resources/easyui/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/easyui/jquery.easyui.min.js" type="text/javascript"></script>

<script type="text/javascript">
	var dataGridData1={total:0,rows:[]};
	var pageObject1={
			currPage:1,
			pageRecordsCount:10,
			totalRecords:0,
			totalPages:0,
			offset:1,
			index:1,
			init:function(){
				var yushu=this.totalRecords%this.pageRecordsCount;
				this.totalPages=(this.totalRecords-yushu)/this.pageRecordsCount+(yushu>0?1:0);
				console.log(this.totalPages);
				return this.totalPages;
			},
			getPrevious:function(){
	
				var pre= this.currPage-1>0?this.currPage-1:1;
				
				return pre;
			},
			getFirst:function(){
				return 1;
			},
			getLast:function(){
				return this.totalPages;
			},
			getNext:function(){
				
				return (this.currPage+1)>this.totalPages?this.totalPages:(this.currPage+1);
			},
			getGo:function(){
				return document.getElementById("pageGo"+index).value;
			},
			getOffset:function(page){
				
				var offSet= (page-1)*this.pageRecordsCount;
				return offSet;
			},
			queryDone:function(currP,count,resultOffset,queryCount){
				this.totalRecords=count;
				this.currPage=currP;
				this.init();
// 				console.log("*******");
// 				console.log("totalPage"+this.index);
// 				console.log(currP);
// 				console.log(count);
// 				console.log(resultOffset);
// 				console.log(queryCount);
// 				console.log("*******");
				//显示一些查询的结果 总记录数 当前页等等
				document.getElementById("totalPage"+this.index).innerHTML=this.totalPages;
				document.getElementById("start"+this.index).innerHTML=resultOffset+1;
				document.getElementById("end"+this.index).innerHTML=resultOffset+queryCount;
				document.getElementById("totalRecords"+this.index).innerHTML=count;
				document.getElementById("pageGo"+this.index).value=currP;

				
				//设置disable 	
				$("#First"+this.index).linkbutton({"disabled":this.isFirstDisable()});
				$("#Next"+this.index).linkbutton({"disabled":this.isNextDisable()});
				$("#Last"+this.index).linkbutton({"disabled":this.isLastDisable()});
				$("#Previous"+this.index).linkbutton({"disabled":this.isPreviousDisdable()});
				
				if(this.totalPages>1)
				{
					document.getElementById("pagination"+this.index).style.display="";
				}
				else
				{
					document.getElementById("pagination"+this.index).style.display="none";
				
				}
				
			},
			isPreviousDisdable:function(){
				
				return this.currPage===1;
			},
			isFirstDisable:function(){
				
				return this.currPage===1;
			},
			isNextDisable:function(){
				
				return this.currPage===this.totalPages;
			},		
			isLastDisable:function(){
				
				return this.currPage===this.totalPages;
			}
				
	};
	
	var pageObject2 =$.extend({}, pageObject1);
	pageObject2.index=2;
	
	var groupTreeData;
	
	var groupId=null;
	var groupName=null;
	var queryGroupUrl='${pageContext.request.contextPath}'+'/group/queryGroup';
	
	//点击组，代表选定一个组
	function onClickGroup(node){
		
		document.getElementById("choiceGroupMessage").innerHTML=node.text;
		document.getElementById("choiceGroupMessageFather").style.display="";
		groupId=node.id;
		groupName=node.text;
		queryAction2("/accountGroup/queryGroupAccount");
	}
	
	//addToGroup removeFromGroup
	function addToGroup(url){
		if(!groupId){
			alert("请选择要添加的组！");
			return;
		}
		var rows = $('#dg1').datagrid('getSelections');
		
		if(rows.length===0){
			alert("请选择要添加到"+groupName+"的账户号码！");
			return;
		}
		
		var addData={groupId:groupId};
		var data=[];
		for(var i=0;i<rows.length;i++){
			data.push(rows[i].accountNo);
		}
// 		console.log(data);
		addData['data']=data;
// 		console.log(addData);
		
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}'+url,
			dataType:'json',
			contentType:'application/json',
			data:JSON.stringify(addData),
			success:function(data){
				queryAction1("/accountGroup/queryUngroupAccount");
			},
			error:function(ajaxObject,errMsg){
				console.log(errMsg);
				closeProgress();
			}
		});
	}
	
	function removeFromGroup(url){
		if(!groupId){
			alert("请选择要添加的组！");
			return;
		}
		var rows = $('#dg2').datagrid('getSelections');
		if(rows.length==-0){
			alert("请选择要移除出"+groupName+"的账户号码！");
			return;
		}
		
		var addData={groupId:groupId};
		var data=[];
		for(var i=0;i<rows.length;i++){
			data.push(rows[i].accountNo);
		}
		addData['data']=data;
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}'+url,
			dataType:'json',
			contentType:'application/json',
			data:JSON.stringify(addData),
			success:function(data){
				queryAction2("/accountGroup/queryGroupAccount");
			},
			error:function(ajaxObject,errMsg){
				console.log(errMsg);
				closeProgress();
			}
		});
		
		
	}
	
	//查询数据
	function queryAction1(url,bounds){
		
		var accountNo=$('#accountNoQuery1').val();
		var queryData={accountNo:accountNo};
		var offset=1;
		var limit=pageObject1.pageRecordsCount;
		var currPage=1;
		
		if(undefined !== bounds){
			var functionName='get'+bounds;
			var functionMethod=pageObject1[functionName];
			
			currPage=functionMethod.apply(pageObject1);		
		}
		
		offset=pageObject1.getOffset(currPage);
		queryData['offset']=offset;
		queryData['limit']=limit;
		console.log(queryData);
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}'+url,
			dataType:'json',
			contentType:'application/json',
			data:JSON.stringify(queryData),
			success:function(data){

				var totalRecords=data['totalRecordsCount'];				
				console.log(data['data']);
				console.log(totalRecords);
				$('#dg1').datagrid("loadData",data['data']);

				pageObject1.queryDone(currPage,totalRecords,offset,data['data'].length);
				closeProgress();
			},
			error:function(ajaxObject,errMsg){
				console.log(errMsg);
				closeProgress();
			}
		});
	}
	
	//查询数据
	function queryAction2(url,bounds){
		
		var accountNo=$('#accountNoQuery2').val();
		if(!groupId){
			alert("Please select group to query");
			return;
		}
		var queryData={accountNo:accountNo,groupId:groupId};
		var offset=1;
		var limit=pageObject2.pageRecordsCount;
		var currPage=1;
		
		if(undefined !== bounds){
			var functionName='get'+bounds;
			var functionMethod=pageObject2[functionName];
			
			currPage=functionMethod.apply(pageObject2);		
		}
			
		offset=pageObject2.getOffset(currPage);
		queryData['offset']=offset;
		queryData['limit']=limit;
		console.log(queryData);
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath}'+url,
			dataType:'json',
			contentType:'application/json',
			data:JSON.stringify(queryData),
			success:function(data){
	
				var totalRecords=data['totalRecordsCount'];				
				console.log(data['data']);
				console.log(totalRecords);
				$('#dg2').datagrid("loadData",data['data']);

				pageObject2.queryDone(currPage,totalRecords,offset,data['data'].length);
				closeProgress();
			},
			error:function(ajaxObject,errMsg){
				console.log(errMsg);
				closeProgress();
			}
		});
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
	
	//设置datagrid field 的宽带
    function fixWidth(percent)  
    {  
        return document.body.clientWidth * percent ; 
    }  

	jQuery(function($){	
		$.ajax({
			url:queryGroupUrl,
			type:'post',
			dataType:'json',
			contentType:'application/json',
			data:JSON.stringify({}),
			success:function(data){
				console.log(data);
				$('#tt').tree("loadData",data);
				
			}
		});
	});
	
</script>
</head>
<body class="easyui-layout" data-options="fit:true">

    <div data-options="region:'center',title:'账户',split:true" style="width:60%;">
    	<div class="easyui-layout" data-options="fit:true">
    		<div data-options="region:'center',title:'未分组用户',split:true" style="height:5%;">
    			<div class="easyui-layout" data-options="fit:true">
    				<div data-options="region:'north'" style="width:100%;height:15%">
    					<table style="width:100%;">
							<tr style="width:100%;">			
								<td style="width:20%;text-align:right;">
									<p>账户号码:</p>
								</td>	
								<td style="width:30%;">
									<input class="easyui-textbox" name="accountNoQuery1" id="accountNoQuery1" data-options="">	
								</td>							
							</tr>			
							<tr style="width:100%;">			
								<td style="text-align:center;" colspan="4">
										<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="addToGroup('{url}')" url="/accountGroup/addToGroup" name="添加到选定组"></PermissionTag:Alink>										
										<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="queryAction1('{url}')" url="/accountGroup/queryUngroupAccount" name="查询"></PermissionTag:Alink>	
								</td>				
							</tr>
						</table>
    				</div>					
				    <div data-options="region:'center'" style="width:100%;height:85%">
				    	<table class="easyui-datagrid" id="dg1" style="width:100%;height:92%" 
							data-options="singleSelect:false,collapsible:true,data:dataGridData1,width:'auto'">
							<thead>
								<tr>
									<th data-options="field:'ck',align:'center',checkbox:true"></th>
									<th data-options="field:'accountNo',width:fixWidth(0.27),align:'center'">账户号码</th>
									<th data-options="field:'iconCls',width:fixWidth(0.27),align:'center'">账户图标</th>
								</tr>
							</thead>
							
						</table>
						<div style="width:100%;height:%8;display:none" id="pagination1">
							<div style="width:100%;text-align:left;float:left">
							
								<a href="#" class="easyui-linkbutton" id="First1" onclick="queryAction1('/accountGroup/queryUngroupAccount','First')" >&lt;&lt;</a>	
								<a href="#"  class="easyui-linkbutton" id="Previous1" onclick="queryAction1('/accountGroup/queryUngroupAccount','Previous')" >&lt;</a>	
								<span>page</span>
								<input type="text" name="pageGo1" id="pageGo1" size="1" style="text-align:center;"/> 
								<span>of</span>
								<span id="totalPage1"></span>
								<a href="#"  class="easyui-linkbutton" onclick="queryAction1('/accountGroup/queryUngroupAccount','Go')" >确定</a>
								<a href="#" class="easyui-linkbutton" id="Next1" onclick="queryAction1('/accountGroup/queryUngroupAccount','Next')" >></a>
								<a href="#" class="easyui-linkbutton" id="Last1" onclick="queryAction1('/accountGroup/queryUngroupAccount','Last')" >>></a>  
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
								<span>Displaying </span><span id="start1"></span> <span> to </span> <span id="end1"></span><span> of </span><span  id="totalRecords1"></span><span> items</span>          
							</div>	
							
						</div>

				    </div>
				</div>
    		</div>
    		
    		<div data-options="region:'north'" style="height:4%;" style="text-align:center">
    			<span style="font-weight: bold;font-size: 14px;color: #777;line-height: 20px;padding: 0px 20px;">&nbsp;选定组信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
    			<span>>>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
    			<span style="text-align:center;display:none" id="choiceGroupMessageFather">你选定的组的名称是: 
    			<span style="color:red" id="choiceGroupMessage"></span></span>
    		</div>
			<div data-options="region:'south',title:'归属于当前组用户',split:true" style="height:92%;">
					<div class="easyui-layout" data-options="fit:true">
    				<div data-options="region:'north'" style="width:100%;height:15%">
    					<table style="width:100%;">
							<tr style="width:100%;">			
								<td style="width:20%;text-align:right;">
									<p>账户号码:</p>
								</td>	
								<td style="width:30%;">
									<input class="easyui-textbox" name="accountNoQuery2" id="accountNoQuery2" data-options="">	
								</td>							
							</tr>			
							<tr style="width:100%;">			
								<td style="text-align:center;" colspan="4">
										<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="removeFromGroup('{url}')" url="/accountGroup/removeFromGroup" name="选中账户移除选定组"></PermissionTag:Alink>										
<%-- 										<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="queryAction2('{url}')" url="/accountGroup/queryGroupAccount" name="查询"></PermissionTag:Alink>	 --%>
								</td>				
							</tr>
						</table>
    				</div>					
				    <div data-options="region:'center'" style="width:100%;height:85%">
				    	<table class="easyui-datagrid" id="dg2" style="width:100%;height:95%" 
							data-options="singleSelect:false,collapsible:true,data:dataGridData1,width:'auto'">
							<thead>
								<tr>
									<th data-options="field:'ck',align:'center',checkbox:true"></th>
									<th data-options="field:'accountNo',width:fixWidth(0.27),align:'center'">账户号码</th>
									<th data-options="field:'iconCls',width:fixWidth(0.27),align:'center'">账户图标</th>
								</tr>
							</thead>
							
						</table>
						<div style="width:100%;height:%5;display:none" id="pagination2">
							<div style="width:100%;text-align:left;float:left">
							
								<a href="#" class="easyui-linkbutton" id="First2" onclick="queryAction2('/accountGroup/queryGroupAccount','First')" >&lt;&lt;</a>	
								<a href="#"  class="easyui-linkbutton" id="Previous2" onclick="queryAction2('/accountGroup/queryGroupAccount','Previous')" >&lt;</a>	
								<span>page</span>
								<input type="text" name="pageGo2" id="pageGo2" size="1" style="text-align:center;"/> 
								<span>of</span>
								<span id="totalPage2"></span>
								<a href="#"  class="easyui-linkbutton" onclick="queryAction2('/accountGroup/queryGroupAccount','Go')" >确定</a>
								<a href="#" class="easyui-linkbutton" id="Next2" onclick="queryAction2('/accountGroup/queryGroupAccount','Next')" >></a>
								<a href="#" class="easyui-linkbutton" id="Last2" onclick="queryAction2('/accountGroup/queryGroupAccount','Last')" >>></a>  
								<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
								<span>Displaying </span><span id="start2"></span> <span> to </span> <span id="end2"></span><span> of </span><span  id="totalRecords2"></span><span> items</span>          
							</div>	
							
						</div>

				    </div>
				</div>
				
			</div>
			
    	</div>
    </div>
	<div data-options="region:'east',title:'权限组',split:true" style="width:40%;">
		
    	<div  class="easyui-panel" style="padding:5px;width:100%; height:100%;color:#444444;border=1px;">
			<ul id="tt" class="easyui-tree"  data-options="
					data: groupTreeData,
					animate: true,
					onClick:onClickGroup			
				"></ul>
		</div>
     
	</div>
	
	
</body>
</html>