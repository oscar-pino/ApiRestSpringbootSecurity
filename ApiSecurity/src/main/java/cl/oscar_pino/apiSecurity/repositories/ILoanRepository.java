package cl.oscar_pino.apiSecurity.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.oscar_pino.apiSecurity.entities.LoanEntity;

@Repository
public interface ILoanRepository extends CrudRepository<LoanEntity, Long> {

	@Query("SELECT l FROM LoanEntity l WHERE l.customer.firstName = :firstName AND l.customer.lastName = :lastName ORDER BY l.id")
	List<LoanEntity> findAllByFirstAndLastNameCustomer(@Param("firstName") String firstName, @Param("lastName") String lastName);
	
	List<LoanEntity> findByLoanDate(LocalDate loanDate);
}
