package net.engineeringdigest.journalapp.schedular;

import net.engineeringdigest.journalapp.Entity.JournalEntry;
import net.engineeringdigest.journalapp.Entity.User;
import net.engineeringdigest.journalapp.JournalMongodbRepo.UserRepositoryImpl;
import net.engineeringdigest.journalapp.Service.EmailService;
import net.engineeringdigest.journalapp.enums.Sentiment;
import net.engineeringdigest.journalapp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UserShcheduler {
    @Autowired
    private UserRepositoryImpl userRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;
    //@Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSAMail(){
        List<User> users = userRepository.getUsersForSA();
        for(User user : users){
            List<JournalEntry> journalentries = user.getJournalentries();
            List<Sentiment> sentiments = journalentries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7)))
                    .map(JournalEntry::getSentiment).toList();
            Map<Sentiment,Integer> sentimentCounts=new HashMap<>();
            for(Sentiment sentiment:sentiments){
                if(sentiment!=null){
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
                }

            }
            Optional<Map.Entry<Sentiment, Integer>> entry = sentimentCounts.entrySet().stream()
                    .min((a, b) -> b.getValue() - a.getValue());

            if(entry.isPresent()){
                SentimentData data=SentimentData.builder().email(user.getEmail()).sentiment(entry.get().getKey().toString()).build();
                try{
                    kafkaTemplate.send("weekly-sentiment-data", user.getEmail(), data);
                }catch(Exception e){
                    emailService.sendSentimentEmail(data.getEmail(),"sentiment for last 7 days",data.getSentiment());
                }

            }
        }


    }


}
