package com.cslg.schedule;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cslg.entity.RoomOrderEntity;
import com.cslg.properties.RoomOrderProperties;
import com.cslg.service.RoomOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @desc:定时处理会议室订单数据
 * @param:* @param null:
 * @return:* @return: null
 * @author:paperfly
 * @time:2020/8/23 18:31
 */
@Service
@Slf4j
public class RoomOrderSchedule {
    @Autowired
    private RoomOrderService roomOrderService;

    /**
     * 每天9,13点定时，处理申请通过和决绝的订单，修改状态为历史状态
     */
    @Scheduled(cron = "1 0 9,13 * * 1-7")
    public void autoModifyRoomOrder1() {

        RoomOrderEntity roomOrderEntity = new RoomOrderEntity();


        //修改申请通过的订单状态
        QueryWrapper<RoomOrderEntity> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("status", RoomOrderProperties.NOW_PASS_STATUS);
        queryWrapper2.lt("start_time", System.currentTimeMillis());
        roomOrderEntity.setStatus(RoomOrderProperties.HISTORY_PASS_STATUS);
        roomOrderEntity.setRejectReason(null);
        roomOrderEntity.setAuthenticTime(null);
        roomOrderService.update(roomOrderEntity, queryWrapper2);

        //修改决绝通过的订单状态
        QueryWrapper<RoomOrderEntity> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("status", RoomOrderProperties.NOW_REJ_STATUS);
        queryWrapper3.lt("start_time", System.currentTimeMillis());
        roomOrderEntity.setStatus(RoomOrderProperties.HISTORY_REJ_STATUS);
        roomOrderEntity.setRejectReason(null);
        roomOrderEntity.setAuthenticTime(null);
        roomOrderService.update(roomOrderEntity, queryWrapper3);
        log.info("上午定时任务开始---------定时处理订单业务");
    }

    /**
     * 每天15/19点30分，定时，处理申请通过和决绝的订单，修改状态为历史状态
     */
    @Scheduled(cron = "1 30 15,19 * * 1-7")
    public void autoModifyRoomOrder2() {

        RoomOrderEntity roomOrderEntity = new RoomOrderEntity();


        //修改申请通过的订单状态
        QueryWrapper<RoomOrderEntity> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("status", RoomOrderProperties.NOW_PASS_STATUS);
        queryWrapper2.lt("start_time", System.currentTimeMillis());
        roomOrderEntity.setStatus(RoomOrderProperties.HISTORY_PASS_STATUS);
        roomOrderService.update(roomOrderEntity, queryWrapper2);

        //修改决绝通过的订单状态
        QueryWrapper<RoomOrderEntity> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("status", RoomOrderProperties.NOW_REJ_STATUS);
        queryWrapper3.lt("start_time", System.currentTimeMillis());
        roomOrderEntity.setStatus(RoomOrderProperties.HISTORY_REJ_STATUS);
        roomOrderService.update(roomOrderEntity, queryWrapper3);
        log.info("下午定时任务开始--------定时处理订单业务");
    }

    /**
     * 15/19点定时处理
     * 会议开始30分钟前管理员还没有处理的申请，自动处理
     */
    @Scheduled(cron = "1 0 15,19 * * 1-7")
    public void autoModifyRoomOrder3(){
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("start_time", System.currentTimeMillis()-30*60*1000);
        queryWrapper.eq("status", RoomOrderProperties.DEFAULT_STATUS);
        RoomOrderEntity roomOrderEntity = new RoomOrderEntity();
        roomOrderEntity.setStatus(RoomOrderProperties.HISTORY_PASS_STATUS);
        roomOrderEntity.setRejectReason("对不起啊,管理员最近比较忙,未能处理您的申请");
        roomOrderEntity.setAuthenticTime(DateTime.now());
        roomOrderService.update(roomOrderEntity, queryWrapper);
    }

    /**
     * 12/8点30分定时处理
     * 会议开始30分钟前管理员还没有处理的申请，自动处理
     */
    @Scheduled(cron = "1 30 8,12 * * 1-7")
    public void autoModifyRoomOrder4(){
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("start_time", System.currentTimeMillis());
        queryWrapper.eq("status", RoomOrderProperties.DEFAULT_STATUS);
        RoomOrderEntity roomOrderEntity = new RoomOrderEntity();
        roomOrderEntity.setStatus(RoomOrderProperties.HISTORY_PASS_STATUS);
        roomOrderEntity.setRejectReason("对不起啊,管理员最近比较忙,未能处理您的申请");
        roomOrderEntity.setAuthenticTime(DateTime.now());
        roomOrderService.update(roomOrderEntity, queryWrapper);
    }
}
