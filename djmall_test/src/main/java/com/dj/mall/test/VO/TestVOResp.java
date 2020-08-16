package com.dj.mall.test.VO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.entity.test
 * @ClassName: Test
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/29 17:49
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestVOResp implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 名字
     */
    private String name;

    /**
     * pId
     */
    private Integer pId;

    /**
     * 1在，2无
     */
    private Integer isDel;

}
