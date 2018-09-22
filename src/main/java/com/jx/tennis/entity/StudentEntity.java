package com.jx.tennis.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生实体类 . <br>
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "student")
public class StudentEntity implements Serializable {

    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -3967265553147264980L;

    /**
     * 学生id
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 学生年龄
     */
    private Integer age;

}