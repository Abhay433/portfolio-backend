package com.portfolio.portfoliogenerator.util;

import org.springframework.stereotype.Component;

@Component
public class Capitalizer {
	
	public String capitalise(String value) {
	    if (value == null || value.trim().isEmpty()) {
	        return value;
	    }

	    String[] words = value.trim().split("\\s+");
	    StringBuilder result = new StringBuilder();

	    for (String word : words) {
	        if (!word.isEmpty()) {
	            result.append(Character.toTitleCase(word.charAt(0)))
	                  .append(word.substring(1).toLowerCase())
	                  .append(" ");
	        }
	    }

	    return result.toString().trim();
	}

}
