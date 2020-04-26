package com.mmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 订单
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Integer id;

    private Long orderNo;

    private Integer userId;

    private Integer shippingId;
    /**
     * 实际付款金额，单位元，保留两位小数
     */
    private BigDecimal payment;
    /**
     * 支付类型，1-在线支付，其它保留
     */
    private Integer paymentType;
    /**
     * 运费
     */
    private Integer postage;
    /**
     * 订单状态：0-已取消，10-未支付，20-已付款，40-已发货，50-交易成功，60-交易关闭
     */
    private Integer status;
    /**
     * 支付时间，支付成功，系统回调时间
     */
    private Date paymentTime;
    /**
     * 发货时间
     */
    private Date sendTime;
    /**
     * 交易完成时间
     */
    private Date endTime;
    /**
     * 交易关闭时间，下单但有效时间内未付款
     */
    private Date closeTime;

    private Date createTime;

    private Date updateTime;


}