package com.smart.web.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.web.product.model.HomeProduct;
import com.smart.web.product.service.GetAllProductService;

@RestController
@RequestMapping("/home/Product/")
public class ProductController {
	
	
	@Autowired
	private GetAllProductService homeProductService;
	
	/*
	 * Api for Returning List of Product
	 */
	
	@GetMapping("productList")
	public ResponseEntity<List<HomeProduct>> getAllHomePageProductList()
	{
		List<HomeProduct> productList=null;
		try
		{
			productList=homeProductService.getAllHomeProduct();
			if(productList==null)
			{
				return new ResponseEntity<List<HomeProduct>>(HttpStatus.NOT_FOUND);
			}
			
			
		}
		catch(Exception e)
		{
			
					}
		
		return new ResponseEntity<List<HomeProduct>>(productList,HttpStatus.OK);
		
	}
	

}
