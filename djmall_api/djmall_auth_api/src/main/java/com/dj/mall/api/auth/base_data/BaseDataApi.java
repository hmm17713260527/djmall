package com.dj.mall.api.auth.base_data;

import com.dj.mall.model.dto.auth.base.BaseDataDTOReq;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;

import java.util.HashMap;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.auth.base
 * @ClassName: BaseDataApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/2 15:49
 * @Version: 1.0
 */
public interface BaseDataApi {


    /**
     * 字典修改
     * @param baseDataDTOReq
     * @throws Exception
     */
    void updateBase(BaseDataDTOReq baseDataDTOReq) throws Exception;

    /**
     * 查询base_data表
     * @return
     */
    HashMap<String, Object> findBaseList() throws Exception;

    /**
     * 字典新增
     * @param baseDataDTOReq
     * @throws Exception
     */
    void addBase(BaseDataDTOReq baseDataDTOReq) throws Exception;

    /**
     * 通过id查询
     * @param baseId
     * @return
     * @throws Exception
     */
    BaseDataDTOResp getBase(Integer baseId) throws Exception;

    /**
     * 通过Parent_Code查询集合
     * @param userStatus
     * @return
     * @throws Exception
     */
    List<BaseDataDTOResp> findBaseListByParentCode(String userStatus) throws Exception;
}
