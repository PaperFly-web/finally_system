package com.cslg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cslg.dao.UserDao;
import com.cslg.entity.UserEntity;
import com.cslg.service.UserService;
import com.cslg.utils.PageUtils;
import com.cslg.utils.Query;
import com.cslg.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserEntity login(String userName) {
        UserEntity login = userDao.login(userName);
        return login;
    }

    @Override
    public R registerUsers(List<UserEntity> list) {
        Integer integer = userDao.registerUsers(list);
        if(integer==list.size()){
            return R.ok("批量注册成功");
        }else {
            return R.error("批量注册失败").put("code",444);
        }

    }


}