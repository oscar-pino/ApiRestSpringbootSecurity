package cl.oscar_pino.apiSecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.oscar_pino.apiSecurity.entities.RoleEntity;
import cl.oscar_pino.apiSecurity.repositories.IRoleRepository;

@Service
public class RoleServiceImp implements IDAO<RoleEntity> {

	@Autowired
	IRoleRepository roleRepository;

	@Override
	public void create(RoleEntity role) {

		roleRepository.save(role);
	}

	@Override
	public List<RoleEntity> readAll() {

		return (List<RoleEntity>) roleRepository.findAll();
	}
	
	public List<RoleEntity> readAllRoleByName(String name) {

		return (List<RoleEntity>) roleRepository.findAllRoleByName(name);
	}
	
	@Override
	public Optional<RoleEntity> readById(Long id) {

		return roleRepository.findById(id);
	}

	@Override
	public void update(RoleEntity roleEntity) {

		roleRepository.save(roleEntity);
	}

	@Override
	public void deleteById(Long id) {

		roleRepository.deleteById(id);
	}	

}
