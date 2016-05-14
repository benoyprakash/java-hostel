package com.hostel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hostel.entity.UserAccount;
import com.hostel.info.CustomerInfo;
import com.hostel.repository.UserAccountRepository;

@Service(value = "customerServiceImpl")
public class CustomerService {

	@Autowired
	private UserAccountRepository userAccountRepository;

	public CustomerInfo getCustomer(Long id) {
		UserAccount customer = userAccountRepository.findByUserTypeAndEmail(0, "b@b.com");
		return buildCustomerInfo(customer);
	}
	
	public CustomerInfo saveCustomer(Long id) {
		UserAccount customer = new UserAccount();
		customer.setFirstName("Prakasan");
		customer.setEmail("b@b.com");
		customer.setLastName("Benoy");
		
		userAccountRepository.save(customer);
		return buildCustomerInfo(customer);
	}

	public List<CustomerInfo> getAllCustomerInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private CustomerInfo buildCustomerInfo(UserAccount user) {
		CustomerInfo customer = null;
		if (user != null) {
			customer = new CustomerInfo();
			buildCustomerInfo(customer, user);
		}
		return customer;
	}
	
	private void buildCustomerInfo(CustomerInfo customer, UserAccount user){
		
		
		customer.setAddressLine1(user.getAddressLine1());
		customer.setAddressLine2(user.getAddressLine2());
		customer.setAddressLine3(user.getAddressLine3());
		customer.setCity(user.getCity());
		customer.setCountry(user.getCountry());
		customer.setEmail(user.getEmail());
		customer.setFirstName(user.getFirstName());
		customer.setId(user.getId());
		customer.setLastName(user.getLastName());
		customer.setMobile1(user.getMobile1());
		customer.setMobile2(user.getMobile2());
		customer.setPassword(user.getPassword());
		customer.setPhone(user.getPhone());
		customer.setPinCode(user.getPinCode());
		customer.setState(user.getState());
		customer.setUserName(user.getUserName());
		customer.setUserType(user.getUserType());
	}

}
