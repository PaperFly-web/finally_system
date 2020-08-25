package com.cslg.controller.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cslg.entity.RoomTypeEntity;
import com.cslg.service.RoomTypeService;
import com.cslg.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

//import org.apache.shiro.authz.annotation.RequiresPermissions;




/**
 * 
 *
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
@RestController
@RequestMapping("cslg/roomType")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestParam Integer page,@RequestParam Integer pageSize){
        Page<RoomTypeEntity> objectPage = new Page<>(page,pageSize);
        Page<RoomTypeEntity> page1 = roomTypeService.page(objectPage);
        return R.ok().put("data", page1);
    }


    /**
     * 信息
     */
    @PostMapping("/info/{roomTypeId}")
    public R info(@PathVariable("roomTypeId") Long roomTypeId){
		RoomTypeEntity roomType = roomTypeService.getById(roomTypeId);
        return R.ok().put("data", roomType);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save( RoomTypeEntity roomType){
		roomTypeService.save(roomType);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update( RoomTypeEntity roomType){
		roomTypeService.updateById(roomType);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete( Long[] roomTypeIds){
		roomTypeService.removeByIds(Arrays.asList(roomTypeIds));

        return R.ok();
    }

}
