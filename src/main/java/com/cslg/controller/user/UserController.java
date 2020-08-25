package com.cslg.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cslg.entity.UserEntity;
import com.cslg.service.UserService;
import com.cslg.utils.R;
import com.cslg.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * @author paperfly
 * @email 1430978392@qq.com
 * @date 2020-08-15 12:25:25
 */
@RestController
@RequestMapping("cslg/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 用户查看个人信息
     */
    @PostMapping("/info")
    public R info() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", UserUtil.getUserName());
        UserEntity user = userService.getOne(queryWrapper);
        return R.ok().put("data", user);
    }


    /**
     * 修改用户密码
     */
    @PostMapping("/update/pwd")
    public R update(String userPwd, String userOldPwd) {
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        //查询用户的信息，比对传入的老密码是否正确
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",UserUtil.getUserName());
        UserEntity one = userService.getOne(queryWrapper);
        boolean matches = bCryptPasswordEncoder.matches(userOldPwd, one.getUserPwd());
        //如果比对密码成功,可以修改密码
        if(matches){
            UserEntity userEntity = new UserEntity();
            userEntity.setUserPwd(bCryptPasswordEncoder.encode(userPwd));
            updateWrapper.eq("user_name", UserUtil.getUserName());
            boolean update = userService.update(userEntity, updateWrapper);
            if (update) {
                return R.ok("修改密码成功");
            } else {
                return R.error("修改密码失败").put("code",444);
            }
        }else {
            return R.error("修改密码失败").put("code",444).put("reason","旧密码不正确");
        }


    }


}
