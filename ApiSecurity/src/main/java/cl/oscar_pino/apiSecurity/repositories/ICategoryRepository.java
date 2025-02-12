package cl.oscar_pino.apiSecurity.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.oscar_pino.apiSecurity.entities.CategoryEntity;

@Repository
public interface ICategoryRepository extends CrudRepository<CategoryEntity, Long> {
	
	Optional<CategoryEntity> findByName(@Param(value = "name") String name);

}
