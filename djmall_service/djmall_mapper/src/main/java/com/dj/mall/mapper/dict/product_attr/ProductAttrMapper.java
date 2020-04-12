package com.dj.mall.mapper.dict.product_attr;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.dict.product_attr.ProductAttr;
import com.dj.mall.mapper.bo.dict.product_attr.ProductAttrBO;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.dict.product_attr
 * @ClassName: ProductAttrMapper
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/11 15:23
 * @Version: 1.0
 */
public interface ProductAttrMapper extends BaseMapper<ProductAttr> {

    /**
     * 展示
     * @return
     */
    List<ProductAttrBO> findFreightList() throws DataAccessException;
}
