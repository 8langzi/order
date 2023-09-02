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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BeverageRule implements BaseRule {

    // 校验奥利奥奶盖和芝士奶盖同时添加的情况
    @Override
    @RuleType(values = "oreoMilklid")
    public void ruleHandler(OrderReq orderReq) {
        List<Beverage> beverages = orderReq.getBeverages();
        beverages.stream().forEach(o -> {
            List<BeverageSeasoning> beverageSeasonings = o.getBeverageSeasonings();
            if(!CollectionUtils.isEmpty(beverageSeasonings)){
                List<String> seasonings = beverageSeasonings.stream().map(BeverageSeasoning::getSeasoningname).collect(Collectors.toList());
                if(CollectionUtils.isEmpty(seasonings)){
                    if(seasonings.containsAll(Constant.milkLidList)){
                        throw new RuleException("-1");
                    }
                }
            }
        });


    }
}
