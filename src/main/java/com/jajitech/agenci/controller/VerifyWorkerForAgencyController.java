/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.controller;

import com.jajitech.agenci.helper.Emailer;
import com.jajitech.agenci.model.WorkerModel;
import com.jajitech.agenci.repository.VerifyWorkerForAgencyRepository;
import com.jajitech.agenci.repository.WorkerRepository;
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
@RequestMapping("/agenci/worker/")
public class VerifyWorkerForAgencyController {
    
    @Autowired
    VerifyWorkerForAgencyRepository verify;
    
    @Autowired
    Emailer mailer;
    
    @Autowired
    WorkerRepository worker;
    
    @Autowired
    WorkerInAgencyController wagency;
    
    @PostMapping(path="verifyWorkerForAgency")
    public boolean verifyWorkerForAgency(@RequestParam("workerId") String workerId,
            @RequestParam("agencyId") String agencyId,
            @RequestParam("code") String code)
    {
        boolean verifyWorkerCode = verify.veryfiyWorkerCode(code, workerId, agencyId);
        if(verifyWorkerCode == true)
        {
            int x = verify.verifyWorker(code, workerId, agencyId);
            if(x > 0)
            {
                boolean y = wagency.save(agencyId, workerId);
                if(y == true)
                {
                    mailer.sendInfoEmail("post_agency_verify", workerId, agencyId);
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
        return false;
    }
    
    @PostMapping(path="sendAgencyVerification")
    public boolean sendVerificationForWorker(@RequestParam("email") String email,
            @RequestParam("agencyId") String agencyId)
    {
        try
        {
        boolean workerId = worker.existsByEmail(email);
        System.out.println(workerId);
        if(workerId == true)
        {
            WorkerModel id = worker.findByEmail(email);
            boolean x = verify.workerExistsForAgency(""+id.getId(), agencyId);
            if(x == true)
            {
                System.out.println("User already exists");
                return false;
            }
            mailer.sendVerificationCodeForAgencyWorker(agencyId, ""+id.getId());
            return true;
        }
        else
        {
            return false;
        }
        }
        catch(Exception er)
        {
            er.printStackTrace();
            return false;
        }
    }
    
    
    
}
