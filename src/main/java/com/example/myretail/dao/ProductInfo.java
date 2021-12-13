package com.example.myretail.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name="product_info")
@Data
public class ProductInfo {

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="price")
    private Double price;

    @Column(name="currency_code")
    private String currencyCode;

    public ProductInfo(Integer id, Double price, String currencyCode){
        this.id = id;
        this.price = price;
        this.currencyCode = currencyCode;
    }

    public ProductInfo(){

    }
}
