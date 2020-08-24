package com.cslg.utils;

import com.cslg.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
/**
*@desc:JWT的工具类
*@param:* @param null:
*@return:* @return: null
*@author:paperfly
*@time:2020/8/21 12:46
*/
public class MyJwtTokenUtil {

    //默认过期时间1天
    public static long expTime = 1000 * 24 * 60 * 60 *1;

    public static long getDayMills(Integer day){
        return 1000 * 24 * 60 * 60 *day;
    }

    public static long getHourMills(Integer hour){
        return 1000  * 60 * 60 *hour;
    }


    /**
     * 生成是否记住我的token
     * @param sec
     * @param remember
     * @param expTime
     * @param user
     * @return
     */
    public static String createToken(String sec,Boolean remember,Long expTime, User user) {
        Map<String, Object> header = new HashMap<>();
        // 从User中获取权限信息,并把权限信息放入list集合里面
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        Iterator<GrantedAuthority> iterator = authorities.iterator();
        List roles = new ArrayList();

        header.put("alg", "HS256");
        header.put("type", "JWT");
        while (iterator.hasNext()) {
            GrantedAuthority next = iterator.next();
            String authority = next.getAuthority();
            roles.add(authority);
        }
        String token = Jwts
                .builder()
                .setHeader(header)
                //自定义的数据部分
                .claim(JwtProperties.ROLE, roles)
                .claim(JwtProperties.USER_NAME, user.getUsername())
                .claim(JwtProperties.REMEMBER,remember)
                //签发人
                .setIssuer("paperfly")
                //签发时间
                .setIssuedAt(new Date())
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + expTime))

                /**
                 * 签名
                 * JWT对key的长度是有要求的，以这里SHA-256为例，就需要256位的key
                 * 我这里直接使用他的HS256加密算法，转换成SH256
                 */
                .signWith(SignatureAlgorithm.HS256, sec)
                //压缩
                .compact();
        return token;
    }
    /**
     * 校验Token是否过期
     */
    public static boolean isExpiration(String token, String sec) {
        Claims claims = Jwts.parser().setSigningKey(sec).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }

    /**
     * 校验Token
     */
    public static Claims checkJWT(String token, String secretKet) {

        try {
            final Claims claims = Jwts.parser().setSigningKey(secretKet).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从Token中获取username
     */
    public static String getUsername(String token, String secretKet) {
        Claims claims = Jwts.parser().setSigningKey(secretKet).parseClaimsJws(token).getBody();
        return claims.get(JwtProperties.USER_NAME).toString();
    }

    /**
     * 从Token中获取用户角色
     */
    public static Object getUserRole(String token, String secretKet) {
        Claims claims = Jwts.parser().setSigningKey(secretKet).parseClaimsJws(token).getBody();
        return claims.get(JwtProperties.ROLE);
    }

    /**
     * 从Token中获取用户角色
     */
    public static Object getRemember(String token, String secretKet) {
        Claims claims = Jwts.parser().setSigningKey(secretKet).parseClaimsJws(token).getBody();
        return claims.get(JwtProperties.REMEMBER);
    }

    /**
     * 通过请求信息，解析token
     *
     * @param request
     * @return
     */
    public static R parseToken(HttpServletRequest request) {
        //在filter中提前获取bean
        RedisTemplate redisTemplate = (RedisTemplate) WebApplicationContextUtil.getBean("redisTemplate", request);
        //获取token信息
        String token = getToken(request);
        //获取用户的秘钥
        String sec = (String) redisTemplate.opsForValue().get(JwtProperties.USER_TOKEN+token);


        // 判断有没有token
        if ( token == null) {
            return R.error().put("code", JwtProperties.NOT_FOUND_TOKEN).put("msg", "请求头中没有token");
            //判断有没有秘钥
        } else if(sec==null){
            return R.error().put("code", JwtProperties.INVALID_TOKEN).put("msg", "无效的token");
        }else {
            boolean expiration = isExpiration(token, sec);
            if (expiration) {
                return R.error().put("msg", "token已经过期了").put("code", JwtProperties.TOKEN_EXPIRED);
            } else {
                //从token中获取用户名和角色
                String username = getUsername(token, sec);
                List<String> roles = (List<String>) getUserRole(token, sec);
                //将角色转换成List
                List realRoles = new ArrayList();
                for (String role : roles) {
                    realRoles.add(new SimpleGrantedAuthority(role));
                }
                return R.ok().put(JwtProperties.USER_NAME, username).put(JwtProperties.ROLE, realRoles);
            }
        }
    }

    public static String getToken(HttpServletRequest request){
        //获取token信息
        String token = request.getHeader(JwtProperties.AUTHORIZATION);
        if(token!=null){
            token= token.replace(JwtProperties.BEARER, "");
        }
        return token;
    }

}
