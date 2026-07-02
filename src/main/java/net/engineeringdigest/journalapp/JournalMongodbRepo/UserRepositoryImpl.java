package net.engineeringdigest.journalapp.JournalMongodbRepo;

import net.engineeringdigest.journalapp.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public class UserRepositoryImpl {


    @Autowired
    private MongoTemplate template;

    public List<User> getUsers() {
        Query query=new  Query();
        query.addCriteria(Criteria.where("userName").is("akash"));
        return template.find(query, User.class);
    }
}
