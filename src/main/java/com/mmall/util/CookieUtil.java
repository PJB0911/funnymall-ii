package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**Cookie工具类
 * @author PJB
 * @date 2020/4/23
 */
@Slf4j
public class CookieUtil {
    private final static String COOKIE_DOMAIN = "funnymall.com";
    private final static String COOKIE_NAME = "mall_login_token";

    /**
     * 获取登录cookie
     */
    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                log.info("read cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    log.info("return cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * 写登录cookie
     */
    public static void writeLoginToken(HttpServletResponse response, String token){
        Cookie cookie = new Cookie(COOKIE_NAME,token);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");//代表设置在根目录
        cookie.setHttpOnly(true); //禁止cookie分享给第三方脚本工具
        //单位是秒。
        /**
         * 如果不设置maxAge，cookie不会写入磁盘，只存在内存中，只在当前页面有效
         * session maxAge默认24h
         */
        cookie.setMaxAge(60 * 60 * 24 * 365);//如果是-1，代表永久
        log.info("write cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
        response.addCookie(cookie);
    }

    /**
     * 删除cookie
     */
    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME)){
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    //将cookie的maxAge设置为0添加到response中，代表删除cookie
                    cookie.setMaxAge(0);
                    log.info("del cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }



}
