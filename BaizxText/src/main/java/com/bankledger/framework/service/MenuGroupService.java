package com.bankledger.framework.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankledger.framework.bean.MenuGroupDaoBean;
import com.bankledger.framework.bean.TreeMenu;
import com.bankledger.framework.dao.DBMenuDao;
import com.bankledger.framework.dao.DBMenuGroupDao;

@Service("menuGroupService")
public class MenuGroupService implements MenuGroupInterface{
	
	@Autowired
	DBMenuDao menuDao;
	
	@Autowired
	DBMenuGroupDao menuGroupDao;

	@Override
	public TreeMenu loadMainFrameMenu(String groupId) {
		//绯荤粺缁勶紝鍔犺浇鎵�鏈夌殑鑿滃崟鍙婂姛鑳介」
		if("0".equals(groupId)){
			return loadSystemGroupMainFrameMenu();
		}
		else{//闈炵郴缁熺粍锛屽姞杞芥樉绀哄瀷鑿滃崟鍦ㄤ富椤典笂鏄剧ず
			return loadGroupIdTreeMenu(groupId);
		}
		
	}

	/**
	 *  鍔犺浇鎵�鏈夌殑鑿滃崟
	 *  鍔犺浇groupId 瀵瑰簲鐨勮彍鍗�
	 *	鍔犺浇parentGroupId瀵瑰簲鐨勮彍鍗�
	 *	杩斿洖parentGroupId 瀵瑰簲鐨勮彍鍗曪紝濡傛灉groupId 涓寘鍚湁鐩稿簲鐨勮彍鍗曪紝閭ｄ箞灏�"checked":true
	 */
	@Override
	public TreeMenu loadEditMenu(String groupId, String parentGroupId) {
		//allMenu 鐖朵翰缁�
		Map<String,TreeMenu> allMenu =null;
		if("0".equals(groupId)){
			return loadSystemGroupMainFrameMenu();
		}
		else if("0".equals(parentGroupId)){
			allMenu =getTreeMenuMap(menuDao.getAllMenu());		
		}
		else
		{
			allMenu= queryMenuDataByGroupId(parentGroupId);
		}
		Map<String,TreeMenu> myGroupMenuMap = queryMenuDataByGroupId(groupId);
		for(Map.Entry<String, TreeMenu> entry:myGroupMenuMap.entrySet()){
			String menuId = entry.getKey();
			TreeMenu menu=allMenu.get(menuId);
			if(null!=menu){
				allMenu.get(menuId).setChecked(true);
			}
		}
		TreeMenu.buildTreeGroup(allMenu);
		return allMenu.get("0");
	}
	
	/**
	 * 鑾峰緱鎵�鏈夌殑鑿滃崟鍙婂姛鑳介」锛岃绁栧綊瀹楋紝杩斿洖鏍硅妭鐐�
	 * 0灏辨槸鏍硅妭鐐�
	 * @return
	 */
	public TreeMenu loadSystemGroupMainFrameMenu(){
		Map<String,TreeMenu> allMenu =getTreeMenuMap(menuDao.getAllMenu());
		TreeMenu.buildTreeGroup(allMenu);
		return allMenu.get("0");
	}
	
	
	/**
	 * 鑾峰彇groupId 瀵瑰簲鐨勮彍鍗曞垪琛紝鍖呮嫭鍏朵腑鐨勫姛鑳介」
	 * 灏嗗姛鑳介」杩囨护鎺�
	 * 璁ょ褰掑畻
	 * 灏嗘牴鑺傜偣杩斿洖
	 * @param groupId
	 * @return
	 */
	public TreeMenu loadGroupIdTreeMenu(String groupId){
		Map<String,TreeMenu> map=queryMenuDataByGroupId(groupId);
		Map<String,TreeMenu> filterMap=filterFunctionMenu(map);
		TreeMenu.buildTreeGroup(filterMap);
		return filterMap.get("0");
	}
	
	/**
	 * 瀵归噷闈㈢殑鏁版嵁杩涜杩囨护锛岃繃婊ゆ帀鍔熻兘椤�
	 */
	public Map<String,TreeMenu> filterFunctionMenu(Map<String,TreeMenu> map){
		Map<String,TreeMenu> rs = new HashMap<String,TreeMenu>();
		for(Map.Entry<String, TreeMenu> entry:map.entrySet()){
			TreeMenu menu = entry.getValue();
			if("1".equals(menu.getMenuFlag())){//鑿滃崟
				rs.put(entry.getKey(), menu);
			}
		}
		return rs;
	}
	
	
	/**
	 * 鑾峰彇groupId 瀵瑰簲鐨勮彍鍗曞垪琛紝鍖呮嫭鍏朵腑鐨勫姛鑳介」
	 * 浠ap缁撴瀯鐨勬柟寮忚繑鍥烇紝灏氭湭璁ょ褰掑畻
	 */
	public Map<String,TreeMenu> queryMenuDataByGroupId(String groupId){		
		Map<String,TreeMenu> allMenu =getTreeMenuMap(menuDao.getAllMenu());
		Map<String,TreeMenu> rs = new HashMap<String,TreeMenu>();
		List<MenuGroupDaoBean> groupMenu =menuGroupDao.queryByGroupId(groupId);
		//鑾峰緱鑷韩鎷ユ湁鐨凪enu 鍦ㄨ〃BL_MENU_GROUP涓瓨鍌紝杩欎簺鏄渶瑕佹巿鏉冪殑
		for(MenuGroupDaoBean bean:groupMenu){
			String menuId =bean.getMenuId();
			if(null!=menuId){
				TreeMenu menu = allMenu.get(menuId);
				if(null!=menu){
					rs.put(menuId, menu);
				}
			}
		}
		//鍦ㄤ笂闈㈢殑鍩虹涓婃坊鍔犱笉闇�瑕佹巿鏉冨氨鏈夌殑鑿滃崟鍖呮嫭鍔熻兘椤�
		for(Map.Entry<String, TreeMenu> entry:allMenu.entrySet()){
			TreeMenu menu = entry.getValue();
			if("0".equals(menu.getNeedAuthFlag())){
				rs.put(entry.getKey(), menu);
			}
		}
		return rs;
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
			menu.setNeedAuthFlag(map.get("AUTH_FLAG"));
			menu.setIconCls(map.get("MENU_ICON"));
			menu.setId(map.get("MENU_ID"));
			menu.setText(map.get("MENU_NAME_"+"CH"));
			menu.setMenuFlag(map.get("MENU_TYPE"));
			menu.setUrl(map.get("MENU_URL"));
			menu.setParentMenuId(map.get("PARENT_MENU_ID"));
			rs.put(menu.getId(), menu);			
		}
		return rs;
	}

	@Override
	public TreeMenu getTreeMenuByGroupId(String groupId) {		
		return loadGroupIdTreeMenu(groupId);
	}

	@Override
	@Transactional (rollbackFor = Exception.class)
	public boolean updateGroupMenu(String groupId, List<String> menuIdList) {
		menuGroupDao.deleteByGroupId(groupId);
		menuGroupDao.insertList(groupId, menuIdList);
		return false;
	}
	

}
