package com.jx.tennis.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "wx_user")
@Builder
public class WxUserEntity implements Serializable {


    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -3967265553147263982L;

    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String openId;
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
    

}