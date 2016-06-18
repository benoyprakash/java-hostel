package com.hostel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hostel.info.CustomerInfo;
import com.hostel.service.UserAccountService;

@RestController
@RequestMapping(value = "/customers")
public class UserController {

	@Autowired
	@Qualifier(value = "userAccountServiceimpl")
	private UserAccountService userAccountService;

	@RequestMapping(value = { "/customer" }, method = RequestMethod.GET)
	public ResponseEntity<CustomerInfo> getCustomer(HttpServletRequest request, @RequestParam("userId") Long userId) {
		return new ResponseEntity<CustomerInfo>(userAccountService.getCustomerInfo(userId), HttpStatus.OK);
	}

	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	public ResponseEntity<CustomerInfo> saveCustomer(HttpServletRequest request, @RequestBody CustomerInfo customer) {
		return new ResponseEntity<CustomerInfo>(userAccountService.saveCustomerInfo(customer), HttpStatus.OK);
	}

	@RequestMapping(value = { "/delete/{userId}" }, method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCustomer(HttpServletRequest request, @PathVariable("userId") Long userId) {
		return new ResponseEntity<String>(String.valueOf(""), HttpStatus.OK);
	}
}
