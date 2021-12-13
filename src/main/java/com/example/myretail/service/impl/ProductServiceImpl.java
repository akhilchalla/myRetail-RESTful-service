package com.example.myretail.service.impl;

import com.example.myretail.dao.ProductInfo;
import com.example.myretail.dto.ProductDetails;
import com.example.myretail.dto.ProductPrice;
import com.example.myretail.exception.GenericApiErrorException;
import com.example.myretail.repository.ProductInfoRepository;
import com.example.myretail.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoRepository productInfoRepository;

    @Autowired
    RestClientProductServiceImpl restClientProductService;

    // Input: Product Id
    // Output : ProductDetails Object
    // Description : This method constructs ProductDetails object where it fetches product name from external api and produce price from db.
    public ProductDetails getProductDetails(Integer id){
        ProductDetails productDetails = new ProductDetails();

        // Retrieves Product Name from external api
        String productName = restClientProductService.getProductNameByIdFromApi(id);
        // Retrieves ProductPrice from DB
        ProductPrice productPrice = getProductPrice(id);

        productDetails.setId(id);
        productDetails.setProductName(productName);
        productDetails.setProductPrice(productPrice);

        return productDetails;
    }

    /*
    Input : Product Id
    Output : ProductPrice Object
    Description : This method constructs ProductPrice object based on the provided id. It retrieves product price details from DB.
    */
    private ProductPrice getProductPrice(Integer id){
        ProductPrice productPrice = new ProductPrice();

        Optional<ProductInfo> productInfo = productInfoRepository.findById(id);

        if(productInfo.isPresent()){
            productPrice.setPrice(productInfo.get().getPrice());
            productPrice.setCurrencyCode(productInfo.get().getCurrencyCode());
        }
        else{
            throw new GenericApiErrorException("Product record not found in DB",null);
        }

        return productPrice;
    }

    /*
    Input : Product Details Object
    Output : None
    Description : This method updates product price information in DB if the provided JSON is valid and if id exists in DB.
    */
    public void updateProductPrice(ProductDetails productDetails){
        ProductPrice productPrice = new ProductPrice();

        if(isProductDetailsValid(productDetails)){
            productPrice = productDetails.getProductPrice();
            ProductInfo productInfo = new ProductInfo(productDetails.getId(),productPrice.getPrice(), productPrice.getCurrencyCode());
            productInfoRepository.save(productInfo);
        }
    }

    /*
    Input : Product Details Object
    Output : Boolean
    Description : This method validates if the input json (ProductDetails) is valid or not and if the provided Id exist in DB.
    If not, then it raises GenericApiErrorException.
    */
    private boolean isProductDetailsValid(ProductDetails productDetails){

        if(productDetails == null || productDetails.getId() == null || productDetails.getProductPrice() == null){
            throw new GenericApiErrorException("Invalid input data", null);
        }

        ProductPrice productPrice = productDetails.getProductPrice();
        if(productPrice.getPrice() == null || productPrice.getCurrencyCode() == null){
            throw new GenericApiErrorException("Invalid input data", null);
        }

        Optional<ProductInfo> productInfo = productInfoRepository.findById(productDetails.getId());
        if(!productInfo.isPresent()){
            throw new GenericApiErrorException("Product id doesn't exist", null);
        }

        return true;
    }
}
