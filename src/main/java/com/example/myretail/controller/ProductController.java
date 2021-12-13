package com.example.myretail.controller;

import com.example.myretail.dto.ProductDetails;
import com.example.myretail.service.ProductService;
import com.example.myretail.util.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@CrossOrigin
@Validated
@RequestMapping("/")
public class ProductController {

    @Autowired
    ProductService productService;

    // Retrieve Product Details based on the id provided
    @GetMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<ProductDetails> getProductDetailsById(@Valid @PathVariable("id") Integer id){

        ProductDetails pd = productService.getProductDetails(id);
        return ResponseEntity.status(HttpStatus.OK).body(pd);
    }

    // Update product price if provided product id exist.
    // Providing ID in the URL is not required since it is already provided as part of ProductDetails Object.
    // However, ID is also provided to ensure it matches with the instructions.
    @PutMapping(value = "/products/{id}", consumes = "application/json")
    public ResponseEntity<String> updateProductPriceById(
            @Valid @PathVariable("id") Integer id,
            @RequestBody ProductDetails productDetails){

        productService.updateProductPrice(productDetails);
        return ResponseEntity.status(HttpStatus.OK).body(AppConstant.SUCCESS);
    }

}
