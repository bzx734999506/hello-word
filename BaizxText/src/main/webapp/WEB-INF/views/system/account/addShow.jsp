<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="PermissionTag" uri="http://www.bankledger.com/tags/PermissionTag" %>
<script type="text/javascript">
	var iconData=[
		{id:'bf4j-icon-node-12',text:'<span class="bf4j-icon-node-12">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-11',text:'<span class="bf4j-icon-node-11">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-15',text:'<span class="bf4j-icon-node-15">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-27',text:'<span class="bf4j-icon-node-27">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-17',text:'<span class="bf4j-icon-node-17">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-21',text:'<span class="bf4j-icon-node-21">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-24',text:'<span class="bf4j-icon-node-24">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-04',text:'<span class="bf4j-icon-node-04">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-25',text:'<span class="bf4j-icon-node-25">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-07',text:'<span class="bf4j-icon-node-07">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-09',text:'<span class="bf4j-icon-node-09">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-34',text:'<span class="bf4j-icon-node-34">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-10',text:'<span class="bf4j-icon-node-10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-20',text:'<span class="bf4j-icon-node-20">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-06',text:'<span class="bf4j-icon-node-06">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-16',text:'<span class="bf4j-icon-node-16">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-18',text:'<span class="bf4j-icon-node-18">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-19',text:'<span class="bf4j-icon-node-19">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-03',text:'<span class="bf4j-icon-node-03">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-22',text:'<span class="bf4j-icon-node-22">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-39',text:'<span class="bf4j-icon-node-39">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-33',text:'<span class="bf4j-icon-node-33">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-32',text:'<span class="bf4j-icon-node-32">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-30',text:'<span class="bf4j-icon-node-30">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-01',text:'<span class="bf4j-icon-node-01">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		{id:'bf4j-icon-node-41',text:'<span class="bf4j-icon-node-41">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>'},
		];

	var mySelectValue=null;
	$("#iconAdd").combobox({

		onChange: function (newValue,oldValue) {
			mySelectValue=newValue;
			$('#iconAdd').combobox('setValue',newValue).combobox('setText',newValue);		
		},
		//onclick is for fixed the problem if click the same icon text will change!
		onClick:function(selectMenu){//selectMenu is the clicked object(json data)
			
			if(mySelectValue===selectMenu.id)//click the same,because the old value is equal to the clicked value
			{
				$('#iconAdd').combobox('setValue',"").combobox('setText',"");	
				return;
			}

 		}
	});
	
	
</script>

<div class="content" align="center">
        <table>
        	
            <tr>
                <td><span>账户号码：</span></td>
                <td><input class="easyui-textbox"  id="accountNo" style="height:25px" /></td>
            </tr>
            
             <tr>
                <td><span>账户密码：</span></td>
                <td><input class="easyui-textbox" type="password" id="password" style="height:25px" /></td>
            </tr>
        	
        	 <tr>
                <td><span>确认密码：</span></td>
                <td><input class="easyui-textbox" type="password" id="confirmPassword" style="height:25px" /></td>
            </tr>
        	
            <tr>
                <td><span>账户图标 ：</span></td>       
                <td>
                	<input id="iconAdd" name="iconAdd" class="easyui-combobox" style="width:100%;"  data-options="
					valueField: 'id',
					textField: 'text',
					data:iconData
					">
				</td>
            </tr>       
            
        </table>

        	
</div>
