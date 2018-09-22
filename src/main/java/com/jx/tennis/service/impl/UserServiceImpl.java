package com.jx.tennis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.internal.compiler.flow.FinallyFlowContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jx.tennis.common.Constants;
import com.jx.tennis.dto.CuUserDTO;
import com.jx.tennis.entity.UserProjectEntity;
import com.jx.tennis.entity.UserTechIndexEntity;
import com.jx.tennis.entity.WxUserEntity;
import com.jx.tennis.project.system.user.domain.User;
import com.jx.tennis.repository.UserDao;
import com.jx.tennis.repository.UserProjectRepository;
import com.jx.tennis.repository.UserRepository;
import com.jx.tennis.repository.UserTechIndexRepository;
import com.jx.tennis.service.UserService;
import com.jx.tennis.util.DateUtils;
import com.jx.tennis.util.StringUtils;
import com.jx.tennis.vo.SystemUserInfoVo;
import com.jx.tennis.vo.UserProjectVo;
import com.jx.tennis.vo.UserTechIndexVo;
import com.jx.tennis.vo.WxUserVo;
import com.mxixm.fastboot.weixin.config.WxProperties.Server;
import com.mxixm.fastboot.weixin.module.user.WxUser;

@Service
public class UserServiceImpl implements UserService{
	
	private static final String T_FOREHAND = "tech_forehand";
	private static final String T_BACKEND = "tech_backend";
	private static final String T_TOPSPIN = "tech_topspin";
	private static final String T_SLICE = "tech_slice";
	private static final String T_SERVE = "tech_serve";
	private static final String T_RETURN = "tech_return";
	private static final String T_VOLLEY_B = "tech_volley_b";
	private static final String T_VOLLEY_F = "tech_volley_f";
	private static final String T_LOB = "tech_lob";
	private static final String T_DROP = "tech_drop";

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserProjectRepository userProjectRepository;
	@Autowired
	private UserTechIndexRepository userTechIndexRepository;
    @Autowired
    private UserDao userDao;
    

	
	@Override
	public void saveUser(WxUser wxUser) {
		Assert.notNull(wxUser, "wxUser must not be null");		
		WxUserEntity wxUserEntity = userRepository.findByOpenId(wxUser.getOpenId());
		if (wxUserEntity != null && wxUserEntity.getId()>0) {
			return;
		}
		wxUserEntity =WxUserEntity.builder()
				.headImgUrl(wxUser.getOpenId()+Constants.IMG_SUFFIX)
				.nickName(wxUser.getNickName())
				.openId(wxUser.getOpenId()).build();
		userRepository.save(wxUserEntity);
	}

	@Override
	public WxUserVo findUser(String openId) {
		WxUserEntity wxUserEntity = userRepository.findByOpenId(openId);
		if (wxUserEntity == null) {
			return null;
		}
		WxUserVo cuUserDTO =new WxUserVo();
		cuUserDTO.setHeadUrl("images/"+wxUserEntity.getHeadImgUrl());
		cuUserDTO.setName(wxUserEntity.getNickName()!=null?wxUserEntity.getNickName():wxUserEntity.getUsername());
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("label", "身高:");
		if (!StringUtils.isEmpty(wxUserEntity.getHeigth())) {
			map.put("value", wxUserEntity.getHeigth()+"cm");
		}else {
			map.put("value", "");
		}
		
		list.add(map);
		Map<String, String> gender = new HashMap<String, String>();
		gender.put("label", "性别:");
		String genderString = "保密";
		if (wxUserEntity.getGender() == 1) {
			genderString = "男";
		}else if (wxUserEntity.getGender() == 2) {
			genderString = "女";
		}
		gender.put("value", genderString);
		Map<String, String> action = new HashMap<String, String>();
		action.put("label", "持拍:");
		action.put("value", wxUserEntity.getAction());
		Map<String, String> backHand = new HashMap<String, String>();
		backHand.put("label", "反手:");
		backHand.put("value", wxUserEntity.getBackhand());
		Map<String, String> startDate = new HashMap<String, String>();
		startDate.put("label", "入队时间:");
		startDate.put("value", wxUserEntity.getStartTime());
		Map<String, String> level = new HashMap<String, String>();
		level.put("label", "技术水平:");
		level.put("value", wxUserEntity.getLevel());
		list.add(level);
		list.add(startDate);
		list.add(gender);
		list.add(backHand);
		list.add(action);
		cuUserDTO.setList(list);
		return cuUserDTO;
	}
	
	
	

	@Override
	public void insertStudent(CuUserDTO userDTO) {
		userDao.insert(userDTO);
		
	}

	@Override
	public User selectUserByLoginName(String loginName) {
		return userDao.query(loginName);
	}

	@Override
	public User selectUserByPhoneNumber(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUserProject(SystemUserInfoVo user, String openId) {
		UserProjectEntity userProjectEntity = userProjectRepository.findOne(user.getTd_id1());
		if (userProjectEntity == null || userProjectEntity.getId() == 0) {
			userProjectEntity = new UserProjectEntity();
			userProjectEntity.setProjectName("项目A");
			userProjectEntity.setOpenId(openId);
			userProjectEntity.setType("td");
		}
		userProjectEntity.setDescription(user.getTd_description1());
		userProjectEntity.setResult(user.getTd_result1());
		userProjectEntity.setStartTime(user.getDt_date());		
		userProjectRepository.save(userProjectEntity);
		
		UserProjectEntity userProjectEntity1 = userProjectRepository.findOne(user.getTd_id2());
		if (userProjectEntity1 == null || userProjectEntity1.getId() == 0) {
			userProjectEntity1 = new UserProjectEntity();
			userProjectEntity1.setProjectName("项目B");
			userProjectEntity1.setOpenId(openId);
			userProjectEntity1.setType("td");
		}
		userProjectEntity1.setDescription(user.getTd_description2());
		userProjectEntity1.setResult(user.getTd_result2());
		userProjectEntity1.setStartTime(user.getDt_date());
		userProjectRepository.save(userProjectEntity1);
		
		UserProjectEntity dt = userProjectRepository.findOne(user.getDt_id1());
		if (dt == null || dt.getId() == 0) {
			dt = new UserProjectEntity();
			dt.setProjectName("套路A");
			dt.setOpenId(openId);
			dt.setType("dt");
		}
		dt.setDescription(user.getDt_description1());
		dt.setResult(user.getDt_result1());
		dt.setStartTime(user.getDt_date());
		userProjectRepository.save(dt);
		
		UserProjectEntity dt2 = userProjectRepository.findOne(user.getDt_id2());
		if (dt2 == null || dt2.getId() == 0) {
			dt2 = new UserProjectEntity();
			dt2.setProjectName("套路B");
			dt2.setOpenId(openId);
			dt2.setType("dt");
		}
		dt2.setDescription(user.getDt_description2());
		dt2.setResult(user.getDt_result2());
		dt2.setStartTime(user.getDt_date());
		userProjectRepository.save(dt2);
		
		UserProjectEntity st = userProjectRepository.findOne(user.getSt_id1());
		if (st == null || st.getId() == 0) {
			st = new UserProjectEntity();
			st.setProjectName("套路A");
			st.setOpenId(openId);
			st.setType("st");
		}
		st.setDescription(user.getSt_description1());
		st.setResult(user.getSt_result1());
		st.setStartTime(user.getDt_date());
		userProjectRepository.save(st);
		
		UserProjectEntity st2 = userProjectRepository.findOne(user.getSt_id2());
		if (st2 == null || st2.getId() == 0) {
			st2 = new UserProjectEntity();
			st2.setProjectName("套路B");
			st2.setOpenId(openId);
			st2.setType("st");
		}
		st2.setDescription(user.getSt_description2());
		st2.setResult(user.getSt_result2());
		st2.setStartTime(user.getDt_date());
		userProjectRepository.save(st2);
		
		UserProjectEntity pc = userProjectRepository.findOne(user.getPc_id1());
		if (pc == null || pc.getId() == 0) {
			pc = new UserProjectEntity();
			pc.setProjectName("项目A");
			pc.setOpenId(openId);
			pc.setType("pc");
		}
		pc.setDescription(user.getPc_description1());
		pc.setResult(user.getPc_result1());
		pc.setStartTime(user.getDt_date());
		userProjectRepository.save(pc);
		
		UserProjectEntity pc2 = userProjectRepository.findOne(user.getPc_id2());
		if (pc2 == null || pc2.getId() == 0) {
			pc2 = new UserProjectEntity();
			pc2.setProjectName("项目B");
			pc2.setOpenId(openId);
			pc2.setType("pc");
		}
		pc2.setDescription(user.getPc_description2());
		pc2.setResult(user.getPc_result2());
		pc2.setStartTime(user.getDt_date());
		userProjectRepository.save(pc2);
		
	}

	@Override
	public Page<WxUserEntity> selectUserList(User user,Pageable pageable) {
		// TODO Auto-generated method stub
		return userRepository.findAll(pageable);
	}

	@Override
	public String insertUser(SystemUserInfoVo user) {
		WxUserEntity wxUserEntity = userRepository.findByNickName(user.getNickName());
		wxUserEntity.setAction(user.getAction());
		wxUserEntity.setBackhand(user.getBackhand());
		wxUserEntity.setGender(user.getGender());
		wxUserEntity.setHeigth(user.getHeigth());
		wxUserEntity.setLevel(user.getLevel());
		wxUserEntity.setStartTime(user.getStartTime());
		wxUserEntity.setUsername(user.getUsername());
		userRepository.save(wxUserEntity);
		UserProjectEntity userProjectEntity = new UserProjectEntity();
		userProjectEntity.setDescription(user.getTd_description1());
		userProjectEntity.setOpenId(wxUserEntity.getOpenId());
		userProjectEntity.setProjectName("项目A");
		userProjectEntity.setResult(user.getTd_result1());
		userProjectEntity.setStartTime(user.getDt_date());
		userProjectEntity.setType("td");
		userProjectRepository.save(userProjectEntity);
		UserProjectEntity userProjectEntity1 = new UserProjectEntity();
		userProjectEntity1.setDescription(user.getTd_description2());
		userProjectEntity1.setOpenId(wxUserEntity.getOpenId());
		userProjectEntity1.setProjectName("项目B");
		userProjectEntity1.setResult(user.getTd_result2());
		userProjectEntity1.setStartTime(user.getDt_date());
		userProjectEntity1.setType("td");
		userProjectRepository.save(userProjectEntity1);
		UserProjectEntity dt = new UserProjectEntity();
		dt.setDescription(user.getDt_description1());
		dt.setOpenId(wxUserEntity.getOpenId());
		dt.setProjectName("套路A");
		dt.setResult(user.getDt_result1());
		dt.setStartTime(user.getDt_date());
		dt.setType("dt");
		userProjectRepository.save(dt);
		UserProjectEntity dt2 = new UserProjectEntity();
		dt2.setDescription(user.getDt_description2());
		dt2.setOpenId(wxUserEntity.getOpenId());
		dt2.setProjectName("套路B");
		dt2.setResult(user.getDt_result2());
		dt2.setStartTime(user.getDt_date());
		dt2.setType("dt");
		userProjectRepository.save(dt2);
		UserProjectEntity st = new UserProjectEntity();
		st.setDescription(user.getSt_description1());
		st.setOpenId(wxUserEntity.getOpenId());
		st.setProjectName("套路A");
		st.setResult(user.getSt_result1());
		st.setStartTime(user.getDt_date());
		st.setType("st");
		userProjectRepository.save(st);
		UserProjectEntity st2 = new UserProjectEntity();
		st2.setDescription(user.getSt_description2());
		st2.setOpenId(wxUserEntity.getOpenId());
		st2.setProjectName("套路B");
		st2.setResult(user.getSt_result2());
		st2.setStartTime(user.getDt_date());
		st2.setType("st");
		userProjectRepository.save(st2);
		
		UserProjectEntity pc = new UserProjectEntity();
		pc.setDescription(user.getPc_description1());
		pc.setOpenId(wxUserEntity.getOpenId());
		pc.setProjectName("项目A");
		pc.setResult(user.getPc_result1());
		pc.setStartTime(user.getDt_date());
		pc.setType("pc");
		userProjectRepository.save(pc);
		UserProjectEntity pc2 = new UserProjectEntity();
		pc2.setDescription(user.getPc_description2());
		pc2.setOpenId(wxUserEntity.getOpenId());
		pc2.setProjectName("项目B");
		pc2.setResult(user.getPc_result2());
		pc2.setStartTime(user.getDt_date());
		pc2.setType("pc");
		userProjectRepository.save(pc2);
		List<UserTechIndexEntity> userTechIndexEntities = new ArrayList<UserTechIndexEntity>();
		UserTechIndexEntity backend = new UserTechIndexEntity();
		backend.setAction(T_BACKEND);
		backend.setAction_ch("反手");
		backend.setOpenId(wxUserEntity.getOpenId());
		backend.setScore(Long.parseLong(user.getTech_backend()));		
		userTechIndexEntities.add(backend);
		UserTechIndexEntity drop = new UserTechIndexEntity();
		drop.setAction(T_DROP);
		drop.setAction_ch("放小球");
		drop.setOpenId(wxUserEntity.getOpenId());
		drop.setScore(Long.parseLong(user.getTech_drop()));		
		userTechIndexEntities.add(drop);
		UserTechIndexEntity forehand = new UserTechIndexEntity();
		forehand.setAction(T_FOREHAND);
		forehand.setAction_ch("正手");
		forehand.setOpenId(wxUserEntity.getOpenId());
		forehand.setScore(Long.parseLong(user.getTech_forehand()));		
		userTechIndexEntities.add(forehand);
		UserTechIndexEntity lob = new UserTechIndexEntity();
		lob.setAction(T_LOB);
		lob.setAction_ch("挑高球");
		lob.setOpenId(wxUserEntity.getOpenId());
		lob.setScore(Long.parseLong(user.getTech_lob()));		
		userTechIndexEntities.add(lob);
		UserTechIndexEntity returnEntity = new UserTechIndexEntity();
		returnEntity.setAction(T_RETURN);
		returnEntity.setAction_ch("接发");
		returnEntity.setOpenId(wxUserEntity.getOpenId());
		returnEntity.setScore(Long.parseLong(user.getTech_return()));		
		userTechIndexEntities.add(returnEntity);
		UserTechIndexEntity serve = new UserTechIndexEntity();
		serve.setAction(T_SERVE);
		serve.setAction_ch("发球");
		serve.setOpenId(wxUserEntity.getOpenId());
		serve.setScore(Long.parseLong(user.getTech_serve()));		
		userTechIndexEntities.add(serve);
		UserTechIndexEntity slice = new UserTechIndexEntity();
		slice.setAction(T_SLICE);
		slice.setAction_ch("反削");
		slice.setOpenId(wxUserEntity.getOpenId());
		slice.setScore(Long.parseLong(user.getTech_slice()));		
		userTechIndexEntities.add(slice);
		UserTechIndexEntity topspin = new UserTechIndexEntity();
		topspin.setAction(T_TOPSPIN);
		topspin.setAction_ch("上旋");
		topspin.setOpenId(wxUserEntity.getOpenId());
		topspin.setScore(Long.parseLong(user.getTech_topspin()));		
		userTechIndexEntities.add(topspin);
		UserTechIndexEntity volleyB = new UserTechIndexEntity();
		volleyB.setAction(T_VOLLEY_B);
		volleyB.setAction_ch("正截击");
		volleyB.setOpenId(wxUserEntity.getOpenId());
		volleyB.setScore(Long.parseLong(user.getTech_volley_b()));		
		userTechIndexEntities.add(volleyB);
		UserTechIndexEntity volleyF = new UserTechIndexEntity();
		volleyF.setAction(T_VOLLEY_F);
		volleyF.setAction_ch("反截击");
		volleyF.setOpenId(wxUserEntity.getOpenId());
		volleyF.setScore(Long.parseLong(user.getTech_volley_f()));		
		userTechIndexEntities.add(volleyF);
		userTechIndexRepository.save(userTechIndexEntities);
		return Constants.SUCCESS;
	}

	@Override
	public String updateUserBase(SystemUserInfoVo user) {
		WxUserEntity wxUserEntity = userRepository.findByNickName(user.getNickName());
		wxUserEntity.setAction(user.getAction());
		wxUserEntity.setBackhand(user.getBackhand());
		wxUserEntity.setGender(user.getGender());
		wxUserEntity.setHeigth(user.getHeigth());
		wxUserEntity.setLevel(user.getLevel());
		wxUserEntity.setStartTime(user.getStartTime());
		wxUserEntity.setUsername(user.getUsername());
		userRepository.save(wxUserEntity);
		List<UserTechIndexEntity> userTechIndexEntities = userTechIndexRepository.findByOpenId(wxUserEntity.getOpenId());
		if (userTechIndexEntities == null || userTechIndexEntities.isEmpty()) {
			userTechIndexEntities = new ArrayList<UserTechIndexEntity>();
			UserTechIndexEntity forehand = new UserTechIndexEntity();
			forehand.setAction(T_FOREHAND);
			forehand.setAction_ch("正手");
			forehand.setOpenId(wxUserEntity.getOpenId());
			forehand.setScore(Long.parseLong(user.getTech_forehand()));		
			userTechIndexEntities.add(forehand);
			
			UserTechIndexEntity backend = new UserTechIndexEntity();
			backend.setAction(T_BACKEND);
			backend.setAction_ch("反手");
			backend.setOpenId(wxUserEntity.getOpenId());
			backend.setScore(Long.parseLong(user.getTech_backend()));		
			userTechIndexEntities.add(backend);
			
			UserTechIndexEntity topspin = new UserTechIndexEntity();
			topspin.setAction(T_TOPSPIN);
			topspin.setAction_ch("上旋");
			topspin.setOpenId(wxUserEntity.getOpenId());
			topspin.setScore(Long.parseLong(user.getTech_topspin()));		
			userTechIndexEntities.add(topspin);
			
			UserTechIndexEntity slice = new UserTechIndexEntity();
			slice.setAction(T_SLICE);
			slice.setAction_ch("反削");
			slice.setOpenId(wxUserEntity.getOpenId());
			slice.setScore(Long.parseLong(user.getTech_slice()));		
			userTechIndexEntities.add(slice);
			
			UserTechIndexEntity serve = new UserTechIndexEntity();
			serve.setAction(T_SERVE);
			serve.setAction_ch("发球");
			serve.setOpenId(wxUserEntity.getOpenId());
			serve.setScore(Long.parseLong(user.getTech_serve()));		
			userTechIndexEntities.add(serve);
			
			UserTechIndexEntity returnEntity = new UserTechIndexEntity();
			returnEntity.setAction(T_RETURN);
			returnEntity.setAction_ch("接发");
			returnEntity.setOpenId(wxUserEntity.getOpenId());
			returnEntity.setScore(Long.parseLong(user.getTech_return()));		
			userTechIndexEntities.add(returnEntity);
			
			UserTechIndexEntity volleyF = new UserTechIndexEntity();
			volleyF.setAction(T_VOLLEY_F);
			volleyF.setAction_ch("正截击");
			volleyF.setOpenId(wxUserEntity.getOpenId());
			volleyF.setScore(Long.parseLong(user.getTech_volley_f()));		
			userTechIndexEntities.add(volleyF);
			
			
			UserTechIndexEntity volleyB = new UserTechIndexEntity();
			volleyB.setAction(T_VOLLEY_B);
			volleyB.setAction_ch("反截击");
			volleyB.setOpenId(wxUserEntity.getOpenId());
			volleyB.setScore(Long.parseLong(user.getTech_volley_b()));		
			userTechIndexEntities.add(volleyB);
			
			UserTechIndexEntity drop = new UserTechIndexEntity();
			drop.setAction(T_DROP);
			drop.setAction_ch("高压");
			drop.setOpenId(wxUserEntity.getOpenId());
			drop.setScore(Long.parseLong(user.getTech_drop()));		
			userTechIndexEntities.add(drop);
						
			UserTechIndexEntity lob = new UserTechIndexEntity();
			lob.setAction(T_LOB);
			lob.setAction_ch("挑高球");
			lob.setOpenId(wxUserEntity.getOpenId());
			lob.setScore(Long.parseLong(user.getTech_lob()));		
			userTechIndexEntities.add(lob);
			userTechIndexRepository.save(userTechIndexEntities);			
			return Constants.SUCCESS;
		}
		List<UserTechIndexEntity> needSavEntities = new ArrayList<UserTechIndexEntity>();
		for (UserTechIndexEntity userTechIndexEntity : userTechIndexEntities) {
			if (T_FOREHAND.equals(userTechIndexEntity.getAction())) {
				Long value = 0L;
			    try {
			    	value = Long.parseLong(user.getTech_forehand());
				} catch (Exception e) {
					value = 0L;
				}
				if (userTechIndexEntity.getScore() != value) {
					userTechIndexEntity.setScore(value);
					needSavEntities.add(userTechIndexEntity);
				}
			}else if (T_BACKEND.equals(userTechIndexEntity.getAction())) {
				Long value = 0L;
			    try {
			    	value = Long.parseLong(user.getTech_backend());
				} catch (Exception e) {
					value = 0L;
				}
				
				if (userTechIndexEntity.getScore() != value) {
					userTechIndexEntity.setScore(value);
					needSavEntities.add(userTechIndexEntity);
				}
			}else if (T_TOPSPIN.equals(userTechIndexEntity.getAction())) {
				Long value = 0L;
			    try {
			    	value = Long.parseLong(user.getTech_topspin());
				} catch (Exception e) {
					value = 0L;
				}
				if (userTechIndexEntity.getScore() != value) {
					userTechIndexEntity.setScore(value);
					needSavEntities.add(userTechIndexEntity);
				}
			}else if (T_SLICE.equals(userTechIndexEntity.getAction())) {
				Long value = 0L;
			    try {
			    	value = Long.parseLong(user.getTech_slice());
				} catch (Exception e) {
					value = 0L;
				}
				if (userTechIndexEntity.getScore() != value) {
					userTechIndexEntity.setScore(value);
					needSavEntities.add(userTechIndexEntity);
				}
			}else if (T_SERVE.equals(userTechIndexEntity.getAction())) {
				Long value = 0L;
			    try {
			    	value = Long.parseLong(user.getTech_serve());
				} catch (Exception e) {
					value = 0L;
				}
				if (userTechIndexEntity.getScore() != value) {
					userTechIndexEntity.setScore(value);
					needSavEntities.add(userTechIndexEntity);
				}
			}else if (T_RETURN.equals(userTechIndexEntity.getAction())) {
				Long value = 0L;
			    try {
			    	value = Long.parseLong(user.getTech_return());
				} catch (Exception e) {
					value = 0L;
				}
				if (userTechIndexEntity.getScore() != value) {
					userTechIndexEntity.setScore(value);
					needSavEntities.add(userTechIndexEntity);
				}
			}else if (T_VOLLEY_F.equals(userTechIndexEntity.getAction())) {
				Long value = 0L;
			    try {
			    	value = Long.parseLong(user.getTech_volley_f());
				} catch (Exception e) {
					value = 0L;
				}
				if (userTechIndexEntity.getScore() != value) {
					userTechIndexEntity.setScore(value);
					needSavEntities.add(userTechIndexEntity);
				}
			}else if (T_VOLLEY_B.equals(userTechIndexEntity.getAction())) {
				Long value = 0L;
			    try {
			    	value = Long.parseLong(user.getTech_volley_b());
				} catch (Exception e) {
					value = 0L;
				}
				if (userTechIndexEntity.getScore() != value) {
					userTechIndexEntity.setScore(value);
					needSavEntities.add(userTechIndexEntity);
				}
			}else if (T_LOB.equals(userTechIndexEntity.getAction())) {
				if (userTechIndexEntity.getScore() != Long.parseLong(user.getTech_lob())) {
					Long value = 0L;
				    try {
				    	value = Long.parseLong(user.getTech_lob());
					} catch (Exception e) {
						value = 0L;
					}
				    userTechIndexEntity.setScore(value);
					needSavEntities.add(userTechIndexEntity);
				}
			}else if (T_DROP.equals(userTechIndexEntity.getAction())) {
				Long value = 0L;
			    try {
			    	value = Long.parseLong(user.getTech_drop());
				} catch (Exception e) {
					value = 0L;
				}
				if (userTechIndexEntity.getScore() != value) {
					userTechIndexEntity.setScore(value);
					needSavEntities.add(userTechIndexEntity);
				}
			}
		}
		if (!needSavEntities.isEmpty()) {
			userTechIndexRepository.save(needSavEntities);
		}		
		return Constants.SUCCESS;
	}

	@Override
	public SystemUserInfoVo selectUserById(String openId) {
		SystemUserInfoVo userInfoVo = new SystemUserInfoVo();
		WxUserEntity userEntity = userRepository.findByOpenId(openId);
		if (userEntity !=null) {
			userInfoVo.setAction(userEntity.getAction()==null?"":userEntity.getAction());
			userInfoVo.setBackhand(userEntity.getBackhand()==null?"":userEntity.getAction());
			userInfoVo.setGender(userEntity.getGender()==0?0:userEntity.getGender());
			userInfoVo.setHeigth(userEntity.getHeigth()==null?"":userEntity.getHeigth());
			userInfoVo.setLevel(userEntity.getLevel()==null?"":userEntity.getLevel());
			userInfoVo.setNickName(userEntity.getNickName()==null?"":userEntity.getNickName());
			userInfoVo.setUsername(userEntity.getUsername()==null?"":userEntity.getUsername());
			userInfoVo.setStartTime(userEntity.getStartTime()==null?"":userEntity.getStartTime());
		}
		List<UserTechIndexEntity> techIndexEntities = userTechIndexRepository.findByOpenId(openId);
		for (UserTechIndexEntity userTechIndexEntity : techIndexEntities) {
			if (T_BACKEND.equals(userTechIndexEntity.getAction())) {
				userInfoVo.setTech_backend(""+userTechIndexEntity.getScore());
			}else if (T_DROP.equals(userTechIndexEntity.getAction())) {
				userInfoVo.setTech_drop(""+userTechIndexEntity.getScore());
			}else if (T_FOREHAND.equals(userTechIndexEntity.getAction())) {
				userInfoVo.setTech_forehand(""+userTechIndexEntity.getScore());
			}else if (T_LOB.equals(userTechIndexEntity.getAction())) {
				userInfoVo.setTech_lob(""+userTechIndexEntity.getScore());
			}else if (T_RETURN.equals(userTechIndexEntity.getAction())) {
				userInfoVo.setTech_return(""+userTechIndexEntity.getScore());
			}else if (T_SERVE.equals(userTechIndexEntity.getAction())) {
				userInfoVo.setTech_serve(""+userTechIndexEntity.getScore());
			}else if (T_SLICE.equals(userTechIndexEntity.getAction())) {
				userInfoVo.setTech_slice(""+userTechIndexEntity.getScore());
			}else if (T_TOPSPIN.equals(userTechIndexEntity.getAction())) {
				userInfoVo.setTech_topspin(""+userTechIndexEntity.getScore());
			}else if (T_VOLLEY_B.equals(userTechIndexEntity.getAction())) {
				userInfoVo.setTech_volley_b(""+userTechIndexEntity.getScore());
			}else if (T_VOLLEY_F.equals(userTechIndexEntity.getAction())) {
				userInfoVo.setTech_volley_f(""+userTechIndexEntity.getScore());
			}
		}
		
		return userInfoVo;
	}

	@Override
	public int resetUserPwd(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteUserByIds(String ids) {
		String[] openIds = ids.split(",");
		if (openIds!=null) {
			for (String openId : openIds) {
				userRepository.deleteByOpenId(openId);
				userTechIndexRepository.deleteByOpenId(openId);
				userProjectRepository.deleteByOpenId(openId);
			}
			return 1;
		}
		return 0;
	}

	@Override
	public String checkLoginNameUnique(String loginName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<UserProjectVo>> findProjectRecord(String openId, String projectDate) {
		List<UserProjectEntity> userProjectEntitys = userProjectRepository.findByOpenIdAndStartTime(openId, projectDate);
		if (userProjectEntitys == null || userProjectEntitys.isEmpty()) {
			String preMonthDate = DateUtils.getPreMonthString(projectDate, "GMT+8");
			userProjectEntitys = userProjectRepository.findByOpenIdAndStartTime(openId, preMonthDate);
			if (userProjectEntitys == null || userProjectEntitys.isEmpty()) {
				String nextMonthDate = DateUtils.getNextMonthString(projectDate, "GMT+8");
				userProjectEntitys = userProjectRepository.findByOpenIdAndStartTime(openId, nextMonthDate);				
			}
		}
		Map<String, List<UserProjectVo>> map = new HashMap<String, List<UserProjectVo>>();
		if (userProjectEntitys != null && !userProjectEntitys.isEmpty()) {
			for (UserProjectEntity userProjectEntity : userProjectEntitys) {
				List<UserProjectVo> list = map.get(userProjectEntity.getType());
				if (list == null) {
					list = new ArrayList<UserProjectVo>();
					map.put(userProjectEntity.getType(), list);
				}
				UserProjectVo userProjectVo = new UserProjectVo();
				userProjectVo.setDesc(userProjectEntity.getDescription());
				userProjectVo.setId(""+userProjectEntity.getId());
				userProjectVo.setResult(userProjectEntity.getResult());
				userProjectVo.setTitle(userProjectEntity.getProjectName());
				list.add(userProjectVo);
			}
		}
		
		return map;
	}

	@Override
	public UserTechIndexVo findTechIndex(String openId, String name) {
		UserTechIndexVo userTechIndexVo = new UserTechIndexVo();
		List<UserTechIndexEntity> userTechIndexEntitys = userTechIndexRepository.findByOpenId(openId);
		if (userTechIndexEntitys !=null && !userTechIndexEntitys.isEmpty()) {
			for (UserTechIndexEntity userTechIndexEntity : userTechIndexEntitys) {
				List<Map<String, String>> infos = userTechIndexVo.getTechInfo();
				if(infos == null){
					infos = new ArrayList<Map<String,String>>();
					userTechIndexVo.setTechInfo(infos);
				}
				Map<String, String> info = new HashMap<String, String>();
				info.put("name", name);
				info.put("动作", userTechIndexEntity.getAction_ch());
				info.put("数值", ""+userTechIndexEntity.getScore());
				infos.add(info);
			}
		}		 
		return userTechIndexVo;
	}

	@Override
	public List<String> findNickNames() {
		List<WxUserEntity> wxUserEntities = userRepository.findAll();
		List<String> nickNames = new ArrayList<String>();
		if (wxUserEntities!= null && !wxUserEntities.isEmpty()) {
			for (WxUserEntity wxUserEntity : wxUserEntities) {
				nickNames.add(wxUserEntity.getNickName());
			}			
		}
		return nickNames;
	}

	@Override
	public SystemUserInfoVo selectUserProjectById(String openId,
			String projectDate) {
		List<UserProjectEntity> userProjectEntitys = userProjectRepository.findByOpenIdAndStartTime(openId, projectDate);
		SystemUserInfoVo vo = new SystemUserInfoVo();
		if (userProjectEntitys != null && !userProjectEntitys.isEmpty()) {
			int td = 0;
			int dt = 0;
			int st = 0;
			int pc = 0;
			for (UserProjectEntity userProjectEntity : userProjectEntitys) {
				if ("td".equalsIgnoreCase(userProjectEntity.getType())) {
					if (td == 0) {
						vo.setTd_description1(userProjectEntity.getDescription());
						vo.setTd_result1(userProjectEntity.getResult());
						vo.setTd_id1(userProjectEntity.getId());
						td++;
					}else if (td == 1) {
						vo.setTd_description2(userProjectEntity.getDescription());
						vo.setTd_result2(userProjectEntity.getResult());
						vo.setTd_id2(userProjectEntity.getId());
					}
				}
				if ("dt".equalsIgnoreCase(userProjectEntity.getType())) {
					if (dt == 0) {
						vo.setDt_description1(userProjectEntity.getDescription());
						vo.setDt_result1(userProjectEntity.getResult());
						vo.setDt_id1(userProjectEntity.getId());
						dt++;
					}else if (dt == 1) {
						vo.setDt_description2(userProjectEntity.getDescription());
						vo.setDt_result2(userProjectEntity.getResult());
						vo.setDt_id2(userProjectEntity.getId());
					}
				}
				if ("pc".equalsIgnoreCase(userProjectEntity.getType())) {
					if (pc == 0) {
						vo.setPc_description1(userProjectEntity.getDescription());
						vo.setPc_result1(userProjectEntity.getResult());
						vo.setPc_id1(userProjectEntity.getId());
						pc++;
					}else if (pc == 1) {
						vo.setPc_description2(userProjectEntity.getDescription());
						vo.setPc_result2(userProjectEntity.getResult());
						vo.setPc_id2(userProjectEntity.getId());
					}
				}
				if ("st".equalsIgnoreCase(userProjectEntity.getType())) {
					if (st == 0) {
						vo.setSt_description1(userProjectEntity.getDescription());
						vo.setSt_result1(userProjectEntity.getResult());
						vo.setSt_id1(userProjectEntity.getId());
						st++;
					}else if (st == 1) {
						vo.setSt_description2(userProjectEntity.getDescription());
						vo.setSt_result2(userProjectEntity.getResult());
						vo.setSt_id2(userProjectEntity.getId());
					}
				}
				
			}
		}
		return vo;
	}

}
