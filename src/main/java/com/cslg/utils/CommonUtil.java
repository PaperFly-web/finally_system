package com.cslg.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
/**
*@desc:常用的工具类
*@param:* @param null:
*@return:* @return: null
*@author:paperfly
*@time:2020/8/22 10:12
*/
public class CommonUtil {
    @Autowired
    private static JavaMailSenderImpl mailSender;

    public static List getErrorMsg(BindingResult bindingResult){
        List list=new ArrayList();
        // 如果有异常的话，就会返回
        if (bindingResult.hasErrors()) {
            //获取所有的异常信息
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError error : allErrors) {
                list.add(error.getDefaultMessage());
            }
        }
        return list;
    }

    public static List getUserAuthList(Collection<GrantedAuthority> authorities){
        List list=new ArrayList();
        Iterator<GrantedAuthority> iterator = authorities.iterator();
        List roles = new ArrayList();
        while (iterator.hasNext()) {
            GrantedAuthority next = iterator.next();
            String authority = next.getAuthority();
            roles.add(authority);
        }
        return roles;
    }

    public static void sendCodeByEmail(String code){
        //这个只能发送简单的邮件
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setSubject("我的测试标题");
        mailMessage.setText("我的测试内容");
        mailMessage.setTo("212335676@qq.com");
        mailMessage.setFrom("1430978392@qq.com");
        mailSender.send(mailMessage);
    }

}
