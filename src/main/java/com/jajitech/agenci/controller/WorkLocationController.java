/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.controller;

import com.google.gson.Gson;
import com.jajitech.agenci.helper.ResponseParser;
import com.jajitech.agenci.model.WorkLocationModel;
import com.jajitech.agenci.repository.WorkLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/agenci/locations/")
public class WorkLocationController {
    
    @Autowired
    WorkLocationRepository location;
    
    @Autowired
    ResponseParser parser;
    
    String actionMessage="";
    
    Gson gson = new Gson();
    
    @PostMapping(path="addLocation")
    public String save(@RequestParam("title") String title, 
            @RequestParam("email") String email, 
            @RequestParam("address") String address, 
            @RequestParam("coordinates") String coordinates,
            @RequestParam("person") String person,
            @RequestParam("agency_id") String agency_id,
            @RequestParam("phone") String phone)
    {
        actionMessage = "";
        WorkLocationModel wm = new WorkLocationModel();
        wm.setLocationAddress(address);
        wm.setLocationCoordinates(coordinates);
        wm.setLocationEmail(email);
        wm.setLocationFocalPerson(person);
        wm.setLocationPhone(phone);
        wm.setLocationTitle(title);
        wm.setAgencyID(agency_id);
        try{location.save(wm);actionMessage = "Location added.";}catch(Exception er){actionMessage = "Error adding location.";}
        return gson.toJson(parser.parseResponse("new_location_add", actionMessage));
    }
    
    @PostMapping(path="updateLocation")
    public String update(@RequestParam("title") String title, 
            @RequestParam("email") String email, 
            @RequestParam("address") String address, 
            @RequestParam("coordinates") String coordinates,
            @RequestParam("person") String person,
            @RequestParam("agency_id") String agency_id,
            @RequestParam("phone") String phone,
            @RequestParam("location_id") String location_id)
    {
        actionMessage = "";
        WorkLocationModel wm = new WorkLocationModel();
        wm.setLocationAddress(address);
        wm.setLocationCoordinates(coordinates);
        wm.setLocationEmail(email);
        wm.setLocationFocalPerson(person);
        wm.setLocationPhone(phone);
        wm.setLocationTitle(title);
        wm.setAgencyID(agency_id);
        wm.setId(Long.parseLong(location_id));
        try{location.save(wm);actionMessage = "Location updated.";}catch(Exception er){actionMessage = "Error updating location.";}
        return gson.toJson(parser.parseResponse("new_location_update", actionMessage));
    }
    
    @PostMapping(path="deleteLocation")
    public String deleteLocation(@RequestParam("id") String id)
    {
        WorkLocationModel wm = new WorkLocationModel();
        wm.setId(Long.parseLong(id));
        location.delete(wm);
        return gson.toJson(parser.parseResponse("location_delete", "Work location deleted"));
    }
    
    @GetMapping(path="getWorkLocationInfo")
    public String getWorkerInfo(@RequestParam("location_id") String location_id)
    {
        return gson.toJson(location.findById(Long.parseLong(location_id)));
    }
    
}
