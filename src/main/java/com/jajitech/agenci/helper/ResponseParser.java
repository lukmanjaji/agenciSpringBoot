/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.helper;

import com.jajitech.agenci.model.ResponseModel;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
@Component
@Service
public class ResponseParser {
    
    public ResponseModel parseResponse(String event, String actionMessage)
    {
        ResponseModel rm = new ResponseModel();
        rm.setAction(event);
        rm.setMessage(actionMessage);
        rm.setDate(new java.util.Date());
        return rm;
    }
    
}
