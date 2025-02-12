package cl.oscar_pino.apiSecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.oscar_pino.apiSecurity.entities.CategoryEntity;
import cl.oscar_pino.apiSecurity.repositories.ICategoryRepository;

@Service
public class CategoryServiceImp implements IDAO<CategoryEntity> {

	@Autowired
	ICategoryRepository categoryRepository;

	@Override
	public void create(CategoryEntity categoryEntity) {

		categoryRepository.save(categoryEntity);

	}
	
	@Override
	public List<CategoryEntity> readAll() {

		return (List<CategoryEntity>) categoryRepository.findAll();
	}

	@Override
	public Optional<CategoryEntity> readById(Long id) {

		return categoryRepository.findById(id);
	}

	public Optional<CategoryEntity> readByName(String name) {

		return categoryRepository.findByName(name);
	}	

	@Override
	public void update(CategoryEntity categoryEntity) {

		categoryRepository.save(categoryEntity);
	}

	@Override
	public void deleteById(Long id) {

		categoryRepository.deleteById(id);
	}
}
