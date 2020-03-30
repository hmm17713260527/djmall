package com.dj.mall.api.auth.resource;


import com.dj.mall.model.dto.auth.resource.ResourceDTOReq;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.api.auth.resource
 * @ClassName: ResourceApi
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/28 18:09
 * @Version: 1.0
 */
public interface ResourceApi {

    /**
     * add
     * @param resourceDTOReq
     * @throws Exception
     */
    void add(ResourceDTOReq resourceDTOReq) throws Exception;;

    /**
     * 查询资源列表
     * @return
     * @throws Exception
     */
    List<ResourceDTOResp> findResList() throws Exception;;

    /**
     * 根据资源名查询
     * @param resourceName
     * @return
     * @throws Exception
     */
    Boolean findResourceByName(String resourceName) throws Exception;;

    /**
     * 根据编码查询
     * @param resourceCode
     * @return
     */
    Boolean findResourceByCode(String resourceCode) throws Exception;;

    /**
     * 根据id查询
     * @param id
     * @return
     * @throws Exception
     */
    ResourceDTOResp findResourceById(Integer id) throws Exception;;

    /**
     * 资源修改
     * @param resourceDTOReq
     * @throws Exception
     */
    void updateRes(ResourceDTOReq resourceDTOReq) throws Exception;;

    /**
     * 资源删除
     * @param resId
     * @throws Exception
     */
    void delById(Integer resId) throws Exception;;
}
