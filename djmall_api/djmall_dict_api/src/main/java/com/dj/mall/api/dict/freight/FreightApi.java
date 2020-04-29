package com.dj.mall.api.dict.freight;

import com.dj.mall.model.dto.dict.freight.FreightDTOReq;
import com.dj.mall.model.dto.dict.freight.FreightDTOResp;
import com.dj.mall.model.util.PageResult;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.dict.freight
 * @ClassName: FreightApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/10 19:46
 * @Version: 1.0
 */
public interface FreightApi {

    /**
     * 新增
     * @param freightDTOReq
     */
    void add(FreightDTOReq freightDTOReq) throws Exception;

    /**
     * 运费修改
     * @param freightId
     * @param freight
     * @throws Exception
     */
    void updateFreight(Integer freightId, String freight) throws Exception;

    /**
     * 展示
     * @return
     * @throws Exception
     */
    List<FreightDTOResp> findFreightList() throws Exception;

    /**
     * 通过id查询
     * @param freightId
     * @return
     * @throws Exception
     */
    FreightDTOResp getFreight(Integer freightId) throws Exception;

}
