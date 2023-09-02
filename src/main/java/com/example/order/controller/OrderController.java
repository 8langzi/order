package com.example.order.controller;

import com.example.order.domain.OrderReq;
import com.example.order.domain.OrderResp;
import com.example.order.service.CompleteCostServiceI;
import com.example.order.service.rule.RuleScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RuleScheduler ruleScheduler;

    @Autowired
    private CompleteCostServiceI completeCostService;

    @RequestMapping("/submit")
    public OrderResp submitOrder(@RequestBody OrderReq orderReq){
        OrderResp orderResp;
        try {
            if(CollectionUtils.isEmpty(orderReq.getBeverages())){
                throw new RuntimeException("-1");
            }
            ruleScheduler.filterOrder(orderReq);
            orderResp = completeCostService.completeSale(orderReq);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return orderResp;
    }


}
