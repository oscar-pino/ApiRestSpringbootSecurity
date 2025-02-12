package cl.oscar_pino.apiSecurity.services;

import java.util.List;
import java.util.Optional;

public interface IDAO<T> {
	
	void create(T t);
	
	List<T> readAll();
	
	Optional<T> readById(Long id);	
	
	void deleteById(Long id);
	
	void update(T t);
}
