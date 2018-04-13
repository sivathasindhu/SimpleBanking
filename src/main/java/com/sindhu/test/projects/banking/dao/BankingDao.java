package com.sindhu.test.projects.banking.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sindhu.test.projects.banking.mapper.CustomerMapper;
import com.sindhu.test.projects.banking.model.Customer;

@Repository
public class BankingDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public boolean isUserExist(String userId) {
		try {
			return getCustomer(userId)!=null;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public double deposit(String userId, double depositAmount) {
		String sql = "UPDATE CUSTOMER SET BALANCE = BALANCE + ? WHERE USER_ID = ?";
		jdbcTemplate.update(sql, new Object[] { depositAmount, userId });

		sql = "SELECT BALANCE FROM CUSTOMER WHERE USER_ID=?";
		double balance = (double) jdbcTemplate.queryForObject(sql, new Object[] { userId }, Double.class);

		//Log Transaction
		insertTransactionEntries(userId, "DEPOSIT", depositAmount, balance);

		return balance;
	}

	public double withdraw(String userId, double withdrawAmount) {
		String sql = "SELECT BALANCE FROM CUSTOMER WHERE USER_ID=?";
		double balance = (double) jdbcTemplate.queryForObject(sql, new Object[] { userId }, Double.class);
		if (balance < withdrawAmount) {
			throw new RuntimeException("Error: Your withdrawal amount is greater than balance");
		}

		sql = "UPDATE CUSTOMER SET BALANCE = BALANCE - ? WHERE USER_ID = ?";
		jdbcTemplate.update(sql, new Object[] { withdrawAmount, userId });

		sql = "SELECT BALANCE FROM CUSTOMER WHERE USER_ID=?";
		balance = (double) jdbcTemplate.queryForObject(sql, new Object[] { userId }, Double.class);

		// logging
		insertTransactionEntries(userId, "WITHDRAWAL", withdrawAmount, balance);
		return balance;
	}

	@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
	public double transfer(String fromUserId, String toUserId, double transferAmount) {

		String sql = "SELECT BALANCE FROM CUSTOMER WHERE USER_ID=?";
		double fromBalance = (double) jdbcTemplate.queryForObject(sql, new Object[] { fromUserId }, Double.class);

		sql = "SELECT BALANCE FROM CUSTOMER WHERE USER_ID=?";
		double toBalance = (double) jdbcTemplate.queryForObject(sql, new Object[] { toUserId }, Double.class);

		if (fromBalance < transferAmount) {
			throw new RuntimeException("Error: Your withdrawal amount is greater than balance");
		}

		sql = "UPDATE CUSTOMER SET BALANCE = BALANCE - ? WHERE USER_ID = ?";
		jdbcTemplate.update(sql, new Object[] { transferAmount, fromUserId });

		sql = "UPDATE CUSTOMER SET BALANCE = BALANCE + ? WHERE USER_ID = ?";
		jdbcTemplate.update(sql, new Object[] { transferAmount, toUserId });

		//Log Transactions
		insertTransactionEntries(fromUserId, "TRANSFER TO A/C #: "+getAccountNo(toUserId), -transferAmount, fromBalance - transferAmount);
		insertTransactionEntries(toUserId, "TRANSFER FROM A/C #: "+getAccountNo(fromUserId), transferAmount, toBalance + transferAmount);

		return fromBalance - transferAmount;

	}

	public List<Customer> getAllCustomers() {
		List<Customer> customerList = jdbcTemplate.query("SELECT * FROM CUSTOMER", new CustomerMapper());
		return customerList;
	}

	public List<Map<String, Object>> getAllCustomerEntries() {
		List<Map<String, Object>> data = jdbcTemplate.queryForList("SELECT * FROM CUSTOMER");
		return data;
	}

	public void insertCustomer(String userId, String password, String name, String emailId) {
		String sql = "INSERT INTO CUSTOMER(USER_ID, PASSWORD, NAME, EMAIL_ID, BALANCE) VALUES(?, ?, ?, ?, 100)";
		jdbcTemplate.update(sql, new Object[] { userId, password, name, emailId });

	}

	public void insertTransactionEntries(String userId, String transactionType, double transactionAmount, double balance) {
		String sql = "INSERT INTO TRANSACTION (ACCOUNT_NO, TRANSACTION_TYPE, TRANSACTION_AMOUNT , BALANCE, TRANS_DATE) VALUES(?, ?, ?, ?, NOW())";
		jdbcTemplate.update(sql, new Object[] { getAccountNo(userId), transactionType, transactionAmount, balance });
	}

	public List<Map<String, Object>> getAllTransactionEntries(String userId) {
		String sql = "SELECT * FROM TRANSACTION WHERE ACCOUNT_NO=?";
		List<Map<String, Object>> data = jdbcTemplate.queryForList(sql, new Object[] { getAccountNo(userId) });
		return data;
	}
	
	private long getAccountNo(String userId) {
		String sql = "SELECT ACCOUNT_NO FROM CUSTOMER WHERE USER_ID=?";
		return jdbcTemplate.queryForObject(sql, new Object[] { userId }, Long.class);
	}
	
	public Customer getCustomer(String userId) {
		String sql = "SELECT * FROM CUSTOMER WHERE USER_ID=?";
		return jdbcTemplate.queryForObject(sql, new Object[] { userId }, new CustomerMapper());
	}

	public List<Map<String, Object>> getTopFiveCustomers() {
		String sql = "SELECT * FROM CUSTOMER ORDER BY BALANCE DESC LIMIT 5";
		List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
		return data;
	}

	public List<Map<String, Object>> getTopFiveActiveCustomers() {
		String sql = "SELECT c.*, t.TRANS_COUNT FROM CUSTOMER c, (SELECT ACCOUNT_NO, COUNT(ACCOUNT_NO) AS TRANS_COUNT FROM TRANSACTION GROUP BY ACCOUNT_NO ORDER BY TRANS_COUNT DESC LIMIT 5) t where c.ACCOUNT_NO=t.ACCOUNT_NO ORDER BY t.TRANS_COUNT DESC";
		List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
		return data;
	}

}
