package com.example.mongodbrelationship;

import com.example.mongodbrelationship.documents.Address;
import com.example.mongodbrelationship.documents.Hobby;
import com.example.mongodbrelationship.documents.Person;
import com.example.mongodbrelationship.repositories.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = PersonRepository.class)
public class MongodbrelationshipApplication implements CommandLineRunner {

        private PersonRepository personRepository;

    public MongodbrelationshipApplication(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public static void main(String[] args) {
        SpringApplication.run(MongodbrelationshipApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        personRepository.deleteAll();

        final Address address = new Address("19 Imaginary Road", "Imaginary Place123", "Imaginary City123", "UK");

        final Hobby badminton = new Hobby("Badminton");
        final Hobby tv = new Hobby("TV");
        final List<Hobby> hobbies = Arrays.asList(badminton, tv);

        final Person andy = new Person("Andy", "Parker", LocalDateTime.now(), address, "Winner", 100, hobbies);
        personRepository.save(andy);

        System.out.println("Find by first name");
        personRepository.findByFirstName("Andy").forEach(System.out::println);

        System.out.println("Find by country (UK)");
        personRepository.findByCountry("UK").forEach(System.out::println);

        address.setCountry("US");
        personRepository.save(andy);
        System.out.println("Find by country (US)");
        personRepository.findByCountry("US").forEach(System.out::println);
    }


}
