package com.cslg.controller.user;

import com.cslg.entity.UserLogEntity;
import com.cslg.service.UserLogService;
import com.cslg.utils.PageUtils;
import com.cslg.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("cslg/userlog")
public class UserLogController {
    /**
     * 获取用户的信息
     */
    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Autowired
    private UserLogService userLogService;

    /**
     * 列表
     */
    @PostMapping("/list")
    //@RequiresPermissions("cslg:userlog:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userLogService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @PostMapping("/info/{logId}")
   // @RequiresPermissions("cslg:userlog:info")
    public R info(@PathVariable("logId") Long logId){
		UserLogEntity userLog = userLogService.getById(logId);

        return R.ok().put("data", userLog);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("cslg:userlog:save")
    public R save( UserLogEntity userLog){
		userLogService.save(userLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("cslg:userlog:update")
    public R update( UserLogEntity userLog){
		userLogService.updateById(userLog);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
   // @RequiresPermissions("cslg:userlog:delete")
    public R delete( Long[] logIds){
		userLogService.removeByIds(Arrays.asList(logIds));

        return R.ok();
    }

}
