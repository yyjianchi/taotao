package com.taotao.service;

import java.util.List;

import com.taotao.easyuidatagridresult.EasyUIDataGridResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParam;
import com.taotao.taotaoresult.TaotaoResult;

public interface ItemService {
	//查询商品列表
	public EasyUIDataGridResult getItemList(int page,int rows);
	
	//删除商品
	public void deleteItemByIds(String ids);
	
	//根据ID查询商品描述
	public TbItemDesc geTbItemDescById(Long itemId);
	
	//根据ID查询商品规格参数
	public TbItemParam getTbItemParamById(String id);
	
	//更新商品信息
	public TaotaoResult updateItem(TbItem item,String desc) ;
	
	//根据ID查询商品信息
	public TbItem getItemById(Long itemId);
}
