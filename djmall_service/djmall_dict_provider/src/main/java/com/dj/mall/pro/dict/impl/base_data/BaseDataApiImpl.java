package com.dj.mall.pro.dict.impl.base_data;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.mall.api.dict.base_data.BaseDataApi;
import com.dj.mall.entity.auth.base_data.BaseData;
import com.dj.mall.entity.dict.product_sku.ProductSku;
import com.dj.mall.mapper.auth.base.BaseDataMapper;
import com.dj.mall.mapper.dict.product_sku.ProductSkuMapper;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.base.BaseDataDTOReq;
import com.dj.mall.model.dto.auth.base.BaseDataDTOResp;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.pro.auth.impl.base_data
 * @ClassName: BaseDataApiImpl
 * @Author: Administrator
 * @Description:
 * @Date: 2020/4/2 15:50
 * @Version: 1.0
 */
@Service
public class BaseDataApiImpl extends ServiceImpl<BaseDataMapper, BaseData> implements BaseDataApi {

    @Autowired
    private ProductSkuMapper productSkuMapper;

    /**
     * 字典修改
     * @param baseDataDTOReq
     * @throws Exception
     */
    @Override
    public void updateBase(BaseDataDTOReq baseDataDTOReq) throws Exception {
        UpdateWrapper<BaseData> baseWrapper = new UpdateWrapper<>();
        baseWrapper.set("name", baseDataDTOReq.getName()).eq("code", baseDataDTOReq.getCode());
        this.update(baseWrapper);
    }

    /**
     * 查询base_data表
     * @return
     * @throws Exception
     */
    @Override
    public PageResult findBaseList(BaseDataDTOReq baseDataDTOReq) throws Exception {

        Page<BaseData> page = new Page();

        page.setCurrent(baseDataDTOReq.getPageNo());
        page.setSize(SystemConstant.PAGE_SIZE);

        IPage<BaseData> pageInfo = this.page(page);

        return PageResult.builder().list(DozerUtil.mapList(pageInfo.getRecords(), BaseDataDTOResp.class)).pages(pageInfo.getPages()).paramList(DozerUtil.mapList(this.list(), BaseDataDTOResp.class)).build();

    }

    /**
     * 字典新增
     * @param baseDataDTOReq
     * @throws Exception
     */
    @Override
    public void addBase(BaseDataDTOReq baseDataDTOReq) throws Exception {
        String s = baseDataDTOReq.getCode().toUpperCase();
        baseDataDTOReq.setCode(s);
        if (SystemConstant.PRODUCT_TYPE.equals(baseDataDTOReq.getParentCode())) {
            ProductSku productSku = new ProductSku();
            productSku.setProductType(s);
            productSkuMapper.insert(productSku);
        }
        this.save(DozerUtil.map(baseDataDTOReq, BaseData.class));
    }

    /**
     * 通过code查询
     * @param code
     * @return
     * @throws Exception
     */
    @Override
    public BaseDataDTOResp getBase(String code) throws Exception {
        QueryWrapper<BaseData> baseWrapper = new QueryWrapper<>();
        baseWrapper.eq("code", code);
        return DozerUtil.map(this.getOne(baseWrapper), BaseDataDTOResp.class);
    }

    @Override
    public List<BaseDataDTOResp> findBaseListByParentCode(String userStatus) throws Exception {

        QueryWrapper<BaseData> baseWrapper = new QueryWrapper<>();
        baseWrapper.eq("parent_code", userStatus);
        List<BaseData> baseDataList = this.list(baseWrapper);

        return DozerUtil.mapList(baseDataList, BaseDataDTOResp.class);
    }


}
