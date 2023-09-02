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
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CoffeeRule implements BaseRule {

    // 咖啡只能加奶盖
    @Override
    @RuleType(values = "americano,coffeeLatte")
    public void ruleHandler(OrderReq orderReq) {
        List<Beverage> beverages = orderReq.getBeverages();
        List<String> finalCoffeeList = Constant.coffeeList;
        if(!CollectionUtils.isEmpty(beverages)){
            List<Beverage> coffeeList = beverages.stream().filter(o -> finalCoffeeList.contains(o.getProductName())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(coffeeList)){
                coffeeList.stream().forEach(o ->{
                    List<String> beverageSeasonings = o.getBeverageSeasonings().stream().map(BeverageSeasoning::getSeasoningname).collect(Collectors.toList());
                    List<String> milkLidList = Constant.milkLidList;
                    boolean flag = beverageSeasonings.removeAll(milkLidList);
                    if(flag && !CollectionUtils.isEmpty(beverageSeasonings)){
                        throw new RuleException("-1");
                    }
                });
            }
        }
    }

}
