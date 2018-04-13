package com.sindhu.test.projects.banking.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.sindhu.test.projects.banking.model.Customer;

public class CustomerMapper implements RowMapper<Customer> {

	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer customer = new Customer();
		customer.setAccountNo(rs.getLong("ACCOUNT_NO"));
		customer.setUserId(rs.getString("USER_ID"));
		customer.setPassword(rs.getString("PASSWORD"));
		customer.setName(rs.getString("NAME"));
		customer.setEmailId(rs.getString("EMAIL_ID"));
		customer.setBalance(rs.getDouble("BALANCE"));
		return customer;
	}

}
