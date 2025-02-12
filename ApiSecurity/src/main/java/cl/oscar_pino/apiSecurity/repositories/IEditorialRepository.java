package cl.oscar_pino.apiSecurity.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.oscar_pino.apiSecurity.entities.EditorialEntity;

@Repository
public interface IEditorialRepository extends CrudRepository<EditorialEntity, Long> {

	Optional<EditorialEntity> findByName(@Param(value = "name") String name);
	
	Optional<EditorialEntity> findByWebSite(@Param(value = "web") String web);
	
	Optional<EditorialEntity> findByEmail(@Param(value = "email") String email);
	
	
}
