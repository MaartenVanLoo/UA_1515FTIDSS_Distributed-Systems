package BankREST;

import org.slf4j.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;

@Configuration
public class LoadDB {
    private static final Logger log = LoggerFactory.getLogger(LoadDB.class);

    @Bean   // loads the database with 2 accounts and prints this to the console (log)
    CommandLineRunner initDB(AccountRepo repo) {
        return args -> {
            log.info("Preloading " + repo.save(new BankAccount("Maarten", "123456")));
            log.info("Preloading " + repo.save(new BankAccount("Jens", "lol69")));
        };
    }

}