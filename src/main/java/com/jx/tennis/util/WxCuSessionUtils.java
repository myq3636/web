package com.jx.tennis.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.jx.tennis.dto.CuUserDTO;
import com.jx.tennis.vo.WxUserVo;
import com.mxixm.fastboot.weixin.module.web.WxRequest;
import com.mxixm.fastboot.weixin.web.WxWebUser;

public class WxCuSessionUtils {
	
    public static final String CU_SESSION_USER = "CU_SESSION_USER";
    private static final String CU_REQUEST_USER = "CU_REQUEST_USER";

	public static void setCuUserToRequest(HttpServletRequest request, WxUserVo cuUser) {       
            request.setAttribute(CU_REQUEST_USER, cuUser);        
    }

    public static WxUserVo getCuUserFromRequest(HttpServletRequest request) {       
           return (WxUserVo) request.getAttribute(CU_REQUEST_USER);
    }
    
    public static void setCuUserToSession(HttpServletRequest request, WxUserVo cuUser) {
        request.getSession().setAttribute(CU_SESSION_USER, cuUser);
    }

    public static WxUserVo getCuUserFromSession(HttpServletRequest request) {
        return (WxUserVo) request.getSession().getAttribute(CU_SESSION_USER);
    }
}
