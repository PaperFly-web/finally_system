package com.cslg.controller.admin;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cslg.entity.RoomOrderEntity;
import com.cslg.properties.RoomOrderProperties;
import com.cslg.service.RoomOrderService;
import com.cslg.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
@RestController
@RequestMapping("admin/cslg/roomOrder")
public class AdminRoomOrderController {

    @Autowired
    private RoomOrderService roomOrderService;


    /**
    *@desc:管理员查询用户申请会议室详情
    *@param:* @param isHandle: 是否查询处理过的
    *@return:* @return: null
    *@author:paperfly
    *@time:2020/8/25 13:29
    */
    @PostMapping("/list")
    public R list(Integer page, Integer pageSize, Boolean isHandle) {
        Page<RoomOrderEntity> objectPage = new Page<>(page, pageSize);
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();

        if (!isHandle) {
            queryWrapper.eq("status", RoomOrderProperties.DEFAULT_STATUS);
        } else {
            queryWrapper.ne("status", RoomOrderProperties.DEFAULT_STATUS);
        }
        queryWrapper.orderByAsc("order_time");
        Page<RoomOrderEntity> page1 = roomOrderService.page(objectPage, queryWrapper);
        return R.ok().put("data", page1);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(Long romOrderId, Integer status, @RequestParam(required = false) String rejectReason) {

        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_order_id", romOrderId);
        RoomOrderEntity roomOrderEntity=new RoomOrderEntity();
        roomOrderEntity.setStatus(status);
        //如果通过申请则生成密码
        if(status==1){
            roomOrderEntity.setPwd(RandomUtil.randomNumbers(6));
        }else {
            roomOrderEntity.setRejectReason(rejectReason);
        }
        roomOrderService.update(roomOrderEntity, queryWrapper);
        RoomOrderEntity one = roomOrderService.getOne(queryWrapper);
        return R.ok().put("data",one);

    }


}
