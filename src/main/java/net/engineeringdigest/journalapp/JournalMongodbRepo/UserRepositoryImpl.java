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
//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(null).ne(""));
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        Criteria criteria=new  Criteria();
//        query.addCriteria(criteria.orOperator(Criteria.where("roles").in("USER","ADMIN"),
//                Criteria.where("sentimentAnalysis").is(true)));
        List<User> users = template.find(query, User.class);
        return users;
    }
}
