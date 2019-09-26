package com.taotao.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.sso.service.UserLoginService;
import com.taotao.taotaoresult.TaotaoResult;
import com.taotao.util.CookieUtils;

@Controller
public class UserLoginController {
	
	@Autowired
	private UserLoginService userService;
	@Value("${COOKIE_TOKEN_KEY}")
	private String COOKIE_TOKEN_KEY;
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult login(String username, String password,
			HttpServletRequest request, HttpServletResponse response) {
		// 1、接收两个参数。
		// 2、调用Service进行登录。
		TaotaoResult result = userService.login(username, password);
		// 3、从返回结果中取token，写入cookie。Cookie要跨域。
		String token = result.getData().toString();
		CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
		// 4、响应数据。Json数据。TaotaoResult，其中包含Token。
		return result;
		
	}
	
	//4.1版本后可以使用这种方式支持JSONP
		@RequestMapping(value="/user/token/{token}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
		@ResponseBody
		public Object getUserByToken(@PathVariable String token,String callback){
			TaotaoResult result = userService.getUserByToken(token);
			if(StringUtils.isNotBlank(callback)){
				MappingJacksonValue value = new MappingJacksonValue(result);
				value.setJsonpFunction(callback);
				return value;
			}
			return result;
		}
}
