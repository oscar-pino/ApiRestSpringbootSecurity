package cl.oscar_pino.apiSecurity.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.oscar_pino.apiSecurity.entities.AuthorEntity;


@Repository
public interface IAuthorRepository extends CrudRepository<AuthorEntity, Long> {

	Optional<AuthorEntity> findByEmail(@Param(value = "email") String email);
	
	Optional<AuthorEntity> findByWebSite(@Param(value = "web") String web);
	
	@Query(value = "select a from AuthorEntity a where a.firstName = :firstName")
	List<AuthorEntity> findByFirstName(@Param(value = "firstName") String firstName);
	
	@Query(value = "select a from AuthorEntity a where a.lastName = :lastName")
	List<AuthorEntity> findByLastName(@Param(value = "lastName") String lastName);
	
	@Query(value = "select a from AuthorEntity a where a.firstName = :firstName and a.lastName = :lastName")
	Optional<AuthorEntity> findByFirstNameAndLastName(@Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName);
}
