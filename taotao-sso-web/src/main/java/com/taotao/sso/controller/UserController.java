package com.taotao.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserRegisterService;
import com.taotao.taotaoresult.TaotaoResult;

@Controller
public class UserController {
	@Autowired
	private UserRegisterService userService;
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public TaotaoResult checkData(@PathVariable String param ,@PathVariable Integer type){
		TaotaoResult taotaoResult = userService.checkData(param, type);
		return taotaoResult;
		
	}
	
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult register(TbUser user){
		TaotaoResult result=userService.register(user);
		return result;
		
	}
}
