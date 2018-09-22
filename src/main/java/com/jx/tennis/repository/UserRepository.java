package com.jx.tennis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.jx.tennis.entity.WxUserEntity;


public interface UserRepository extends CrudRepository<WxUserEntity, Integer>, JpaRepository<WxUserEntity, Integer> {
 
	public WxUserEntity findByOpenId(String openId);
	public WxUserEntity findByNickName(String nickName);
	
	@Query(value = "delete from wx_user where open_id= ?1", nativeQuery = true)
	@Modifying
	@Transactional
	void deleteByOpenId(String openId);
}
