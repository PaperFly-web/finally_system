package com.cslg.controller.user;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cslg.entity.RoomOrderEntity;
import com.cslg.properties.RoomOrderProperties;
import com.cslg.service.RoomOrderService;
import com.cslg.utils.CommonUtil;
import com.cslg.utils.R;
import com.cslg.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

//import org.apache.shiro.authz.annotation.RequiresPermissions;




/**
 * 
 *
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
@RestController
@RequestMapping("cslg/roomOrder")
public class RoomOrderController {

    @Autowired
    private RoomOrderService roomOrderService;

    /**
     * 查询用户订单详情，可以查看历史，也可以查看在处理中的
     */
    @PostMapping("/list")
    public R list(Integer page,Integer pageSize,Boolean isHistory){
        Page<RoomOrderEntity> objectPage = new Page<>(page, pageSize);
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        if(!isHistory){
            queryWrapper.gt("start_time",System.currentTimeMillis());
        }else {
            queryWrapper.lt("start_time",System.currentTimeMillis());
        }

        queryWrapper.eq("user_name",UserUtil.getUserName());
        queryWrapper.orderByAsc("order_time");
        Page<RoomOrderEntity> page1 = roomOrderService.page(objectPage, queryWrapper);
        return R.ok().put("data", page1);
    }


    /**
     * 信息
     */
    @PostMapping("/info/{roomOrderId}")
    public R info(@PathVariable("roomOrderId") Long roomOrderId){
		RoomOrderEntity roomOrder = roomOrderService.getById(roomOrderId);
        return R.ok().put("data", roomOrder);
    }

    /**
     * 生成订单
     */
    @PostMapping("/save")
    public R save(@Valid RoomOrderEntity roomOrder, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return R.error().put("code",444).put("msg",CommonUtil.getErrorMsg(bindingResult));
        }

        roomOrder.setOrderTime(DateTime.now());
          //默认申请状态，不通过
        roomOrder.setStatus(RoomOrderProperties.DEFAULT_STATUS);

        roomOrder.setOverTime(RoomOrderProperties.DEFAULT_OVER_TIME);
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
    public R update(Long roomOrderId,String activity,Integer peopleNum){

        QueryWrapper<RoomOrderEntity> wrapper=new QueryWrapper<>();
        //只能处理还没有使用的订单
        wrapper.or(w->w.eq("status",RoomOrderProperties.DEFAULT_STATUS).or().eq("status",RoomOrderProperties.NOW_PASS_STATUS).or().eq("status",RoomOrderProperties.NOW_REJ_STATUS));
        wrapper.eq("room_order_id",roomOrderId);
        //会议室开始30分钟前不能修改订单状态
        wrapper.lt("start_time",System.currentTimeMillis()+30*60*1000);
        //只能修改自己的
        wrapper.eq("user_name", UserUtil.getUserName());
        RoomOrderEntity roomOrderEntity=new RoomOrderEntity();
        roomOrderEntity.setActivity(activity);
        //修改信息好，订单状态修改为默认状态
        roomOrderEntity.setStatus(RoomOrderProperties.DEFAULT_STATUS);
        roomOrderEntity.setPeopleNum(peopleNum);
        boolean update = roomOrderService.update(roomOrderEntity, wrapper);
        if(update){
            return R.ok();
        }else {
            return R.error("更新失败").put("code",444);
        }


    }

    /**
     * 撤销申请会议室,只能撤销没有处理的申请
     */
    @PostMapping("/delete")
    public R delete(Long roomOrderId){
        QueryWrapper<RoomOrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",UserUtil.getUserName());
        queryWrapper.eq("room_order_id",roomOrderId);
        //会议室开始30分钟前不能撤销订单
        queryWrapper.lt("start_time",System.currentTimeMillis()+30*60*1000);
        //只能处理还没有使用的订单
        queryWrapper.or(w->w.eq("status",RoomOrderProperties.DEFAULT_STATUS).or().eq("status",RoomOrderProperties.NOW_PASS_STATUS).or().eq("status",RoomOrderProperties.NOW_REJ_STATUS));
        boolean remove = roomOrderService.remove(queryWrapper);
        if(remove){
            return R.ok("撤销申请会议室成功");
        }else {
            return R.error(444,"撤销申请会议室失败");
        }

    }



}
