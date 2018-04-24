package com.bankledger.framework.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankledger.framework.bean.TreeGroup;
import com.bankledger.framework.dao.DBAccountGroupDao;
import com.bankledger.framework.dao.DBGroupDao;
import com.bankledger.framework.dao.DBMenuGroupDao;

@Service("GroupService")
public class GroupService implements GroupServiceInterface {
	
	@Autowired
	DBGroupDao groupDao;
	
	@Autowired
	DBAccountGroupDao accountGroupDao;
	
	@Autowired
	DBMenuGroupDao menuGroupDao;

	/**
	 * 鎵惧埌groupId 瀵瑰簲鐨則reeGroup 鍖呭惈浜嗚嚜宸辩埗浜插拰鎵�鏈夌殑瀛╁瓙
	 */
	@Override
	public TreeGroup queryGroup(String groupId) {
		List<Map<String,String>> allGroup =groupDao.queryAllGroup();	
		return buildTreeGroupWithParentAndChildrensMap(allGroup).get(groupId);
	}
	
	
	/**
	 * 杩斿洖涓�涓互groupId 涓篕ey TreeGroup 瀵硅薄涓哄�煎緱map缁撴瀯
	 * TreeGroup涓瘡涓璞￠兘鍖呭惈浜嗗畬鏁寸殑鑷繁鐨勭埗浜插拰鎵�鏈夌殑瀛╁瓙
	 * @param groupList
	 * @return
	 */
	private Map<String,TreeGroup> buildTreeGroupWithParentAndChildrensMap(List<Map<String,String>> groupList){
		List<TreeGroup> treeGroupList = new ArrayList<TreeGroup>();
		for(Map<String,String> map:groupList){
			treeGroupList.add(buildTreeGroup(map));
		}
		return TreeGroup.buildTreeGroup(treeGroupList);
	}
	
	/**
	 * 灏咲B涓幏寰楃殑Map瀵硅薄杞崲涓篢reeGroup瀵硅薄
	 * @param map
	 * @return
	 */
	private TreeGroup buildTreeGroup(Map<String,String> map){
		TreeGroup treeGroup = new TreeGroup();
		treeGroup.setId(map.get("GROUP_ID"));
		treeGroup.setText(map.get("GROUP_NAME"));
		treeGroup.setFatherGroupId(map.get("GROUP_FATHER"));
		treeGroup.setIconCls(map.get("ICON_CLS"));
		return treeGroup;
	}
	
	/**
	 * 鏂板缁�
	 * @param data
	 * @return
	 */
	public boolean addGroup(Map<String,String> data){
		return groupDao.insert(data);
	}
	
	/**
	 * 鏇存柊缁�
	 */
	public boolean updateGroup(Map<String,String> data){
		return groupDao.update(data);
	}
	
	/**
	 * 鍒犻櫎缁� 鍒犻櫎姣忎竴涓猤roupIdList 閲岄潰鐨勫厓绱�
	 * 濡傛灉杩欎釜缁勫搴斾簡鏈夎处鎴凤紝閭ｄ箞杩欑粍鍏崇郴涔熶竴骞跺垹闄�
	 * 濡傛灉杩欎釜缁勫搴斾簡鏈夎彍鍗曪紝閭ｄ箞杩欑粍鍏崇郴涔熶竴骞跺垹闄�
	 */	
	@Transactional (rollbackFor = Exception.class)
	public boolean deleteGroupList(List<String> groupIdList){
		groupDao.deleteList(groupIdList);
		accountGroupDao.deleteListGroup(groupIdList);
		menuGroupDao.deleteListByGroupIdList(groupIdList);
		return true;
	}

}
