package net.engineeringdigest.journalapp.JournalMongodbRepo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.engineeringdigest.journalapp.Entity.User;

public interface UserRepo extends MongoRepository<User, ObjectId> {

   User findByUserName(String user);
   void deleteByUserName(String username);
}
