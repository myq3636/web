package com.jx.tennis.service;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;





import com.google.common.base.Strings;
import com.jx.tennis.common.Constants;
import com.mxixm.fastboot.weixin.module.user.WxUser;

@Component
public class AsyncService {
	
	private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass());
	@Value("${wx.user.headImagesPath}")
    private String hImagesPath;
	
	@Async
	public void saveUser(WxUser wxUser) {
		String opId = wxUser.getOpenId();
		//need test header suffix
		String fileName = opId+Constants.IMG_SUFFIX;		
		String url = wxUser.getHeadImgUrl();		
		if (!Strings.isNullOrEmpty(url)) {
			downloadHttpUrl(url, fileName);
		}
	}
	
	private void downloadHttpUrl(String url, String fileName) {  
        try {  
            URL httpurl = new URL(url);  
            
            File f = new File(hImagesPath + fileName);  
            if(!f.exists()){
            	FileUtils.copyURLToFile(httpurl, f);
            } 
        } catch (Exception e) {  
            logger.error("download user header image failed" ,e);               
        }            
    }  
}
