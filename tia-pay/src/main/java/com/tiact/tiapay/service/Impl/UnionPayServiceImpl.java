package com.tiact.tiapay.service.Impl;

import com.tiact.tiapay.common.sdk.AcpService;
import com.tiact.tiapay.common.sdk.LogUtil;
import com.tiact.tiapay.common.sdk.SDKConfig;
import com.tiact.tiapay.config.UnionPayConfig;
import com.tiact.tiapay.entity.UnionPay;
import com.tiact.tiapay.service.UnionPayService;
import com.tiact.tiapay.unionpay.DemoBase;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tia_ct
 */
@Service
public class UnionPayServiceImpl implements UnionPayService {


    /**
     * 网站支付
     * @param unionPay
     * @return
     */
    @Override
    public String pay(UnionPay unionPay) {
        Map<String, String> requestData = new HashMap<String, String>();

        requestData.put("orderId",unionPay.getOrderId());
        requestData.put("txnAmt", String.format("%.0f", unionPay.getTxnAmt()*100));
        requestData.put("riskRateInfo", "{commodityName="+unionPay.getGoodsName()+"}");
        UnionPayConfig.setData(requestData,0);

        Map<String, String> submitFromData = AcpService.sign(requestData, DemoBase.encoding);

        String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();

        String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,DemoBase.encoding);

        return html;
    }

    /**
     * 条码支付
     * @param unionPay
     * @return
     */
    @Override
    public String payTwo(UnionPay unionPay) {
        Map<String, String> requestData = new HashMap<String, String>();
        requestData.put("orderId",unionPay.getOrderId());
        requestData.put("txnAmt", String.format("%.0f", unionPay.getTxnAmt()*100));
        requestData.put("qrNo", unionPay.getQrNo());
        UnionPayConfig.setData(requestData,1);

        Map<String, String> reqData = AcpService.sign(requestData,DemoBase.encoding);

        String requestAppUrl = SDKConfig.getConfig().getBackRequestUrl();

        Map<String, String> rspData = AcpService.post(reqData,requestAppUrl,DemoBase.encoding);

        if(!rspData.isEmpty()){
            if(AcpService.validate(rspData, DemoBase.encoding)){
                LogUtil.writeLog("验证签名成功");
                String respCode = rspData.get("respCode") ;
                if(("00").equals(respCode)){
                    return "00";
                }else{
                }
            }else{
                LogUtil.writeErrorLog("验证签名失败");
            }
        }else{
            LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");

        }
//        String reqMessage = DemoBase.genHtmlResult(reqData);
//        String rspMessage = DemoBase.genHtmlResult(rspData);
        return rspData.get("respMsg");
    }

    @Override
    public String payThree(UnionPay unionPay) {
        Map<String, String> requestData = new HashMap<String, String>();
        requestData.put("orderId",unionPay.getOrderId());
        requestData.put("txnAmt", String.format("%.0f", unionPay.getTxnAmt()*100));
        UnionPayConfig.setData(requestData,1);
        //申请消费二维码
        requestData.put("txnSubType", "07");

        Map<String, String> reqData = AcpService.sign(requestData,DemoBase.encoding);

        String requestAppUrl = SDKConfig.getConfig().getBackRequestUrl();

        Map<String, String> rspData = AcpService.post(reqData,requestAppUrl,DemoBase.encoding);

        if(!rspData.isEmpty()){
            if(AcpService.validate(rspData, DemoBase.encoding)){
                LogUtil.writeLog("验证签名成功");
                String respCode = rspData.get("respCode") ;
                if(("00").equals(respCode)){
                    return "00"+rspData.get("qrCode");
                }else{
                }
            }else{
                LogUtil.writeErrorLog("验证签名失败");
            }
        }else{
            LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
        }
        return rspData.get("respMsg");
    }
}
