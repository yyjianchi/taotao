package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.easyuidatagridresult.EasyUIDataGridResult;
import com.taotao.jsonutil.JsonUtils;
import com.taotao.manager.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemService;
import com.taotao.taotaoresult.TaotaoResult;

import javassist.ClassPath;
import redis.clients.jedis.Jedis;
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private JedisClient client;
	@Value("${ITEM_INFO_KEY}")
	private String ITEM_INFO_KEY;
	@Value("${ITEM_INFO_KEY_EXPIRE}")
	private Integer ITEM_INFO_KEY_EXPIRE;
	
	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		
		//创建返回结果对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		Long total = pageInfo.getTotal();
		
		result.setTotal(new Integer(total.toString()));
		result.setRows(list);
		
		return result;
	}
	
	@Override
	public void deleteItemByIds(String ids) {
		String [] strings=ids.split(",");
		for (String string : strings) {
			itemMapper.deleteByPrimaryKey(Long.valueOf(string));
		}
	}
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Override
	public TbItemDesc geTbItemDescById(Long itemId) {
		// 查询缓存
		try {
			if (itemId!=null) {
				String jsString=client.get(ITEM_INFO_KEY+":"+itemId+":DESC");
				if (StringUtils.isNotBlank(jsString)) {
					client.expire(ITEM_INFO_KEY+":"+itemId+":DESC", ITEM_INFO_KEY_EXPIRE);
					return JsonUtils.jsonToPojo(jsString, TbItemDesc.class);
				}
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}	
		TbItemDesc itemDesc=itemDescMapper.selectByPrimaryKey(itemId);
		//添加缓存
		try {
			if (itemDesc!=null) {
				client.set(ITEM_INFO_KEY+":"+itemId+":DESC", JsonUtils.objectToJson(itemDesc));
				client.expire(ITEM_INFO_KEY+":"+itemId+":DESC", ITEM_INFO_KEY_EXPIRE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

	@Autowired
	private TbItemParamMapper itemParamMapper;
	@Override
	public TbItemParam getTbItemParamById(String id) {
		//根据商品id查询出商品类目ID
		TbItem tbItem=itemMapper.selectByPrimaryKey(Long.valueOf(id));
		Long cid=tbItem.getCid();
		//根据类目ID查询商品规格
		TbItemParamExample example=new TbItemParamExample();
		Criteria criteria=example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list=itemParamMapper.selectByExample(example);
		TbItemParam itemParam=list.get(0);
		return itemParam; 
	}
	//更新商品信息
	
	@Override
	public TaotaoResult updateItem(TbItem item, String desc) {
		//System.out.println(item.getTitle());
		
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		
		TbItemDesc itemDesc=new TbItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		return TaotaoResult.ok();
		
	}

	@Override
	public TbItem getItemById(Long itemId) {
		// 添加缓存的原则是，不能够影响现在有的业务逻辑
		// 查询缓存
		try {
			if (itemId!=null) {
				// 从缓存中查询
				String jsonString=client.get(ITEM_INFO_KEY+":"+itemId+":BASE");
				if(StringUtils.isNotBlank(jsonString)){// 不为空则直接返回
					client.expire(ITEM_INFO_KEY+":"+itemId+":BASE", ITEM_INFO_KEY_EXPIRE);
					return JsonUtils.jsonToPojo(jsonString, TbItem.class);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItem tbItem=itemMapper.selectByPrimaryKey(itemId);
		System.out.println(tbItem);
		//查询结果添加到缓存
		try {
			// 注入redisclient
			if (tbItem != null){
				client.set(ITEM_INFO_KEY + ":" + itemId + ":BASE", JsonUtils.objectToJson(tbItem));
				client.expire(ITEM_INFO_KEY + ":" + itemId + ":BASE", ITEM_INFO_KEY_EXPIRE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbItem;
	}
	
	


}
