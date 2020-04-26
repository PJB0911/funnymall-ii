package com.mmall.pojo;

import lombok.*;

import java.util.Date;

/**
 * 用户
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    /**
     * username有建立唯一索引
     */
    private String username;
    /**
     * 用户密码，MD5加密
     */
    private String password;

    private String email;

    private String phone;

    private String question;

    private String answer;
    /**
     * 角色：0-管理员，1-普通用户
     */
    private Integer role;

    private Date createTime;

    private Date updateTime;


}