package com.pay.modules.wxpay.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.SortedMap;

/**
 * 相关配置参数 
 * 创建者 科帮网 
 * 创建时间 2017年7月31日
 */
public class ConfigUtil {
	private static Configuration configs;
	public  static String APP_ID;// 服务号的应用ID
	public  static String APP_SECRET;// 服务号的应用密钥
	public  static String TOKEN;// 服务号的配置token
	public  static String MCH_ID;// 商户号
	public  static String API_KEY;// API密钥
	public  static String SIGN_TYPE;// 签名加密方式
	public  static String CERT_PATH ;//微信支付证书

	public static synchronized void init(String filePath) {
		if (configs != null) {
			return;
		}
		try {
			configs = new PropertiesConfiguration(filePath);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

		if (configs == null) {
			throw new IllegalStateException("can`t find file by path:"
					+ filePath);
		}
		APP_ID = configs.getString("APP_ID");
		APP_SECRET = configs.getString("APP_SECRET");
		TOKEN = configs.getString("TOKEN");
		MCH_ID = configs.getString("MCH_ID");
		API_KEY = configs.getString("API_KEY");
		SIGN_TYPE = configs.getString("SIGN_TYPE");
		CERT_PATH = configs.getString("CERT_PATH");
	}

	/**
	 * 微信支付接口地址
	 */
	// 微信支付统一接口(POST)
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 微信退款接口(POST)
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	// 订单查询接口(POST)
	public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	// 关闭订单接口(POST)
	public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	// 退款查询接口(POST)
	public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	// 对账单接口(POST)
	public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	// 短链接转换接口(POST)
	public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
	// 接口调用上报接口(POST)
	public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
    
	/**
	 * 基础参数
	 * @Author  科帮网
	 * @param packageParams  void
	 * @Date	2017年7月31日
	 * 更新日志
	 * 2017年7月31日  科帮网 首次创建
	 */
	public static void commonParams(SortedMap<Object, Object> packageParams) {
		// 账号信息
		String appId = ConfigUtil.APP_ID;
		String mchId = ConfigUtil.MCH_ID;
		// 生成随机字符串
		String currTime = PayCommonUtil.getCurrTime();
		String strTime = currTime.substring(8);
		String strRandom = PayCommonUtil.buildRandom(4) + "";
		String nonce_str = strTime + strRandom;
		packageParams.put("appid", appId);// 公众账号ID
		packageParams.put("mch_id", mchId);// 商户号
		packageParams.put("nonce_str", nonce_str);// 随机字符串
	}
}