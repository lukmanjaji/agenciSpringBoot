/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.controller;

import com.jajitech.agenci.model.ShiftModel;
import com.jajitech.agenci.service.ShiftService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
@RestController
@RequestMapping("/agenci/shifts/")
public class ShiftController {
    
    @Autowired
    ShiftService shift;
    
    @PostMapping(path = "add")
    public String add(@RequestParam("agencyId") String agencyId,
            @RequestParam("shiftDateTime") @DateTimeFormat(pattern="dd-MM-yyyy kk:mm") Date shiftDateTime,
            @RequestParam("shift_address") String address,
            @RequestParam("shift_contact") String shift_contact,
            @RequestParam("shift_coord") String shift_coord,
            @RequestParam("shift_remark") String shift_remark)
    {
        ShiftModel model = new ShiftModel(); 
        model.setAddress(address);
        model.setAgency_id(agencyId);
        model.setContactPerson(shift_contact);
        model.setRemark(shift_remark);
        model.setShiftCoordinates(shift_coord);
        model.setShiftDateTime(shiftDateTime);
        model.setStatus("Open");
        return  shift.save(model);
    }
    
    @GetMapping(path = "getAllShifts")
    public String getAllShifts()
    {
        return shift.getAllShifts();
    }
    
    @GetMapping(path = "getShiftInfo")
    public String getShiftInfo(String id)
    {
        return shift.getShiftInfo(id);
    }
    
    @GetMapping(path = "getAgencyShifts")
    public String getAgencyShifts(String id)
    {
        return shift.getAllAgencyShifts(id);
    }
    
}
