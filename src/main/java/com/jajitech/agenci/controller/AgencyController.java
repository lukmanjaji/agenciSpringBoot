/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.controller;

import com.google.gson.Gson;
import com.jajitech.agenci.helper.FileUploadManager;
import com.jajitech.agenci.helper.ResponseParser;
import com.jajitech.agenci.model.AgencyModel;
import com.jajitech.agenci.repository.AgencyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
@RestController
@RequestMapping("/agenci/")

public class AgencyController {
    
    @Autowired
    AgencyRepository agency;
    
    @Autowired
    FileUploadManager uploader;
    
    @Autowired
    ResponseParser parser;
    
    Gson gson = new Gson();
    String actionMessage = "";
    
    @PostMapping(path="save")
    public String save(@RequestParam("name") String name, @RequestParam("email") String email, 
            @RequestParam("address") String address, @RequestParam("phone") String phone, @RequestParam("photoFile") MultipartFile photoFile)
    {
        AgencyModel am = new AgencyModel();
        String savedID = "";
        boolean success = false;
        am.setAgencyName(name);
        am.setAgencyEmail(email);
        am.setAgencyAddress(address);
        am.setAgencyPhone(phone);
        am.setIsLogoUploaded(false);
        try
        {
            AgencyModel ag = agency.save(am);
            savedID = ag.getId()+"";
            success = true;
            actionMessage = "Agency registered.";
        }catch(Exception er){actionMessage = "Agency NOT registered.";}
        if(success == true && photoFile != null)
        {
            boolean uploaded = uploader.doUpload("logo", photoFile);
            if(uploaded == true)
            {
                agency.updateLogo(savedID);
                actionMessage += "Logo uploaded";
            }
            else
            {
                actionMessage += "Logo NOT uploaded";
            }
        }
        return gson.toJson(parser.parseResponse("agency_registration", actionMessage));
    }
    
    @GetMapping(path = "searchAgencies")
    public List<AgencyModel> search(String keyword)
    {
        List<AgencyModel> s = agency.search(keyword);
        return s;
    }
    
    @GetMapping(path = "listAllAgencies")
    public List<AgencyModel> listAgencies()
    {
        List<AgencyModel> s = agency.listAllAgencies();
        return s;
    }
    
    

    
}
