package com.cslg.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cslg.entity.RoomEntity;
import com.cslg.entity.RoomOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
@Mapper
public interface RoomDao extends BaseMapper<RoomEntity> {
   IPage<RoomEntity> selectRoomByTime(Page<RoomEntity> page, @Param(Constants.ENTITY)RoomOrderEntity roomOrderEntity);
}
