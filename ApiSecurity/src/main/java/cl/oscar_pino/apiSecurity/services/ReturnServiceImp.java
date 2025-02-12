package cl.oscar_pino.apiSecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.oscar_pino.apiSecurity.entities.ReturnEntity;
import cl.oscar_pino.apiSecurity.repositories.IReturnRepository;

@Service
public class ReturnServiceImp implements IDAO<ReturnEntity> {
	
	@Autowired
	private IReturnRepository returnRepository;

	@Override
	public void create(ReturnEntity returnEntity) {
		
		returnRepository.save(returnEntity);		
	}
	
	@Override
	public List<ReturnEntity> readAll() {
		
		return (List<ReturnEntity>)returnRepository.findAll();
	}
	
	public List<ReturnEntity> readAllByReturnDate(String returnDate){
		
		return (List<ReturnEntity>) returnRepository.findAllByReturnDate(returnDate);
	}

	@Override
	public Optional<ReturnEntity> readById(Long id) {
		
		return returnRepository.findById(id);
	}	
	
	@Override
	public void update(ReturnEntity returnEntity) {
		
		returnRepository.save(returnEntity);
	}

	@Override
	public void deleteById(Long id) {

		returnRepository.deleteById(id);
	}

}
