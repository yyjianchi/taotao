package com.taotao.sso.service;

import com.taotao.pojo.TbUser;
import com.taotao.taotaoresult.TaotaoResult;

public interface UserRegisterService {
	public TaotaoResult checkData(String param, int type);
	
	public TaotaoResult register(TbUser user);
}
