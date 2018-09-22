package com.jx.tennis.module.session;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.guava.GuavaCacheManager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mxixm.fastboot.weixin.module.web.WxRequest;
import com.mxixm.fastboot.weixin.module.web.session.DefaultWxSession;
import com.mxixm.fastboot.weixin.module.web.session.DefaultWxSessionIdGenerator;
import com.mxixm.fastboot.weixin.module.web.session.WxSession;
import com.mxixm.fastboot.weixin.module.web.session.WxSessionIdGenerator;
import com.mxixm.fastboot.weixin.module.web.session.WxSessionManager;
import com.mxixm.fastboot.weixin.util.CacheMap;

public class CuWxSessionManager implements WxSessionManager, InitializingBean{

	private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass());

    protected WxSessionIdGenerator wxSessionIdGenerator = null;

    /**
     * sesison超时
     */
    protected int sessionTimeout;

    /**
     * 最大活跃session数
     */
    protected int maxActiveLimit;

    public CuWxSessionManager(int sessionTimeout, int maxActiveLimit, WxSessionIdGenerator wxSessionIdGenerator) {
        this.sessionTimeout = sessionTimeout;
        this.maxActiveLimit = maxActiveLimit;
        this.setWxSessionIdGenerator(wxSessionIdGenerator);
    }

    protected LoadingCache<String, WxSession> sessions;

    public WxSessionIdGenerator getWxSessionIdGenerator() {
        return wxSessionIdGenerator;
    }

    public void setWxSessionIdGenerator(WxSessionIdGenerator wxSessionIdGenerator) {
        this.wxSessionIdGenerator = wxSessionIdGenerator;
    }

    public void add(WxSession wxSession) {
        sessions.put(wxSession.getId(), wxSession);
        long size = sessions.size();
        if (maxActiveLimit > 0 && size > maxActiveLimit) {
            logger.info("session数量超限，将触发内存清理");
        }
    }

    @Override
    public WxSession createWxSession(WxRequest wxRequest) {
        WxSession wxSession = createEmptySession();
        wxSession.setValid(true);
        wxSession.setCreationTime(System.currentTimeMillis());
        wxSession.setMaxIdleTime(sessionTimeout);
        wxSession.setId(wxSessionIdGenerator.generate(wxRequest));
        add(wxSession);
        return wxSession;
    }


    public WxSession createEmptySession() {
        return getNewWxSession();
    }

    @Override
    public WxSession getWxSession(WxRequest wxRequest) {
        return this.getWxSession(wxRequest, true);
    }

    @Override
    public WxSession getWxSession(WxRequest wxRequest, boolean create) {
        if (wxRequest == null) {
            return null;
        }
        WxSession wxSession =null;
		try {
			wxSession = sessions.get(wxSessionIdGenerator.generate(wxRequest));
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
		}
        if (wxSession != null) {
            wxSession.access();
        } else if (create) {
            wxSession = createWxSession(wxRequest);
        }
        return wxSession;
    }

    protected WxSession getNewWxSession() {
        return new DefaultWxSession(this);
    }

    public WxSession[] findWxSessions() {
        return sessions.asMap().values().toArray(new WxSession[0]);
    }

    @Override
    public void removeWxSession(WxSession wxSession) {
        if (wxSession.getId() != null) {
            sessions.invalidate(wxSession.getId());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        WxSessionIdGenerator wxSessionIdGenerator = getWxSessionIdGenerator();
        if (wxSessionIdGenerator == null) {
            setWxSessionIdGenerator(new DefaultWxSessionIdGenerator());
        }
        logger.info("CuWxSessionManager start to loading -----------");
        sessions = CacheBuilder.newBuilder().initialCapacity(1000)
        		.maximumSize(maxActiveLimit).expireAfterAccess(sessionTimeout, TimeUnit.MILLISECONDS)
        		.build(new CacheLoader<String, WxSession>(){

					@Override
					public WxSession load(String key) throws Exception {
						// TODO Auto-generated method stub
						return null;
					}
        			
        		});
    }

}
