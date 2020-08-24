package com.cslg.controller.leader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cslg.entity.RoomOrderEntity;
import com.cslg.service.RoomOrderService;
import com.cslg.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * 
 *
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
@RestController
@RequestMapping("leader/cslg/roomorder")
public class LeaderRoomOrderController {
    /**
     * 获取用户的信息
     */
    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Autowired
    private RoomOrderService roomOrderService;


    /**
     * 报表,按照订单时间升序查询
     */
    @PostMapping("/getRoomOrderByOrderTime")
    //@RequiresPermissions("cslg:roomorder:list")
    public R getRoomOrderByOrderTime(Integer page, Integer pageSize){

        Page<RoomOrderEntity> objectPage = new Page<>(page,pageSize);
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("order_time");
        //按照订单时间分页查询
        Page<RoomOrderEntity> page1 = roomOrderService.page(objectPage,queryWrapper);
        return R.ok().put("page", page1);
    }

    /**
     * 报表,按照用户名字查询（排序按照订单时间升序）
     */
    @PostMapping("/getRoomOrderByUserName")
    //@RequiresPermissions("cslg:roomorder:list")
    public R getRoomOrderByUserName( Integer page, Integer pageSize, String userName){
        Page<RoomOrderEntity> objectPage = new Page<>(page,pageSize);
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        queryWrapper.orderByDesc("order_time");
        //按照用户名字查询
        Page<RoomOrderEntity> page1 = roomOrderService.page(objectPage,queryWrapper);
        return R.ok().put("page", page1);
    }

    /**
     * 报表,按照会议室类型查询（排序按照订单时间升序）
     */
    @PostMapping("/getRoomOrderByRoomType")
    //@RequiresPermissions("cslg:roomorder:list")
    public R getRoomOrderByRoomType(Integer page
            , Integer pageSize
            , Integer roomType){
        Page<RoomOrderEntity> objectPage = new Page<>(page,pageSize);
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("room_id","select room_id from room where room_type="+roomType);
        queryWrapper.orderByDesc("order_time");
        //按照用户名字查询
        Page<RoomOrderEntity> page1 = roomOrderService.page(objectPage,queryWrapper);
        return R.ok().put("page", page1);
    }

    /**
     * 报表,按照会议室查询（排序按照订单时间升序）
     */
    @PostMapping("/getRoomOrderByRoomId")
    //@RequiresPermissions("cslg:roomorder:list")
    public R getRoomOrderByRoomId( Integer page
            ,Integer pageSize
            ,Integer roomId){
        Page<RoomOrderEntity> objectPage = new Page<>(page,pageSize);
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id",roomId);
        queryWrapper.orderByDesc("order_time");
        //按照用户名字查询
        Page<RoomOrderEntity> page1 = roomOrderService.page(objectPage,queryWrapper);
        return R.ok().put("page", page1);
    }
}
