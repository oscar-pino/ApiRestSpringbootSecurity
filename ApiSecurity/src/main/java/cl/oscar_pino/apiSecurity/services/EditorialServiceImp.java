package cl.oscar_pino.apiSecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.oscar_pino.apiSecurity.entities.EditorialEntity;
import cl.oscar_pino.apiSecurity.repositories.IEditorialRepository;

@Service
public class EditorialServiceImp implements IDAO<EditorialEntity> {

	@Autowired
	private IEditorialRepository editorialRepository;

	@Override
	public void create(EditorialEntity editorialEntity) {

		editorialRepository.save(editorialEntity);
	}

	@Override
	public List<EditorialEntity> readAll() {

		return (List<EditorialEntity>) editorialRepository.findAll();
	}

	@Override
	public Optional<EditorialEntity> readById(Long id) {

		return editorialRepository.findById(id);
	}

	public Optional<EditorialEntity> readByName(String name) {

		return editorialRepository.findByName(name);
	}

	public Optional<EditorialEntity> readByWebSite(String webSite) {

		return editorialRepository.findByWebSite(webSite);
	}

	public Optional<EditorialEntity> readByEmail(String email) {

		return editorialRepository.findByEmail(email);
	}

	@Override
	public void update(EditorialEntity editorialEntity) {

		editorialRepository.save(editorialEntity);
	}

	@Override
	public void deleteById(Long id) {

		editorialRepository.deleteById(id);
	}
}
