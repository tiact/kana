package com.tiact.tiapay.config;

import com.tiact.tiapay.common.sdk.CertUtil;
import com.tiact.tiapay.common.sdk.SDKConfig;
import com.tiact.tiapay.unionpay.DemoBase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Tia_ct
 */
public class UnionPayConfig {

    /**
     * 商户号码
     * */
    public static final String MER_ID = "777290058185477";

    /**
     * 交易类型 01：消费
     */
    public static final String TXN_TYPE_01 = "01";

    /**
     * 交易子类型 01：自助消费 06：二维码消费
     */
    public static final String TXN_SUB_TYPE_01 = "01";
    public static final String TXN_SUB_TYPE_06 = "06";

    /**
     * 业务类型 B2C网关支付：000201 手机wap支付：000000
     */
    public static final String BIZ_TYPE_201 = "000201";
    public static final String BIZ_TYPE_000 = "000000";
    /**
     * 渠道类型 这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机
     */
    public static final String CHANNEL_TYPE_07 = "07";
    public static final String CHANNEL_TYPE_08 = "08";

    /**
     * 接入类型 0：直连商户
     */
    public static final String ACCESS_TYPE = "0";


    /**
     * 交易币种（境内商户一般是156 人民币）
     */
    public static final String CURRENCY_CODE = "156";


    public static final String SIGN_CERT = "/data/certs/acp_test_sign.pfx";

    public static final String SIGN_CERT_PWD = "000000";

    public static final String ENCRYPT_CERT = "/data/certs/acp_test_enc.cer";

    public static final String MIDDLE_CERT = "/data/certs/acp_test_middle.cer";

    public static final String ROOT_CERT = "/data/certs/acp_test_middle.cer";

    /**
     * 前端异步通知地址
     */
    public static String FRONT_URL = "http://tia-ct.xyz/tiapay/";

    /**
     * 后台异步通知地址
     */
    public static String BACK_URL = "http://tia-ct.xyz/tiapay/union/notifyUrl";


    /**
     * 重新加载证书
     */
    public static void setConfig(){
        System.out.println(SIGN_CERT);
        SDKConfig.getConfig().setSignCertPath(SIGN_CERT);
        SDKConfig.getConfig().setSignCertPwd(SIGN_CERT_PWD);
        SDKConfig.getConfig().setEncryptCertPath(ENCRYPT_CERT);
        SDKConfig.getConfig().setMiddleCertPath(MIDDLE_CERT);
        SDKConfig.getConfig().setRootCertPath(ROOT_CERT);
        CertUtil.initCert();
    }


    /**
     * 银联支付
     * @param requestData
     * @return
     */
    public static Map<String,String> setData(Map<String, String> requestData,Integer way){
        /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
        requestData.put("version", DemoBase.version);
        requestData.put("encoding", DemoBase.encoding);
        requestData.put("signMethod", SDKConfig.getConfig().getSignMethod());
        requestData.put("txnType", TXN_TYPE_01);

        requestData.put("txnSubType", way==0?TXN_SUB_TYPE_01:TXN_SUB_TYPE_06);
        requestData.put("bizType", way==0?BIZ_TYPE_201:BIZ_TYPE_000);
        requestData.put("channelType", way==0?CHANNEL_TYPE_07:CHANNEL_TYPE_08);

        /***商户接入参数***/
        requestData.put("merId", MER_ID);
        requestData.put("accessType",ACCESS_TYPE);
        requestData.put("txnTime",new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        requestData.put("currencyCode", CURRENCY_CODE);

        /*** 网站支付*/
        if(way==0){
            //前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”按钮的时候将异步通知报文post到该地址
            //如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
            requestData.put("frontUrl", UnionPayConfig.FRONT_URL);

            //后台通知地址（需设置为【外网】能访问 http https均可），支付成功后银联会自动将异步通知报文post到商户上送的该地址，失败的交易银联不会发送后台通知
            //后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 消费交易 商户通知
            //注意:1.需设置为外网能访问，否则收不到通知    2.http https均可  3.收单后台通知后需要10秒内返回http200或302状态码
            //    4.如果银联通知服务器发送通知后10秒内未收到返回状态码或者应答码非http200，那么银联会间隔一段时间再次发送。总共发送5次，每次的间隔时间为0,1,2,4分钟。
            //    5.后台通知地址如果上送了带有？的参数，例如：http://abc/web?a=b&c=d 在后台通知处理程序验证签名之前需要编写逻辑将这些字段去掉再验签，否则将会验签失败
            requestData.put("backUrl", UnionPayConfig.BACK_URL);

            // 订单超时时间。
            // 超过此时间后，除网银交易外，其他交易银联系统会拒绝受理，提示超时。 跳转银行网银交易如果超时后交易成功，会自动退款，大约5个工作日金额返还到持卡人账户。
            // 此时间建议取支付时的北京时间加15分钟。
            // 超过超时时间调查询接口应答origRespCode不是A6或者00的就可以判断为失败。
            requestData.put("payTimeout", new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis() + 15 * 60 * 1000));
        }else{
            requestData.put("backUrl", DemoBase.backUrl);
        }

        return requestData;
    }


}
