package com.cslg.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cslg.entity.RoomEntity;
import com.cslg.entity.RoomOrderEntity;
import com.cslg.properties.RoomOrderProperties;
import com.cslg.service.RoomService;
import com.cslg.utils.CommonUtil;
import com.cslg.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("cslg/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     * 通过时间查询空闲会议室
     * 需要日期，时间段，页数，每页尺寸
     * @return
     */
    @PostMapping("/selectRoomByTime")
    public R selectRoomByTime(@RequestBody RoomOrderEntity roomOrderEntity, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List errorMsg = CommonUtil.getErrorMsg(bindingResult);
            return R.error("传入的参数有问题").put("reason",errorMsg).put("code",444);
        }
        roomOrderEntity.setDay(RoomOrderProperties.getDay(roomOrderEntity.getDay()));
        R r = roomService.selectRoomByTime(roomOrderEntity);
        return r;
    }



    /**
     * 列表,用户只能查询开放使用的会议室
     */
    @PostMapping("/list")
    public R list( Integer page, Integer pageSize){
        QueryWrapper<RoomEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_enabled",1);
        Page<RoomEntity> roomEntityPage = new Page<>(page,pageSize);
        Page<RoomEntity> page1 = roomService.page(roomEntityPage,queryWrapper);
        return R.ok().put("data", page1);
    }


    /**
     * 信息,用户只能查询开放使用的会议室
     */
    @PostMapping("/info/{roomId}")
    @PreAuthorize("hasAnyRole(3)")
    public R info(@PathVariable("roomId") Long roomId){
        QueryWrapper<RoomEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_enabled",1);
        RoomEntity room = roomService.getById(roomId);
        return R.ok().put("data", room);
    }


}
