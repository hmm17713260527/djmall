package com.dj.mall.model.util;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult implements Serializable {

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 数据
     */
    private List<?> list;

    /**
     * 数据2
     */
    private List<?> paramList;


}
