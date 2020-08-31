/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.helper;

import com.jajitech.agenci.controller.FileReaderHelper;
import com.jajitech.agenci.model.AgencyModel;
import com.jajitech.agenci.model.WorkerModel;
import com.jajitech.agenci.repository.AgencyRepository;
import com.jajitech.agenci.repository.WorkerRepository;
import java.io.FileReader;
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
    FileReaderHelper reader;
    
    String path = System.getProperty("user.dir")+"/agenciFiles/mailer/";
    
    
    
    
    
    public String sendVerificationCode(String agencyID, String workerID)
    {
        WorkerModel w = worker.getWorkerInfo(workerID);
        AgencyModel a = agency.getAgencyInfo(agencyID);
        String v = code.randomNumeric(5);
        v = agencyID+v;
        String contents = reader.readFile(path + "verify.txt");
        contents = contents.replace("#code#", v);
        contents = contents.replace("#name#", w.getWorkerName());
        contents = contents.replace("#agency#", a.getAgencyName());
        System.out.println(contents);
        EmailModel m = new EmailModel();
        m.setContents(contents);
        m.setReceiverEmail(w.getWorkerEmail());
        m.setReceiverName(w.getWorkerName());
        m.setSubject("Verify Your Email");
        boolean r = sendEmail(m);
        return "";
    }
    
    public boolean sendEmail(EmailModel model)
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
            System.out.println("I just sent an email");
            return true;
        }
        catch(Exception er)
        {
            er.printStackTrace();
            return false;
        }
    }
    
}
