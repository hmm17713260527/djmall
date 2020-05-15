package com.dj.mall.api.dict.area;

import com.dj.mall.model.dto.dict.area.AreaDTOResp;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.dict.area
 * @ClassName: AreaApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/14 17:09
 * @Version: 1.0
 */
public interface AreaApi {

    /**
     * 搜索地址
     * @param areaParentId
     * @return
     */
    List<AreaDTOResp> findAreaListByPid(String areaParentId) throws Exception;
}
