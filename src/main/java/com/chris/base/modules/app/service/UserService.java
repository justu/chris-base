package com.chris.base.modules.app.service;


import com.chris.base.modules.app.entity.UserEntity;
import com.chris.base.modules.sys.entity.SysMenuEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 用户
 * 
 * @author chris
 * @email 258321511@qq.com
 * @date 2017-03-23 15:22:06
 */
public interface UserService {

	UserEntity queryObject(Long userId);

	List<UserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(UserEntity user);
	
	void update(UserEntity user);
	
	void delete(Long userId);
	
	void deleteBatch(Long[] userIds);

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param mobile    手机号
	 * @param password  密码
	 * @return          返回用户ID
	 */
	UserEntity login(String mobile, String password);

	UserEntity queryUserByOpenId(String openId);

	List<SysMenuEntity> queryUserMenusByOpenId(String openId);

	@Transactional
	void registerUser(UserEntity user);

	/**
	 * 注册不需要密码
	 * @param user
	 */
	@Transactional
    void registerUserWithoutPwd(UserEntity user);
}
