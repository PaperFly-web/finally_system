package com.cslg.handler;

import cn.hutool.json.JSONUtil;
import com.cslg.properties.JwtProperties;
import com.cslg.utils.MyJwtTokenUtil;
import com.cslg.utils.R;
import com.cslg.utils.WebApplicationContextUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 处理退出登录的处理器
 */
public class MyLogoutHandler implements LogoutSuccessHandler {



    @Override
    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();


        //在filter中提前获取bean
        RedisTemplate redisTemplate = (RedisTemplate) WebApplicationContextUtil.getBean("redisTemplate", req);
        //获取token
        String token = MyJwtTokenUtil.getToken(req);
        String sec = (String)redisTemplate.opsForValue().get(JwtProperties.USER_TOKEN + token);
        //获取到秘钥就可以放行
        if(sec!=null){
            //查看记住我有没有勾选
            Boolean remember = (Boolean)MyJwtTokenUtil.getRemember(token, sec);
            if(!remember){
                //没有勾选记住我功能就把redis的token删除掉
                redisTemplate.delete(JwtProperties.USER_TOKEN+token);
                out.write(JSONUtil.toJsonStr(R.ok("退出系统成功,缓存的token已经删除")) );
            }
            out.write(JSONUtil.toJsonStr(R.ok("退出系统成功,缓存的token还有效")) );
        }else {
            out.write(JSONUtil.toJsonStr(R.error("退出系统失败,传递的token无效").put("code",401)) );
        }

        out.flush();
        out.close();
    }
}
