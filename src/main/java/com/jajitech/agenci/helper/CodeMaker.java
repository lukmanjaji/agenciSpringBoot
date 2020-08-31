/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.helper;

import org.springframework.stereotype.Service;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
@Service
public class CodeMaker {
    
    public static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
    public static final String NUMERIC_STRING = "0123456789";
    
    public String randomAlphaNumeric(int count)
    {
	StringBuilder builder = new StringBuilder();
	while (count-- != 0)
        {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
	}
	return builder.toString();
    }
    
    public String randomNumeric(int count)
    {
	StringBuilder builder = new StringBuilder();
	while (count-- != 0)
        {
            int character = (int)(Math.random()*NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
	}
	return builder.toString();
    }
    
}
