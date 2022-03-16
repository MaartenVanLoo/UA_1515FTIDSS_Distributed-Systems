package BankREST;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<BankAccount,String> {


}
