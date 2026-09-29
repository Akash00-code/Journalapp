package net.engineeringdigest.journalapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalapp.Service.EmailService;
import net.engineeringdigest.journalapp.schedular.UserShcheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@Tag(name="Email APIs",description = "Email controller")
public class EmailController {

    @Autowired
    EmailService emailService;
    @Autowired
    UserShcheduler  userShcheduler;
    @Operation(summary = "send text mail")
    @GetMapping("/send-text")
    public String sendText(){
        return emailService.sendTextEmail();
    }
    @Operation(summary = "send attachment mail")
    @GetMapping("/send-attachment")
    public String sendAttachment(){
        return emailService.sendAttachmentEmail();
    }
    @Operation(summary = "send sentiment email")
    @GetMapping("/send-sentiment-email")
    public String sendSentimentEmail(){
        userShcheduler.fetchUsersAndSendSAMail();
        return " ";
    }

}
