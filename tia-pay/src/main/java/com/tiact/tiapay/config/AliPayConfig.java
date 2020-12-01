package com.tiact.tiapay.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Tia_ct
 */
public class AliPayConfig {

    //应用Id
    public static String APP_ID = "2021000118691749";

    //应用私钥
    public static String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCGaVtKtxUWQu2ifFuUZzUGVUsiwb3hXm+4RPpz+xOJLnoPDscIroXKLO5zbXp7udJDrSBuVssDI7vKzO0VGGuZosLXa/cixsg9J3Svc51Dz9z40zONBDg3fuPo1BGXUbIhFcghorWYAE9c+krRGahHK3IhaCiw3aRWSKV35fF/SqhgCWDlY7DZu+3ZgD+ILlCHAWgjj4nuQnLDs0c/rsh3PQOkc9MXcoQZa5eEZOGD+LpWtIUoU0vyj2Q4MsWtUHJOXuqpkPInDlpqaT+yjhFBsIID04bZJJicrCCEHS3k6wqxDaeIggJf1pIi3knhhfp8pq/IDbDKMIvn+aKSOLEdAgMBAAECggEASYvUnClZrHiqtsH9ClL+vJxnJHmGgI6/6sAzmsrDgcigmSSo26NDKcE0KIy6/IBNASL2H+n1453l364J/AUEu2hH3MAC1lCWuH+iZW6JmtVeP/iwuHSaniMcagXAKmBVa60TePQjESz6QBHkZk33uhmXtZfczAo7JctSXyy+27sXlmrJu7nCKQu4ZKlAnio1qqWAw8ZFF+wMrvS0obWcZUeOI14hFECFYWGGtCESFkCwN4rb+sKDMcg7o0LRCwTSkZFDrtt8tdfCATNtFE3Ab7GAr1f3D85UW7c8pD+nJSfpIMXUSZlvTJXUJcYFQ/qvDiGXv+FQA0clHVRaMVyMMQKBgQDzpO7mLofZMxorItpYLXAx1z7ewUYTvlZWRPh47RR3Z2Alk9GPso4nplqP33CHQJHjTxTi3uZAzkNCjW7anbi7avSajpx7g2cneKJ2V9KJAStFX5WasWaXNohD8Fk7VXUqdyp2ilC7A9py4jbJhbq5GbZDrwtZR87pcCDuXvncCwKBgQCNOlRq2Rm1a+E6rCC9hlrypdtpplAjMHTqlk5/drWhFRa5m3f/eN6UzfAa0mNNrNZcLrGvNf2Mhk3OX9FziwZTugLRodQVp8MDXTh6bdDi7xhLg1ARDPJ9QhPPlo/PO0HNi8N1an2+cynyy2PU7Phv5caahLZ/zADixZBnoXM4dwKBgC9e0+wBA/ygyOe2IQmf2/50wQ6COWPBT/v58O1h+ua0PKdRtB7iMm1XFKiAYtqJHVQ6ne72M4JKGB2qbfEnVTFrPB+Itrf09mtgMwYbUR2+EnGsgePPtgiUW64J3u+AUAVvodvmTIfv2dsYTFqmG4W9kAhc0jILmhkc6eouoRm9AoGAIya5jD6tH3LShIADRikCyhJuIkW6/Ic7EoiG0UMCwi01ks0XeQbkn7UCZiqnfNXgJOGaaDnP+gITq6I9x7x6vqw1t2fIGfu/lj7Fp/NdYp6QNXthmlzWnkasF6cp9DKTlk2LQNZYpeaUMlJdbsnsrC1M2Xje08A7GDaEUsbkntECgYBBCzdXr+2cffLgf1YI0sZSFLp1bBHjvniIYqjNVlcpcWXBid8jTxcW/uxyuOYlAs7dTldvFrAKwr7X+/m6WKD0pHGW7OpDH2VgnFwzR/xyrYqvI+qhKUceyGcQ0pL0DVg0IikRr1xWXlrS7OMjT7o/YE/6cl6yOhJGGQD4UEe4Iw==";

    //支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApD2QcuhY/TntbpQwuUgCKv7cflIg/eo2gReJ6OuIAoE9Nen1cjv3wwv/9NPB9pYYW6XJKy84EZiJp/XNPBYlP0VRnseNtLDTgww15anjZg6GnW1+6WVgZpN0sNU6cj6bl6oguKwUklNzzZ2v4mFC3v0Im7UvWf49xJjy7Rk21mTPBzmpRLGDuWyh1ghXwqlD3ygft3rEOvWDp0OrhzXvP/Fy+IRdybBb8QQshGq/GRAwwPEujpE2+nv7fToxzaGgiUk9YJ0oMKeDHh43qu3xIUfb6qAV884CnE1pRaQ8OJ5og3JQZGP8FYoBElgvTeFAjX3jckRdGldvFx4K6Yi6ywIDAQAB";

    //应用公钥证书路径
    public static String CERT_PATH = "/data/crt/appCertPublicKey.crt";

    //支付宝公钥证书路径
    public static String ALIPAY_PUBLIC_CERT_PATH = "/data/crt/alipayCertPublicKey_RSA2.crt";

    //支付宝根证书路径
    public static String ROOT_CERT_PATH = "/data/crt/alipayRootCert.crt";

    //签名类型
    public static String SIGN_TYPE = "RSA2";

    //字符集
    public static String CHARSET = "UTF-8";

    //数据类型
    public static String DATA_FORMAT = "json";

    //网关地址
    public static String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";

    //同步回调
    public static String notify_url = "https://tia-ct.xyz/tiapay/Alipay/notifyUrl";

    //异步通知
    public static String return_url = "https://tia-ct.xyz/tiapay/Alipay/returnUrl";


    public static AlipayClient client(){
        //公钥
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL
                ,APP_ID
                ,APP_PRIVATE_KEY
                ,DATA_FORMAT
                ,CHARSET
                ,ALIPAY_PUBLIC_KEY
                ,SIGN_TYPE);
        return alipayClient;
    }


    public static AlipayClient certClient() throws AlipayApiException {
        //公钥证书
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(GATEWAY_URL);
        certAlipayRequest.setAppId(APP_ID);
        certAlipayRequest.setPrivateKey(APP_PRIVATE_KEY);
        certAlipayRequest.setCertPath(CERT_PATH);
        certAlipayRequest.setAlipayPublicCertPath(ALIPAY_PUBLIC_CERT_PATH);
        certAlipayRequest.setRootCertPath(ROOT_CERT_PATH);
        certAlipayRequest.setCharset(CHARSET);
        certAlipayRequest.setSignType(SIGN_TYPE);
        certAlipayRequest.setFormat(DATA_FORMAT);
        AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
        return alipayClient;
    }

}
