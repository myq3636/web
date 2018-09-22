package com.jx.tennis.config;

import java.lang.invoke.MethodHandles;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jx.tennis.dto.CuUserDTO;
import com.jx.tennis.service.UserService;
import com.jx.tennis.util.Career;
import com.jx.tennis.util.WxCuSessionUtils;
import com.jx.tennis.vo.WxUserVo;
import com.mxixm.fastboot.weixin.util.WxWebUtils;
import com.mxixm.fastboot.weixin.web.WxWebUser;

@Configuration
public class LoginSecurityConfig extends WebMvcConfigurerAdapter{

	private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass());	
	
	@Autowired
	private UserService userService;
	
	@Value("${wx.user.headImagesPath}")
    private String hImagesPath;
	

	@Bean
	public SecurityInterceptor getSecurityInterceptor() {
		return new SecurityInterceptor();
	}
	
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

		// 排除配置
		addInterceptor.excludePathPatterns("/error");
		addInterceptor.excludePathPatterns("/wx/register**");
		addInterceptor.excludePathPatterns("/home**");

		// 拦截配置
		//addInterceptor.addPathPatterns("/wx/course");
	}
	
	/**
	 * 图片地址映射
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {		
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+hImagesPath);
		super.addResourceHandlers(registry);
	}

	private class SecurityInterceptor extends HandlerInterceptorAdapter {

		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			
			/*WxUserVo cuUserVo = WxCuSessionUtils.getCuUserFromRequest(request);
			if (cuUserVo == null || StringUtils.isEmpty(cuUserVo.getOpenId())) {
				//由于前端不传openid
				cuUserVo = WxCuSessionUtils.getCuUserFromSession(request);
				if (cuUserVo == null || StringUtils.isEmpty(cuUserVo.getOpenId())) {
					WxWebUser wxUser = WxWebUtils.getWxWebUserFromSession(request);
					if (wxUser == null || StringUtils.isEmpty(wxUser.getOpenId())) {						
						return doRedirect(response);
					}else {
						cuUserVo = userService.findUser(wxUser.getOpenId());
						if (cuUserVo == null) {
							return doRedirect(response);
						}						
						WxCuSessionUtils.setCuUserToSession(request, cuUserVo);						
					}
				}				
			}
			
			WxWebUser wxUser = WxWebUtils.getWxWebUserFromSession(request);
			if (wxUser == null) {
				return doRedirect(response);
			}*/
			return true;
			
		}
		
		private boolean doRedirect(HttpServletResponse response) throws Exception{
			// 跳转登录
			String url = "/wx/register";
			response.sendRedirect(url);
			return false;
		}
	}
}
