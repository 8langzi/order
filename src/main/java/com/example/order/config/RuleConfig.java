package com.example.order.config;

import com.example.order.common.Constant;
import com.example.order.service.BaseRule;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;

@Configuration
public class RuleConfig implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean("ruleMaps")
    public Map<String, List<BaseRule>> getSmsTradeTypeMapper() {
        Map<String, BaseRule> baseRuleMap = applicationContext.getBeansOfType(BaseRule.class);
        List<BaseRule> allTypeMatchList = new ArrayList<>();

        Map<String,List<BaseRule>> ruleMaps = new HashMap<>();
        baseRuleMap.entrySet().stream().forEach(o ->{
            String className = o.getKey();
            BaseRule baseRule = o.getValue();
            Method[] methods = baseRule.getClass().getDeclaredMethods();
            Arrays.stream(methods).forEach(k -> {
                RuleType ruleType = k.getDeclaredAnnotation(RuleType.class);
                if(ruleType != null){
                    String values = ruleType.values();
                    if(Constant.ALL_RULE.equals(values)){
                        allTypeMatchList.add(baseRule);
                    }else {
                        String[] types = values.split(",");
                        Arrays.stream(types).forEach(type ->{
                            List<BaseRule> baseRules = ruleMaps.get(type);
                            if(baseRules == null){
                                baseRules = new ArrayList<>();
                            }
                            baseRules.add(baseRule);
                            ruleMaps.put(type,baseRules);
                        });
                    }
                    ruleMaps.put("*",allTypeMatchList);
                }
            });

        });
        return ruleMaps;
    }

}
