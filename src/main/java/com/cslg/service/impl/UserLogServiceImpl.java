package com.cslg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cslg.dao.UserLogDao;
import com.cslg.entity.UserLogEntity;
import com.cslg.service.UserLogService;
import com.cslg.utils.PageUtils;
import com.cslg.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("userLogService")
public class UserLogServiceImpl extends ServiceImpl<UserLogDao, UserLogEntity> implements UserLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserLogEntity> page = this.page(
                new Query<UserLogEntity>().getPage(params),
                new QueryWrapper<UserLogEntity>()
        );

        return new PageUtils(page);
    }

}