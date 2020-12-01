package com.tiact.tiapay.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;
import com.tiact.tiapay.config.AliPayConfig;
import com.tiact.tiapay.entity.AliPay;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Tia_ct
 */
@Controller
@RequestMapping(value="/aliPay")
public class AliPayController {

    /**
     * 当面付-条码支付
     */
    @RequestMapping(value = "pay")
    @ResponseBody
    public String pay(AliPay aliPay) throws AlipayApiException {
        //公钥证书
        AlipayClient alipayClient = AliPayConfig.certClient();
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        request.setBizContent(aliPay.toTradePay());

        //公钥证书
        AlipayTradePayResponse response = alipayClient.certificateExecute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            return "支付成功 "+aliPay.getTotal_amount()+"元";
        } else {
            System.out.println("调用失败");
            return response.getSubMsg();
        }
    }

    /**
     * 结算界面-线上支付
     */
    @RequestMapping(value = "payTwo")
    @ResponseBody
    public String payTwo(AliPay aliPay) throws AlipayApiException {
        AlipayClient alipayClient = AliPayConfig.certClient();
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(AliPayConfig.return_url);
        request.setNotifyUrl(AliPayConfig.notify_url);

        request.setBizContent(aliPay.toPagePay());
        String url = "";
        try {
            url = alipayClient.pageExecute(request).getBody();

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        System.out.println(url);
        return url;
    }


    /**
     * 同步回调
     */
    @RequestMapping("/returnUrl")
    @ResponseBody
    public ModelAndView returnUrl(HttpServletRequest request,ModelAndView mav){
        Map<String, String[]> requestParams = request.getParameterMap();
        System.out.println("同步返回："+ JSON.toJSONString(requestParams));
        mav.setViewName("paid");
        return mav;
    }


    /**
     * 支付宝异步通知
     */
    @RequestMapping("/notifyUrl")
    @ResponseBody
    public void notifyUrl(HttpServletRequest request) throws Exception {
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        System.out.println("反馈："+JSON.toJSON(params).toString());
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AliPayConfig.ALIPAY_PUBLIC_KEY, AliPayConfig.CHARSET, AliPayConfig.SIGN_TYPE);

        if (signVerified) {
            System.out.println("异步通知成功");
            // 商户订单号
            String out_trade_no = request.getParameter("out_trade_no");
            System.out.println("商户订单号: "+out_trade_no);
            // 交易状态
            String trade_status = request.getParameter("trade_status");
            System.out.println("交易状态: "+trade_status);
            // 修改数据库
        } else {
            System.out.println("异步通知失败");
        }
    }
}
