package com.cslg.Schedule;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cslg.entity.RoomOrderEntity;
import com.cslg.service.RoomOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
*@desc:把到期的会议室申请还没有审核的自动审核
*@param:* @param null:
*@return:* @return: null
*@author:paperfly
*@time:2020/8/23 18:31
*/
@Service
@Slf4j
public class RoomOrderSchedule {
    @Autowired
    private RoomOrderService roomOrderService;

    @Scheduled(cron = "1 0 9,13 * * 1-7")
    public void autoModifyRoomOrder1(){
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("start_time",System.currentTimeMillis());
        queryWrapper.eq("status",-1);
        RoomOrderEntity roomOrderEntity=new RoomOrderEntity();
        roomOrderEntity.setStatus(0);
        roomOrderEntity.setRejectReason("对不起啊,管理员最近比较忙,未能处理您的申请");
        roomOrderEntity.setAuthenticTime(DateTime.now());
        roomOrderService.update(roomOrderEntity,queryWrapper);
        log.info("处理管理员还没处理的会议室申请----------上午定时任务开始");
    }

    @Scheduled(cron = "1 30 15,19 * * 1-7")
    public void autoModifyRoomOrder2(){
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lt("start_time",System.currentTimeMillis());
        queryWrapper.eq("status",-1);
        RoomOrderEntity roomOrderEntity=new RoomOrderEntity();
        roomOrderEntity.setStatus(0);
        roomOrderEntity.setRejectReason("对不起啊,管理员最近比较忙,未能处理您的申请");
        roomOrderEntity.setAuthenticTime(DateTime.now());
        roomOrderService.update(roomOrderEntity,queryWrapper);
        log.info("处理管理员还没处理的会议室申请------下午定时任务开始");
    }
}
