package com.dj.mall.test.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户视图对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVOReq implements Serializable {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 当前页
     */
    private Integer pageNo;



    /**
     * 用户名
     */
    private String userName;


    /**
     * 密码
     */
    private String password;



}
