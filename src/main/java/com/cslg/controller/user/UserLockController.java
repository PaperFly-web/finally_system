package com.cslg.controller.user;

import com.cslg.entity.UserLockEntity;
import com.cslg.service.UserLockService;
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
 * 这个表主要是防止用户频繁登陆错误  频繁获取验证码等，超过限制就锁定一段时间
 *
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
@RestController
@RequestMapping("cslg/userlock")
public class UserLockController {
    /**
     * 获取用户的信息
     */
    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Autowired
    private UserLockService userLockService;

    /**
     * 列表
     */
    @PostMapping("/list")
    //@RequiresPermissions("cslg:userlock:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userLockService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @PostMapping("/info/{lockId}")
   // @RequiresPermissions("cslg:userlock:info")
    public R info(@PathVariable("lockId") Long lockId){
		UserLockEntity userLock = userLockService.getById(lockId);

        return R.ok().put("data", userLock);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("cslg:userlock:save")
    public R save(@RequestBody UserLockEntity userLock){
		userLockService.save(userLock);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("cslg:userlock:update")
    public R update(UserLockEntity userLock){
		userLockService.updateById(userLock);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
   // @RequiresPermissions("cslg:userlock:delete")
    public R delete( Long[] lockIds){
		userLockService.removeByIds(Arrays.asList(lockIds));

        return R.ok();
    }

}
