package com.tiact.tiapay.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Tia_ct
 */
@Data
public class AliPay {

    /**
     * 商户订单号
     */
    String out_trade_no;

    /**
     * 条码支付：bar_code
     * 声波支付：wave_code
     */
    String scene = "bar_code";

    /**
     * 付款码
     */
    String auth_code;

    /**
     * 商品名
     */
    String subject = "测试商品1";

    /**
     * 消费金额
     */
    BigDecimal  total_amount;

    /**
     * 商品描述
     */
    String body = "测试参数";

    /**
     * 过期时间
     * 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
     */
    String timeout_express = "4h";


    /**
     * 当面付-条码支付
     */
    public String toTradePay() {
        return "{" +
                "\"out_trade_no\":\""+out_trade_no+"\"," +
                "\"scene\":\""+scene+"\"," +
                "\"auth_code\":\""+auth_code+"\"," +
                "\"subject\":\""+subject+"\"," +
                "\"body\":\"" + body + "\"," +
                "\"total_amount\":"+total_amount +"}";
    }


    /**
     *结算界面-线上支付
     */
    public String toPagePay(){
        return "{" +
                "\"out_trade_no\":\""+out_trade_no+"\"," +
                "\"subject\":\""+subject+"\"," +
                "\"body\":\"" + body + "\"," +
                "\"timeout_express\":\""+ timeout_express +"\"," +
                "\"total_amount\":"+total_amount +"," +
                "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}";
    }
}
