package com.taotao.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.content.service.ContentService;
import com.taotao.easyuidatagridresult.EasyUIDataGridResult;
import com.taotao.pojo.TbContent;
import com.taotao.taotaoresult.TaotaoResult;

@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContent(@RequestParam(value="categoryId", defaultValue="0")Long catagoryId,int page,int rows){
		return contentService.getContentList(catagoryId, page, rows);	
	}
	
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent content) {
		TaotaoResult result = contentService.addContent(content);
		return result;
	}
	
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public TaotaoResult updateContent(TbContent content){
		TaotaoResult result=contentService.updateContent(content);
		return result;
	}
	
	@RequestMapping("/content/delete")
	@ResponseBody
	public TaotaoResult deleteContent(String ids){
		TaotaoResult result=contentService.deleteContent(ids);
		return result;
	}
}
