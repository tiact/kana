package com.tiact.tiapay.entity;

import lombok.Data;

/**
 * @author Tia_ct
 */

@Data
public class UnionPay {

    /**
     * 订单号
     */
    String orderId;

    /**
     * 交易金额
     */
    Double txnAmt;

    /**
     * 商品
     */
    String goodsName;

    /**
     * 条码
     */
    String qrNo;

    /**
     * 终端号
     */
    String termId = "UNION0133";

}
