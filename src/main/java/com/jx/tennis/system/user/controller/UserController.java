package com.jx.tennis.system.user.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jx.tennis.controller.BaseController;
import com.jx.tennis.entity.WxUserEntity;
import com.jx.tennis.framework.aspectj.lang.annotation.Log;
import com.jx.tennis.framework.aspectj.lang.enums.BusinessType;
import com.jx.tennis.project.system.user.domain.User;
import com.jx.tennis.service.UserService;
import com.jx.tennis.util.DateUtils;
import com.jx.tennis.util.StringUtils;
import com.jx.tennis.vo.AjaxResult;
import com.jx.tennis.vo.SystemUserInfoVo;



/**
 * 用户信息
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController
{
    private String prefix = "system/user";

    @Autowired
    private UserService userService;

   

    @GetMapping()
    public String user()
    {
        return prefix + "/user";
    }
    
    @GetMapping("/add/nickNameList")
    @ResponseBody
    public List<String> queryNickNameList()
    {
    	List<String> result = userService.findNickNames();
        return result;
    }

    @PostMapping("/list")
    @ResponseBody
    public HashMap<String, Object> list(User user, int pageSize, int pageNum)
    {
    	//startPage();
    	//PageHelper.startPage(pageNum, pageSize);
    	PageRequest pageRequest = new PageRequest(pageNum-1, pageSize);
    	Page<WxUserEntity> pages = userService.selectUserList(user, pageRequest);
        /*List<WxUserEntity> list = pages.getContent();
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(pageNum-1);
        rspData.setRows(list);
        rspData.setTotal(pages.getTotalElements());
*/      
    	HashMap<String, Object> result = new HashMap<String, Object>();
    	result.put("rows", pages.getContent());
    	result.put("total", pages.getTotalElements());

    	return result;
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {        
    	List<String> result = userService.findNickNames();
    	mmap.addAttribute("nickNames", result);
    	return prefix + "/add";
    }

    /**
     * 新增保存用户
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SystemUserInfoVo user)
    {
        try {
			userService.insertUser(user);
			return AjaxResult.success();
		} catch (Exception e) {
			
		}
        return AjaxResult.error();
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String userId, ModelMap mmap)
    {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(SystemUserInfoVo user)
    {
    	try {
			userService.updateUserBase(user);
			return AjaxResult.success();
		} catch (Exception e) {
			
		}
        return AjaxResult.error();
    }
    
    /**
     * 修改用户
     */
    @GetMapping("/editp/{id}")
    public String editP(@PathVariable("id") String userId, ModelMap mmap)
    {
        mmap.addAttribute("openId", userId);
        String month = DateUtils.getCurrentMonth();
        mmap.addAttribute("month", month);
        mmap.put("user", userService.selectUserProjectById(userId, month));
        return prefix + "/editp";
    }
    
    @GetMapping("/queryprojectbydate")
    @ResponseBody
    public SystemUserInfoVo queryP(String openId, String date)
    {
        return userService.selectUserProjectById(openId, date);
    }

    /**
     * 修改保存用户
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/editp")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editPSave(SystemUserInfoVo user, String openId)
    {
    	try {
			userService.updateUserProject(user, openId);
			return AjaxResult.success();
		} catch (Exception e) {
			
		}
        return AjaxResult.error();
    }
   

    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(userService.deleteUserByIds(ids));
        }
        catch (Exception e)
        {
            System.out.println(e);
        	return AjaxResult.error();
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(User user)
    {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

}