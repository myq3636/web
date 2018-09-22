package com.jx.tennis.system.menu.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.jx.tennis.dto.CuUserDTO;
import com.jx.tennis.project.system.user.domain.User;
import com.jx.tennis.system.menu.domain.Menu;

@Repository
public class MenuDaoImpl implements MenuMapper {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	@Override
	public List<Menu> selectMenusByUserId(Long userId) {
		String sql = "select * from sys_menu";
		List<Menu> menus = new ArrayList<Menu>();
		SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);		
		try {
			while (sqlRowSet!=null && sqlRowSet.next()) {
				Menu menu = new Menu();
				menu.setMenuName(sqlRowSet.getString("menu_name"));
				menu.setIcon(sqlRowSet.getString("icon"));
				menu.setUrl(sqlRowSet.getString("url"));
				menu.setParentId(sqlRowSet.getLong("parent_id"));
				menu.setOrderNum(""+sqlRowSet.getInt("order_num"));
				menu.setMenuId(sqlRowSet.getLong("menu_id"));
				menus.add(menu);			
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if (sqlRowSet != null) {
				sqlRowSet = null;
            }
		}
		return menus;
	}

	@Override
	public List<String> selectPermsByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> selectMenuTree(Long roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Menu> selectMenuList(Menu menu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Menu> selectMenuAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteMenuById(Long menuId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Menu selectMenuById(Long menuId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectCountMenuByParentId(Long parentId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertMenu(Menu menu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateMenu(Menu menu) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Menu checkMenuNameUnique(String menuName) {
		// TODO Auto-generated method stub
		return null;
	}

}
