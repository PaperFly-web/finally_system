package com.cslg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cslg.entity.UserEntity;
import com.cslg.utils.PageUtils;
import com.cslg.utils.R;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UserEntity login(String userName);

    R registerUsers(List<UserEntity> list);
}

