package com.tiact.tiapay.entity;

import com.tiact.tiapay.common.sdk.CertUtil;
import com.tiact.tiapay.common.sdk.SDKConfig;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @program service-feign-client
 * @description: 支付参数
 * @author: chenmet
 * @create: 2020/04/10 10:02
 */
@Data
public class PayParam {

    private String merId = "777290058185477";

    private String frontUrl="http://localhost:8080/tiapay/";

    private String backUrl="http://tia-ct.xyz/tiapay/pay";

    private String signCert;

    private String signCertPwd;

    private String encryptCert;

    private String middleCert;

    private String rootCert;


    public static Map<String,PayParam> getConfig(){
        Map<String,PayParam> config=new HashMap<>();
        PayParam payParam=new PayParam();
        payParam.setMerId("777290058185477");
        payParam.setEncryptCert("/certs/acp_test_enc.cer");
        payParam.setMiddleCert("/certs/acp_test_middle.cer");
        payParam.setRootCert("/certs/acp_test_middle.cer");
        payParam.setSignCert("/certs/acp_test_sign.pfx");
        payParam.setSignCertPwd("000000");
        config.put(payParam.merId,payParam);

        PayParam payParam1=new PayParam();
        payParam1.setMerId("777290058185477");
        payParam1.setEncryptCert("/certs/acp_test_enc.cer");
        payParam1.setMiddleCert("/certs/acp_test_middle.cer");
        payParam1.setRootCert("/certs/acp_test_middle.cer");
        payParam1.setSignCert("/certs/acp_test_sign.pfx");
        payParam1.setSignCertPwd("000000");
        config.put(payParam1.merId,payParam1);
        return  config;
    }


    public static void setConfig(PayParam payParam){
        SDKConfig.getConfig().setSignCertPath(payParam.getSignCert());
        SDKConfig.getConfig().setSignCertPwd(payParam.getSignCertPwd());
        SDKConfig.getConfig().setEncryptCertPath(payParam.getEncryptCert());
        SDKConfig.getConfig().setMiddleCertPath(payParam.getMiddleCert());
        SDKConfig.getConfig().setRootCertPath(payParam.getRootCert());
        CertUtil.initCert();
    }

}
