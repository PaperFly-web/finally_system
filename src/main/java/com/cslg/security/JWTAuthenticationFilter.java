package com.cslg.security;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cslg.entity.UserEntity;
import com.cslg.entity.UserLogEntity;
import com.cslg.properties.JwtProperties;
import com.cslg.service.UserLogService;
import com.cslg.service.UserService;
import com.cslg.utils.*;
import org.apache.http.HttpStatus;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
*@desc:验证用户名密码正确后 生成一个token并将token返回给客户端
*@param:* @param null:
*@return:* @return: null
*@author:paperfly
*@time:2020/8/21 12:21
*/
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {



    //认证管理器
    private AuthenticationManager authenticationManager;

    //公共的返回类
    private R r;

    //构造方法
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        //设置处理登录请求的url
        super.setFilterProcessesUrl("/login");
    }

    /**
     * 验证操作 接收并解析用户凭证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        /**
         * 从输入流中获取到登录的信息
         *     创建一个token并调用authenticationManager.authenticate() 让Spring security进行验证
         *     判断login是不是POST请求
         */
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        //获取requset中的用户参数
        String username = request.getParameter(JwtProperties.USER_NAME);
        String password = request.getParameter(JwtProperties.USER_PWD);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }
        //传递给security让他查询数据库比较做认证
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

    }

    /**
     * 验证【成功】后调用的方法
     * 若验证成功 生成token并返回
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain, Authentication authentication) throws IOException {
        //在filter中提前获取bean
        RedisTemplate redisTemplate = (RedisTemplate) WebApplicationContextUtil.getBean("redisTemplate", req);
        //生成随机的秘钥
        String SECRET_KEY = String.valueOf(RandomUtil.randomString(20));
        String token;
        // 生成Token(只需要传递一个秘钥和一个UserDetails)
        if(req.getParameter(JwtProperties.REMEMBER)!=null){
            //如果勾选了记住我就保存token7天,保存记住我参数
            token = MyJwtTokenUtil.createToken(SECRET_KEY,true, MyJwtTokenUtil.getDayMills(7),(User)authentication.getPrincipal());
            //把秘钥保存到redis中
            redisTemplate.opsForValue().set(JwtProperties.USER_TOKEN+token, SECRET_KEY);
            redisTemplate.expire(JwtProperties.USER_TOKEN+token,7, TimeUnit.DAYS);
        }else {
            //没有勾选保存记住我就保存1天
            token = MyJwtTokenUtil.createToken(SECRET_KEY,false,MyJwtTokenUtil.getDayMills(1), (User) authentication.getPrincipal());
            //把秘钥保存到redis中
            redisTemplate.opsForValue().set(JwtProperties.USER_TOKEN+token, SECRET_KEY);
            redisTemplate.expire(JwtProperties.USER_TOKEN+token,1, TimeUnit.DAYS);
        }

        // 设置编码 防止乱码问题
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        //返回前端登录信息,token信息
        PrintWriter out = resp.getWriter();

        Collection<GrantedAuthority> authorities = ((User) authentication.getPrincipal()).getAuthorities();
        List userAuthList = CommonUtil.getUserAuthList(authorities);
        UserEntity userEntity=new UserEntity();
        userEntity.setUserName(authentication.getName());
        userEntity.setRoles(userAuthList);
        Object principal = authentication.getPrincipal();
        System.out.println(principal.getClass().getName());
        r = R.ok().put("data", userEntity).put("token", token);
        out.write(JSONUtil.toJsonStr(r));
        out.flush();
        out.close();
        /**
         * 登录成功记录登录日志
         */
        saveUserLog(req);


    }

    /**
     * 验证【失败】调用的方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
       if (e instanceof LockedException) {
            r = R.error(444, "账户被锁定，请联系管理员!");
        } else if (e instanceof CredentialsExpiredException) {
            r = R.error(444, "密码过期，请联系管理员!");
        } else if (e instanceof AccountExpiredException) {
            r = R.error(444, "账户过期，请联系管理员!");
        } else if (e instanceof DisabledException) {
            r = R.error(403, "账户被禁用，请联系管理员!");
        } else if (e instanceof BadCredentialsException) {
            r = R.error(444, "用户名或者密码输入错误，请重新输入!");
        } else if (e instanceof InternalAuthenticationServiceException) {
            r = R.error(HttpStatus.SC_NOT_FOUND, "登录失败,可能用户未注册");
        }
        /**
         * 登录失败记录登录日志
         */
        saveUserLog(req);
        /**
         * 返回给前端的json数据
         */
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(JSONUtil.toJsonStr(r));
        out.flush();
        out.close();
    }


    public void saveUserLog(HttpServletRequest req) {
        //获取登录日志类(因为filter是在servlet之前创建，所以不能用@Auweired直接把类注入进来)
        UserLogService userLogService = (UserLogService) WebApplicationContextUtil.getBean("userLogService", req);
        UserLogEntity userLogEntity = new UserLogEntity();
        userLogEntity.setUserName(req.getParameter("userName"));
        userLogEntity.setIpAddress(IpUtil.getIpAddr(req));
        userLogEntity.setLogContent(r.get("msg").toString());
        userLogService.save(userLogEntity);
    }

    public void getUserInfoByUserName(HttpServletRequest req,String userName){
        UserService userService = (UserService) WebApplicationContextUtil.getBean("userService", req);
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();

    }
}