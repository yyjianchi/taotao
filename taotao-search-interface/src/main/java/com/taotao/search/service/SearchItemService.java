package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.taotaoresult.TaotaoResult;

public interface SearchItemService {
	public TaotaoResult importAllItems() throws Exception;
	
	public SearchResult search(String queryString,Integer page,Integer rows) throws Exception;
	
	//根据商品的id查询商品的数据，并且更新到索引库中
	public TaotaoResult updateItemById(Long itemId) throws Exception;
}
