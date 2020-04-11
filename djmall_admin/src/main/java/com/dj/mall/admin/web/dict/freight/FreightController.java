package com.dj.mall.admin.web.dict.freight;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.dict.freight.FreightVOReq;
import com.dj.mall.admin.vo.dict.freight.FreightVOResp;
import com.dj.mall.api.dict.freight.FreightApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.dict.freight.FreightDTOReq;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import org.springframework.web.bind.annotation.*;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.dict.freight
 * @ClassName: FreightController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/10 19:51
 * @Version: 1.0
 */
@RestController
@RequestMapping("/dict/freight/")
public class FreightController {


    @Reference
    private FreightApi freightApi;

    /**
     * 运费修改
     * @param freightId
     * @param freight
     * @return
     * @throws Exception
     */
    @PutMapping("update")
    public ResultModel<Object> update(Integer freightId, String freight) throws Exception {
        if (Integer.parseInt(freight) == 0) {
            freight = "包邮";
        }
        freightApi.updateFreight(freightId, freight);
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }


    /**
     * 展示
     * @return
     * @throws Exception
     */
    @GetMapping("show")
    public ResultModel<Object> show() throws Exception {

        PageResult pageResult = freightApi.findFreightList();

        PageResult.builder().list(DozerUtil.mapList(pageResult.getList(), FreightVOResp.class));

        return new ResultModel<>().success(pageResult);

    }


    /**
     * 新增
     * @param freightVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    public ResultModel<Object> add(FreightVOReq freightVOReq) throws Exception {
        if (Integer.parseInt(freightVOReq.getFreight()) == 0) {
            freightVOReq.setFreight("包邮");
        }
        freightApi.add(DozerUtil.map(freightVOReq, FreightDTOReq.class));
        return new ResultModel<>().success(SystemConstant.REQ_YES);
    }

}
