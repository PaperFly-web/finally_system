package com.cslg.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cslg.entity.RoomOrderEntity;
import com.cslg.service.RoomOrderService;
import com.cslg.utils.PageUtils;
import com.cslg.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * 
 *
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
@RestController
@RequestMapping("admin/cslg/roomorder")
public class AdminRoomOrderController {
    /**
     * 获取用户的信息
     */
    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Autowired
    private RoomOrderService roomOrderService;

    /**
     * 查询还在申请的订单
     */
    @PostMapping("/list")
    //@RequiresPermissions("cslg:roomorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = roomOrderService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("cslg:roomorder:update")
    public R update( Long roomOrderId,  Integer status,  String rejectReason){
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_order_id",roomOrderId);
        RoomOrderEntity roomOrderEntity=new RoomOrderEntity();
        roomOrderEntity.setRejectReason(rejectReason);
        roomOrderEntity.setStatus(status);
        roomOrderEntity.setRoomOrderId(roomOrderId);
        roomOrderService.update(roomOrderEntity,queryWrapper);
        return R.ok();

    }





}
