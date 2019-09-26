package com.taotao.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.search.mapper.SearchItemMapper;
import com.taotao.taotaoresult.TaotaoResult;

@Repository
public class SearchDao {
	@Autowired
	private SolrServer solrServer;
	
	public SearchResult search(SolrQuery query) throws Exception {
		//根据query对象查询索引库
		QueryResponse response=solrServer.query(query);
		//取商品列表
		SolrDocumentList solrDocumentList=response.getResults();
		//商品列表
		List<SearchItem> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem item=new SearchItem();
			item.setId(Long.valueOf((String)solrDocument.get("id")));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			//取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle = "";
			//有高亮显示的内容时。
			if (list != null && list.size() > 0) {
				itemTitle = list.get(0);
			}else{
				itemTitle = (String) solrDocument.get("item_title");
			}
			item.setTitle(itemTitle);
			//添加到商品列表
			itemList.add(item);
		}
		
		SearchResult result = new SearchResult();
		//商品列表
		result.setItemList(itemList);
		//总记录数
		result.setRecordCount(solrDocumentList.getNumFound());
		
		return result;
		
		
	}
	@Autowired
	private SearchItemMapper mapper;
	public TaotaoResult updateItemById(Long itemId) throws Exception {
		//1.调用mapper中的方法
		SearchItem searchItem = mapper.getItemById(itemId);
		//2.创建solrinputdocument
		SolrInputDocument document = new SolrInputDocument();
		//3.向文档中添加域
		document.addField("id", searchItem.getId());
		document.addField("item_title", searchItem.getTitle());
		document.addField("item_sell_point", searchItem.getSell_point());
		document.addField("item_price", searchItem.getPrice());
		document.addField("item_image", searchItem.getImage());
		document.addField("item_category_name", searchItem.getCategory_name());
		document.addField("item_desc", searchItem.getItem_desc());
		//4.添加文档到索引库中
		solrServer.add(document);
		//5.提交
		solrServer.commit();
		
		return TaotaoResult.ok();
		
		
		
	}
}
