package com.tiact.tiapay.unionpay;


import com.tiact.tiapay.common.sdk.AcpService;
import com.tiact.tiapay.common.sdk.SDKConfig;
import com.tiact.tiapay.config.UnionPayConfig;
import com.tiact.tiapay.entity.PayParam;
import com.tiact.tiapay.entity.UnionPay;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @program service-feign-client
 * @description:
 * @author: chenmet
 * @create: 2020/04/08 14:27
 */
public class UnionPayClient {

        /**
         *  银联支付
         * @param orderId 商户订单号
         * @param txnAmt 金额 （分）
         * @param goodsName 商品名称
         * @return
         */
        public static String pay(String orderId,String txnAmt,String goodsName,String merId){
            Map<String, String> requestData = new HashMap<String, String>();

            requestData.put("orderId",orderId);
            //交易金额，单位分，不要带小数点
            requestData.put("txnAmt", txnAmt);
            //requestData.put("reqReserved", "透传字段");        		      //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。

            requestData.put("riskRateInfo", "{commodityName="+goodsName+"}");
            UnionPayConfig.setData(requestData,0);

            //SDKConfig.getConfig().loadPropertiesFromSrc();  //加载支付参数
            //重新设置支付参数
            UnionPayConfig.setConfig();

            /**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/

            //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
            Map<String, String> submitFromData = AcpService.sign(requestData,DemoBase.encoding);
            //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
            String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
            //生成自动跳转的Html表单
            String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,DemoBase.encoding);

            return html;

        }

        /**
         * 查询订单状态
         * @param orderId 订单号
         * @param txnTime 订单发送时间
         * @return
         */
        public static Map<String, String> query(String orderId,String txnTime){

            Map<String, String> data = new HashMap<String, String>();

            /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
            data.put("version", "5.1.0");                 //版本号
            data.put("encoding", DemoBase.encoding);               //字符集编码 可以使用UTF-8,GBK两种方式
            data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
            data.put("txnType", "00");                             //交易类型 00-默认
            data.put("txnSubType", "00");                          //交易子类型  默认00
            data.put("bizType", "000201");                         //业务类型 B2C网关支付，手机wap支付

            /***商户接入参数***/
            data.put("merId", UnionPayConfig.MER_ID);                  //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
            data.put("accessType", "0");                           //接入类型，商户接入固定填0，不需修改

            /***要调通交易以下字段必须修改***/
            data.put("orderId", orderId);                 //****商户订单号，每次发交易测试需修改为被查询的交易的订单号
            data.put("txnTime", txnTime);                 //****订单发送时间，每次发交易测试需修改为被查询的交易的订单发送时间

            /**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
            Map<String, String> reqData = AcpService.sign(data,DemoBase.encoding);//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。

            String url = SDKConfig.getConfig().getSingleQueryUrl();// 交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.singleQueryUrl
            //这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
            Map<String, String> rspData = AcpService.post(reqData,url,DemoBase.encoding);

            return rspData;

        }

        /**
         * 订单退款
         * @param refundOrderId （退款单号）
         * @param txnAmt 退款金额（分）
         * @param origQryId 原消费交易返回的的queryId
         * @return
         */
        public static Map<String, String> refund(String refundOrderId,String txnAmt,String origQryId){

            String txnTime = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());

            Map<String, String> data = new HashMap<String, String>();

            /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
            data.put("version","5.1.0");               //版本号
            data.put("encoding", DemoBase.encoding);             //字符集编码 可以使用UTF-8,GBK两种方式
            data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
            data.put("txnType", "04");                           //交易类型 04-退货
            data.put("txnSubType", "00");                        //交易子类型  默认00
            data.put("bizType", "000201");                       //业务类型 B2C网关支付，手机wap支付
            data.put("channelType", "07");                       //渠道类型，07-PC，08-手机

            /***商户接入参数***/
            data.put("merId", UnionPayConfig.MER_ID);                //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
            data.put("accessType", "0");                         //接入类型，商户接入固定填0，不需修改
            data.put("orderId",refundOrderId);          //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则，重新产生，不同于原消费
            data.put("txnTime", txnTime);      //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
            data.put("currencyCode", "156");                     //交易币种（境内商户一般是156 人民币）
            data.put("txnAmt", txnAmt);                          //****退货金额，单位分，不要带小数点。退货金额小于等于原消费金额，当小于的时候可以多次退货至退货累计金额等于原消费金额
            //data.put("reqReserved", "透传信息");                  //请求方保留域，如需使用请启用即可；透传字段（可以实现商户自定义参数的追踪）本交易的后台通知,对本交易的交易状态查询交易、对账文件中均会原样返回，商户可以按需上传，长度为1-1024个字节。出现&={}[]符号时可能导致查询接口应答报文解析失败，建议尽量只传字母数字并使用|分割，或者可以最外层做一次base64编码(base64编码之后出现的等号不会导致解析失败可以不用管)。
            data.put("backUrl", UnionPayConfig.BACK_URL);               //后台通知地址，后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 退货交易 商户通知,其他说明同消费交易的后台通知

            /***要调通交易以下字段必须修改***/
            data.put("origQryId", origQryId);      //****原消费交易返回的的queryId，可以从消费交易后台通知接口中或者交易状态查询接口中获取

            /**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
            Map<String, String> reqData  = AcpService.sign(data,DemoBase.encoding);//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
            String url = SDKConfig.getConfig().getBackRequestUrl();//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl

            Map<String, String> rspData = AcpService.post(reqData,url,DemoBase.encoding);//这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过

            return rspData;

        }

}
