package com.hostel.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.hostel.entity.UserAccount;

@Transactional
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

	public UserAccount findByEmail(String email);

	public UserAccount findByUserTypeAndUserName(Integer userType, String userName);

	public UserAccount findByUserTypeAndEmail(Integer userType, String email);

}
