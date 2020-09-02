/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.controller;

import com.jajitech.agenci.helper.Emailer;
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
@RequestMapping("/agenci/worker/verify/")
public class VerifyWorkerForAgencyController {
    
    @Autowired
    VerifyWorkerForAgencyRepository verify;
    
    @Autowired
    Emailer mailer;
    
    @Autowired
    WorkerRepository worker;
    
    @PostMapping(path="verifyWorker")
    public boolean verifyWorker(@RequestParam("workerId") String workerId,
            @RequestParam("agencyId") String agencyId,
            @RequestParam("code") String code)
    {
        String verifyWorkerCode = verify.veryfiyWorkerCode(code, workerId, agencyId);
        if(verifyWorkerCode != null && verifyWorkerCode.length() > 0)
        {
            verify.verifyWorker(code, workerId, agencyId);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    @PostMapping(path="sendVerificationForWorker")
    public boolean sendVerificationForWorker(@RequestParam("email") String email,
            @RequestParam("agencyId") String agencyId)
    {
        String workerId = worker.verifyWorkerEmail(email);
        System.out.println(workerId);
        if(workerId != null && workerId.length() > 0)
        {
            mailer.sendVerificationCodeForAgencyWorker(agencyId, workerId);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    
    
}
