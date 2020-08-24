package com.cslg.service;


import com.cslg.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service(value = "MyUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;



    @Autowired
    HttpServletRequest request;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity login = userService.login(username);

        if (login==null){
            //返回null就代表认证失败，security就抛出403  forbiden
            return null;
        }

        /**
         * 获取用户角色
         */
        String []roles=new String[login.getRoles().size()];
        for (int i=0;i<login.getRoles().size();i++){
            roles[i]=login.getRoles().get(i).getRoleName();
        }
        UserDetails build =
                User.withUsername(login.getUserName())
                        .password(login.getUserPwd())
                        .authorities(roles)
                        .disabled(login.getUserEnabled()!=1)
                        .build();
        return build;
    }
}
