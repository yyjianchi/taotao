package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.content.service.ContentCategoryService;
import com.taotao.easyuitreenode.EasyUITreeNode;
import com.taotao.taotaoresult.TaotaoResult;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0") Long parentId) {
		
		List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
		return list;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createCategory(Long parentId, String name) {
		TaotaoResult result = contentCategoryService.addContentCategory(parentId, name);
		return result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult renameCategory(Long id ,String name){
		TaotaoResult result = contentCategoryService.renameContentCategory(id, name);
		return result;	
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteCategory(Long id){
		TaotaoResult result=contentCategoryService.deleteContentCategory(id);
		return result;
	}
}