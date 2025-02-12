package cl.oscar_pino.apiSecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.oscar_pino.apiSecurity.entities.PermissionEntity;
import cl.oscar_pino.apiSecurity.repositories.IPermissionRepository;

@Service
public class PermissionServiceImp implements IDAO<PermissionEntity> {
	

	@Autowired
	IPermissionRepository permissionRepository;

	@Override
	public void create(PermissionEntity permissionEntity) {
		
		permissionRepository.save(permissionEntity);
	}
	
	@Override
	public List<PermissionEntity> readAll() {

		return (List<PermissionEntity>)permissionRepository.findAll();
	}

	@Override
	public Optional<PermissionEntity> readById(Long id) {

		return permissionRepository.findById(id);
	
	}
	
	public List<PermissionEntity> readAllPermissionByName(String name){		
		
		return permissionRepository.findAllPermissionByName(name);
	}	
	
	@Override
	public void update(PermissionEntity permissionEntity) {
		
		permissionRepository.save(permissionEntity);		
	}

	@Override
	public void deleteById(Long id) {
		
		permissionRepository.deleteById(id);
	}
}
