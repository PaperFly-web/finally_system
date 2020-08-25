package com.cslg.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
*@desc:获取用户的详细信息
*@param:* @param null:
*@return:* @return: null
*@author:paperfly
*@time:2020/8/24 15:52
*/
public class UserUtil {
    public static String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
