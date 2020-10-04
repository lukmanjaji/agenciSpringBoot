/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.agenci.helper;

import com.google.gson.Gson;
import com.jajitech.agenci.model.AgencyModel;
import com.jajitech.agenci.model.VerifyWorkerForAgencyModel;
import com.jajitech.agenci.model.WorkerModel;
import com.jajitech.agenci.repository.AgencyRepository;
import com.jajitech.agenci.repository.VerifyWorkerForAgencyRepository;
import com.jajitech.agenci.repository.WorkerRepository;
import java.net.URLDecoder;
import java.util.Optional;
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
public class Emailer implements Constants{
    
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
    
    @Autowired
    Gson gson;
    
    @Autowired
    ResponseParser parser;
    
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
        m.setSubject("Verify Your Email For "+a.getAgencyName());
        boolean r = sendEmail(m, agencyID, workerID, v, true, "verify");
        if(r == true)
        {
            return gson.toJson(parser.parseResponse("verif_for_agency_worker", "Email sent"));
        }
        else
        {
            gson.toJson(parser.parseResponse("verif_for_agency_worker", "Error sending verification email"));
        }
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
        String contents = "Thank you for registering on the "+APP_NAME+" platform."
                + "To complete the registration process, use the code below "
                + "to login to the mobile app and set your password.";
        body = body.replace("#body#", contents);
        body = body.replace("#agency#", "Powered By "+APP_NAME);
        body = body.replace("#address#", "");
        String ml="";
        try{ml = URLDecoder.decode(w.getWorkerEmail(), "utf-8");}catch(Exception er){}
        EmailModel m = buildEmailModel("",body,ml,w.getWorkerName(),"Verify Your Email");
        try
        {
        boolean r = sendEmail(m, "", workerID, v, false, "verify");
        if(r == true)
        {
            return gson.toJson(parser.parseResponse("verify_worker", "Email sent"));
        }
        else
        {
            gson.toJson(parser.parseResponse("verif_for_agency_worker", "Error sending verification email"));
        }
        }catch(Exception er){er.printStackTrace();}
        return "";
    }
    
    public String sendPassCodeForWorker(String workerID)
    {
        String body = reader.readFile(path + "template.jaj");
        WorkerModel w = worker.getWorkerInfo(workerID);
        String v = code.randomNumeric(5);
        String rr = v;
        v = spaceCode(v);
        body = body.replace("#code#", v);
        body = body.replace("#name#", w.getWorkerName());
        String contents = "You have initiated a password reset process from our app. Please use the code below to continue.";
        body = body.replace("#body#", contents);
        body = body.replace("#agency#", "Powered By "+APP_NAME);
        body = body.replace("#address#", "");
        EmailModel m = buildEmailModel("",body,w.getWorkerEmail(),w.getWorkerName(),"Verify Your Email");
        boolean r = sendEmail(m, "", workerID, v, false, "pass_code");
         if(r == true)
        {
            return rr;
        }
        else
        {
            return "Error sending verification email";
        }
    }
    
    public boolean sendInfoEmail(String type, String workerID, String agencyID)
    {
        String body = reader.readFile(path + "template.jaj");
        body = body.replace("#code#", "");
        EmailModel model = null;
        String tt = "";
        boolean val = false;
        WorkerModel w = worker.getWorkerInfo(workerID);
        if(type.equals("post_agency_verify"))
        {
            AgencyModel a = agency.getAgencyInfo(agencyID);
            body = body.replace("#name#", w.getWorkerName());
            body = body.replace("#agency#", "");
            body = body.replace("#address#", "");
            String contents = "You have successfully been added to "+a.getAgencyName()+"."
                    + " Now you can request and be assigned shifts.<p>Thank you.";
            String subject = "You are verified for "+a.getAgencyName();
            body = body.replace("#body#", contents);
            model = buildEmailModel(a.getAgencyName(), body,w.getWorkerEmail(),w.getWorkerName(), subject);
            tt = "info";
            val = true;
        }
        
        
        if(type.equals("post_reg"))
        {
            body = body.replace("#name#", w.getWorkerName());
            String contents = "You have been verified on "+APP_NAME+".<p>Thank you.";
            body = body.replace("#body#", contents);
            String subject = "You are verified";
            model = buildEmailModel("", body,w.getWorkerEmail(),w.getWorkerName(), subject);
            tt = "info";
            val = false;
        }
        
        if(type.equals("pass_code"))
        {
            body = body.replace("#name#", w.getWorkerName());
            String contents = "You have been verified on "+APP_NAME+".<p>Thank you.";
            body = body.replace("#body#", contents);
            String subject = "Password Reset";
            model = buildEmailModel("", body,w.getWorkerEmail(),w.getWorkerName(), subject);
            val = false;
            tt = "pass_code";
        }
        boolean r = sendEmail(model, agencyID, workerID, "", val, tt);
        return r;
    }
    
    private boolean sendEmail(EmailModel model, String agency, String worker, 
            String code, boolean isAgency, String type)
    {
        boolean success = false;
        MimeMessage m = sender.createMimeMessage();
        try
        {
            MimeMessageHelper mh = new MimeMessageHelper(m, true);
            if(isAgency == true)
            {
                Optional<AgencyModel> aa = this.agency.findById(Long.parseLong(agency));
                AgencyModel a = this.agency.getAgencyInfo(agency);
                mh.setFrom(FROM_EMAIL, a.getAgencyName());
                mh.setReplyTo(a.getAgencyEmail(), a.getAgencyName());
            }
            else
            {
                mh.setFrom(FROM_EMAIL, APP_NAME);
            }
            String ml = URLDecoder.decode(model.getReceiverEmail(), "utf-8");
            mh.setTo(ml);
            mh.setSubject(model.getSubject());
            mh.setText(model.getContents(), true);
            ClassPathResource r = new ClassPathResource("logo.png");
            mh.addInline("logo", r);
            try
            {
                sender.send(m);
                success = true;
            }
            catch(Exception er)
            {
                er.printStackTrace();
                success = false;
            }
            
            if(type.equals("verify") || type.equals("pass_code") && success == true)
            {
                verify.delExisting(worker, agency);
                VerifyWorkerForAgencyModel vs = new VerifyWorkerForAgencyModel();
                vs.setIsVerified(false);
                vs.setVerificationCode(code.replace(" ",""));
                vs.setWorkerID(worker);
                vs.setAgencyID(agency);
                verify.save(vs);
            }
        }
        catch(Exception er)
        {
            er.printStackTrace();
            return false;
        }
        return success;
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
    
    public EmailModel buildEmailModel(String agencyName, String contents, String receiverEmail, String receiverName, String subject)
    {
        EmailModel model = new EmailModel();
        model.setContents(contents);
        model.setReceiverEmail(receiverEmail);
        model.setReceiverName(receiverName);
        model.setSubject(subject);
        model.setAgencyName(agencyName);
        return model;
    }
    
}
