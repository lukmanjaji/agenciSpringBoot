/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.helper;

import com.jajitech.agenci.controller.FileReaderHelper;
import com.jajitech.agenci.model.AgencyModel;
import com.jajitech.agenci.model.VerifyWorkerForAgencyModel;
import com.jajitech.agenci.model.WorkerModel;
import com.jajitech.agenci.repository.AgencyRepository;
import com.jajitech.agenci.repository.VerifyWorkerForAgencyRepository;
import com.jajitech.agenci.repository.WorkerRepository;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lukman Jaji <lukman@lukmanjaji.com>
 */
@Service
public class Emailer {
    
    @Autowired
    JavaMailSender sender;
    
    @Autowired
    CodeMaker code;
    
    @Autowired
    WorkerRepository worker;
    
    @Autowired
    AgencyRepository agency;
    
    @Autowired
    VerifyWorkerForAgencyRepository verify;
    
    @Autowired
    FileReaderHelper reader;
    
    String path = System.getProperty("user.dir")+"/agenciFiles/mailer/";
    
    public String sendVerificationCodeForAgencyWorker(String agencyID, String workerID)
    {
        String body = reader.readFile(path + "template.jaj");
        WorkerModel w = worker.getWorkerInfo(workerID);
        AgencyModel a = agency.getAgencyInfo(agencyID);
        String v = code.randomNumeric(5);
        
        v = agencyID+" "+spaceCode(v);
        
        body = body.replace("#code#", v);
        body = body.replace("#name#", w.getWorkerName());
        String contents = "#agency# have successfully registered you as a staff. "
                + "To complete the enrollment process, use the code below "
                + "to login to the mobile app and set your password.";
        body = body.replace("#body#", contents);
        body = body.replace("#agency#", a.getAgencyName());
        body = body.replace("#address#", a.getAgencyAddress());
        EmailModel m = new EmailModel();
        m.setContents(body);
        m.setReceiverEmail(w.getWorkerEmail());
        m.setReceiverName(w.getWorkerName());
        
        m.setSubject("Verify Your Email");
        boolean r = sendEmail(m, agencyID, workerID, v);
        return "";
    }
    
    public String sendVerificationCodeForWorker(String workerID)
    {
        String body = reader.readFile(path + "template.jaj");
        WorkerModel w = worker.getWorkerInfo(workerID);
        String v = code.randomNumeric(5);
        v = spaceCode(v);
        body = body.replace("#code#", v);
        body = body.replace("#name#", w.getWorkerName());
        String contents = "Thank you for registering on the Agenzi platform."
                + "To complete the registration process, use the code below "
                + "to login to the mobile app and set your password.";
        body = body.replace("#body#", contents);
        body = body.replace("#agency#", "Powered By AgenZi");
        body = body.replace("#address#", "");
        EmailModel m = new EmailModel();
        m.setContents(body);
        m.setReceiverEmail(w.getWorkerEmail());
        m.setReceiverName(w.getWorkerName());
        
        m.setSubject("Verify Your Email");
        boolean r = sendEmail(m, "", workerID, v);
        return "";
    }
    
    public boolean sendEmail(EmailModel model, String agency, String worker, String code)
    {
        MimeMessage m = sender.createMimeMessage();
        try
        {
            MimeMessageHelper mh = new MimeMessageHelper(m, true);
            mh.setFrom("smilewithjaji@gmail.com", "Peaceful Homes Agency");
            mh.setTo(model.getReceiverEmail());
            mh.setSubject(model.getSubject());
            mh.setText(model.getContents(), true);
            ClassPathResource r = new ClassPathResource("logo.png");
            mh.addInline("logo", r);
            sender.send(m);
            VerifyWorkerForAgencyModel vs = new VerifyWorkerForAgencyModel();
            vs.setIsVerified(false);
            vs.setVerificationCode(code);
            vs.setWorkerID(worker);
            vs.setAgencyID(agency);
            verify.save(vs);
            return true;
        }
        catch(Exception er)
        {
            er.printStackTrace();
            return false;
        }
    }
    
    public String spaceCode(String code)
    {
        String spacedCode = "";
        for (int x=0; x<code.length();x++)
        {
            spacedCode = spacedCode + code.charAt(x) + " ";
        }
        return spacedCode;
    }
    
}
