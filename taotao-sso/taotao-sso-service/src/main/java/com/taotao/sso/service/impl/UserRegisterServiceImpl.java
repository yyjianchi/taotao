package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.UserRegisterService;
import com.taotao.taotaoresult.TaotaoResult;
@Service
public class UserRegisterServiceImpl implements UserRegisterService {
	@Autowired
	private TbUserMapper userMapper;
	@Override
	public TaotaoResult checkData(String param, int type) {
		// 1、从tb_user表中查询数据
		TbUserExample example=new TbUserExample();
		Criteria criteria=example.createCriteria();
		// 2、查询条件根据参数动态生成。
		//1、2、3分别代表username、phone、email
		if (type == 1) {
			criteria.andUsernameEqualTo(param);
		} else if (type == 2) {
			criteria.andPhoneEqualTo(param);
		} else if (type == 3) {
			criteria.andEmailEqualTo(param);
		} else {
			return TaotaoResult.build(400, "非法的参数");
		}
		//执行查询
		
		List<TbUser> list=userMapper.selectByExample(example);
		// 3、判断查询结果，如果查询到数据返回false。
		if (list ==null || list.size()==0) {
			// 4、如果没有返回true。
			return TaotaoResult.ok(true);
		}
		// 5、使用TaotaoResult包装，并返回。
		return TaotaoResult.ok(false);
	}
	
	@Override
	public TaotaoResult register(TbUser user) {
		//1.校验用户名和密码不能为空
		if (StringUtils.isEmpty(user.getUsername())) {
			return TaotaoResult.build(400, "注册失败.请校验数据后请再提交数据");
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			return TaotaoResult.build(400, "注册失败.请校验数据后请再提交数据");			
		}
		//2.校验数据的可用性
		//2.1 校验用户名是否可用
		TaotaoResult checkData = checkData(user.getUsername(),1);
		if (!(Boolean)checkData.getData()) {//如果不可用，返回400
			return TaotaoResult.build(400, "用户名已经被注册");
		}
		//2.2校验phone
		if(StringUtils.isNotBlank(user.getPhone())){
			TaotaoResult checkDataphone = checkData(user.getPhone(),2);
			if(!(Boolean)checkDataphone.getData()){//如果不可用，返回400
				return TaotaoResult.build(400, "手机已经被注册");
			}
		}
		//2.3校验email
		if(StringUtils.isNotBlank(user.getEmail())){
			TaotaoResult checkDataEmail = checkData(user.getEmail(),3);
			if(!(Boolean)checkDataEmail.getData()){//如果不可用，返回400
				return TaotaoResult.build(400, "邮箱已经被注册");
			}
		}
		//密码的MD5加密处理
		String password=user.getPassword();
		String md5Password=DigestUtils.md5DigestAsHex(password.getBytes());
		user.setPassword(md5Password);
		
		//补全属性的值
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		
		//3.插入
		userMapper.insertSelective(user);
		return TaotaoResult.ok();//插入成功
	}
	
}
