package com.cslg.security;

import cn.hutool.json.JSONUtil;
import com.cslg.utils.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
             AuthenticationException authException) throws IOException, ServletException {

    response.setCharacterEncoding("utf-8");
    response.setContentType("text/javascript;charset=utf-8");
    response.setStatus(401);
    response.getWriter().print(JSONUtil.toJsonStr(R.error("forbidden")
            .put("code",401)
            .put("reason",authException.getMessage())
            .put("suggest","请重新登录")));
  }
}