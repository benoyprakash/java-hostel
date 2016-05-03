package com.hostel.service;

import java.util.List;

import com.hostel.info.CustomerInfo;

public interface CustomerService {

	public CustomerInfo getCustomer(Long id);
	
	public List<CustomerInfo> getAllCustomerInfo();
}
