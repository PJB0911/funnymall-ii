package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.service.IShippingService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/** 收货地址管理模块Controller
 * @author PJB
 * @date 2020/4/14
 */
@Controller
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    private IShippingService iShippingService;

    /**
     * 处理新增收货地址的请求
     * @param httpServletRequest httpServletRequest
     * @param shipping 收货地址
     * @return 返回给前端的新增收货地址后的信息
     */
    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpServletRequest httpServletRequest, Shipping shipping){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest); //获取登录cookie
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class); //将json转换成user对象
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.add(user.getId(),shipping);
    }

    /**
     * 处理删除收货地址的请求
     * @param httpServletRequest httpServletRequest
     * @param shippingId 收货地址id
     * @return 返回给前端的删除收货地址后的信息
     */
    @RequestMapping("del.do")
    @ResponseBody
    public ServerResponse del(HttpServletRequest httpServletRequest,Integer shippingId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest); //获取登录cookie
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class); //将json转换成user对象
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        // 防止横向越权，传入当前用户的id,防止其他用户删除
        return iShippingService.del(user.getId(),shippingId);
    }
    /**
     * 处理更新收货地址的请求
     * @param httpServletRequest httpServletRequest
     * @param shipping 收货地址
     * @return 返回给前端的更新收货地址后的信息
     */
    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpServletRequest httpServletRequest,Shipping shipping){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest); //获取登录cookie
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class); //将json转换成user对象
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        // 防止横向越权，传入当前用户的id,防止其他用户更新
        return iShippingService.update(user.getId(),shipping);
    }

    /**
     * 处理查看选中的收货地址的请求
     * @param httpServletRequest httpServletRequest
     * @param shippingId 收货地址id
     * @return 返回给前端的选中的收货地址的信息
     */
    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<Shipping> select(HttpServletRequest httpServletRequest, Integer shippingId){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest); //获取登录cookie
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class); //将json转换成user对象
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        // 防止横向越权，传入当前用户的id,防止其他用户查询
        return iShippingService.select(user.getId(),shippingId);
    }

    /**
     * 处理查看所有收货地址的请求
     * @param pageNum 页码
     * @param pageSize 每页的数量
     * @param httpServletRequest httpServletRequest
     * @return 返回给前端的所有的收货地址的信息
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                         HttpServletRequest httpServletRequest){
        String loginToken = CookieUtil.readLoginToken(httpServletRequest); //获取登录cookie
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class); //将json转换成user对象
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        // 防止横向越权，传入当前用户的id,防止其他用户查询
        return iShippingService.list(user.getId(),pageNum,pageSize);
    }


}
