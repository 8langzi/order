package com.example.order.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeverageSeasoning {

    @JsonProperty("SEASONING_NAME")
    private String Seasoningname;


    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BeverageSeasoning beverageSeasoning = (BeverageSeasoning) obj;
        return Objects.equals(this.Seasoningname, beverageSeasoning.getSeasoningname());
    }
    // 重写hashCode()方法
    @Override
    public int hashCode() {
        return Objects.hash(this.Seasoningname);
    }
}
