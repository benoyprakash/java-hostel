package com.hostel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.hostel.entity.UserAccount;
import com.hostel.info.CustomerInfo;
import com.hostel.repository.UserAccountRepository;
import com.hostel.service.UserAccountService;
import com.hostel.utils.Constants;
import com.hostel.utils.ErrorConstants;

public class UserAccountServiceimpl implements UserAccountService {

	private static final Logger LOGGER = Logger.getLogger("UserAccountServiceimpl");

	@Autowired
	private UserAccountRepository userAccountRepository;

	private CustomerInfo buildCustomerInfo(UserAccount user) {
		CustomerInfo customer = null;
		if (user != null) {
			customer = new CustomerInfo();
			buildCustomerInfo(customer, user);
		}
		return customer;
	}

	private void buildCustomerInfo(CustomerInfo customer, UserAccount user) {

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
		customer.setUserStatus(user.getUserStatus());
	}

	private void buildUserAccount(UserAccount user, CustomerInfo customer) {
		user.setAddressLine1(customer.getAddressLine1());
		user.setAddressLine2(customer.getAddressLine2());
		user.setAddressLine3(customer.getAddressLine3());
		user.setCity(customer.getCity());
		user.setCountry(customer.getCountry());
		user.setEmail(customer.getEmail());
		user.setFirstName(customer.getFirstName());
		user.setId(customer.getId());
		user.setLastName(customer.getLastName());
		user.setMobile1(customer.getMobile1());
		user.setMobile2(customer.getMobile2());
		user.setPassword(customer.getPassword());
		user.setPhone(customer.getPhone());
		user.setPinCode(customer.getPinCode());
		user.setState(customer.getState());
		user.setUserName(customer.getUserName());
		user.setUserStatus(customer.getUserStatus());
	}

	private UserAccount getCustomer(Long id) {
		UserAccount customer = userAccountRepository.findById(id);
		return customer;
	}

	private UserAccount saveUserAccount(UserAccount user) {
		return userAccountRepository.save(user);
	}

	@Override
	public CustomerInfo getCustomerInfo(Long id) {
		UserAccount customer = getCustomer(id);
		return buildCustomerInfo(customer);
	}

	@Override
	public List<CustomerInfo> getCustomers(int userStatus) {
		List<UserAccount> users = userAccountRepository.findByUserStatus(userStatus);
		List<CustomerInfo> userInfoList = new ArrayList<CustomerInfo>();
		for (UserAccount user : users) {
			CustomerInfo customer = buildCustomerInfo(user);
			userInfoList.add(customer);
		}
		return null;
	}

	@Override
	public CustomerInfo saveCustomerInfo(CustomerInfo customer) {
		if (customer == null) {
			throw new IllegalArgumentException(ErrorConstants.USER_DELETEION_ERROR1);
		}
		UserAccount user = null;
		if (customer.getId() == null) {
			user = new UserAccount();
		}
		buildUserAccount(user, customer);
		user = saveUserAccount(user);
		return buildCustomerInfo(user);
	}

	@Override
	public void deleteCustomer(Long userId) {
		UserAccount user = getCustomer(userId);
		if (user == null) {
			LOGGER.warning(ErrorConstants.USER_DELETEION_ERROR2 + userId);
		}
		user.setUserStatus(Constants.USER_STATUS_INACTIVE);
		saveUserAccount(user);
	}
}
