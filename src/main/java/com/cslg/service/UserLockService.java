package com.cslg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cslg.entity.UserLockEntity;
import com.cslg.utils.PageUtils;

import java.util.Map;

/**
 * 这个表主要是防止用户频繁登陆错误  频繁获取验证码等，超过限制就锁定一段时间
 *
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
public interface UserLockService extends IService<UserLockEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

