package com.cslg.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    //插入策略
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.setFieldValByName("userRegisterTime",new Date(),metaObject);
        this.setFieldValByName("logTime",new Date(),metaObject);
        this.setFieldValByName("orderTime",new Date(),metaObject);
    }

    //更新策略
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setFieldValByName("authenticTime",new Date(),metaObject);
    }
}