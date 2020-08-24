package com.cslg.properties;


/**
*@desc:jwt的常用信息
*@param:* @param null:
*@return:* @return: null
*@author:paperfly
*@time:2020/8/21 12:18
*/
public class JwtProperties {
    public static final String REMEMBER="remember";
    //存储在redis中token的前缀，用于区分其他数据
    public static final String USER_TOKEN="userToken:";
    public static final String USER_NAME="userName";
    public static final String USER_PWD="userPwd";
    public static final String BEARER="Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String ROLE="role";
    public static final Integer NOT_FOUND_TOKEN=404;
    public static final Integer INVALID_TOKEN=403;
    public static final Integer TOKEN_EXPIRED=444;
}
