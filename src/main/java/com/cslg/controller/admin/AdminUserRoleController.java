package com.cslg.controller.admin;

import com.cslg.entity.UserRoleEntity;
import com.cslg.service.UserRoleService;
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
@RequestMapping("admin/cslg/userRole")
public class AdminUserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userRoleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @PostMapping("/info/{userRoleId}")
    public R info(@PathVariable("userRoleId") Long userRoleId){
		UserRoleEntity userRole = userRoleService.getById(userRoleId);

        return R.ok().put("data", userRole);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save( UserRoleEntity userRole){
		userRoleService.save(userRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update( UserRoleEntity userRole){
		userRoleService.updateById(userRole);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete( Long[] userRoleIds){
		userRoleService.removeByIds(Arrays.asList(userRoleIds));

        return R.ok();
    }

}
