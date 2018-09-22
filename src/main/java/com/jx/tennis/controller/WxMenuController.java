package com.jx.tennis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;







import com.jx.tennis.service.AsyncService;
import com.jx.tennis.service.UserService;
import com.mxixm.fastboot.weixin.annotation.WxButton;
import com.mxixm.fastboot.weixin.annotation.WxController;
import com.mxixm.fastboot.weixin.annotation.WxEventMapping;
import com.mxixm.fastboot.weixin.module.event.WxEvent;
import com.mxixm.fastboot.weixin.module.message.WxMessage;
import com.mxixm.fastboot.weixin.module.user.WxUser;
import com.mxixm.fastboot.weixin.module.web.WxRequest;

@WxController
public class WxMenuController {

	@Value("${wx.welcome}")
	private String welcome;
	
	@Autowired
	private AsyncService asyncService;
	
	@Autowired
	private UserService userService;
	
	@WxButton(group = WxButton.Group.LEFT, main = true, name = "京西网球")
    public void left() {
    
    }
	
	@WxButton(group = WxButton.Group.MIDDLE, main = true, name = "网球培训")
    public void center() {
		
    }
	
	@WxButton(group = WxButton.Group.RIGHT, main = true,
			type = WxButton.Type.VIEW,
			url="http://mp.weixin.qq.com/bizmall/mallshelf?id=&t=mall/list&biz=MzI5NjQzNzk0OA==&shelf_id=1&showwxpaytitle=1#wechat_redirect",
			name = "器材装备")
    public void right() {
    
    }
	
	/**
     * 定义微信子菜单
     */
    @WxButton(type = WxButton.Type.VIEW,
            group = WxButton.Group.LEFT,
            order = WxButton.Order.FIRST,
            url = "http://mp.weixin.qq.com/s?__biz=MzI5NjQzNzk0OA==&mid=100000066&idx=1&sn=dee09f64b9c8068094406b5ca7671fd1&chksm=6c4510915b32998789feefffbe073807c165ce61449eb51bde59435e6c8269c8907154c21ff0&scene=18#wechat_redirect",
            name = "关于我们")
    public void leftSub1() {
        
    }
    
    @WxButton(type = WxButton.Type.VIEW,
            group = WxButton.Group.LEFT,
            order = WxButton.Order.SECOND,
            url = "http://mp.weixin.qq.com/s?__biz=MzI5NjQzNzk0OA==&mid=2247483878&idx=7&sn=a24b73c9a5affe503fd1d168cca097e1&chksm=ec451035db329923a4d3d2caee7fa9de2e4736e7e6c299f209d704abc2fd03deaf5c8851d67d&scene=18#wechat_redirect",
            name = "场馆基地")
    public void leftSub2() {
        
    }
    
    @WxButton(type = WxButton.Type.VIEW,
            group = WxButton.Group.LEFT,
            order = WxButton.Order.THIRD,
            url = "http://mp.weixin.qq.com/s?__biz=MzI5NjQzNzk0OA==&mid=2247483878&idx=6&sn=9fa0a0ee48994510faa499d96ff50d0a&chksm=ec451035db3299235f13ef8bb8f21c91de9df5d528cabdefe76f0c2bf3d1d51902113a9d47ae&scene=18#wechat_redirect",
            name = "发球机，球拍穿线")
    public void leftSub3() {
        
    }
    @WxButton(type = WxButton.Type.CLICK,
            group = WxButton.Group.LEFT,
            order = WxButton.Order.FORTH,
            name = "报名咨询")
    public WxMessage leftSub4() {
    	return WxMessage.textBuilder().content("报名咨询：苏教练;\n电话：15810722068").build();
    }
    
    @WxButton(type = WxButton.Type.VIEW,
            group = WxButton.Group.MIDDLE,
            order = WxButton.Order.FIRST,
            url = "http://mp.weixin.qq.com/s?__biz=MzI5NjQzNzk0OA==&mid=100000066&idx=2&sn=a63e99856a11a8699783d908856693e5&chksm=6c4510915b329987e33f6f5ba894cfd30c5cb672e8c06476d411112151c8c048688364fd7eaf&scene=18#wechat_redirect",
            name = "专项技术培训")
    public void centerSub1() {         
         
    }
    
    
    @WxButton(type = WxButton.Type.CLICK,
            group = WxButton.Group.MIDDLE,
            order = WxButton.Order.SECOND,
            name = "京西练球卡")
    public WxMessage centerSub2() {
    	return WxMessage.textBuilder().content("两次（集体）指导，每月一次职业球员场地指导。"
    			+ "\n- 提供智能发球机以提高训练的多样化和密度"
    			+ "\n- 提供发球机训练用球"
    			+ "\n- 专项技术训练辅导"
    			+ "\n咨询电话：15810722068").build();
    }
    
    @WxButton(type = WxButton.Type.VIEW,
            group = WxButton.Group.MIDDLE,
            order = WxButton.Order.THIRD,
            url = "http://mp.weixin.qq.com/s?__biz=MzI5NjQzNzk0OA==&mid=100000066&idx=3&sn=6d6712d9072c3fc28020baa5c052d745&chksm=6c4510915b3299872904bcaa29e0b3281ae4ce247150b4bb91b7953bff91b5fdfaf5da788054&scene=18#wechat_redirect",
            name = "青少培训")
    public void centerSub3() {         
         
    }
    
    @WxButton(type = WxButton.Type.VIEW,
            group = WxButton.Group.MIDDLE,
            order = WxButton.Order.FORTH,
            url = "http://mp.weixin.qq.com/s?__biz=MzI5NjQzNzk0OA==&mid=100000066&idx=4&sn=5e484675fbb8c4c68b794f32cb12c4f3&chksm=6c4510915b329987e83e0f9fb2c973a9b68cf01eae30598c5f82e6c37b6d8ec682e53cfe950b&scene=18#wechat_redirect",
            name = "成年人培训")
    public void centerSub4() {         
         
    }
    
    @WxButton(type = WxButton.Type.VIEW,
            group = WxButton.Group.MIDDLE,
            order = WxButton.Order.FIFTH,
            url = "http://wx.pcloudhotel.cn/wx/home",
            name = "我的技术档案")
    public void centerSub5() {         
         
    }
	
	/**
     * 关注微信事件
     * @param wxRequest
     * @param wxUser
     */
    @WxEventMapping(type = WxEvent.Type.SUBSCRIBE)
    public String subscribe(WxRequest wxRequest, WxUser wxUser) { 
    	userService.saveUser(wxUser);
    	asyncService.saveUser(wxUser);
        return welcome;
    }
    
    /**
     * 取消关注微信事件
     * @param wxRequest
     * @param wxUser
     */
    @WxEventMapping(type = WxEvent.Type.UNSUBSCRIBE)
    public void unSubscribe(WxRequest wxRequest, WxUser wxUser) {
    	System.out.println("取消关注成功！");
    }
         
}
