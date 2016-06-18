package com.hostel.service;

import java.util.List;

import com.hostel.entity.UserAccount;
import com.hostel.info.CustomerInfo;

/**
 * @author Binu
 *
 */
public interface UserAccountService {
	
	public CustomerInfo getCustomerInfo(Long id);
	
	public List<CustomerInfo> getCustomers(int userStatus);

	public CustomerInfo saveCustomerInfo(CustomerInfo customer);
	
	public void deleteCustomer(Long id);
}
