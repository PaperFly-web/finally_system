package com.cslg.controller.leader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cslg.entity.RoomOrderEntity;
import com.cslg.service.RoomOrderService;
import com.cslg.utils.MyFileUtil;
import com.cslg.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * 
 *
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
@RestController
@RequestMapping("leader/cslg/roomOrder")
public class LeaderRoomOrderController {

    @Autowired
    private RoomOrderService roomOrderService;


    /**
     * 报表,按照订单时间降序查询
     */
    @PostMapping("/getRoomOrderByOrderTime")
    public R getRoomOrderByOrderTime(Integer page, Integer pageSize){

        Page<RoomOrderEntity> objectPage = new Page<>(page,pageSize);
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("order_time");
        //按照订单时间分页查询
        Page<RoomOrderEntity> page1 = roomOrderService.page(objectPage,queryWrapper);
        return R.ok().put("page", page1);
    }

    /**
     * 报表,按照用户名字查询（排序按照订单时间降序）
     */
    @PostMapping("/getRoomOrderByUserName")
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
     * 报表,按照会议室类型查询（排序按照订单时间降序）
     */
    @PostMapping("/getRoomOrderByRoomType")
    public R getRoomOrderByRoomType(Integer page
            , Integer pageSize
            , String  roomTypeName){
        Page<RoomOrderEntity> objectPage = new Page<>(page,pageSize);
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_type_name",roomTypeName);
        queryWrapper.orderByDesc("order_time");
        Page<RoomOrderEntity> page1 = roomOrderService.page(objectPage,queryWrapper);
        return R.ok().put("page", page1);
    }

    /**
     * 报表,按照会议室查询（排序按照订单时间降序）
     */
    @PostMapping("/getRoomOrderByRoomId")
    public R getRoomOrderByRoomId( Integer page
            ,Integer pageSize
            ,Integer roomId){
        Page<RoomOrderEntity> objectPage = new Page<>(page,pageSize);
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id",roomId);
        queryWrapper.orderByDesc("order_time");
        Page<RoomOrderEntity> page1 = roomOrderService.page(objectPage,queryWrapper);
        return R.ok().put("page", page1);
    }
    /**
     * 下载excel,报表,按照订单时间降序查询
     */
    @PostMapping("/getRoomOrderByOrderTimeExcel")
    public void getRoomOrderByOrderTimeExcel(Integer page, Integer pageSize, HttpServletResponse response){

        Page<RoomOrderEntity> objectPage = new Page<>(page,pageSize);
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("order_time");
        //按照订单时间分页查询
        Page<RoomOrderEntity> page1 = roomOrderService.page(objectPage,queryWrapper);
        List<RoomOrderEntity> records = page1.getRecords();
        MyFileUtil.downloadExcel(response,records,RoomOrderEntity.class,"roomOrderByTime.xls");
    }

    /**
     * 下载excel,报表,按照用户名字查询（排序按照订单时间降序）
     */
    @PostMapping("/getRoomOrderByUserNameExcel")
    public void getRoomOrderByUserNameExcel( Integer page, Integer pageSize, String userName,HttpServletResponse response){
        Page<RoomOrderEntity> objectPage = new Page<>(page,pageSize);
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        queryWrapper.orderByDesc("order_time");
        //按照用户名字查询
        Page<RoomOrderEntity> page1 = roomOrderService.page(objectPage,queryWrapper);
        MyFileUtil.downloadExcel(response,page1.getRecords(),RoomOrderEntity.class,userName+".xls");

    }

    /**
     * 下载excel,报表,按照会议室类型查询（排序按照订单时间降序）
     */
    @PostMapping("/getRoomOrderByRoomTypeExcel")
    public void getRoomOrderByRoomTypeExcel(Integer page
            , Integer pageSize
            , String  roomTypeName
    ,HttpServletResponse response){
        Page<RoomOrderEntity> objectPage = new Page<>(page,pageSize);
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("room_type_name",roomTypeName);
        queryWrapper.orderByDesc("order_time");
        Page<RoomOrderEntity> page1 = roomOrderService.page(objectPage,queryWrapper);
        MyFileUtil.downloadExcel(response,page1.getRecords(),RoomOrderEntity.class,"roomTypeOrder.xls");
    }


    /**
     * 下载excel,报表,按照会议室查询（排序按照订单时间降序）
     */
    @PostMapping("/getRoomOrderByRoomIdExcel")
    public void getRoomOrderByRoomIdExcel( Integer page
            ,Integer pageSize
            ,Integer roomId
    ,HttpServletResponse response){
        Page<RoomOrderEntity> objectPage = new Page<>(page,pageSize);
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id",roomId);
        queryWrapper.orderByDesc("order_time");
        Page<RoomOrderEntity> page1 = roomOrderService.page(objectPage,queryWrapper);
        MyFileUtil.downloadExcel(response,page1.getRecords(),RoomOrderEntity.class,"roomOrder.xls");
    }
}
