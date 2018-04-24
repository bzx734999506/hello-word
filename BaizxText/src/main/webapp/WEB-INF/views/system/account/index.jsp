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

<script src="${pageContext.request.contextPath}/resources/easyui/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/common/easyuiExtensions/validateExtends.js" type="text/javascript"></script>
<script type="text/javascript">

	var dataGridData={total:0,rows:[]};
	
	var pageObject={
			currPage:1,
			pageRecordsCount:10,
			totalRecords:0,
			totalPages:0,
			offset:1,
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
				return document.getElementById("pageGo").value;
			},
			getOffset:function(page){
				
				var offSet= (page-1)*this.pageRecordsCount;
				return offSet;
			},
			queryDone:function(currP,count,resultOffset,queryCount){
				this.totalRecords=count;
				this.currPage=currP;
				this.init();
				//显示一些查询的结果 总记录数 当前页等等
				document.getElementById("totalPage").innerHTML=this.totalPages;
				document.getElementById("start").innerHTML=resultOffset+1;
				document.getElementById("end").innerHTML=resultOffset+queryCount;
				document.getElementById("totalRecords").innerHTML=count;
				document.getElementById("pageGo").value=currP;
				
				//设置disable 	
				$("#First").linkbutton({"disabled":this.isFirstDisable()});
				$("#Next").linkbutton({"disabled":this.isNextDisable()});
				$("#Last").linkbutton({"disabled":this.isLastDisable()});
				$("#Previous").linkbutton({"disabled":this.isPreviousDisdable()});
				
				if(this.totalPages>1)
				{
					document.getElementById("pagination").style.display="";
				}
				else
				{
					document.getElementById("pagination").style.display="none";
				
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
	
	
	//添加用户 弹出页面
	function addActionShow(url){
		
		$('#dialogIdAdd').dialog({  
	        left:"450px",
	        top:"200px",
	        title: '新增用户',
	        inline:false,
	        closed:true,
	        width: 350,    
	        height: 200,              
	        cache: false,
	        shadow:false,
	        modal: true ,
	        buttons:'#dialogButtonDivAdd',
	        href:'${pageContext.request.contextPath}'+url,
			onLoad:function(){

	        	$('#accountNo').textbox('setValue','');
	        	$('#iconAdd').textbox('setValue','');        	
	        }
	    });
		$('#dialogIdAdd').dialog("open");
	}
	
	//更新用户 弹出页面
	function updateActionShow(url){
		var row = $('#dg').datagrid('getSelected');//json data
		if (row){
			$('#dialogIdUpdate').dialog({  
		        left:"450px",
		        top:"200px",
		        title: '更新用户',
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
			    	if(null!==row){			    		
		            	$('#accountNo').textbox('setValue',row.accountNo);
				    	$('#iconUpdate').textbox('setValue',row.iconCls);  	
		        	}
		        }
		    });
			$('#dialogIdUpdate').dialog("open");
		}
    	
		
	}
	
	

	//查询数据
	function queryAction(url,bounds){
		
		var accountNo=$('#accountNoQuery').val();
		var queryData={accountNo:accountNo};
		var offset=1;
		var limit=pageObject.pageRecordsCount;
		var currPage=1;
		console.log(bounds);
		if(undefined !== bounds){
			var functionName='get'+bounds;
			var functionMethod=pageObject[functionName];
			
			currPage=functionMethod.apply(pageObject);		
		}
		console.log("currPage:"+currPage);		
		offset=pageObject.getOffset(currPage);
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
				
				dataGridData=data['data'];
				var totalRecords=data['totalRecordsCount'];				
				console.log(dataGridData);
				console.log(totalRecords);
				$('#dg').datagrid("loadData",dataGridData);

				pageObject.queryDone(currPage,totalRecords,offset,dataGridData.length);
				closeProgress();
			},
			error:function(ajaxObject,errMsg){
				console.log(errMsg);
				closeProgress();
			}
		});
	}
	
	//删除数据
	function deleteAction(url){
		
		var row = $('#dg').datagrid('getSelected');
		var rowIndex=$('#dg').datagrid('getRowIndex',$('#dg').datagrid('getSelected'));

		if("system"===row.accountNo)
		{
			alert("账户system不允许被删除");
			return;
		}	
		if (row){
			openProgress();
			$.ajax({
				type:'post',
				dataType:'json',
				contentType:'application/json',
				url:'${pageContext.request.contextPath}'+url,
				data:JSON.stringify(row),
				success:function(data){
					$('#dg').datagrid('deleteRow',rowIndex);
					closeProgress();
				},
				error:function(ajaxObject,errMsg){
					console.log(errMsg);
					closeProgress();
				}
			});
		}
	}
	
	
	//弹出窗口点击确定处理函数，更新用户
	function updateAction(url)
	{
		openProgress();

		var accountNo=$('#accountNo').val();
		
		var iconUpdate=$('#iconUpdate').val();
		
		var postData={accountNo:accountNo,iconCls:iconUpdate};
		var row = $('#dg').datagrid('getSelected');//json data
		var rowIndex=$('#dg').datagrid('getRowIndex',$('#dg').datagrid('getSelected'));
		$.ajax({
			type:'post',
			dataType:'json',
			url:'${pageContext.request.contextPath}'+url,
			contentType: "application/json", 
			data:JSON.stringify(postData),
			success:function(data){
				console.log(data); 
				$('#dg').datagrid('updateRow',{
					index: rowIndex,
					row: postData
				});
				$('#dialogIdUpdate').dialog('close');
				closeProgress();
			},
			error:function(ajaxObject,errMsg){
				console.log(errMsg);
				closeProgress();
			}
		});
		
		
	}
	
	//弹出窗口点击确定处理函数，新增用户
	function addAction(url)
	{
		openProgress();

		var accountNo=$('#accountNo').val();
		var password=$('#password').val();
		var confirmPassword=$('#confirmPassword').val();
		var iconAdd=$('#iconAdd').val();
		
		var AddMenuData={accountNo:accountNo,password:password,iconCls:iconAdd};
		
		$.ajax({
			type:'post',
			dataType:'json',
			url:'${pageContext.request.contextPath}'+url,
			contentType: "application/json", 
			data:JSON.stringify(AddMenuData),
			success:function(data){
				console.log(data);
				$('#dg').datagrid('insertRow',{
					index: 0,
					row: AddMenuData
				});
				AddMenuData[password]="*******";
				$('#dialogIdAdd').dialog('close');
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
	
</script>
</head>
<body class="easyui-layout">

	<div data-options="region:'north'" style="height:200px;padding:20px 60px 20px 60px">
		<table style="width:100%;">
			<tr style="width:100%;">			
				<td style="width:20%;text-align:right;">
					<p>账户号码:</p>
				</td>	
				<td style="width:30%;">
					<input class="easyui-textbox" name="accountNoQuery" id="accountNoQuery" data-options="">	
				</td>	
				
			</tr>

			<tr style="width:100%;">			
				<td style="text-align:center;" colspan="4">
						<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="addActionShow('{url}')" url="/account/addShow" name="添加"></PermissionTag:Alink>	
						<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="updateActionShow('{url}')" url="/account/updateShow" name="编辑"></PermissionTag:Alink>	
						<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="deleteAction('{url}')" url="/account/delete" name="删除"></PermissionTag:Alink>
						<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="queryAction('{url}')" url="/account/query" name="查询"></PermissionTag:Alink>	
				</td>				
			</tr>
		</table>
		
	</div>
    <div id="centerId" data-options="region:'center'" style="">
    	<table class="easyui-datagrid" id="dg" style="width:100%;height:95%" 
			data-options="singleSelect:true,collapsible:true,data:dataGridData,width:'auto'">
			<thead>
				<tr>
					<th data-options="field:'ck',align:'center',checkbox:true"></th>
					<th data-options="field:'accountNo',width:fixWidth(0.47),align:'center'">账户号码</th>
					<th data-options="field:'iconCls',width:fixWidth(0.47),align:'center'">账户图标</th>
				</tr>
			</thead>
			
		</table>
		<div style="width:100%;height:%5;display:none" id="pagination">
			<div style="width:70%;text-align:left;float:left">
			
				<a href="#" class="easyui-linkbutton" id="First" onclick="queryAction('/account/query','First')" >&lt;&lt;</a>	
				<a href="#"  class="easyui-linkbutton" id="Previous" onclick="queryAction('/account/query','Previous')" >&lt;</a>	
				<span>page</span>
				<input type="text" name="pageGo" id="pageGo" size="1" style="text-align:center;"/> 
				<span>of</span>
				<span id="totalPage"></span>
				<a href="#"  class="easyui-linkbutton" onclick="queryAction('/account/query','Go')" >确定</a>
				<a href="#" class="easyui-linkbutton" id="Next" onclick="queryAction('/account/query','Next')" >></a>
				<a href="#" class="easyui-linkbutton" id="Last" onclick="queryAction('/account/query','Last')" >>></a>            
			</div>	
			<div style="width:30%;text-align:center;;float:right">
				<span>Displaying </span><span id="start"></span> <span> to </span> <span id="end"></span><span> of </span><span  id="totalRecords"></span><span> items</span>
			</div>	
		</div>
					
    </div>

	
	<div id="dialogIdAdd" style="display:none">
		
	</div>
	
	<div id="dialogIdUpdate" style="display:none">
		
	</div>

	<div id="dialogButtonDivAdd" style="display:none">
		<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="addAction('{url}')" url="/account/add" name="添加确认"></PermissionTag:Alink>	
	</div>
	
	<div id="dialogButtonDivUpdate" style="display:none">
		<PermissionTag:Alink cssClass="easyui-linkbutton" onclick="updateAction('{url}')" url="/account/update" name="修改确认"></PermissionTag:Alink>	
	</div>
	
</body>
</html>