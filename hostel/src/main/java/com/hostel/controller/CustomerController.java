package com.hostel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hostel.info.CustomerInfo;
import com.hostel.service.CustomerService;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

	@Autowired
	@Qualifier(value = "customerService")
	private CustomerService customerService;

	@RequestMapping(value = { "/customer" })
	public ResponseEntity<CustomerInfo> getCustomer(HttpServletRequest request, @RequestParam("userId")Long userId) {

		return new ResponseEntity<CustomerInfo>(customerService.getCustomer(userId), HttpStatus.OK);
	}

	@RequestMapping(value = { "/save" })
	public ResponseEntity<CustomerInfo> saveCustomer(HttpServletRequest request) {
		return new ResponseEntity<CustomerInfo>(customerService.saveCustomer(1L), HttpStatus.OK);
	}
}
