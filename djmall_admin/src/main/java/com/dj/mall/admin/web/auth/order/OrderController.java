package com.dj.mall.admin.web.auth.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dj.mall.admin.vo.order.OrderInfoVOResp;
import com.dj.mall.admin.vo.order.OrderVOReq;
import com.dj.mall.admin.vo.product.product_sku.ProductSkuVOReq;
import com.dj.mall.admin.vo.product.product_spu.ProductSpuVOReq;
import com.dj.mall.api.order.OrderApi;
import com.dj.mall.api.order.OrderInfoApi;
import com.dj.mall.api.product.product_spu.ProductSpuApi;
import com.dj.mall.model.base.ResultModel;
import com.dj.mall.model.base.SystemConstant;
import com.dj.mall.model.dto.auth.user.UserDTOResp;
import com.dj.mall.model.dto.order.OrderDTOReq;
import com.dj.mall.model.dto.order.OrderInfoDTOResp;
import com.dj.mall.model.dto.product.product_spu.ProductSpuDTOReq;
import com.dj.mall.model.util.DozerUtil;
import com.dj.mall.model.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: djmall
 * @Package: com.dj.mall.admin.web.auth.order
 * @ClassName: OrderController
 * @Author: Administrator
 * @Description:
 * @Date: 2020/5/22 15:47
 * @Version: 1.0
 */
@RestController
@RequestMapping("/auth/order/")
public class OrderController {


    @Reference
    private OrderInfoApi orderInfoApi;


    @Reference
    private OrderApi orderApi;

    @Reference
    private ProductSpuApi productSpuApi;


    /**
     * 导入
     * @throws Exception
     */
    @PostMapping("importExcel")
    public void importExcel(HttpSession session) throws Exception {

        UserDTOResp userDTOResp = (UserDTOResp) session.getAttribute(SystemConstant.USER_SESSION);

        ProductSpuVOReq productSpuVOReq = new ProductSpuVOReq();

        List<ProductSkuVOReq> productSkuList = new ArrayList<>();

        // 文件名
        String fileName = "e://导入模板.xlsx";
        // 工作蒲
        Workbook workbook = null;
        // 判断文件格式
        if (fileName.endsWith(".xls")) {
            // 2003
            workbook = new HSSFWorkbook(new FileInputStream(fileName));
        }else {
            // 2007
            workbook = new XSSFWorkbook(new FileInputStream(fileName));
        }
        // 获取工作表
//        workbook.getNumberOfSheets();// 得到Sheet的数量
        Sheet sheet = workbook.getSheetAt(0);
        // 总行数
        // 多少行
        int rows = sheet.getPhysicalNumberOfRows();
        // 遍历行
        for (int i = 0; i < rows; i++) {

            if (i == 1) {
                Row row = sheet.getRow(i);
                // 多少个单元格
                int cells = row.getPhysicalNumberOfCells();
                productSpuVOReq.setProductName(ExcelUtil.getCellValueToString(row.getCell(0)));
                productSpuVOReq.setFreightId(Integer.valueOf(ExcelUtil.getCellValueToString(row.getCell(1))));
                //商品描述
                productSpuVOReq.setProductDescribe(ExcelUtil.getCellValueToString(row.getCell(2)));
                productSpuVOReq.setType(ExcelUtil.getCellValueToString(row.getCell(3)));
                productSpuVOReq.setUserId(userDTOResp.getUserId());
                productSpuVOReq.setProductOrder(0);
                productSpuVOReq.setSpuStatus(1);
                productSpuVOReq.setPraise(0);

            }

            if (i > 3) {
                ProductSkuVOReq productSkuVOReq = new ProductSkuVOReq();
                Row row = sheet.getRow(i);
                // 多少个单元格
                int cells = row.getPhysicalNumberOfCells();
                productSkuVOReq.setSkuPrice(new BigDecimal(ExcelUtil.getCellValueToString(row.getCell(0))));
                productSkuVOReq.setSkuCount(Integer.valueOf(ExcelUtil.getCellValueToString(row.getCell(1))));
                productSkuVOReq.setSkuRate(Integer.valueOf(ExcelUtil.getCellValueToString(row.getCell(2))));
                productSkuVOReq.setSkuStatus(1);
                productSkuVOReq.setSkuAttrNames(ExcelUtil.getCellValueToString(row.getCell(3)));
                productSkuVOReq.setSkuAttrValueNames(ExcelUtil.getCellValueToString(row.getCell(4)));
                productSkuVOReq.setIsDefault(i == 4 ? 0 : 1);
                productSkuList.add(productSkuVOReq);
            }

        }

        //千人千面
        String[] split = productSkuList.get(SystemConstant.ARRAY_SUB).getSkuAttrNames().split(",");
        String productDescribe = productSpuVOReq.getProductDescribe() + "-";
        for (int i = 0; i < split.length; i++) {
            productDescribe += split[i] + "为:" + split[i] + "1;";
        }
        productSpuVOReq.setProductDescribe(productDescribe);

        productSpuVOReq.setProductSkuList(productSkuList);

        productSpuApi.adds(DozerUtil.map(productSpuVOReq, ProductSpuDTOReq.class));

    }


    /**
     * 下载导入模板
     * @return
     * @throws Exception
     */
    @GetMapping("exportTemp")
    public void exportTemp() throws Exception {

        // 创建空白工作簿
        Workbook workbook = new XSSFWorkbook();
        // 创建工作表
        Sheet sheet = workbook.createSheet("导入模板");
        // 创建表头
        Row title = sheet.createRow(0);
        title.createCell(0).setCellValue("商品名称");
        title.createCell(1).setCellValue("运费ID");
        title.createCell(2).setCellValue("商品描述");
        title.createCell(3).setCellValue("商品类型");

        Row title1 = sheet.createRow(2);
        title1.createCell(0).setCellValue("商品SKU：");
        Row title2 = sheet.createRow(3);
        title2.createCell(0).setCellValue("价格");
        title2.createCell(1).setCellValue("库存");
        title2.createCell(2).setCellValue("折扣（%）");
        title2.createCell(3).setCellValue("属性");
        title2.createCell(4).setCellValue("属性值");
        // 保存
        OutputStream fileOutputStream =  new FileOutputStream("e://导入模板.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();

    }

    /**
     * POI-导出
     * @param sheet
     * @param orderList
     * @param dateTimeFormatter
     */
    public static void poiUtil(Sheet sheet, List<OrderInfoDTOResp> orderList, DateTimeFormatter dateTimeFormatter, String orderStatus) {
        // 创建表头
        Row title = sheet.createRow(0);
        title.createCell(0).setCellValue("订单号");
        title.createCell(1).setCellValue("商品名称");
        title.createCell(2).setCellValue("购买数量");
        title.createCell(3).setCellValue("付款金额（包含邮费）");
        title.createCell(4).setCellValue("支付方式");
        title.createCell(5).setCellValue("邮费");
        title.createCell(6).setCellValue("收货人信息");
        title.createCell(7).setCellValue("下单人");
        title.createCell(8).setCellValue("下单人电话");
        title.createCell(9).setCellValue("下单时间");
        if (orderStatus.equals("已完成") || orderStatus.equals("已支付")) {
            title.createCell(10).setCellValue("付款时间");
        }
        // 构建表格数据
        for (int i = 0; i < orderList.size(); i++) {
            Row row = sheet.createRow(i + 1);
            OrderInfoDTOResp order = orderList.get(i);
            row.createCell(0).setCellValue(order.getOrderNo());
            row.createCell(1).setCellValue(order.getProductName());
            row.createCell(2).setCellValue(order.getTotalBuyCount());
            row.createCell(3).setCellValue(order.getPayMoney().toString());
            row.createCell(4).setCellValue(order.getName());
            row.createCell(5).setCellValue(order.getTotalFreight().toString());
            row.createCell(6).setCellValue(order.getReceiverName() + "-" + order.getReceiverPhone() + "-" + order.getReceiverProvince() + "-" + order.getReceiverCity() + "-" + order.getReceiverCounty() + "-" + order.getReceiverDetail());
            row.createCell(7).setCellValue(order.getReceiverName());
            row.createCell(8).setCellValue(order.getReceiverPhone());
            row.createCell(9).setCellValue(dateTimeFormatter.format(order.getCreateTime()));
            if (orderStatus.equals("已完成") || orderStatus.equals("已支付")) {
                row.createCell(10).setCellValue(order.getPayTime() == null ? "暂无" : dateTimeFormatter.format(order.getPayTime()));
            }
        }
    }

    /**
     * poi-订单导出
     * @param userId
     * @param roleId
     * @return
     * @throws Exception
     */
    @GetMapping("poiExport")
    public void poiExport(Integer userId, Integer roleId) throws Exception {

        List<OrderInfoDTOResp> orderList = orderInfoApi.findOrderExport(userId, roleId);

        //待支付
        List<OrderInfoDTOResp> list1 = new ArrayList<>();
        //已完成
        List<OrderInfoDTOResp> list2 = new ArrayList<>();
        //已取消
        List<OrderInfoDTOResp> list3 = new ArrayList<>();
        //已支付
        List<OrderInfoDTOResp> list4 = new ArrayList<>();

        for (OrderInfoDTOResp order : orderList) {
            if (order.getOrderStatus().equals("待支付")) {
                list1.add(order);
                continue;
            }
            if (order.getOrderStatus().equals("已完成")) {
                list2.add(order);
                continue;
            }
            if (order.getOrderStatus().equals("已取消")) {
                list3.add(order);
                continue;
            }
            list4.add(order);
        }

        // 创建空白工作簿
        Workbook workbook = new XSSFWorkbook();
        // 创建工作表
        Sheet sheet = workbook.createSheet("订单-待支付");
        Sheet sheet1 = workbook.createSheet("订单-已完成");
        Sheet sheet2 = workbook.createSheet("订单-已取消");
        Sheet sheet3 = workbook.createSheet("订单-已支付");
        // 日期转换
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        poiUtil(sheet, list1, dateTimeFormatter, "待支付");
        poiUtil(sheet1, list2, dateTimeFormatter, "已完成");
        poiUtil(sheet2, list3, dateTimeFormatter, "已取消");
        poiUtil(sheet3, list4, dateTimeFormatter, "已支付");

        // 保存
        String s = "e://order-"+ LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")) +".xlsx";
        OutputStream fileOutputStream =  new FileOutputStream(s);
        workbook.write(fileOutputStream);
        fileOutputStream.close();


    }



    /**
     * admin修改状态
     * @param orderVOReq
     * @return
     * @throws Exception
     */
    @PostMapping("updateOrderStatus")
    public ResultModel<Object> updateOrderStatus(OrderVOReq orderVOReq) throws Exception {

        orderApi.updateOrderStatus(DozerUtil.map(orderVOReq, OrderDTOReq.class));

        return new ResultModel<>().success(SystemConstant.REQ_YES);

    }

    /**
     * 订单展示
     * @param userId
     * @param roleId
     * @return
     * @throws Exception
     */
    @GetMapping("show")
    public ResultModel<Object> show(Integer userId, Integer roleId) throws Exception {

        List<OrderInfoDTOResp> orderList = orderInfoApi.findOrderList(userId, roleId);
        return new ResultModel<>().success(DozerUtil.mapList(orderList, OrderInfoVOResp.class));

    }
}
