package com.hostel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hostel.info.CustomerInfo;

@Service(value = "customerServiceImpl")
public class CustomerServiceImpl implements CustomerService{

	@Override
	public CustomerInfo getCustomer(Long id) {
		return new CustomerInfo("Benoy");
	}

	@Override
	public List<CustomerInfo> getAllCustomerInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
