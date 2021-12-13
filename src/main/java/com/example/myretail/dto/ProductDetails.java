package com.example.myretail.dto;

import com.example.myretail.util.AppConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetails {

    @JsonProperty("id")
    @Min(0)
    @Max(1000000000)
    private Integer id;

    @JsonProperty("name")
    @Pattern(regexp = AppConstant.DATA_PATTERN)
    @Size(min = 1, max = 100)
    private String productName;

    @JsonProperty("current_price")
    private ProductPrice productPrice;

    public ProductDetails() {
    }

    public ProductDetails(Integer id, String productName, ProductPrice productPrice) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
    }

}
