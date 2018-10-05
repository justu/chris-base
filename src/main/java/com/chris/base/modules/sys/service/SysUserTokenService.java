package com.chris.base.modules.sys.service;

import com.chris.base.common.utils.CommonResponse;
import com.chris.base.modules.sys.entity.SysUserTokenEntity;

/**
 * 用户Token
 * 
 * @author chris
 * @email 258321511@qq.com
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
