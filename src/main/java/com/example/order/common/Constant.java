package com.example.order.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Constant {

    public static String ALL_RULE = "*";


    public static ConcurrentHashMap<String,String> coffee = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String,String> beverage = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String,String> seasoning = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String,String> allProd = new ConcurrentHashMap<>();
    public static List<String> beverageList = new ArrayList<>();
    public static List<String> coffeeList = new ArrayList<>();
    public static List<String> seasoningList = new ArrayList<>();

    public static List<String> milkLidList = new ArrayList<>();

    // 先用着，后面可以放在数据库中，再后面可以放在redis或者服务端缓存
    static {

        beverage.put("coconutgelMilkTea","10");
        beverage.put("sagoMilkTea","10");
        beverage.put("honeyMilkTea","12");
        beverage.put("almondMilkTea","14");

        coffee.put("americano","11");
        coffee.put("coffeeLatte","12");

        seasoning.put("ormosia","2");
        seasoning.put("taro","2");
        seasoning.put("oreoMilklid","4");
        seasoning.put("cheeseMilklid","5");

        allProd.putAll(beverage);
        allProd.putAll(coffee);

        beverageList = beverage.keySet().stream().collect(Collectors.toList());
        coffeeList = coffee.keySet().stream().collect(Collectors.toList());
        seasoningList = seasoning.keySet().stream().collect(Collectors.toList());

        milkLidList.add("oreoMilklid");
        milkLidList.add("cheeseMilkLid");
    }


}
