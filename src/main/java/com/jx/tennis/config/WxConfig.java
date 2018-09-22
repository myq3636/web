package com.jx.tennis.config;

import java.lang.invoke.MethodHandles;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jx.tennis.module.session.CuWxSessionManager;
import com.mxixm.fastboot.weixin.config.WxProperties;
import com.mxixm.fastboot.weixin.module.web.session.WxSessionManager;

@Configuration()
public class WxConfig {

	private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass());

    private final WxProperties wxProperties;

    public WxConfig(WxProperties wxProperties) {
        this.wxProperties = wxProperties;
    }

    @Bean    
    public WxSessionManager wxSessionManager() {
        return new CuWxSessionManager(wxProperties.getServer().getSessionTimeout(),
                wxProperties.getServer().getMaxActiveLimit(),
                BeanUtils.instantiateClass(wxProperties.getServer().getWxSessionIdGeneratorClass()));
    }
}
