package com.cslg.controller.user;

import com.cslg.entity.RoleEntity;
import com.cslg.service.RoleService;
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
@RequestMapping("cslg/role")
public class RoleController {
    /**
     * 获取用户的信息
     */
    private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @Autowired
    private RoleService roleService;

    /**
     * 列表
     */
    @PostMapping("/list")
    //@RequiresPermissions("cslg:role:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = roleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @PostMapping("/info/{roleId}")
   // @RequiresPermissions("cslg:role:info")
    public R info(@PathVariable("roleId") Long roleId){
		RoleEntity role = roleService.getById(roleId);

        return R.ok().put("data", role);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("cslg:role:save")
    public R save( RoleEntity role){
		roleService.save(role);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("cslg:role:update")
    public R update( RoleEntity role){
		roleService.updateById(role);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
   // @RequiresPermissions("cslg:role:delete")
    public R delete(Long[] roleIds){
		roleService.removeByIds(Arrays.asList(roleIds));

        return R.ok();
    }

}
