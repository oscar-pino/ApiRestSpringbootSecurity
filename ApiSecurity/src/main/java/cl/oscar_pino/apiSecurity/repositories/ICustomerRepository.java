package cl.oscar_pino.apiSecurity.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.oscar_pino.apiSecurity.entities.CustomerEntity;

@Repository
public interface ICustomerRepository extends CrudRepository<CustomerEntity, Long> {

	Optional<CustomerEntity> findByEmail(@Param(value = "email") String email);
	
	Optional<CustomerEntity> findByPhone(@Param(value = "phone") String phone);
	
	@Query("SELECT c FROM CustomerEntity c WHERE c.nationality.name = :name ORDER BY c.id")
	List<CustomerEntity> findAllByNationalityName(@Param("name") String name);

}
