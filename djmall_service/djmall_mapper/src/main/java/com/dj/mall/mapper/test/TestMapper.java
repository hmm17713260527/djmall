package com.dj.mall.mapper.test;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dj.mall.entity.test.Test;
import com.dj.mall.mapper.bo.auth.user.UserBo;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mapper.test
 * @ClassName: TestMapper
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/29 17:51
 * @Version: 1.0
 */
public interface TestMapper extends BaseMapper<Test> {
    IPage<Test> findListByPid(@Param("page") Page<Test> page, @Param("id") Integer id) throws DataAccessException;


}
