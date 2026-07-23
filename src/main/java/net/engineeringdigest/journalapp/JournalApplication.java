package net.engineeringdigest.journalapp;


import com.mongodb.client.MongoDatabase;
import io.jsonwebtoken.io.Decoders;
import jakarta.websocket.Decoder;
import org.eclipse.angus.mail.util.BASE64DecoderStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {
	public static void main(String[] args) {

		SpringApplication.run(JournalApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
		return new MongoTransactionManager(dbFactory);
	}
	@Bean
	public WebClient.Builder webClientBuilder(){
		return WebClient.builder();
	}

}
