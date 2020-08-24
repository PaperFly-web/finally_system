package com.cslg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cslg.dao.UserLockDao;
import com.cslg.entity.UserLockEntity;
import com.cslg.service.UserLockService;
import com.cslg.utils.PageUtils;
import com.cslg.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("userLockService")
public class UserLockServiceImpl extends ServiceImpl<UserLockDao, UserLockEntity> implements UserLockService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserLockEntity> page = this.page(
                new Query<UserLockEntity>().getPage(params),
                new QueryWrapper<UserLockEntity>()
        );

        return new PageUtils(page);
    }

}