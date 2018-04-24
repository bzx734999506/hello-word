package com.bankledger.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.bankledger.test.bean.Text;

@Repository
public interface DBTextDao {
	
	@Select("select * from text")
	public List<Text> queryIndex();
	
}
