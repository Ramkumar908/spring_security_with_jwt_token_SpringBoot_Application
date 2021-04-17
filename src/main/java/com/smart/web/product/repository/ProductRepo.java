package com.smart.web.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart.web.product.model.HomeProduct;

@Repository
public interface ProductRepo   extends JpaRepository<HomeProduct, Long>{

}
