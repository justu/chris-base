package com.chris.base.modules.app.service.impl;


import com.chris.base.common.utils.Constant;
import com.chris.base.common.utils.ValidateUtils;
import com.chris.base.modules.app.dao.UserDao;
import com.chris.base.modules.app.entity.UserEntity;
import com.chris.base.modules.app.service.UserService;
import com.chris.base.common.exception.CommonException;
import com.chris.base.common.validator.Assert;
import com.chris.base.modules.sys.entity.SysMenuEntity;
import com.chris.base.modules.sys.entity.SysUserEntity;
import com.chris.base.modules.sys.service.SysUserRoleService;
import com.chris.base.modules.sys.service.SysUserService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserRoleService sysUserRoleService;

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
	public UserEntity login(String mobile, String password) {
		UserEntity user = queryByMobile(mobile);
		Assert.isNull(user, "手机号或密码错误");

		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(password))){
			throw new CommonException("手机号或密码错误");
		}

		return user;
	}

	@Override
	public UserEntity queryUserByOpenId(String openId) {
		List<UserEntity> userList = this.userDao.queryList(ImmutableMap.of("openId", openId));
		return ValidateUtils.isEmptyCollection(userList) ? null : userList.get(0);
	}

    @Override
    public List<SysMenuEntity> queryUserMenusByOpenId(String openId) {
        return this.userDao.queryUserMenusByOpenId(openId);
    }

	@Override
	public void registerUser(UserEntity user) {
		this.setUsername(user);
		this.verifyUserIsExist(user);
		// 保存用户信息
		this.save(user);
		this.sysUserRoleService.saveOrUpdate(user.getUserId(), ImmutableList.of(this.getUserRoleId(user)), Constant.UserSource.WX_USER);
	}

	@Override
	public void registerUserWithoutPwd(UserEntity user) {
		this.setUsername(user);
		// 设置默认密码为手机号后6位
		user.setPassword(user.getMobile().substring(5));
		// 保存用户信息
		this.save(user);
		this.sysUserRoleService.saveOrUpdate(user.getUserId(), ImmutableList.of(this.getUserRoleId(user)), Constant.UserSource.WX_USER);
	}

	private void verifyUserIsExist(UserEntity user) {
		if (ValidateUtils.isNotEmpty(this.userDao.queryByMobile(user.getMobile()))) {
			throw new CommonException(user.getMobile() + " 号码已经注册，请直接登录！");
		}
	}

	private void setUsername(UserEntity user) {
		if (ValidateUtils.isEmptyString(user.getUsername())) {
			user.setUsername(user.getMobile());
		}
	}

	private long getUserRoleId(UserEntity user) {
		// 根据手机号关联到系统管理员用户
		List<SysUserEntity> sysUsers = this.sysUserService.queryList(ImmutableMap.of("mobile", user.getMobile()));
		if (ValidateUtils.isNotEmptyCollection(sysUsers)) {
			// 用户也是管理员 创建用户
			return Constant.WXRole.ADMIN;
		}
		// 用户是员工
		int count = this.userDao.countStaffByMobile(user.getMobile());
		if (count > 0) {
			return Constant.WXRole.STAFF;
		}
		return Constant.WXRole.VISITOR;
	}
}
