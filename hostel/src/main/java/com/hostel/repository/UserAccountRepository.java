package com.hostel.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hostel.entity.UserAccount;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

	public UserAccount findByEmail(String email);

	public UserAccount findById(Long id);

	public UserAccount findByUserStatusAndUserName(int userStatus, String userName);

	public UserAccount findByUserStatusAndEmail(int userStatus, String email);

	public List<UserAccount> findByUserStatus(int userStatus);

}
