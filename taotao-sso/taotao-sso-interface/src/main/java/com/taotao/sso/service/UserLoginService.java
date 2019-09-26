package com.taotao.sso.service;

import com.taotao.taotaoresult.TaotaoResult;

public interface UserLoginService {
	public TaotaoResult login(String username, String password);
	
	public TaotaoResult getUserByToken(String token);
}
