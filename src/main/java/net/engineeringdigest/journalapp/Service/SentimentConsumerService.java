package net.engineeringdigest.journalapp.Service;

import net.engineeringdigest.journalapp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {

    @Autowired
    EmailService emailService;

    @KafkaListener(topics="weekly-sentiment-data",groupId = "weekly-sentiment-grp")
    public void consumeEvent(SentimentData sentimentData){
        sendmail(sentimentData);
    }
    public void sendmail(SentimentData sentimentData){
        emailService.sendSentimentEmail(sentimentData.getEmail(),"Sentiments for last 7 days",sentimentData.getSentiment());
    }
}
