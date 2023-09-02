package com.example.order.service.rule;

import com.example.order.common.Constant;
import com.example.order.domain.Beverage;
import com.example.order.domain.OrderReq;
import com.example.order.service.BaseRule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RuleScheduler {

    @Autowired
    @Qualifier("ruleMaps")
    private Map<String, List<BaseRule>> ruleMaps;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    public OrderReq filterOrder(OrderReq orderReq) throws ExecutionException, InterruptedException {
        List<BaseRule> baseRules = ruleMaps.get(Constant.ALL_RULE);
        List<CompletableFuture<OrderReq>> allRuleFutureList = new ArrayList<>(baseRules.size());
        baseRules.stream().forEach(o ->{
            CompletableFuture<OrderReq> completableFuture = CompletableFuture.supplyAsync(()->{
                    o.ruleHandler(orderReq);
                    return null;
            }, threadPoolExecutor);
            allRuleFutureList.add(completableFuture);
        });
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(allRuleFutureList.toArray(new CompletableFuture[allRuleFutureList.size()]));
        CompletableFuture<List<OrderReq>> results = allDoneFuture
                .thenApply(v -> allRuleFutureList.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
        List<OrderReq> orderReqs = getFutureResults(results);
        Map<String, List<Beverage>> beverageMap = new HashMap<>();
        List<Beverage> beverages = orderReq.getBeverages();

        beverages.stream().forEach(o ->{
            List<Beverage> bgs = beverageMap.get(o.getProductName());
            if(CollectionUtils.isEmpty(beverageMap)){
                bgs = new ArrayList<>();
            }
            bgs.add(o);
            beverageMap.put(o.getProductName(),bgs);
        });

        beverageMap.entrySet().stream().forEach(o ->{
            String productName = o.getKey();
            List<BaseRule> productNameRules = ruleMaps.get(productName);
            productNameRules.stream().forEach(ruleClass -> {
                ruleClass.ruleHandler(orderReq);
            });
        });


        return orderReq;
    }


    private static List<OrderReq> getFutureResults(CompletableFuture<List<OrderReq>> results) throws InterruptedException, ExecutionException {
        List<OrderReq> tempResults = null;
        try {
            tempResults = results.get();
        } catch (InterruptedException e) {
            log.info("InterruptedException: {}", e.getMessage());
            throw e;
        } catch (ExecutionException e) {
            log.info("ExecutionException: {}", e.getMessage());
            throw e;
        }
        return tempResults;
    }
}