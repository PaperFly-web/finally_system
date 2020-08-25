package com.cslg.controller.admin;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cslg.entity.UserEntity;
import com.cslg.service.UserService;
import com.cslg.utils.MyFileUtil;
import com.cslg.utils.PageUtils;
import com.cslg.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
@RequestMapping("admin/cslg/user")
@Slf4j
public class AdminUserController {

    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }

    @PostMapping("/selectUserIsExist")
    public R selectUserIsExist(@RequestParam("file") MultipartFile file){
        R r = MyFileUtil.readExcel(file);
        QueryWrapper queryWrapper = new QueryWrapper();
        String[] userName;
        if(r.get("code").equals(200)){
            List<List<String>> lists = (List<List<String>>) r.get("data");
            userName=new String[lists.size()];
            for (int i=0;i<lists.size();i++){
                userName[i]=lists.get(i).get(1);
            }
            queryWrapper.in("user_name", userName);
        }

        List<UserEntity> list = userService.list(queryWrapper);
        if(list.size()==0){
            return R.notFound("没有查询到有重复的用户名");
        }
        List users=new ArrayList();
        for (UserEntity user : list) {
            users.add(user.getUserName());
        }
        return R.ok("查询到如下用户名重复").put("data",users);
    }
    /**
     * 信息
     */
    @PostMapping("/info/{userId}")
    public R info(@PathVariable("userId") Long userId){
		UserEntity user = userService.getById(userId);
        return R.ok().put("data", user);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save( @RequestParam("file") MultipartFile file){
        //读取excel中的数据
        R r = MyFileUtil.readExcel(file);
        QueryWrapper queryWrapper = new QueryWrapper();
        String[] userName;

        List<List<String>> lists=new ArrayList<>();
        if(r.get("code").equals(200)){
            lists = (List<List<String>>) r.get("data");
            lists.remove(0);
            userName=new String[lists.size()];
            for (int i=0;i<lists.size();i++){
                userName[i]=lists.get(i).get(1);
            }
            queryWrapper.in("user_name", userName);
        }

        List<UserEntity> userExist = userService.list(queryWrapper);


        for (int i=0;i<userExist.size();i++){
            for (int j=0;j<lists.size();j++){
                if(lists.get(j).get(1).equals(userExist.get(i).getUserName())){

                    lists.remove(j);
                    j=j-1;
                }
            }
        }
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        List<UserEntity> userEntityList=new ArrayList<>();
        for (int i=0;i<lists.size();i++){
            UserEntity userEntity=new UserEntity();
            userEntity.setUserName(lists.get(i).get(1));
            userEntity.setUserPwd(bCryptPasswordEncoder.encode(lists.get(i).get(2)));
            userEntity.setUserTrueName(lists.get(i).get(3));
            userEntity.setUserDepartment(lists.get(i).get(4));
            userEntity.setUserEmail(lists.get(i).get(5));
            userEntity.setUserEnabled(Integer.valueOf(lists.get(i).get(6)));
            userEntity.setUserRegisterTime(DateUtil.parse(lists.get(i).get(7)));
            userEntityList.add(userEntity);
        }
        if(userEntityList.size()==0){
            return R.notFound("没有发现可以注册的用户");
        }
        R r1 = userService.registerUsers(userEntityList);
        return r1;
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update( UserEntity user){
		userService.updateById(user);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] userIds){
		userService.removeByIds(Arrays.asList(userIds));
        return R.ok();
    }

    /**
    *@desc:获取用户的excel表
    *@param:* @param null:
    *@return:* @return: null
    *@author:paperfly
    *@time:2020/8/22 19:20
    */
    @GetMapping("/getAllUserExcel")
    public void getAllUserExcel(HttpServletResponse response){

        List<UserEntity> list = userService.list(null);

        MyFileUtil.downloadExcel(response,list,UserEntity.class,"user.xls");

    }

    @PostMapping("/readExcel")
    public  R readExcel( @RequestParam("file") MultipartFile file) {
        R r = MyFileUtil.readExcel(file);
        if(r.get("code").equals(200)){
            return r;
        }else {
            return r;
        }
    }
}
