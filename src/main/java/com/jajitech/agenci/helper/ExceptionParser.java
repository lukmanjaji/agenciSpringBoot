/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.helper;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
@Service
public class ExceptionParser {
    
    public String parseException(Exception er, String actionMessage)
    {
        String r = "Exception occured during "+actionMessage+"\nMessage: "+er.getMessage()+"\nCaused by: "+er.getCause();
        return "";
    }
    
}
