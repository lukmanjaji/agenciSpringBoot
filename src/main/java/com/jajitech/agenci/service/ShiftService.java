/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.service;

import com.google.gson.Gson;
import com.jajitech.agenci.helper.ExceptionParser;
import com.jajitech.agenci.helper.ResponseParser;
import com.jajitech.agenci.model.ShiftModel;
import com.jajitech.agenci.repository.ShiftRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
@Service
public class ShiftService {
    
    @Autowired
    ShiftRepository shift;
    
    @Autowired
    ResponseParser parser;
    
    String actionMessage = "";
    
    @Autowired
    Gson gson;
    
    @Autowired
    ExceptionParser ex;
    
    public String save(ShiftModel model)
    {
        try
        {
            ShiftModel m = shift.save(model);
            if(m.getId() != null && m.getId().toString().length() > 0)
            {
                return gson.toJson(parser.parseResponse("new_shift_add", "Shift added successfully."));
            }
            else
            {
                return gson.toJson(parser.parseResponse("new_shift_add", "Error adding shift."));
            }
        }
        catch(Exception er)
        {
            System.out.println("i got to exception");
            return gson.toJson(parser.parseResponse("new_shift_add", ex.parseException(er, "new_shift_add")));
        }
    }
    
    public String getAllShifts()
    {
        return gson.toJson(shift.listAllShifts());
    }
    
    public String getShiftInfo(String id)
    {
        return gson.toJson(shift.getShiftInfo(id));
    }
    
    public String getAllAgencyShifts(String agencyId)
    {
        return gson.toJson(shift.getAgencyShifts(agencyId));
    }
    
}
