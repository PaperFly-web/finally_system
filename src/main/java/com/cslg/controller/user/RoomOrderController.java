package com.cslg.controller.user;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cslg.entity.RoomOrderEntity;
import com.cslg.properties.RoomOrderProperties;
import com.cslg.service.RoomOrderService;
import com.cslg.utils.CommonUtil;
import com.cslg.utils.PageUtils;
import com.cslg.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
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
@RequestMapping("cslg/roomorder")
public class RoomOrderController {
    /**
     * 获取用户的信息
     */
    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Autowired
    private RoomOrderService roomOrderService;

    /**
     * 列表
     */
    @PostMapping("/list")
    //@RequiresPermissions("cslg:roomorder:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = roomOrderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @PostMapping("/info/{roomOrderId}")
   // @RequiresPermissions("cslg:roomorder:info")
    public R info(@PathVariable("roomOrderId") Long roomOrderId){
		RoomOrderEntity roomOrder = roomOrderService.getById(roomOrderId);
        return R.ok().put("data", roomOrder);
    }

    /**
     * 生成订单
     */
    @PostMapping("/save")
    //@RequiresPermissions("cslg:roomorder:save")
    public R save(@Valid RoomOrderEntity roomOrder, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return R.error().put("code",444).put("msg",CommonUtil.getErrorMsg(bindingResult));
        }

        roomOrder.setOrderTime(DateTime.now());
          //默认申请状态，不通过
        roomOrder.setStatus(-1);
        //测试数据
        //roomOrder.setDay(RoomOrderProperties.getDay(roomOrder.getDay()));
        roomOrder.setDay(DateUtil.parse("2020-08-24"));
        //roomOrder.setUserName(authentication.getName());
        roomOrder.setOverTime(30);
        roomOrder.setStartEndTime(RoomOrderProperties.getTimeDescription(roomOrder.getDay(),roomOrder.getTimeSlot()));
        roomOrder.setStartTime(RoomOrderProperties.getMills(roomOrder.getDay(),roomOrder.getTimeSlot(),true));
        roomOrder.setEndTime(RoomOrderProperties.getMills(roomOrder.getDay(),roomOrder.getTimeSlot(),false));
        R r = roomOrderService.addRoomOrder(roomOrder);

        return r;
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("cslg:roomorder:update")
    public R update(Long roomOrderId,String activity){

        QueryWrapper<RoomOrderEntity> wrapper=new QueryWrapper<>();
        //只能修改还没有处理的申请
        wrapper.eq("status",-1);
        wrapper.eq("room_order_id",roomOrderId);
        //只能修改自己的
        wrapper.eq("user_name",authentication.getName());
        RoomOrderEntity roomOrderEntity=new RoomOrderEntity();
        roomOrderEntity.setActivity(activity);

        boolean update = roomOrderService.update(roomOrderEntity, wrapper);
        if(update){
            return R.ok();
        }else {
            return R.error("更新失败").put("code",444);
        }


    }

    /**
     * 删除
     */
    @PostMapping("/delete")
   // @RequiresPermissions("cslg:roomorder:delete")
    public R delete(Long[] roomOrderIds){
		roomOrderService.removeByIds(Arrays.asList(roomOrderIds));

        return R.ok();
    }



}
