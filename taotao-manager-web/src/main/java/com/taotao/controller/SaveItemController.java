package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.service.SaveItemService;
import com.taotao.taotaoresult.TaotaoResult;

@Controller
public class SaveItemController {
	@Autowired
	private SaveItemService saveItemService;
	
	
	@RequestMapping("/item/save")
	@ResponseBody
	public TaotaoResult saveItem(TbItem item, String desc) {
	TaotaoResult result = saveItemService.saveItem(item, desc);
			return result;
	}
}
