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
@Entity(name = "wx_project_record")
@Builder
public class UserProjectEntity implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -3555555555982L;

    

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String openId;
    /**
     * 项目类型：
     * td：技术目标 technical development target
     * st：单打战术 single tactics
     * dt：双打战术 double tactics
     * pc：比赛心理 psychologic
     */
    private String type;
    private String description;
    private String result;
    private String startTime;
    private String projectName;


}