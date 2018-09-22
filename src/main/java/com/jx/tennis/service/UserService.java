package com.jx.tennis.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jx.tennis.common.Constants;
import com.jx.tennis.dto.CuUserDTO;
import com.jx.tennis.entity.WxUserEntity;
import com.jx.tennis.project.system.user.domain.User;
import com.jx.tennis.vo.SystemUserInfoVo;
import com.jx.tennis.vo.UserProjectVo;
import com.jx.tennis.vo.UserTechIndexVo;
import com.jx.tennis.vo.WxUserVo;
import com.mxixm.fastboot.weixin.module.user.WxUser;




/**
 * 学生信息接口 . <br>
 * 
 */
public interface UserService {

    
	public Page<WxUserEntity> selectUserList(User user,Pageable pageable);
	public String insertUser(SystemUserInfoVo user);
	
	public void insertStudent(CuUserDTO userDTO);
	public User selectUserByLoginName(String username);
	public User selectUserByPhoneNumber(String username);
	public void updateUserProject(SystemUserInfoVo user, String openId);	
	public String updateUserBase(SystemUserInfoVo user);
	public SystemUserInfoVo selectUserById(String openId);
	public SystemUserInfoVo selectUserProjectById(String openId,String createDate);
	public int resetUserPwd(User user);
	public int deleteUserByIds(String ids);
	public String checkLoginNameUnique(String loginName);
	public List<String> findNickNames();
	
	/**
	 * tennis user interface
	 */
	public void saveUser(WxUser wxUser);
    public WxUserVo findUser(String openId);
    public Map<String, List<UserProjectVo>> findProjectRecord(String openId, String projectDate);
    public UserTechIndexVo findTechIndex(String openId, String name);
    
    
}
