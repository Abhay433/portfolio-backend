package com.portfolio.portfoliogenerator.util;

import org.springframework.stereotype.Component;

@Component
public class Capitalizer {
	
	public String capitalise(String value) {
		
		String[] words = value.split("\\s");

        // StringBuilder to store the result
		
        StringBuilder result = new StringBuilder();
        

        // iterate through each word
        
        for (String word : words) {
        	
            // capitalize the first letter, append the rest of the word, and add a space
        	
            result.append(Character.toTitleCase(word.charAt(0)))
                  .append(word.substring(1))
                  .append(" ");
            
        }

        // convert StringBuilder to String and trim leading/trailing spaces
        
        return result.toString().trim();
	}

}
