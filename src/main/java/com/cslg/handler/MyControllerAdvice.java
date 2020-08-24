package com.cslg.handler;

import com.cslg.utils.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
*@desc:全局异常处理器
*@param:* @param null:
*@return:* @return: null
*@author:paperfly
*@time:2020/8/21 22:34
*/
@RestControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(Exception.class)
    public R  doException(Exception e){
        System.out.println(e.getClass().getName());
        return R.error().put("msg",e.getMessage()).put("reason","系统出现了异常");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R doAccessDeniedException(AccessDeniedException e){
        return R.error().put("msg",e.getMessage()).put("code",403).put("reason","你的权限不足");
    }

}