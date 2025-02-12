package cl.oscar_pino.apiSecurity.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.oscar_pino.apiSecurity.entities.RoleEntity;

@Repository
public interface IRoleRepository extends CrudRepository<RoleEntity, Long> {
	
	@Query(value = "SELECT * FROM roles WHERE role_name = :name", nativeQuery = true)	
	List<RoleEntity> findAllRoleByName(@Param("name") String name);
	
}
