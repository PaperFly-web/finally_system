package com.cslg.security;

import cn.hutool.json.JSONUtil;
import com.cslg.properties.JwtProperties;
import com.cslg.utils.MyJwtTokenUtil;
import com.cslg.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 登录成功后 走此类进行鉴权操作
 */
@Slf4j
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {


    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 在过滤之前和之后执行的事件
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //解析token，返回信息
        R r = MyJwtTokenUtil.parseToken(request);

        if((Integer) r.get("code")!=200){
            //如果获取不token和秘钥就可以放行（因为有的资源是匿名用户也可以访问的）
            if(r.get("code").equals(JwtProperties.NOT_FOUND_TOKEN)){
                log.info(r.get("msg").toString());
            }else {
                //如果携带了token但是解密不出来就返回一个错误信息
                returnMsg(response,r);
                log.info(r.get("msg").toString());
                return;
            }

        }else {
            String userName = (String) r.get(JwtProperties.USER_NAME);
            List roles = (List<String>) r.get(JwtProperties.ROLE);
            if(userName==null){
                log.info("用户名为空");
                returnMsg(response,R.error("forbidden").put("code", 403).put("msg", "用户名为空"));
                return;

            }else {
                try {
                    //授权检查通过
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userName, null, roles));
                } catch (Exception e) {
                    //返回json形式的错误信息
                    String reason = "统一处理，原因：" + e.getMessage();
                    returnMsg(response,R.error("error").put("code", 403).put("reason", reason));
                    return;
                }

            }
        }
        super.doFilterInternal(request, response, chain);
    }


    /**
     * 返回响应流给前端
     * @param response
     * @param r
     */
    public void returnMsg(HttpServletResponse response,R r){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus((Integer) r.get("code"));
        try {
            response.getWriter().write(JSONUtil.toJsonStr(r));
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}