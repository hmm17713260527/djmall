package com.dj.mall.admin.web.auth.base_data;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.auth.base.BaseDataVOReq;
import com.dj.mall.admin.vo.auth.role.RoleVOReq;
import com.dj.mall.admin.vo.auth.user.UserVOReq;
import com.dj.mall.api.auth.base_data.BaseDataApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.base.BaseDataDTOReq;
import com.dj.mall.model.dto.auth.role.RoleDTOReq;
import com.dj.mall.model.dto.auth.user.UserDTOReq;
import com.dj.mall.model.util.DozerUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.auth.base_data
 * @ClassName: BaseDataController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/2 15:55
 * @Version: 1.0
 */
@RestController
@RequestMapping("/auth/base_data/")
public class BaseDataController {


    @Reference
    private BaseDataApi baseDataApi;

    /**
     * 字典修改
     * @param baseDataVOReq
     * @return
     * @throws Exception
     */
    @PutMapping("update")
    public ResultModel<Object> update(BaseDataVOReq baseDataVOReq) throws Exception {
        baseDataApi.updateBase(DozerUtil.map(baseDataVOReq, BaseDataDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

    /**
     * 字典展示
     * @return
     */
    @RequestMapping("show")
    public ResultModel<Object> show() throws Exception {

        HashMap<String, Object> map = baseDataApi.findBaseList();
        return new ResultModel<>().success(map);

    }


    /**
     * 字典新增
     * @param baseDataVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    public ResultModel<Object> addUser(BaseDataVOReq baseDataVOReq) throws Exception {
        baseDataApi.addBase(DozerUtil.map(baseDataVOReq, BaseDataDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }

}
