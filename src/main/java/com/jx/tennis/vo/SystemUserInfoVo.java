package com.jx.tennis.vo;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemUserInfoVo implements Serializable {


    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -39672655577777777L;

    
    private String nickName;
    private String headImgUrl;
    private String username;
    //身高
    private String heigth;
    //性别： 1=男；2=女
    private int gender;
    //持拍L/R
    private String action;
    //反手
    private String backhand;
    //技术水平
    private String level;
    //注册时间
    private String startTime;
    
    //项目时间
    private String dt_date;
    
    private String td_description1;
    private String td_result1;
    private int td_id1;
    
    private String td_description2;
    private String td_result2;
    private int td_id2;
    
    private String dt_description1;
    private String dt_result1;
    private int dt_id1;
    
    private String dt_description2;
    private String dt_result2;
    private int dt_id2;
    
    private String st_description1;
    private String st_result1;
    private int st_id1;
    
    private String st_description2;
    private String st_result2;
    private int st_id2;
    
    private String pc_description1;
    private String pc_result1;
    private int pc_id1;
    
    private String pc_description2;
    private String pc_result2;
    private int pc_id2;
    
    private String tech_forehand;
    private String tech_backend;
    private String tech_topspin;
    private String tech_slice;
    private String tech_serve;
    private String tech_return;
    private String tech_volley_b;
    private String tech_volley_f;
    private String tech_lob;
    private String tech_drop;

}