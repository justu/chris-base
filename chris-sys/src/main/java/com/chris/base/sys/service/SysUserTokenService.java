package com.chris.base.sys.service;

import com.chris.base.common.model.CommonResponse;
import com.chris.base.sys.entity.SysUserTokenEntity;

/**
 * 用户Token
 * 
 * @author chris
 * @email forzamilan0607@gmail.com
 * @date 2017-03-23 15:22:07
 */
public interface SysUserTokenService {

	SysUserTokenEntity queryByUserId(Long userId);

	void save(SysUserTokenEntity token);
	
	void update(SysUserTokenEntity token);

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	CommonResponse createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}
