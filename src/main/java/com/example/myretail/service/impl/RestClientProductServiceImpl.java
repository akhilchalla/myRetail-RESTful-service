package com.example.myretail.service.impl;

import com.example.myretail.exception.GenericApiErrorException;
import com.example.myretail.service.RestClientProductService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Id;
import java.util.Map;

@Service
@Slf4j
public class RestClientProductServiceImpl implements RestClientProductService {

    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;

    @Value("${baseurl}")
    private String baseUrl;

    /*
    Input : Product Id
    Output : Product Name
    Description : This method calls external API and retrieves Product Name.
    */

    public String getProductNameByIdFromApi(Integer id){
        String productName = "";
        ResponseEntity<String> responseEntity = null;

        try{
            String url = generateUrl(id);
            responseEntity = restTemplate.getForEntity(url,String.class);
            productName = retrieveProductName(responseEntity);
        }
        catch (Exception ex){
            throw new GenericApiErrorException("Failed to retrieve product name from api", null);
        }

        return productName;
    }

    /*
    Input : Product Id
    Output : Url
    Description : This method constructs external api url based on the provided id.
    Note: At this moment this method is not required since we are using a static JSON.
    */
    private String generateUrl(Integer id){
        // We can generate a url based on the provided id as per the requirement.
        //return baseUrl + "/"+id.toString();
        return baseUrl;
    }

    /*
    Input : Response Entity
    Output : Product Name
    Description : This method validates the response from external api and retrieves product name from json response.
    */
    private String retrieveProductName(ResponseEntity<String> response) throws JsonProcessingException {
        String productName = "";

        if(!response.getStatusCode().equals(HttpStatus.OK)){
            throw new GenericApiErrorException("", null);
        }

        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> data = mapper.readValue(response.getBody(), Map.class);
        productName = data.get("name");

        if(productName == null || productName.isEmpty()){
            throw new GenericApiErrorException("", null);
        }

        return productName;
    }

}
