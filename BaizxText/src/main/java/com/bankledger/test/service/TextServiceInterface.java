package com.bankledger.test.service;

import java.util.List;
import com.bankledger.test.bean.Text;

public interface TextServiceInterface {
	
	default List<Text> queryIndex() {
		return null;
	}

}
