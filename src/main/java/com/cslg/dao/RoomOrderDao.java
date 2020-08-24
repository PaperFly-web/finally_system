package com.cslg.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cslg.entity.RoomEntity;
import com.cslg.entity.RoomOrderEntity;
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
public interface RoomOrderDao extends BaseMapper<RoomOrderEntity> {
    int addRoomOrder(RoomOrderEntity roomOrderEntity);

}
