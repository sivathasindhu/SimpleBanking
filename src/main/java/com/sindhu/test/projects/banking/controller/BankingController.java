package com.sindhu.test.projects.banking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.sindhu.test.projects.banking.model.Customer;
import com.sindhu.test.projects.banking.service.BankingService;
import com.sindhu.test.projects.banking.util.Utils;

@RestController
public class BankingController {

	@Autowired
	private BankingService bankingService;

	@RequestMapping(method = RequestMethod.GET, value = "/banking/greeting")
	public ResponseEntity<String> welcome() {
		return new ResponseEntity<String>(new Gson().toJson(Utils.getUserDetail()), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/banking/signUp")
	public ResponseEntity<String> signUp(String userId, String password, String name, String emailId) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(password) || StringUtils.isEmpty(name) || StringUtils.isEmpty(emailId)) {
			return new ResponseEntity<String>("Error: Please enter all the required details", HttpStatus.BAD_REQUEST);
		}
		String result = bankingService.createUserInfo(userId, password, name, emailId);
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/banking/deposit")
	public ResponseEntity<String> depositAmount(double amount) {
		if (StringUtils.isEmpty(amount)) {
			return new ResponseEntity<String>("Error: Please enter all the required details", HttpStatus.BAD_REQUEST);
		}
		if (amount < 20) {
			return new ResponseEntity<String>("Error: Minimum deposit amount is $20", HttpStatus.BAD_REQUEST);
		}
		double balance = bankingService.deposit(Utils.getUserIdFromSession(), amount);
		String result = "Your amount is successfully deposited. Your new balance is:" + balance;
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/banking/withdraw")
	public ResponseEntity<String> withdrawAmount(double withdrawAmount) {
		if (StringUtils.isEmpty(withdrawAmount)) {
			return new ResponseEntity<String>("Error: Please enter all the required details", HttpStatus.BAD_REQUEST);
		}
		if (withdrawAmount < 20) {
			return new ResponseEntity<String>("Error: Minimum withdraw amount is $20", HttpStatus.BAD_REQUEST);
		}
		double balance = bankingService.withdraw(Utils.getUserIdFromSession(), withdrawAmount);
		String result = "You have successfully withdrawn. Your new balance is:" + balance;
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/banking/transfer")
	public ResponseEntity<String> transferAmount(String toUserId, double transferAmount) {
		if (StringUtils.isEmpty(toUserId)) {
			return new ResponseEntity<String>("Error: Please enter all the required details", HttpStatus.BAD_REQUEST);
		}
		if(Utils.getUserIdFromSession().equals(toUserId)) {
			return new ResponseEntity<String>("Error: Customer can't transfer to self - Please select other's account", HttpStatus.BAD_REQUEST);
		}
		if (transferAmount < 20) {
			return new ResponseEntity<String>("Error: Minimum transfer amount is $20", HttpStatus.BAD_REQUEST);
		}
		double balance = bankingService.transfer(Utils.getUserIdFromSession(), toUserId, transferAmount);

		String result = "You have successfully transfered. Your new balance is:" + balance;
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	// Get all records as Customer objects
	@RequestMapping(method = RequestMethod.GET, value = "/banking/getAllCustomers")
	public ResponseEntity<String> getAllCustomers() {
		List<Customer> customerList = bankingService.getAllCustomers();
		return new ResponseEntity<String>(new Gson().toJson(customerList), HttpStatus.OK);
	}

	// Get all records with column names
	@RequestMapping(method = RequestMethod.GET, value = "/banking/getAllCustomerEntries")
	public ResponseEntity<String> getAllCustomerEntries() {
		List<Map<String, Object>> data = bankingService.getAllCustomerEntries();
		return new ResponseEntity<String>(new Gson().toJson(data), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/banking/getCustomerTransactionDetails", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> getCustomerTransactionDetails() {
		List<Map<String, Object>> data = bankingService.getAllTransactionEntries(Utils.getUserIdFromSession());
		return new ResponseEntity<String>(new Gson().toJson(data), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/banking/getCustomerTransactionReport")
	public @ResponseBody ResponseEntity<String> getCustomerTransactionReport() {
		List<Map<String, Object>> data = bankingService.getAllTransactionEntries(Utils.getUserIdFromSession());

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("content-disposition", "attachment; filename=report.csv");
		responseHeaders.add("Content-Type", "text/plain");

		ResponseEntity<String> responseEntity = new ResponseEntity<String>(Utils.getCSVContent(data), responseHeaders, HttpStatus.OK);
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/banking/getTopFiveCustomer")
	public ResponseEntity<String> getTopFiveCustomer() {
			List<Map<String, Object>> data = bankingService.getTopFiveCustomer();
			return new ResponseEntity<String>(new Gson().toJson(data), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/banking/getTopFiveCustomerReport")
	public @ResponseBody ResponseEntity<String> getTopFiveCustomerReport() {
		List<Map<String, Object>> data = bankingService.getTopFiveCustomer();

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("content-disposition", "attachment; filename=TopFiveReport.csv");
		responseHeaders.add("Content-Type", "text/plain");

		ResponseEntity<String> responseEntity = new ResponseEntity<String>(Utils.getCSVContent(data), responseHeaders, HttpStatus.OK);
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/banking/getTopFiveActiveCustomer")
	public ResponseEntity<String> getTopFiveActiveCustomer() {
			List<Map<String, Object>> data = bankingService.getTopFiveActiveCustomer();
			return new ResponseEntity<String>(new Gson().toJson(data), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/banking/getTopFiveActiveCustomerReport")
	public @ResponseBody ResponseEntity<String> getTopFiveActiveCustomerReport() {
		List<Map<String, Object>> data = bankingService.getTopFiveActiveCustomer();

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("content-disposition", "attachment; filename=TopFiveActiveReport.csv");
		responseHeaders.add("Content-Type", "text/plain");

		ResponseEntity<String> responseEntity = new ResponseEntity<String>(Utils.getCSVContent(data), responseHeaders, HttpStatus.OK);
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/banking/logout")
	public ResponseEntity<String> logout() {
		SecurityContextHolder.getContext().setAuthentication(null);
		return new ResponseEntity<String>("Successfully logged out. Please click here for <A href='/banking/'><b>Login</b></A>", HttpStatus.UNAUTHORIZED);
	}
}
