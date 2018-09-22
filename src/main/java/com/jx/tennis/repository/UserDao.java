package com.jx.tennis.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.jx.tennis.dto.CuUserDTO;
import com.jx.tennis.project.system.user.domain.User;


@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void insert(CuUserDTO userDTO){
		String sql = "insert into student(name, age) values ('tiantian', 11);";
		jdbcTemplate.execute(sql);
	}
	
	public User query(String loginName){
		String sql = "select * from sys_user where login_name='"+loginName+"';";
		User user = null;
		SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
		if (sqlRowSet!=null && sqlRowSet.next()) {
			try {
				user = new User();
				user.setUserName(sqlRowSet.getString("user_name"));
				user.setLoginName(sqlRowSet.getString("login_name"));
				user.setUserId(sqlRowSet.getLong("user_id"));
				user.setPassword(sqlRowSet.getString("password"));
			} catch (Exception e) {
				// TODO: handle exception
			}finally{
				if (sqlRowSet != null) {
					sqlRowSet = null;
	            }
			}
			
		}
		return user;
	}
	
	public List<User> queryAll(){
		String sql = "select * from sys_user;";
		List<User> users = new ArrayList<User>();
		SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
		
		try {
			while (sqlRowSet!=null && sqlRowSet.next()) {
				User user = new User();
				user.setUserName(sqlRowSet.getString("user_name"));
				user.setLoginName(sqlRowSet.getString("login_name"));
				user.setUserId(sqlRowSet.getLong("user_id"));
				user.setPassword(sqlRowSet.getString("password"));
				users.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if (sqlRowSet != null) {
				sqlRowSet = null;
            }
		}
		return users;
	}
}
