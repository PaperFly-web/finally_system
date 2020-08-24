package com.cslg.service;

import com.baomidou.mybatisplus.extension.service.IService;import com.cslg.entity.RoomOrderEntity;
import com.cslg.utils.PageUtils;
import com.cslg.utils.R;

import java.util.Map;

/**
 * 
 *
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
public interface RoomOrderService extends IService<RoomOrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    R addRoomOrder(RoomOrderEntity roomOrderEntity);
}

