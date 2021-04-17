package com.smart.web.product.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.web.product.model.HomeProduct;
import com.smart.web.product.repository.ProductRepo;
import com.smart.web.product.service.GetAllProductService;

@Service
public class HomeProductServiceImpl  implements GetAllProductService{

	
	@Autowired
	private ProductRepo productRepo;
	@Override
	public List<HomeProduct> getAllHomeProduct() {
		
		return productRepo.findAll();
	}

}
