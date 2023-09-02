package com.example.order.service.rule;

import com.example.order.Exception.RuleException;
import com.example.order.common.Constant;
import com.example.order.config.RuleType;
import com.example.order.domain.Beverage;
import com.example.order.domain.BeverageSeasoning;
import com.example.order.domain.OrderReq;
import com.example.order.service.BaseRule;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderRule implements BaseRule {

    @Override
    @RuleType(values = "*")
    public void ruleHandler(OrderReq orderReq) {
        List<Beverage> beverages = orderReq.getBeverages();
        List<String> milkLidList = Constant.milkLidList;
        // 校验每笔订单的饮品不可超过10杯
        if(CollectionUtils.isEmpty(beverages) || beverages.size() > 10){
           throw new RuleException("-1");
        }
        // 校验每杯饮品的每种料最多加一份
        // 校验奥利奥奶盖和芝士奶盖不能同时添加
        beverages.stream().forEach(o -> {
            List<BeverageSeasoning> beverageSeasonings = o.getBeverageSeasonings();
            if(!StringUtils.isEmpty(beverageSeasonings)){
                Set<BeverageSeasoning> sets = new HashSet<>(beverageSeasonings);
                if(beverageSeasonings.size() != sets.size()){
                    throw new RuleException("-1");
                }
                if(sets.containsAll(milkLidList)){
                    throw new RuleException("-1");
                }
            }
        });
    }
}
