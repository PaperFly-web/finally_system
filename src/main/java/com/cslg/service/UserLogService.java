package com.cslg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cslg.entity.UserLogEntity;
import com.cslg.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
public interface UserLogService extends IService<UserLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

