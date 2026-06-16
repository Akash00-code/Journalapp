package net.engineeringdigest.journalapp.cache;

import jakarta.annotation.PostConstruct;
import net.engineeringdigest.journalapp.Entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalapp.JournalMongodbRepo.ConfigJournalApp;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public final ConfigJournalApp configJournalApp;

    public AppCache(ConfigJournalApp configJournalApp) {
        this.configJournalApp = configJournalApp;
    }

    public Map<String, String> appCache;

    public enum apis{
        WEATHER_API,QUOTES_API
    }


    @PostConstruct
    public void init(){
        appCache=new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalApp.findAll();
        all.forEach(entity -> {appCache.put(entity.getKey(),entity.getValue());});
    }

}
