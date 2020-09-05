/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.controller;

import com.jajitech.agenci.model.WorkerInAgencyModel;
import com.jajitech.agenci.repository.WorkerInAgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
@RestController
@RequestMapping("/agenci/staff/")
public class WorkerInAgencyController {
    
    @Autowired
    WorkerInAgencyRepository model;
    
    @PostMapping(path = "save")
    public boolean save(@RequestParam("agencyID") String agencyID, @RequestParam("workerID") String workerID)
    {
        WorkerInAgencyModel a = new WorkerInAgencyModel();
        a.setAgencyID(agencyID);
        a.setWorkerID(workerID);
        WorkerInAgencyModel s = model.save(a);
        if(s.getId() != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
}
