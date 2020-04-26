package com.mmall.pojo;

import lombok.*;

import java.util.Date;
/**
 * 商品分类
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {
    private Integer id;
    /**
     * 商品父类别id：id=0表示根节点，一级类别
     */
    private Integer parentId;

    private String name;
    /**
     * 类别装填：1-正常，2-已废弃
     */
    private Boolean status;
    /**
     * 排序编号，同类展示顺序，数值相等则自然排序
     */
    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;


}