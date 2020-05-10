package com.dj.mall.model.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    /**
     * 数据3
     */
    private List<?> baseList;


}
