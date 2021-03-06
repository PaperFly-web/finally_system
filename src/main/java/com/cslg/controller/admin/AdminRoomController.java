package com.cslg.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cslg.entity.RoomEntity;
import com.cslg.service.RoomService;
import com.cslg.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("admin/cslg/room")
public class AdminRoomController {

    @Autowired
    private RoomService roomService;





    /**
     * 列表
     */
    @PostMapping("/list")
    public R list( Integer page, Integer pageSize){
        Page<RoomEntity> roomEntityPage = new Page<>(page,pageSize);
        Page<RoomEntity> page1 = roomService.page(roomEntityPage);
        return R.ok().put("data", page1);
    }


    /**
     * 信息
     */
    @PostMapping("/info/{roomId}")
    public R info(@PathVariable("roomId") Long roomId){
		RoomEntity room = roomService.getById(roomId);

        return R.ok().put("data", room);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(RoomEntity room,@RequestParam("img") MultipartFile file){
		roomService.save(room);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update( RoomEntity room){

		roomService.updateById(room);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete( Long[] roomIds){
		roomService.removeByIds(Arrays.asList(roomIds));

        return R.ok();
    }

}
