package com.jx.tennis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jx.tennis.dto.CuUserDTO;
import com.jx.tennis.service.UserService;
import com.jx.tennis.util.Career;
import com.jx.tennis.util.DateUtils;
import com.jx.tennis.vo.AjaxResult;
import com.jx.tennis.vo.UserProjectVo;
import com.jx.tennis.vo.UserTechIndexVo;
import com.jx.tennis.vo.WxUserVo;
import com.mxixm.fastboot.weixin.module.message.WxMessage;
import com.mxixm.fastboot.weixin.module.message.WxMessageTemplate;
import com.mxixm.fastboot.weixin.module.user.WxUser;
import com.mxixm.fastboot.weixin.util.WxWebUtils;
import com.mxixm.fastboot.weixin.web.WxUserManager;
import com.mxixm.fastboot.weixin.web.WxWebUser;

@Controller
public class WxTennisController {
	
	@Autowired
	private WxUserManager wxUsermanager;
	
	@Value("${wx.admin}")
	private String contact;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WxMessageTemplate wxMsgTemplate;

	@RequestMapping("/wx/home")
	public String home(Model model) {
		WxWebUser wxWebUser = WxWebUtils.getWxWebUserFromSession();	
		if (wxWebUser!=null) {
			String id=wxWebUser.getOpenId();
			UserTechIndexVo vo = userService.findTechIndex(id, "");
			if (vo.getTechInfo()!=null && !vo.getTechInfo().isEmpty()) {
				return "redirect:/?id="+id;
			}else {
				WxMessage wxMessage = WxMessage.textBuilder().content(contact).toUser(id).build();
				wxMsgTemplate.sendMessage(wxMessage);
			}
			return "redirect:/contact";
		}
		return null;
	}
	
	@RequestMapping("/contact")
	public String contact(Model model) {
		model.addAttribute("contact", contact);
		return "404";
	}
	
	@RequestMapping("/api/home/index")
	@ResponseBody
	public AjaxResult homeInfo(String id) {
		//WxWebUser wxWebUser = WxWebUtils.getWxWebUserFromSession();		
		//System.out.println(wxWebUser);
		WxUserVo user =  userService.findUser(id);
		String currentMonth = DateUtils.getFormatTimeFromMsWithTimeZone(System.currentTimeMillis(), "yyyy-MM", "GMT+8");
		Map<String,List<UserProjectVo>> userProjectVo = userService.findProjectRecord(id, currentMonth);
		UserTechIndexVo userTechIndexVo = userService.findTechIndex(id, user.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectDate", currentMonth);
		map.put("techInfo", userTechIndexVo.getTechInfo());
		map.put("target", userProjectVo);
		map.put("info", user);
		AjaxResult result = new AjaxResult();
		result.put("data", map);
		result.put("code", 0);
		return result;
	}
	
	@RequestMapping("/api/home/queryProject")
	@ResponseBody
	public AjaxResult queryProject(String id, String date) {
		//WxWebUser wxWebUser = WxWebUtils.getWxWebUserFromSession();		
		//System.out.println(wxWebUser);
		long ptime = DateUtils.getMsFromFormatTimeWithTimeZone(date, "yyyy-MM", "GMT+8");
		String currentMonth = DateUtils.getFormatTimeFromMsWithTimeZone(ptime, "yyyy-MM", "GMT+8");
		Map<String,List<UserProjectVo>> userProjectVo = userService.findProjectRecord(id, currentMonth);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectDate", currentMonth);
		map.put("target", userProjectVo);
		AjaxResult result = new AjaxResult();
		result.put("data", map);
		result.put("code", 0);
		return result;
	}
	
	@RequestMapping("/wx/register")
	public String register(Model model) {
		WxWebUser wxWebUser = WxWebUtils.getWxWebUserFromSession();
		
		System.out.println(wxWebUser);
		WxUser wxUser = wxUsermanager.getWxUserByWxWebUser(wxWebUser);
		CuUserDTO cuUserDTO = new CuUserDTO();
		cuUserDTO.setNickName(wxUser.getNickName());
		cuUserDTO.setOpenId(wxUser.getOpenId());
		cuUserDTO.setType(Career.UNKNOWN);
		model.addAttribute("user", cuUserDTO);
		model.addAttribute("message", "user");
		return "login";
	}
	
	
	/*@RequestMapping("/wx/student")
	public String student(HttpServletRequest req,Model model) {
		WxWebUser wxWebUser = WxWebUtils.getWxWebUserFromSession();
		WxUser wxUser = wxUsermanager.getWxUserByWxWebUser(wxWebUser);
		 
		CuUserDTO cuUserDTO = new CuUserDTO();
		cuUserDTO.setNickName(wxUser.getNickName());
		cuUserDTO.setOpenId(wxUser.getOpenId());
		cuUserDTO.setType(Career.STUDENT);
		WxCuSessionUtils.setCuUserToSession(req, cuUserDTO);
		
		wxMsgTemplate.sendMessage(wxUser.getOpenId(), "感谢你的注册，你已经成功注册为学生。");
		model.addAttribute("message", "学生");
		return "index";
	}*/

	@PostMapping("/loginPost")
	public @ResponseBody Map<String, Object> loginPost(String account, String password, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		if (!"123456".equals(password)) {
			map.put("success", false);
			map.put("message", "密码错误");
			return map;
		}

		// 设置session
		//session.setAttribute(LoginSecurityConfig.SESSION_KEY, account);

		map.put("success", true);
		map.put("message", "登录成功");
		return map;
	}

	@GetMapping("/test")
	public String login(HttpServletRequest request,String name) {
		// 移除session
		System.out.println(request.getSession().getId());
		//session.removeAttribute(LoginSecurityConfig.SESSION_KEY);
		return "login";
	}

}
