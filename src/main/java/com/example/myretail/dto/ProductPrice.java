package com.example.myretail.dto;

import com.example.myretail.util.AppConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductPrice {

    @JsonProperty("value")
    @Pattern(regexp = AppConstant.DATA_PATTERN)
    private Double price;

    @JsonProperty("currency_code")
    @Pattern(regexp = AppConstant.DATA_PATTERN)
    @Size(min = 0, max = 10)
    private String currencyCode;

    public ProductPrice(Double price, String currencyCode) {
        this.price = price;
        this.currencyCode = currencyCode;
    }

    public ProductPrice() {

    }
}
