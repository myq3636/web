package com.jx.tennis.controller.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 捕获进入controller之前抛出的异常，例如请求一个不存在的地址
 * @author g707327
 *
 */
@RestController
public class FirstExceptionHandler implements ErrorController{

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}
	
	@RequestMapping(value = "/error")
    public Object error(HttpServletResponse resp, HttpServletRequest req) {
        // 错误处理逻辑
        return "其他异常";
    }

}
