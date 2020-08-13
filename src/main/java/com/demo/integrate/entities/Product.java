package com.demo.integrate.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer pid;
	private String pName;
	private Integer pPrice;

	public Product() {
	}

	public Product(Integer pid, String pName, Integer pPrice) {
		this.pid = pid;
		this.pName = pName;
		this.pPrice = pPrice;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public Integer getpPrice() {
		return pPrice;
	}

	public void setpPrice(Integer pPrice) {
		this.pPrice = pPrice;
	}
	
	@Override
	public String toString() {
		return "id="+pid+"\n Product_name="+pName+"\n price="+pPrice;
	}

}
