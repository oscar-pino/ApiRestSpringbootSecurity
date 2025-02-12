package cl.oscar_pino.apiSecurity.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.oscar_pino.apiSecurity.entities.BookEntity;

@Repository
public interface IBookRepository extends CrudRepository<BookEntity, Long> {

	Optional<BookEntity> findByIsbm(@Param(value = "isbm") String isbm);
	
	Optional<BookEntity> findByTitle(@Param(value = "title") String title);	
	
	@Query(value="SELECT b FROM BookEntity b WHERE b.editorial.name = :name", nativeQuery=false)
	Optional<BookEntity> findByEditorialName(@Param(value = "name") String name);	
	
	@Query("SELECT b FROM BookEntity b WHERE b.category.name = :name")
	List<BookEntity> findByCategoryName(@Param(value = "name") String name); 
	
	@Query("SELECT b FROM BookEntity b WHERE b.author.firstName = :firstName AND b.author.lastName = :lastName")
	Optional<BookEntity> findByAuthorFirstNameAndLastName(@Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName);
	
	List<BookEntity> findByQuantity(int quantity);
	
	@Query(value = "SELECT SUM(b.quantity) FROM books b", nativeQuery = true)
	int getTotalNumberOfBooks();
}
