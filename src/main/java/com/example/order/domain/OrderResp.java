package com.example.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResp {

    private String orderSale;

    private String beverageSale;

    private String beverageSeasoningSale;

    private List<Beverage> beverages;

}
