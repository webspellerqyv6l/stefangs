package com.itstyle.modules.ppay.service.impl;

import cn.hutool.core.util.IdUtil;
import com.itstyle.common.model.Product;
import com.itstyle.common.model.Result;
import com.itstyle.modules.ppay.service.AliPayService;
import com.itstyle.modules.ppay.util.PayUtils;
import com.yungouos.pay.common.PayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    private PayUtils payUtils;

    @Override
    public Result aliPay(Product product) {
        try {
            Long orderNo = IdUtil.getSnowflake(1,1).nextId();
            product.setOutTradeNo(orderNo+"");
            String result = payUtils.aliPay(product);
            /**
             * 数据库生成订单
             */
            return Result.ok(result);
        }catch (PayException e){
            return Result.error(e.getMessage());
        }
    }
}
