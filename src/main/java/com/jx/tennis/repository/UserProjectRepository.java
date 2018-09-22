package com.jx.tennis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.jx.tennis.entity.UserProjectEntity;


public interface UserProjectRepository extends CrudRepository<UserProjectEntity, Integer>, JpaRepository<UserProjectEntity, Integer> {
 
	public List<UserProjectEntity> findByOpenIdAndStartTime(String openId, String StartTime);
	@Query(value = "delete from wx_project_record where open_id= ?1", nativeQuery = true)
	@Modifying
	@Transactional
	void deleteByOpenId(String openId);
}
