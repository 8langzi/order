package com.example.order.service.impl;

import com.example.order.common.Constant;
import com.example.order.domain.Beverage;
import com.example.order.domain.BeverageSeasoning;
import com.example.order.domain.OrderReq;
import com.example.order.domain.OrderResp;
import com.example.order.service.CompleteCostServiceI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class CompleteCostServiceImpl implements CompleteCostServiceI {

    @Override
    public OrderResp completeSale(OrderReq orderReq) {
        BigDecimal orderSale = BigDecimal.ZERO;
        BigDecimal beverageSale = BigDecimal.ZERO;
        BigDecimal beverageSeasoningSale = BigDecimal.ZERO;
        List<Beverage> beverages = orderReq.getBeverages();
        ConcurrentHashMap<String, String> allProd = Constant.allProd;
        ConcurrentHashMap<String, String> seasoning = Constant.seasoning;
        for (int i = 0; i < beverages.size(); i++) {
            Beverage beverage = beverages.get(i);
            String productPrice = allProd.get(beverage.getProductName());
            beverageSale = beverageSale.add(new BigDecimal(productPrice));
            List<BeverageSeasoning> beverageSeasonings = beverage.getBeverageSeasonings();
            for (int j = 0; j < beverageSeasonings.size(); j++) {
                BeverageSeasoning beverageSeasoning = beverageSeasonings.get(j);
                String seasoningPrice = seasoning.get(beverageSeasoning.getSeasoningname());
                beverageSeasoningSale = beverageSeasoningSale.add(new BigDecimal(seasoningPrice));
            }
        }
        orderSale = orderSale.add(beverageSale).add(beverageSeasoningSale);
        OrderResp orderResp = OrderResp.builder()
                .orderSale(orderSale.toString())
                .beverageSale(beverageSale.toString())
                .beverageSeasoningSale(beverageSeasoningSale.toString())
                .beverages(orderReq.getBeverages()).build();

        return orderResp;
    }
}
