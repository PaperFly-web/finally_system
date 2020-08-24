package com.cslg.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cslg.dao.RoomDao;
import com.cslg.entity.RoomEntity;
import com.cslg.entity.RoomOrderEntity;
import com.cslg.properties.RoomOrderProperties;
import com.cslg.service.RoomService;
import com.cslg.utils.PageUtils;
import com.cslg.utils.Query;
import com.cslg.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("roomService")
public class RoomServiceImpl extends ServiceImpl<RoomDao, RoomEntity> implements RoomService {

    @Autowired
    RoomDao roomDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RoomEntity> page = this.page(
                new Query<RoomEntity>().getPage(params),
                new QueryWrapper<RoomEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public R selectRoomByTime(RoomOrderEntity roomOrderEntity){
        Page<RoomEntity> objectPage = new Page<>(roomOrderEntity.getPage(),roomOrderEntity.getPageSize());
        IPage<RoomEntity> roomEntityIPage = roomDao.selectRoomByTime(objectPage, roomOrderEntity);
        return R.ok().put("data",roomEntityIPage);
    }


}