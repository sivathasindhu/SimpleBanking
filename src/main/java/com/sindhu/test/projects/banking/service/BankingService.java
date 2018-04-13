package com.sindhu.test.projects.banking.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sindhu.test.projects.banking.dao.BankingDao;
import com.sindhu.test.projects.banking.model.Customer;

@Service
public class BankingService {

	@Autowired
	private BankingDao bankingDao;

	public String createUserInfo(String userId, String password, String name, String emailId) {
		if (bankingDao.isUserExist(userId)) {
			return "ERROR: User Id is already exist. Please give different Id";
		}
		bankingDao.insertCustomer(userId, password, name, emailId);
		return "Your account is successfully signed up";
	}

	public double deposit(String userId, double amount) {
		return bankingDao.deposit(userId, amount);
	}

	public double withdraw(String userId, double withdrawAmount) {
		return bankingDao.withdraw(userId, withdrawAmount);
	}

	public double transfer(String userId1, String userId2, double transferAmount) {
		return bankingDao.transfer(userId1, userId2, transferAmount);
	}

	public List<Customer> getAllCustomers() {
		return bankingDao.getAllCustomers();
	}

	public List<Map<String, Object>> getAllCustomerEntries() {
		return bankingDao.getAllCustomerEntries();
	}

	public List<Map<String, Object>> getAllTransactionEntries(String userId) {
		return bankingDao.getAllTransactionEntries(userId);
	}

	public List<Map<String, Object>> getTopFiveCustomer() {
		return bankingDao.getTopFiveCustomers();
	}

	public List<Map<String, Object>> getTopFiveActiveCustomer() {
		return bankingDao.getTopFiveActiveCustomers();
	}
}
