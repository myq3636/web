package com.curriculum.service.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jx.tennis.SpringbootwebdemoApplication;
import com.jx.tennis.dto.CuUserDTO;
import com.jx.tennis.service.UserService;
import com.mxixm.fastboot.weixin.module.user.WxUser;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=SpringbootwebdemoApplication.class)
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	private WxUser wxUser;
	
	@Before
    public void before() throws Exception{
		wxUser = new WxUser();
		wxUser.setOpenId("12121");
		wxUser.setNickName("wang");
    }
	
    @After
    public void after() throws Exception {
    }
	
	@Test(expected = Exception.class)
	public void saveUser(){
		userService.saveUser(wxUser);				
	}
	
	@Test
	public void saveUserWithData(){
		userService.saveUser(wxUser);
	}
	
	@Test
	public void insertUser(){
		CuUserDTO userDTO = new CuUserDTO();
		userService.insertStudent(userDTO);
	}
}
