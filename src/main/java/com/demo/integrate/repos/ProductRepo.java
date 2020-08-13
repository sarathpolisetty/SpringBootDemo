package com.demo.integrate.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.integrate.entities.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{

}
