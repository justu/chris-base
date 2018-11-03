package com.chris.base.modules.app.dao;

import com.chris.base.modules.app.entity.UserEntity;
import com.chris.base.modules.sys.dao.BaseDao;
import com.chris.base.modules.sys.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户
 * 
 * @author chris
 * @email 258321511@qq.com
 * @date 2017-03-23 15:22:06
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {

    UserEntity queryByMobile(String mobile);

    /**
     * 根据 openId 查询用户菜单
     * @param openId
     * @return
     */
    List<SysMenuEntity> queryUserMenusByOpenId(String openId);
}
