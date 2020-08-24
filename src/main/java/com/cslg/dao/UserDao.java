package com.cslg.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cslg.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	/**
	*@desc:用户登录
	*@param:* @param null:
	*@return:* @return: null
	*@author:paperfly
	*@time:2020/8/22 22:46
	*/
	UserEntity login(String userName);
	/**
	*@desc:批量注册用户
	*@param:* @param null:
	*@return:* @return: null
	*@author:paperfly
	*@time:2020/8/22 22:46
	*/
	Integer registerUsers(List<UserEntity> list);
}
