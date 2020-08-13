package com.demo.integrate.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.integrate.entities.Product;
import com.demo.integrate.repos.ProductRepo;



@Service
public class ProductService {
	
	@Autowired 
	private ProductRepo repo;
	
	public List<Product> getAllProducts(){
		return repo.findAll();
	}
	
	public Product getById(Integer id) {
		return repo.findById(id).get();
	}
	
	public void insert(Product product) {
		repo.save(product);
	}
	
	public void delete(Integer id) {
		repo.deleteById(id);
	}

}
