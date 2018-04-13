package com.sindhu.test.projects.banking.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class CustomerUserDetails implements UserDetails {
	
	private String username;
	private String password;
	private long accountNo;
	private String name;
	private String emailId;
	
	public CustomerUserDetails(String username, String password, long accountNo, String name, String emailId) {
		this.username = username;
		this.password = password;
		this.accountNo = accountNo;
		this.name = name;
		this.emailId = emailId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public long getAccountNo() {
		return accountNo;
	}

	public String getName() {
		return name;
	}

	public String getEmailId() {
		return emailId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
