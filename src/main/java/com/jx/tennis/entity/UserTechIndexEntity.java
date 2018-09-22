package com.jx.tennis.entity;

import java.io.Serializable;

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
@Entity(name = "wx_tech_index")
@Builder
public class UserTechIndexEntity implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -2L;

    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String openId;
    private String action;
    private String action_ch;
    private long score;


}