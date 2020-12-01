package com.tiact.tiapay.controller;

import com.tiact.tiapay.common.sdk.AcpService;
import com.tiact.tiapay.common.sdk.LogUtil;
import com.tiact.tiapay.common.sdk.SDKConstants;
import com.tiact.tiapay.entity.PayParam;
import com.tiact.tiapay.entity.UnionPay;
import com.tiact.tiapay.service.UnionPayService;
import com.tiact.tiapay.unionpay.DemoBase;
import com.tiact.tiapay.unionpay.UnionPayClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Tia_ct
 */

@Slf4j
@Controller
@RequestMapping("/union")
public class UnionPayController {

    @Autowired
    private UnionPayService sus;

    /**
     * 网站支付
     */
    @RequestMapping(value = "pay")
    public String pay(UnionPay unionPay, ModelMap map){
        String payHtml = sus.pay(unionPay);
        map.addAttribute("form", payHtml);
        return "union/pay";
    }


    /**
     * 条码支付
     * @return
     */
    @RequestMapping(value = "payTwo")
    @ResponseBody
    public String payTwo(UnionPay unionPay){
        return sus.payTwo(unionPay);
    }

    /**
     * 生成二维码
     */
    @RequestMapping(value = "payThree")
    @ResponseBody
    public String payThree(UnionPay unionPay){
        return sus.payThree(unionPay);
    }



    /**
     * 后台异步通知路径
     * @param req
     * @return
     */
    @RequestMapping(value = "notifyUrl")
    public ResponseEntity<Object> notifyUrl(HttpServletRequest req){

        log.info("BackRcvResponse接收后台通知开始");
        String encoding = req.getParameter(SDKConstants.param_encoding);

        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(req);

        Map<String, String> valideData = null;
        if (null != reqParam && !reqParam.isEmpty()) {

            Iterator<Map.Entry<String, String>> it = reqParam.entrySet().iterator();
            valideData = new HashMap<String, String>(reqParam.size());

            while (it.hasNext()) {

                Map.Entry<String, String> e = it.next();
                String key = (String) e.getKey();
                String value = (String) e.getValue();
                valideData.put(key, value);
            }
        }

        String merId =valideData.get("merId"); //获取后台通知的数据，其他字段也可用类似方式获取
        PayParam.setConfig(PayParam.getConfig().get(merId));
//        SDKConfig.getConfig().loadPropertiesFromSrc();  //加载支付参数

        //重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
        if (!AcpService.validate(valideData, encoding)) {
            log.info("验证签名结果[失败].");
            //验签失败，需解决验签问题
        } else {
            log.info("验证签名结果[成功].");

            //【注：为了安全验签成功才应该写商户的成功处理逻辑】交易成功，更新商户订单状态

            String orderId =valideData.get("orderId"); //获取后台通知的数据，其他字段也可用类似方式获取
            String respCode = valideData.get("respCode");
            System.out.println(orderId+respCode);
        }

        log.info("BackRcvResponse接收后台通知结束");

        //返回给银联服务器http 200  状态码
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    /**
     * 获取参数集合
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(
            final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                    // System.out.println("======为空的字段名===="+en);
                    res.remove(en);
                }
            }
        }
        return res;
    }



    /**
     * 支付
     * @param orderNumber 商户订单号
     * @param txnAmt 金额（分）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "orderpay")
    public String pay(@RequestParam String orderNumber,
                      @RequestParam Integer txnAmt,
                      @RequestParam String goodsName,ModelMap map) throws Exception{

        String payhtml = UnionPayClient.pay(orderNumber, String.valueOf(txnAmt*100),goodsName, "777290058185477");
        //生成自动跳转的form表单，直接返给前端，让前端做页面的跳转
        map.addAttribute("form", payhtml);
        return "union/pay";
    }

    /**
     * 订单状态查询
     * @param orderNumber
     * @param txnTime
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "orderquery")
    public String query(@RequestParam String orderNumber,
                                      @RequestParam String txnTime)throws Exception{

        //参数限制逻辑

        Map<String, String> rspData = UnionPayClient.query(orderNumber, txnTime);

        //返回参数处理
        if(!rspData.isEmpty()){
            //验证签名
            if(AcpService.validate(rspData, DemoBase.encoding)){
                log.info("验证签名成功");
                if("00".equals(rspData.get("respCode"))){//如果查询交易成功
                    //处理被查询交易的应答码逻辑
                    String origRespCode = rspData.get("origRespCode");
                    if("00".equals(origRespCode)){
                        System.out.println("交易成功了！！！！！！！！");

                        //交易成功，更新商户订单状态
                        //数据库修改成功后告诉前端，用户支付成功
                        return "success";

                    }else if("03".equals(origRespCode) ||
                            "04".equals(origRespCode) ||
                            "05".equals(origRespCode)){
                        //需再次发起交易状态查询交易
                    }else{
                        //其他应答码为失败请排查原因
                    }
                }else{
                    //查询交易本身失败，或者未查到原交易，检查查询交易报文要素
                }
            }else{
                LogUtil.writeErrorLog("验证签名失败");
                //检查验证签名失败的原因
            }
        }else{
            //未返回正确的http状态
            LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
        }
        return rspData.get("queryId")+"==="+rspData.get("traceNo");
    }

    @RequestMapping(value = "orderrefund")
    public String refund(@RequestParam String refundOrderId,
                                       @RequestParam String txnAmt,
                                       @RequestParam String queryId){

        Map<String, String> rspData = UnionPayClient.refund(refundOrderId, txnAmt, queryId);

        /**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
        //应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
        if(!rspData.isEmpty()){
            if(AcpService.validate(rspData, DemoBase.encoding)){
                log.info("验证签名成功");
                String respCode = rspData.get("respCode");
                if("00".equals(respCode)){

                    //交易已受理，等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。
                    return "success";

                }else if("03".equals(respCode)||
                        "04".equals(respCode)||
                        "05".equals(respCode)){
                    //后续需发起交易状态查询交易确定交易状态
                }else{
                    //其他应答码为失败请排查原因
                }
            }else{
                LogUtil.writeErrorLog("验证签名失败");
            }
        }else{
            //未返回正确的http状态
            LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
        }

        return "success";
    }
}
