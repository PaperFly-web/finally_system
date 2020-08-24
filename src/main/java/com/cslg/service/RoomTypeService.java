package com.cslg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cslg.entity.RoomTypeEntity;
import com.cslg.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
public interface RoomTypeService extends IService<RoomTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

