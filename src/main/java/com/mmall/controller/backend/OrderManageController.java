package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
import com.mmall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/** 订单管理模块的后台Controller
 * @author PJB
 * @date 2020/4/15
 */
@Controller
@RequestMapping("/manage/order")
public class OrderManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;
    /**
     * 处理获取所有订单列表的请求
     * @param httpServletRequest httpServletRequest
     * @param pageNum 页码
     * @param pageSize 每页的数量
     * @return 返回给前端的订单列表的信息
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpServletRequest httpServletRequest, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        //通过拦截器验证是否登录以及权限
        return iOrderService.manageList(pageNum,pageSize);
    }
    /**
     * 处理获取订单详情的请求
     * @param httpServletRequest httpServletRequest
     * @param orderNo 订单号
     * @return 返回给前端的订单详情的信息
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderDetail(HttpServletRequest httpServletRequest, Long orderNo){
        //通过拦截器验证是否登录以及权限
        return iOrderService.manageDetail(orderNo);
    }

    /**
     * 处理查找订单的请求
     * @param httpServletRequest httpServletRequest
     * @param orderNo 订单号
     * @param pageNum 页码
     * @param pageSize 每页的数量
     * @return 返回给前端的订单列表的信息
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(HttpServletRequest httpServletRequest, Long orderNo,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        //通过拦截器验证是否登录以及权限
        return iOrderService.manageSearch(orderNo,pageNum,pageSize);
    }


    /**
     * 处理订单发货的请求
     * @param httpServletRequest httpServletRequest
     * @param orderNo 订单号
     * @return 返回给前端订单是否成功的信息
     */
    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> orderSendGoods(HttpServletRequest httpServletRequest, Long orderNo){
        //通过拦截器验证是否登录以及权限, 增加产品
        return iOrderService.manageSendGoods(orderNo);
    }



}
