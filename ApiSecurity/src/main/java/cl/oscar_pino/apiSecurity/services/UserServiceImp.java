package cl.oscar_pino.apiSecurity.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.oscar_pino.apiSecurity.entities.UserEntity;
import cl.oscar_pino.apiSecurity.repositories.IUserRepository;

@Service
public class UserServiceImp implements IDAO<UserEntity> {
	
	@Autowired
	IUserRepository userRepository;

	@Override
	public void create(UserEntity userEntity) {
	
		userRepository.save(userEntity);
	}	
	
	public void createAll(Set<UserEntity> users) {
		
		userRepository.saveAll(users);
	}	

	@Override
	public List<UserEntity> readAll() {
		
		return (List<UserEntity>)userRepository.findAll();
	}

	@Override
	public Optional<UserEntity> readById(Long id) {
		
		return userRepository.findById(id);
	}
	
	public Optional<UserEntity> readByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}
	
	@Override
	public void update(UserEntity userEntity) {
		
		userRepository.save(userEntity);
	}

	@Override
	public void deleteById(Long id) {		

		userRepository.deleteById(id);
	}

}
