package com.example.order.service;

import com.example.order.domain.OrderReq;
import com.example.order.domain.OrderResp;

public interface CompleteCostServiceI {

    public OrderResp completeSale(OrderReq orderReq);

}
