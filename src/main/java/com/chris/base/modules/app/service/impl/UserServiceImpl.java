package com.chris.base.modules.app.service.impl;


import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.modules.app.dao.UserDao;
import com.chris.base.modules.app.entity.UserEntity;
import com.chris.base.modules.app.service.UserService;
import com.chris.base.common.exception.CommonException;
import com.chris.base.common.validator.Assert;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserEntity queryObject(Long userId){
		return userDao.queryObject(userId);
	}
	
	@Override
	public List<UserEntity> queryList(Map<String, Object> map){
		return userDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userDao.queryTotal(map);
	}
	
	@Override
	public void save(UserEntity user){
		// 密码 SHA256 加密
		user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
		userDao.save(user);
	}
	
	@Override
	public void update(UserEntity user){
		userDao.update(user);
	}
	
	@Override
	public void delete(Long userId){
		userDao.delete(userId);
	}
	
	@Override
	public void deleteBatch(Long[] userIds){
		userDao.deleteBatch(userIds);
	}

	@Override
	public UserEntity queryByMobile(String mobile) {
		return userDao.queryByMobile(mobile);
	}

	@Override
	public long login(String mobile, String password) {
		UserEntity user = queryByMobile(mobile);
		Assert.isNull(user, "手机号或密码错误");

		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(password))){
			throw new CommonException("手机号或密码错误");
		}

		return user.getUserId();
	}

	@Override
	public UserEntity queryUserByOpenId(String openId) {
		List<UserEntity> userList = this.userDao.queryList(ImmutableMap.of("openId", openId));
		return ValidateUtils.isEmptyCollection(userList) ? null : userList.get(0);
	}
}
