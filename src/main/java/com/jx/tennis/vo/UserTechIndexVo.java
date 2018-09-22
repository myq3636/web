package com.jx.tennis.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class UserTechIndexVo implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -5L;

    private List<Map<String, String>> techInfo;

	public List<Map<String, String>> getTechInfo() {
		return techInfo;
	}

	public void setTechInfo(List<Map<String, String>> techInfo) {
		this.techInfo = techInfo;
	}


    
}