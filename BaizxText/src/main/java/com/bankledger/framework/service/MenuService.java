package com.bankledger.framework.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankledger.framework.bean.TreeMenu;
import com.bankledger.framework.dao.DBMenuDao;
import com.bankledger.framework.dao.DBMenuGroupDao;

import net.sf.json.JSONArray;

@Service("menuService")
public class MenuService implements MenuServiceInterface {
	@Autowired
	DBMenuDao menuDao;
	
	@Autowired
	DBMenuGroupDao menuGroupDao;
	
	@Resource(name="menuGroupService")
	MenuGroupInterface menuGorupService;
	
	@Override
	@Transactional (rollbackFor = Exception.class)
	public boolean deleteMenu(String menuId) {
		
		Map<String,TreeMenu> allMenu =getTreeMenuMap(menuDao.getAllMenu());
		TreeMenu.buildTreeGroup(allMenu);
		
		TreeMenu deleteMenu = allMenu.get(menuId);
		
		List<String> deleteMenuIdList= deleteMenu.getAllChildrenMenuId();
		
		System.out.println(JSONArray.fromObject(deleteMenuIdList).toString());
		
		//鍒犻櫎鑿滃崟鍒楄〃
		menuDao.deleteList(deleteMenuIdList);
		
		//鑿滃崟宸茬粡鍒犻櫎浜嗭紝鎵�浠ュ皢鑿滃崟涓庣粍琛ㄤ腑鐨勭浉鍏崇殑鏁版嵁涔熷垹闄ゆ帀
		menuGroupDao.deleteList(deleteMenuIdList);
		return true;
	}
	
	/**
	 * 灏嗘暟鎹簱鏌ヨ缁撴灉杞崲涓篬key 涓簃enuId锛寁alue 涓篢reeMap 鐨勬暟鎹粨鏋刔灏氭湭璁ょ褰掑畻
	 * @param menuData
	 * @return
	 */

	public Map<String,TreeMenu> getTreeMenuMap(List<Map<String,String>> menuData){

		Map<String,TreeMenu> rs = new HashMap<String,TreeMenu>();
		for(Map<String,String> map:menuData){
			TreeMenu menu = new TreeMenu();
			menu.setNeedAuthFlag(map.get("auth_flag"));
			menu.setIconCls(map.get("menu_icon"));
			menu.setId(map.get("menu_id"));
			menu.setText(map.get("menu_name_"+"ch"));
			menu.setMenuFlag(map.get("menu_type"));
			menu.setUrl(map.get("menu_url"));
			menu.setParentMenuId(map.get("parent_menu_id"));
			rs.put(menu.getId(), menu);			
		}
		return rs;
	}

}
