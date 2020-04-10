package com.dj.mall.mapper.dict.freight;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.mall.entity.dict.freight.Freight;
import com.dj.mall.mapper.bo.dict.freight.FreightBO;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.dict.freight
 * @ClassName: FreightMapper
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/10 19:40
 * @Version: 1.0
 */
public interface FreightMapper extends BaseMapper<Freight> {

    List<FreightBO> findFreightList() throws DataAccessException;


}
