package com.jx.tennis.system.menu.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jx.tennis.common.utils.security.ShiroUtils;
import com.jx.tennis.system.menu.domain.Menu;
import com.jx.tennis.system.menu.mapper.MenuMapper;
import com.jx.tennis.util.StringUtils;
import com.jx.tennis.util.TreeUtils;

/**
 * 菜单 业务层处理
 * 
 * @author ruoyi
 */
@Service
public class MenuServiceImpl implements IMenuService
{
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Autowired
    private MenuMapper menuMapper;



    /**
     * 查询菜单集合
     * 
     * @return 所有菜单信息
     */
    @Override
    public List<Menu> selectMenuList(Menu menu)
    {
        return menuMapper.selectMenuList(menu);
    }

    /**
     * 查询菜单集合
     * 
     * @return 所有菜单信息
     */
    @Override
    public List<Menu> selectMenuAll()
    {
        return menuMapper.selectMenuAll();
    }

    /**
     * 根据用户ID查询权限
     * 
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectPermsByUserId(Long userId)
    {
        List<String> perms = menuMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }


    /**
     * 查询所有菜单
     * 
     * @return 菜单列表
     */
    @Override
    public List<Map<String, Object>> menuTreeData()
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        List<Menu> menuList = menuMapper.selectMenuAll();
        trees = getTrees(menuList, false, null, false);
        return trees;
    }

    /**
     * 查询系统所有权限
     * 
     * @return 权限列表
     */
    @Override
    public LinkedHashMap<String, String> selectPermsAll()
    {
        LinkedHashMap<String, String> section = new LinkedHashMap<>();
        List<Menu> permissions = menuMapper.selectMenuAll();
        if (StringUtils.isNotEmpty(permissions))
        {
            for (Menu menu : permissions)
            {
                section.put(menu.getUrl(), MessageFormat.format(PREMISSION_STRING, menu.getPerms()));
            }
        }
        return section;
    }

    /**
     * 对象转菜单树
     * 
     * @param menuList 菜单列表
     * @param isCheck 是否需要选中
     * @param roleMenuList 角色已存在菜单列表
     * @param permsFlag 是否需要显示权限标识
     * @return
     */
    public List<Map<String, Object>> getTrees(List<Menu> menuList, boolean isCheck, List<String> roleMenuList,
            boolean permsFlag)
    {
        List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
        for (Menu menu : menuList)
        {
            Map<String, Object> deptMap = new HashMap<String, Object>();
            deptMap.put("id", menu.getMenuId());
            deptMap.put("pId", menu.getParentId());
            deptMap.put("name", transMenuName(menu, roleMenuList, permsFlag));
            deptMap.put("title", menu.getMenuName());
            if (isCheck)
            {
                deptMap.put("checked", roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
            }
            else
            {
                deptMap.put("checked", false);
            }
            trees.add(deptMap);
        }
        return trees;
    }

    public String transMenuName(Menu menu, List<String> roleMenuList, boolean permsFlag)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(menu.getMenuName());
        if (permsFlag)
        {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
        }
        return sb.toString();
    }

    /**
     * 删除菜单管理信息
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int deleteMenuById(Long menuId)
    {
        ShiroUtils.clearCachedAuthorizationInfo();
        return menuMapper.deleteMenuById(menuId);
    }

    /**
     * 根据菜单ID查询信息
     * 
     * @param menuId 菜单ID
     * @return 菜单信息
     */
    @Override
    public Menu selectMenuById(Long menuId)
    {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 查询子菜单数量
     * 
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int selectCountMenuByParentId(Long parentId)
    {
        return menuMapper.selectCountMenuByParentId(parentId);
    }

	@Override
	public List<Menu> selectMenusByUserId(Long userId) {
		List<Menu> menus = menuMapper.selectMenusByUserId(userId);
        return TreeUtils.getChildPerms(menus, 0);
	}

	@Override
	public int selectCountRoleMenuByMenuId(Long menuId) {
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
	public String checkMenuNameUnique(Menu menu) {
		// TODO Auto-generated method stub
		return null;
	}


}
