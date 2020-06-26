package com.dj.mall.mq.task;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.api.order.OrderApi;
import com.dj.mall.api.order.OrderInfoApi;
import com.dj.mall.model.dto.order.OrderInfoDTOResp;
import com.dj.mall.model.util.HttpClientUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.mq.task
 * @ClassName: DsTask
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/24 23:00
 * @Version: 1.0
 */
@Component
@EnableScheduling
public class DsTask {

    @Reference
    private OrderInfoApi orderInfoApi;

    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateSolrIndex() {
        try {
            HttpClientUtil.sendHttpRequest("http://127.0.0.1:8089/solr/test/dataimport?command=delta-import", HttpClientUtil.HttpRequestMethod.GET, new HashMap<>());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void delSolrIndex() {
        try {
            HttpClientUtil.sendHttpRequest("http://127.0.0.1:8089/solr/test/dataimport?command=full-import", HttpClientUtil.HttpRequestMethod.GET, new HashMap<>());

            orderInfoApi.findOrderInfoByTime();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
