package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.easyuidatagridresult.EasyUIDataGridResult;
import com.taotao.service.ItemService;

@Controller
public class PageController {

	@RequestMapping("/")
	public String ShowIndex(){
		return "index";
	}
	
	@RequestMapping("/{page}")
	public String showItemList(@PathVariable String page){
		return page;
	}
	

}
