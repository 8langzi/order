package com.example.order.service;


import com.example.order.domain.OrderReq;

public interface BaseRule {

    public void ruleHandler(OrderReq orderReq);

}
