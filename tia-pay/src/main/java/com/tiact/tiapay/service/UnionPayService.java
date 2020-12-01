package com.tiact.tiapay.service;

import com.tiact.tiapay.entity.UnionPay;

/**
 * @author Tia_ct
 */
public interface UnionPayService {

    public String pay(UnionPay unionPay);

    public String payTwo(UnionPay unionPay);

    String payThree(UnionPay unionPay);
}
