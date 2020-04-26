package com.mmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * 购物车
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;
    /**
     * 是否勾选：1-已勾选，0-未勾选
     */
    private Integer checked;

    private Date createTime;

    private Date updateTime;

}