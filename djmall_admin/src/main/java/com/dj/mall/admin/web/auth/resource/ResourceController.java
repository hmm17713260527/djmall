package com.dj.mall.admin.web.auth.resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.resource.ResourceVOReq;
import com.dj.mall.admin.vo.auth.resource.ResourceVOResp;
import com.dj.mall.api.auth.resource.ResourceApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.resource.ResourceDTOReq;
import com.dj.mall.model.dto.auth.resource.ResourceDTOResp;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.auth.resource
 * @ClassName: ResourceController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/3/28 18:20
 * @Version: 1.0
 */
@RestController
@RequestMapping("/auth/res/")
public class ResourceController {

    @Reference
    private ResourceApi resourceApi;

    /**
     * 资源删除
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping("delById")
    public ResultModel<Object> delById(Integer id) throws Exception {

        resourceApi.delById(id);

        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 资源修改
     * @param resourceVOReq
     * @return
     */
    @PutMapping("update")
    public ResultModel<Object> update(ResourceVOReq resourceVOReq) throws Exception {
        String s = resourceVOReq.getResourceCode().toUpperCase();

        ResourceVOReq.builder().resourceCode(s);

        resourceApi.updateRes(DozerUtil.map(resourceVOReq, ResourceDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 资源新增去重，根据名称
     * @param resourceName
     * @return
     */
    @GetMapping("findResourceByName")
    public Boolean findResourceByName(String resourceName) throws Exception {
        Boolean bol = resourceApi.findResourceByName(resourceName);
        return bol;
    }

    /**
     * 资源新增去重,根据编码
     * @param resourceCode
     * @return
     */
    @GetMapping("findResourceByCode")
    public Boolean findResourceByCode(String resourceCode) throws Exception {
        Boolean bol = resourceApi.findResourceByCode(resourceCode);
        return bol;
    }

    /**
     * 资源展示
     * @return
     * @throws Exception
     */
    @GetMapping("show")
    public ResultModel<Object> show() throws Exception {
        List<ResourceDTOResp> resList = resourceApi.findResList();
        return new ResultModel<>().success(DozerUtil.mapList(resList, ResourceVOResp.class));
    }

    /**
     * 左侧菜单展示
     * @param id
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping("toList")
    public List<ResourceVOResp> toList(Integer id, HttpSession session) throws Exception {

        UserDTOResp userDTOResp  = (UserDTOResp) session.getAttribute(SystemConstant.USER_SESSION);
        List<ResourceVOResp> resourceList = new ArrayList<ResourceVOResp>();

        userDTOResp.getResourceList().forEach(resource ->{
            if (resource.getType().equals(SystemConstant.RES_TYPE)) {
                resourceList.add(DozerUtil.map(resource, ResourceVOResp.class));
            }
        });

//        for (ResourceDTOResp resource : userDTOResp.getResourceList()) {
//            if (resource.getType().equals(SystemConstant.RES_TYPE)) {
//                resourceList.add(DozerUtil.map(resource, ResourceVOResp.class));
//            }
//        }
        return resourceList;

    }



    /**
     * add
     * @param resourceVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    public ResultModel<Object> addUser(ResourceVOReq resourceVOReq) throws Exception {
        ResourceDTOReq resourceDTOReq = DozerUtil.map(resourceVOReq, ResourceDTOReq.class);
        resourceApi.add(resourceDTOReq);
        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }

}
