package net.engineeringdigest.journalapp.controller;

import net.engineeringdigest.journalapp.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService emailService;
    @GetMapping("/send-text")
    public String sendText(){
        return emailService.sendTextEmail();
    }
    @GetMapping("/send-attachment")
    public String sendAttachment(){
        return emailService.sendAttachmentEmail();
    }
}
