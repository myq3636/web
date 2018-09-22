package com.jx.tennis.monitor.online.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jx.tennis.monitor.online.domain.UserOnline;

@Repository
public class UserOnlineDao implements UserOnlineMapper {

	@Override
	public UserOnline selectOnlineById(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteOnlineById(String sessionId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int saveOnline(UserOnline online) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserOnline> selectUserOnlineList(UserOnline userOnline) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserOnline> selectOnlineByExpired(String lastAccessTime) {
		// TODO Auto-generated method stub
		return null;
	}

}
