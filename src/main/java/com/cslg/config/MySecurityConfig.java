package com.cslg.config;


import com.cslg.handler.MyLogoutHandler;
import com.cslg.properties.JwtProperties;
import com.cslg.security.JWTAuthenticationEntryPoint;
import com.cslg.security.JWTAuthenticationFilter;
import com.cslg.security.JWTAuthorizationFilter;
import com.cslg.service.MyUserDetailsService;
import com.cslg.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;


/**
 * @EnableGlobalMethodSecurity: 开启注解方式
*@desc:
*@param:* @param null:
*@return:* @return: null
*@author:paperfly
*@time:2020/8/21 18:29
*/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    HttpServletRequest request;

    @Autowired
    UserLogService userLogService;

    //注入自己的service
    @Autowired
    @Qualifier("MyUserDetailsService")
    MyUserDetailsService myUserDetailsService;

    //给资源授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置权限管理规则
        http.authorizeRequests()
                //允许匿名访问的链接
                .antMatchers("/login").permitAll()
                .antMatchers("/admin/**").hasAnyAuthority("2")
                .antMatchers("/leader/**").hasAnyAuthority("3")
                //其他所有请求需要身份认证
                .anyRequest().permitAll()
                .and()
                // 添加JWT登录拦截器,这个过滤器是继承UsernamePasswordAuthenticationFilter，所以就不使用它默认的用户密码过滤器，就是使用我们自己的
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                // 添加JWT鉴权拦截器，同理
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                // 设置Session的创建策略为：Spring Security永不创建HttpSession 不使用HttpSession来获取SecurityContext
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 异常处理
                .exceptionHandling()
                // 认证失败的异常处理
                .authenticationEntryPoint(new JWTAuthenticationEntryPoint());

        http.csrf()
                //关闭网站攻击
                .disable();
        //开启跨域请求
        http.cors();
        http.formLogin()
                .passwordParameter(JwtProperties.USER_PWD)
                .usernameParameter(JwtProperties.USER_NAME);
        http.rememberMe()
                //设置记住我，只要登录的时候携带了remember这个参数，不管他的值，就代表记住我。
                // 默认保存14天，保存的数据在cookie中
                .rememberMeParameter(JwtProperties.REMEMBER);

        http.logout()
                .logoutUrl("/logout")
                //退出登录
                .logoutSuccessHandler(new MyLogoutHandler());

    }
    //测试使用
    /*//给资源授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //设置权限管理规则
        http.authorizeRequests().anyRequest().permitAll();


        http.csrf()
                //关闭网站攻击
                .disable();
        //开启跨域请求
        http.cors();
        http.formLogin()
                .passwordParameter(JwtProperties.USER_PWD)
                .usernameParameter(JwtProperties.USER_NAME)
                .successHandler((req, resp, authentication) -> {//登录成功
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(JSONUtil.toJsonStr(R.ok().put("data",authentication.getPrincipal())) );
                    out.flush();
                    out.close();
                })//前后端分离
                .failureHandler((req, resp, e) -> {//登录失败
                    R r;
                    if (e instanceof LockedException) {
                        r=R.error("账户被锁定，请联系管理员!").put("code",403);
                    } else if (e instanceof CredentialsExpiredException) {
                        r=R.error("密码过期，请联系管理员!").put("code",403);
                    } else if (e instanceof AccountExpiredException) {
                        r=R.error("账户过期，请联系管理员!").put("code",403);
                    } else if (e instanceof DisabledException) {
                        r=R.error("账户被禁用，请联系管理员!").put("code",403);
                    } else if (e instanceof BadCredentialsException) {
                        r=R.error("用户名或者密码输入错误，请重新输入!").put("code",403);
                    }else {
                        r=R.error().put("code",403);
                    }
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(JSONUtil.toJsonStr(r));
                    out.flush();
                    out.close();
                });//前后端分离
        http.rememberMe()
                //设置记住我，只要登录的时候携带了remember这个参数，不管他的值，就代表记住我。
                // 默认保存14天，保存的数据在cookie中
                .rememberMeParameter(JwtProperties.REMEMBER);

        http.logout()
                .logoutUrl("/logout")
                //退出登录
                .logoutSuccessHandler(new MyLogoutHandler());

    }*/


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //把自己的service交给Security
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    //注入密码加密的算法
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }




}
