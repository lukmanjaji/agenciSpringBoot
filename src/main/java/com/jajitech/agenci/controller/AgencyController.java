/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.controller;

import com.google.gson.Gson;
import com.jajitech.agenci.helper.FileUploadManager;
import com.jajitech.agenci.helper.Hasher;
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
    
    @Autowired
    Hasher hasher;
    
    Gson gson = new Gson();
    String actionMessage = "";
    
    @PostMapping(path="save")
    public String save(@RequestParam("name") String name, @RequestParam("email") String email, 
            @RequestParam("address") String address, @RequestParam("phone") String phone, 
            @RequestParam("photoFile") MultipartFile photoFile,
            @RequestParam("p") String p)
    {
        boolean b = agency.existsByEmail(email);
        if(b == true)
        {
            return gson.toJson(parser.parseResponse("agency_registration", "Email already exists"));
        }
        
        actionMessage = "";
        AgencyModel am = new AgencyModel();
        String savedID = "";
        boolean success = false;
        am.setAgencyName(name);
        am.setAgencyEmail(email);
        am.setAgencyAddress(address);
        am.setAgencyPhone(phone);
        am.setIsLogoUploaded(false);
        am.setU_p("");
        try{am.setP_u(hasher.hashPassword(p));}catch(Exception er){}
        try
        {
            AgencyModel ag = agency.save(am);
            savedID = ag.getId()+"";
            success = true;
            actionMessage = "Agency registered.";
        }catch(Exception er){actionMessage = "Agency NOT registered.";}
        if(success == true && photoFile != null)
        {
            boolean uploaded = uploader.doUpload("logo", photoFile, savedID);
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
    
    @GetMapping(path="getAgencyInfo")
    public String getWorkerInfo(@RequestParam("agency_id") String agency_id)
    {
        return gson.toJson(agency.findById(Long.parseLong(agency_id)));
    }
    
    @PostMapping("updateCredentials")
    public String u_p(@RequestParam("u") String u, @RequestParam("p") String p,
    @RequestParam("op") String op,
    @RequestParam("agency_id") String agency_id)
    {
        try
        {
            String x = agency.getOp(agency_id, u);
            if( x != null)
            {
                System.out.println("this is op "+x);
                boolean m = hasher.verifyHash(op, x);
                if(m == false)
                {
                    actionMessage = "Invalid credentials";
                }
                else
                {
                    agency.u_p(u, hasher.hashPassword(p), agency_id);
                    actionMessage = "Credentials updated.";
                }
            }
            else
            {
                actionMessage = "Invalid credentials";
            }
        }
        catch(Exception er)
        {
            actionMessage = "Error updating credentials.";
            er.printStackTrace();
        }
        return gson.toJson(parser.parseResponse("updating_cred", actionMessage));
    }
     
    

    
}
