package com.example.myretail.service;

import com.example.myretail.dto.ProductDetails;

public interface ProductService {

    public ProductDetails getProductDetails(Integer id);

    public void updateProductPrice(ProductDetails productDetails);
}
