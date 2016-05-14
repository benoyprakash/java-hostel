package com.hostel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hostel.info.CustomerInfo;
import com.hostel.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	@Qualifier(value = "customerServiceImpl")
	private CustomerService customerService;

	@RequestMapping(value = { "/customers/customer" })
	public CustomerInfo getCustomer(HttpServletRequest request) {
		return customerService.getCustomer(1L);
	}
	
	@RequestMapping(value = { "/customers/save" })
	public CustomerInfo saveCustomer(HttpServletRequest request) {
		return customerService.getCustomer(1L);
	}
}
