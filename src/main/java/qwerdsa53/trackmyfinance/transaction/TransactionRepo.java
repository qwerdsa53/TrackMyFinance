package qwerdsa53.trackmyfinance.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import qwerdsa53.trackmyfinance.user.User;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "transactions", path = "transactions")
public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
