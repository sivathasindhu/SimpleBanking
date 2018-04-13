package com.sindhu.test.projects.banking.util;

import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sindhu.test.projects.banking.model.CustomerUserDetails;

public class Utils {

	public static String getUserIdFromSession() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    return authentication.getName();
		}
		return null;
	}
	
	public static CustomerUserDetails getUserDetail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    return (CustomerUserDetails)authentication.getPrincipal();
		}
		return null;
	}

	public static String getCSVContent(List<Map<String, Object>> data) {
		StringBuilder header = new StringBuilder();
		StringBuilder content = new StringBuilder();
		
		for (Map<String, Object> map : data) {
			
			//Prepare header once initially
			if(header.length()==0){
				for (String key : map.keySet()) {
					header.append(key).append(",");
				}
				header.deleteCharAt(header.length()-1); //delete last comma
				header.append("\n");
			}
			
			//Prepare content
			for (String key : map.keySet()) {
				content.append(map.get(key)).append(",");
			}
			content.deleteCharAt(content.length()-1); //delete last comma
			content.append("\n");
		}
		return header.append(content).toString();
	}
}
