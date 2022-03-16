package BankREST;

import org.springframework.data.jpa.repository.JpaRepository;

// https://spring.io/blog/2011/02/10/getting-started-with-spring-data-jpa/

public interface AccountRepo extends JpaRepository<BankAccount,String> { }
