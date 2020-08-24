package com.cslg;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cslg.dao.RoomDao;
import com.cslg.entity.RoomEntity;
import com.cslg.entity.RoomOrderEntity;
import com.cslg.properties.RoomOrderProperties;
import com.cslg.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@SpringBootTest
@Slf4j
public class TestApplication {

    @Autowired
    RoomDao roomDao;

    @Test
    public void t1(){

        boolean before = DateTime.now().isBefore(RoomOrderProperties.getDay(new Date()));
    }

    @Test
    public void t2(){
        QueryWrapper<RoomEntity> wrapper=new QueryWrapper<>();
        wrapper.notIn("room_id","select room_id from room_order" +
                "                                where day=" +RoomOrderProperties.getDay(DateUtil.parse("2020-08-15"))+
                "                                  and time_slot=" +1+
                "                                  and status!="+0);
        Page<RoomEntity> objectPage = new Page<>(1,5);
        Page<RoomEntity> roomEntityPage = roomDao.selectPage(objectPage, wrapper);
        PageUtils pageUtils=new PageUtils(roomEntityPage);
        roomEntityPage.getRecords().forEach(System.out::println);
    }


}
