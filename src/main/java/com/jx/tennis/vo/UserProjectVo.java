package com.jx.tennis.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserProjectVo implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -3L;

    
    private String id;
    private String openId;
    private String type;
    private String desc;
    private String result;
    private String title;


}