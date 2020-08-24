package com.cslg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cslg.dao.RoomTypeDao;
import com.cslg.entity.RoomTypeEntity;
import com.cslg.service.RoomTypeService;
import com.cslg.utils.PageUtils;
import com.cslg.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("roomTypeService")
public class RoomTypeServiceImpl extends ServiceImpl<RoomTypeDao, RoomTypeEntity> implements RoomTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RoomTypeEntity> page = this.page(
                new Query<RoomTypeEntity>().getPage(params),
                new QueryWrapper<RoomTypeEntity>()
        );

        return new PageUtils(page);
    }

}