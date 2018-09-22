package com.jx.tennis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.jx.tennis.entity.UserTechIndexEntity;


public interface UserTechIndexRepository extends CrudRepository<UserTechIndexEntity, Integer>, JpaRepository<UserTechIndexEntity, Integer> {
 
	public List<UserTechIndexEntity> findByOpenId(String openId);
	@Query(value = "delete from wx_tech_index where open_id= ?1", nativeQuery = true)
	@Modifying
	@Transactional
	void deleteByOpenId(String openId);
}
