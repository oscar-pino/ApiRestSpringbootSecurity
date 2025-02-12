package cl.oscar_pino.apiSecurity.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.oscar_pino.apiSecurity.entities.ReturnEntity;

@Repository
public interface IReturnRepository extends CrudRepository<ReturnEntity, Long> {

	@Query("SELECT r FROM ReturnEntity r WHERE r.returnDate = :date ORDER BY r.id")
	List<ReturnEntity> findAllByReturnDate(@Param("date") String date);
}
