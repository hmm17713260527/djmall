package com.dj.mall.model.util;

import com.alipay.api.*;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AliPayUtils {

	// 支付宝公钥
	static final String ALIPAYPUBLICKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgXNPZ953eB6cxYmCUCPEU3jbkPi4r2Uw2fK3Pbj5QWgcTSm0AGOjo1TKyKOy193cbx3WgcYOT/PHud3Pv89O+CUwx/6SIOhhh5Yxpg6NSJENEYctCr8rpJV0sj2JcGDPMt1gs9MGz+rnjzdDsQZomlw6v9LHd9ZJo1Zr+BiPifIp2VfaK3KfSXnIBRRJshoO8gYgXi8j79jgo1EK0wQkk8FS56U2/1ELmcOjncYpFhOS6/JTpvKFhfn8aWL2eCyavfTW9pEP0QIoRI2U/QINZWbhtLrEbHAESYjW7pmhMKwOeqoRKslKG8wvuAZ73LcYskLQC7Uw+Plq3trG8BkPbwIDAQAB";
	// 应用私钥
	static final String ALIPAYAPPPRIVATEKEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC44h0kVDX8hutJJJTLqdNgwcInuY4Odehsna6QwKUY3TFBoAZuCo/Iy3ZUV0E6//SUqosPYYRMliZwgTeHs/mLmid1Tvv+nhRqXXTe/KNrxmwwB3BBUbQd7yKXalLYWaqJr0pa3h4M7hCV9xgOwkOazOwUVbYvaC2HOs2T0pmLGuvM3YkN7Wun7ph0NYYOQ7poRQRP5uT1ASKw6FGut5b/C/tD84TofeoCTBHUHGT71feRXkOVtnKY4heH6Ja3WkheYKTvni22jgPHhsx4tdENYyN4emd9YUgpWomZPg4t88F+6gZTr1I7B3d+6Xcq5hjj/OiAUEbv8LKSXFaLzMBzAgMBAAECggEAR6p+mWJ7OtQhyEyB0SCXa14wIK3wvvI9kjzdOgcy+Bxlx4Z8d5NogPE9gRwBkRk+eZJSLTgjzHw3kkzEdbnx9Ydf3D0tuVkVmNV5RCayQki8DzIS9X7yTtgvlHoGew7m6FAmhmqrQ1/U1gzeKnprMkpVCNzgrie33j+RCMgN9LlmFi779kvAlbBi+AsY8Nbozw3y72Z3jwCWH9FPuQNqB65SKlbnGozz7acYOAR9PLTvKVxAq//jsqrPbt47XGaC80IPQOWDbntWZPC0vk53WENoelWC12PQ+w2C71vOto/cmomYKTi7Qhxsn5C1IZ2zVnxT1n67O5hrdh5+HnYtIQKBgQDdO7OKfcKQQTT5u1esXkeL8VWhVnUqtrq/JAdJhX6NmHMbe0KmozdvYBIHj/ygYwoh+mlVBhSa6zxUX4fFzCWGXSSkDVm2qAQT02UVnI4VyWhCYJ0a4beihQC5xadRNetxAfXCAsqhd8LHQkhnDqd01Ox5diAR4P1iZonWNML9hwKBgQDV8AorVUeHGfe6nIJCXpp/Q3k41pp9cWzU+YQ0rbnVFtz6I53JqIO5IEgWxU5aITgQdRp6oYYIWGjqXHCs2zmZ9eXCDaG8Yz1XlZOmV+kdl096xbxa8udvuHCQ77JUJIHacLfwH4vtE3xTuoozcASL6X/8DRrd2PC+MCfpsnSAtQKBgC2Xmh6WYDxeU86Lfzv0igYbDCydprPQyJePkzjhm/0OR9qIAmpZBJmdUNniIIvlHTd3ZbGx+RkcFWjF7zMiKTGRjHWKksyDv6NeU1yq0NVIloXnZzkc/z0s78NZKmwgEo9NehorfRrdlXR/AnDKolP/dWRqpZ1Joq5et1U/tleNAoGAL/1ZeqD1m5Oyo1SqT5MDGxsanSpem1cPL7MM+fgiYG9xgFbu9SZs7eEU6q61xbf/6IksOGf1NfgHCNNVnyDKCgRD1sTSC/1C7FusNDsyGqYveP3UI56rVb9M2OY73W4XBtYUHLQm4jZlxC5rA+DT1EK7p+GUrkEUt8T7nwud4KUCgYEAxRKLrbKnjj/rhcs/jlJ9bAi7v3kzWpwfJQZ88zAxT2C7F6Uwe7beFg5owW8XNKDQz2pw8kdfOCBlD+DfAl26nV7Xt3Legn0p9rPxdoi5yByLwnFuKmbW7SpBh9KB2mGTOh2addT6BB3rlljlCD0IWJM5KrgbGWp4ES3ecmgdRU8=";
	// APPID
	static final String APPID = "2016102600765974";
	/**
	 * 支付 阿里
	 * @param orderNum 订单号
	 * @param totalAmount	总金额
	 * @param subject 商品名称
	 * @return
	 * @throws Exception
	 */
	public static String toAliPay(String orderNum, Double totalAmount, 
			String subject, String token) throws Exception {
		String aliPayGateWayUrl = "https://openapi.alipaydev.com/gateway.do";
		AlipayClient alipayClient = new DefaultAlipayClient(aliPayGateWayUrl, APPID, ALIPAYAPPPRIVATEKEY,
				AlipayConstants.FORMAT_JSON, AlipayConstants.CHARSET_UTF8, ALIPAYPUBLICKEY,
				AlipayConstants.SIGN_TYPE_RSA2); 
		AlipayRequest alipayRequest = new AlipayTradeWapPayRequest();
		AlipayTradeWapPayModel alipayTradeWapPayModel = new AlipayTradeWapPayModel();
		alipayTradeWapPayModel.setOutTradeNo(orderNum);// 订单号
		alipayTradeWapPayModel.setTotalAmount(String.valueOf(totalAmount));// 总金额
		alipayTradeWapPayModel.setSubject(URLEncoder.encode(subject,"utf-8"));// 商品名称
		alipayTradeWapPayModel.setProductCode("QUICK_WAP_PAY"); // WAP：手机APP和浏览器
		alipayRequest.setBizModel(alipayTradeWapPayModel);
		alipayRequest.setReturnUrl("http://32486x69z7.qicp.vip/order/aliPaySuccess?TOKEN="+token);// 同步url地址 支付宝成功后返回的页面
		alipayRequest.setNotifyUrl("http://32486x69z7.qicp.vip/order/aliPayCallBack"); // 异步url地址  支付宝回调函数 修改数据库内自己的订单状态
		return alipayClient.pageExecute(alipayRequest).getBody();
	}
	/**
	 *	WAIT_BUYER_PAY	交易创建，等待买家付款
	 *	TRADE_CLOSED	未付款交易超时关闭，或支付完成后全额退款
	 *	TRADE_SUCCESS	交易支付成功
	 *	TRADE_FINISHED	交易结束，不可退款
	 */
	/**
	 * 程序执行完后必须打印输出“success”（不包含引号）。
	 * 如果商户反馈给支付宝的字符不是success这7个字符，
	 * 支付宝服务器会不断重发通知，
	 * 直到超过24小时22分钟。在25小时内完成6~10次通知
	 * （通知频率：5s,2m,10m,15m,1h,2h,6h,15h）。
	 * */
	public static Map<String, String> aliPayCallBack(HttpServletRequest request) throws AlipayApiException {
		Map<String, String> parameterMap = getParameterMap(request);
		// 该请求是否来源于阿里  验签
		boolean signVerified = AlipaySignature
	          .rsaCheckV1(parameterMap, ALIPAYPUBLICKEY, AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
		System.out.println(parameterMap);
		// 判断订单状态是否成功
		//	支付时传入的商户订单号 out_trade_no
		//支付宝28位交易号 trade_no
		if(signVerified && "TRADE_SUCCESS".equals(parameterMap.get("trade_status"))) {
			 Map<String,String> map = new HashMap<String,String>();
			 // 商家订单号
			String merchant  = parameterMap.get("out_trade_no");
			// 支付宝的订单号
			String aliPay = parameterMap.get("trade_no");
			map.put("out_trade_no", merchant);
			map.put("ali_trade_no", aliPay);
			map.put("status", parameterMap.get("trade_status"));
			return map;
		}
		// return fail/success;
		return null;
	}
	
	/**
	 * 将request中的数据转换成map
	 * @param request
	 * @return
	 */
	public static Map<String,String> getParameterMap(HttpServletRequest request) {
        Map paramsMap = request.getParameterMap(); 
        Map<String,String> returnMap = new HashMap();
        Iterator entries = paramsMap.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }
}
