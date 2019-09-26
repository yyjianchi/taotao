package com.taotao.service;

import com.taotao.pojo.TbItem;
import com.taotao.taotaoresult.TaotaoResult;

public interface SaveItemService {
	/**根据商品的基础数据 和商品的描述信息 插入商品（插入商品基础表  和商品描述表）
	 * @param item
	 * @param desc
	 * @return
	 */
	public TaotaoResult saveItem(TbItem item,String desc);
}
