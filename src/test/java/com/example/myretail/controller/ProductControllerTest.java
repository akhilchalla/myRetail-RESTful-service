package com.example.myretail.controller;

import com.example.myretail.dao.ProductInfo;
import com.example.myretail.dto.ProductDetails;
import com.example.myretail.dto.ProductPrice;
import com.example.myretail.exception.GenericApiErrorException;
import com.example.myretail.repository.ProductInfoRepository;
import com.example.myretail.service.ProductService;
import com.example.myretail.service.RestClientProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    private ProductInfoRepository productInfoRepository;

    @MockBean
    private RestClientProductService restClientProductService;

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    private static final Integer PRODUCT_ID =  99999999;

    private static final String PRODUCT_NAME = "TestProductName";

    private static final Double PRICE = 10.11;

    private static final String CURRENCY_CODE = "EUR";

    private static final ProductPrice PRODUCT_PRICE = new ProductPrice(PRICE, CURRENCY_CODE);

    private static final ProductDetails PRODUCT_DETAILS = new ProductDetails(PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE);

    private static final ProductInfo PRODUCT_INFO = new ProductInfo(PRODUCT_ID, PRICE, CURRENCY_CODE);

    private static final Integer INVALID_PRODUCT_ID = 11111111;

    private static final String PRODUCT_JSON = "{\n" +
            "    \"id\": 99999999,\n" +
            "    \"name\": \"TestProductName\",\n" +
            "    \"current_price\": {\n" +
            "        \"value\": 10.11,\n" +
            "        \"currency_code\": \"EUR\"\n" +
            "    }\n" +
            "}";

    @Test
    public void getProductDetailsByIdTest() throws Exception {
        when(productService.getProductDetails(PRODUCT_ID)).thenReturn(PRODUCT_DETAILS);

        this.mockMvc.perform(get("/products/"+PRODUCT_ID)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(PRODUCT_ID))
                .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$.current_price.value").value(PRICE))
                .andExpect(jsonPath("$.current_price.currency_code").value(CURRENCY_CODE));
    }

    @Test
    public void getProductDetailsInvalidIdTest() throws Exception {
        when(productService.getProductDetails(INVALID_PRODUCT_ID)).thenThrow(new GenericApiErrorException("Product record not found in DB",null));

        this.mockMvc.perform(get("/products/"+INVALID_PRODUCT_ID)).andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void getProductDetailsNullIdTest() throws Exception {
        this.mockMvc.perform(get("/products/null")).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateProductPriceByIdTest() throws Exception{
        doNothing().when(productService).updateProductPrice(PRODUCT_DETAILS);

        this.mockMvc.perform(put("/products/"+PRODUCT_ID)
                .content(PRODUCT_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));

    }

    @Test
    public void updateProductPriceInvalidIdTest() throws Exception{

        doThrow(new GenericApiErrorException("Product id doesn't exist", null)).when(productService).updateProductPrice(PRODUCT_DETAILS);

        this.mockMvc.perform(put("/products/"+INVALID_PRODUCT_ID)
                        .content(PRODUCT_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void updateProductPriceNoBodyTest() throws Exception{

        this.mockMvc.perform(put("/products/"+INVALID_PRODUCT_ID))
                .andDo(print())
                .andExpect(status().isUnsupportedMediaType());
    }

}