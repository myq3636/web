package com.jx.tennis.dto;

import lombok.Data;

import com.jx.tennis.util.Career;

@Data
public class CuUserDTO {

    private String openId;
    private String nickName;
    private Career type; 
    
}
