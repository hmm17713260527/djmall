package com.dj.mall.api.dict.base_data;

import com.dj.mall.entity.auth.base_data.BaseData;
import com.dj.mall.model.dto.auth.base.BaseDataDTOReq;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import com.dj.mall.model.util.PageResult;

import java.util.List;
import java.util.Set;

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
     * @param baseDataDTOReq
     * @return
     * @throws Exception
     */
    PageResult findBaseList(BaseDataDTOReq baseDataDTOReq) throws Exception;

    /**
     * 字典新增
     * @param baseDataDTOReq
     * @throws Exception
     */
    void addBase(BaseDataDTOReq baseDataDTOReq) throws Exception;

    /**
     * 通过code查询
     * @param code
     * @return
     * @throws Exception
     */
    BaseDataDTOResp getBase(String code) throws Exception;

    /**
     * 通过Parent_Code查询基础数据
     * @param userStatus
     * @return
     * @throws Exception
     */
    List<BaseData> findBaseListByParentCode(String userStatus) throws Exception;
}
