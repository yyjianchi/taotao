package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.easyuidatagridresult.EasyUIDataGridResult;
import com.taotao.jsonutil.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemService;
import com.taotao.service.SaveItemService;
import com.taotao.taotaoresult.TaotaoResult;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemservice;

	//url:/item/list
	//method:get
	//参数：page,rows
	//返回值:json
	@RequestMapping(value="/item/list",method=RequestMethod.GET)
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page,Integer rows){
		//1.引入服务
		//2.注入服务
		//3.调用服务的方法
		return itemservice.getItemList(page, rows);

		}
	
	@RequestMapping(value="rest/item/delete")
	@ResponseBody
	public TaotaoResult deleteItemByIds(String ids){
		itemservice.deleteItemByIds(ids);
		return TaotaoResult.ok();
	}
	
	@RequestMapping(value="rest/page/item-edit")
	public String showEdit(){
		return "item-edit";
	}
	
	@RequestMapping(value="rest/item/query/item/desc/{id}")
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable Long id){
		 TbItemDesc tbItemDesc=itemservice.geTbItemDescById(id);
		 return TaotaoResult.ok(tbItemDesc);
	}
	
	@RequestMapping(value="rest/item/param/item/query/{id}")
	@ResponseBody
	public TaotaoResult getItemParam(@PathVariable String id){
		TbItemParam itemParam=itemservice.getTbItemParamById(id);
		System.out.println(itemParam.getParamData());
		return TaotaoResult.ok(itemParam);
	}
	
	@RequestMapping(value="rest/item/update",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateItem(TbItem item,String desc){
		
		return itemservice.updateItem(item, desc);
	}
	
}
