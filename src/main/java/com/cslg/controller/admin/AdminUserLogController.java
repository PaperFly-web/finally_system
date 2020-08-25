package com.cslg.controller.admin;

import com.cslg.entity.UserLogEntity;
import com.cslg.service.UserLogService;
import com.cslg.utils.PageUtils;
import com.cslg.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("admin/cslg/userLog")
public class AdminUserLogController {

    @Autowired
    private UserLogService userLogService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @PostMapping("/info/{logId}")
    public R info(@PathVariable("logId") Long logId){
		UserLogEntity userLog = userLogService.getById(logId);

        return R.ok().put("data", userLog);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save( UserLogEntity userLog){
		userLogService.save(userLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update( UserLogEntity userLog){
		userLogService.updateById(userLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete( Long[] logIds){
		userLogService.removeByIds(Arrays.asList(logIds));

        return R.ok();
    }

}
