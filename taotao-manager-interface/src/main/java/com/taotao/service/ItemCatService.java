package com.taotao.service;

import java.util.List;

import com.taotao.easyuitreenode.EasyUITreeNode;

public interface ItemCatService {
	public List< EasyUITreeNode > getItemCatList(Long parentId); 
}
