package com.example.order.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beverage  {

    @JsonProperty("PRODUCT_NAME")
    private String productName;

    @JsonProperty("BEVERAGESEAONING")
    List<BeverageSeasoning> beverageSeasonings;
}
