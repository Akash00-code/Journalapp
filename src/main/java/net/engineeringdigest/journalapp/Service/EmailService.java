package net.engineeringdigest.journalapp.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.nio.file.Path;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendTextEmail() {
        try{
            SimpleMailMessage msg=new  SimpleMailMessage();
            msg.setFrom("Akash");
            msg.setTo("iamakashnaik143@gmail.com");
            msg.setSubject("Checking the JavaMailSender| SpringBoot");
            msg.setText("its working keep going");
            mailSender.send(msg);
            return "success";
        }catch(Exception e){
            log.error("Error in sending email",e);
            return "error";
        }

    }
    public String sendAttachmentEmail() {
        try{
            MimeMessage msg=mailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(msg,true);
            helper.setFrom("Akash");
            helper.setTo("iamakashnaik143@gmail.com");
            helper.setSubject("Checking the JavaMailSender attachment| SpringBoot");
            helper.setText("please find your attachments here.");
            File passphoto=new File("D:\\spring\\passphoto.jpg");
            File memo=new File("C:\\Users\\Lenovo\\Downloads\\10th memo.pdf");
            helper.addAttachment("passphoto.jpg",passphoto);
            helper.addAttachment("10th memo.pdf",memo);
            mailSender.send(msg);
            return "success";
        }catch(Exception e){
            log.error("Error in sending email",e);
            return "error";
        }

    }


}
