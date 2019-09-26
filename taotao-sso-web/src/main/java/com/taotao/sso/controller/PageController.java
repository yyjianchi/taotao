package com.taotao.sso.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public class PageController {
	@RequestMapping("/page/{page}")
	public String showPage(@PathVariable String page){
		return page;
	}
}
