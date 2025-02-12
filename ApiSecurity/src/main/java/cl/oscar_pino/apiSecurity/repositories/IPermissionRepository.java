package cl.oscar_pino.apiSecurity.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.oscar_pino.apiSecurity.entities.PermissionEntity;

@Repository
public interface IPermissionRepository extends CrudRepository<PermissionEntity, Long> {
	
	@Query(value = "SELECT * FROM permissions WHERE permission_name = :name", nativeQuery = true)	
	List<PermissionEntity> findAllPermissionByName(@Param("name") String name);	

}


