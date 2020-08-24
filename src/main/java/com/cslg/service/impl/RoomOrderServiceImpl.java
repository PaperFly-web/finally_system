package com.cslg.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cslg.dao.RoomOrderDao;
import com.cslg.entity.RoomOrderEntity;
import com.cslg.properties.RoomOrderProperties;
import com.cslg.service.RoomOrderService;
import com.cslg.utils.PageUtils;
import com.cslg.utils.Query;
import com.cslg.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;


@Service("roomOrderService")
public class RoomOrderServiceImpl extends ServiceImpl<RoomOrderDao, RoomOrderEntity> implements RoomOrderService {

    @Autowired
    RoomOrderDao roomOrderDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RoomOrderEntity> page = this.page(
                new Query<RoomOrderEntity>().getPage(params),
                new QueryWrapper<RoomOrderEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public R addRoomOrder(RoomOrderEntity roomOrder) {
        //查看日期是否是明天以后
        if(roomOrder.getDay()==null||
                !DateTime.now().isBefore(RoomOrderProperties.getDay(roomOrder.getDay()))
        ){
            return R.error("预约日期异常").put("code",444);
        }
        int i = roomOrderDao.addRoomOrder(roomOrder);
        if(i>0){
            return R.ok("申请预约会议室成功");
        }else {
            return R.error(444,"申请预约会议室失败,可能原因如下:【已经被预约,会议室不存在,会议室不可用】");
        }
    }

}