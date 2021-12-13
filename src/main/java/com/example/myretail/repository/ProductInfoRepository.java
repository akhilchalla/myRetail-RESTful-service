package com.example.myretail.repository;

import com.example.myretail.dao.ProductInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductInfoRepository extends CrudRepository<ProductInfo, Long> {

    Optional<ProductInfo> findById(Integer id);

}
