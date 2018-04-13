package com.sindhu.test.projects.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sindhu.test.projects.banking.dao.BankingDao;
import com.sindhu.test.projects.banking.model.Customer;
import com.sindhu.test.projects.banking.model.CustomerUserDetails;

@Service
public class CustomerDetailsService implements UserDetailsService {
	
	@Autowired
	private BankingDao bankingDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer = bankingDao.getCustomer(username);
		return new CustomerUserDetails(username, customer.getPassword(), customer.getAccountNo(), customer.getName(), customer.getEmailId());
	}

}
