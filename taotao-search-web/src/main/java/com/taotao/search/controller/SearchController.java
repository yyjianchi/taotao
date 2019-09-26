package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.service.SearchItemService;

@Controller

public class SearchController {
	@Value("${ITEM_ROWS}")
	private Integer ITEM_ROWS;
	
	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/search")
	public String search(@RequestParam("q")String queryString,@RequestParam(defaultValue="1")
	Integer page,Model model) throws Exception{
		
		queryString=new String(queryString.getBytes("iso8859-1"), "utf-8");
	
		SearchResult result=searchItemService.search(queryString, page,ITEM_ROWS);
		
		//传递给页面
		model.addAttribute("query", queryString);
		model.addAttribute("totalPages", result.getPageCount());
		model.addAttribute("itemList", result.getItemList());
		model.addAttribute("page", page);
		
		//返回逻辑视图
		return "search";
	}
}
