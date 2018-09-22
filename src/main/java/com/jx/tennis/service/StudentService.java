package com.jx.tennis.service;

import java.util.List;

import com.jx.tennis.entity.StudentEntity;


/**
 * 学生信息接口 . <br>
 * 
 */
public interface StudentService {

    /**
     * 添加学生
     * 
     * @param student
     * @return
     */
    int addStudent(StudentEntity student);

    /**
     * 修改学生信息
     * 
     * @param student
     * @return
     */
    int updateStudent(StudentEntity student);

    /**
     * 根据id删除学生信息
     * 
     * @param id
     * @return
     */
    boolean deleteStudentById(Integer id);

    /**
     * 根据id查询学生信息
     * 
     * @param id
     * @return StudentEntity
     */
    StudentEntity findStudentById(Integer id);

    /**
     * 根据年龄查询学生
     * 
     * @param age
     * @return List
     */
    List<StudentEntity> findStudentByAge(Integer age);

    /**
     * 根据年龄查询学生 <br>
     * 原生sql实现
     * 
     * @param age
     * @return
     */
    List<StudentEntity> getStudentByAge(Integer age);

    /**
     * 查询所有学生
     * 
     * @return List
     */
    List<StudentEntity> findAllStudent();

}
