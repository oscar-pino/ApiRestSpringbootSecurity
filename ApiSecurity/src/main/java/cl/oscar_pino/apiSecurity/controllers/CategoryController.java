package cl.oscar_pino.apiSecurity.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.oscar_pino.apiSecurity.dto.CategoryDTO;
import cl.oscar_pino.apiSecurity.entities.CategoryEntity;
import cl.oscar_pino.apiSecurity.services.CategoryServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryServiceImp categoryServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		boolean repeated = false;
		boolean emptyFields = categoryDTO.getName().isBlank() | categoryDTO.getDescription().isBlank();

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else
			repeated = categoryServiceImp.readAll().stream()
					.anyMatch(c -> c.getName().equalsIgnoreCase(categoryDTO.getName()));

		if (repeated)
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(categoryDTO.getName() + " ya existe, pruebe con otra categoria.");
		else {
			categoryServiceImp.create(new CategoryEntity(categoryDTO.getName(), categoryDTO.getDescription()));
			return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO.getName() + " creada correctamente.");
		}
	}
	
	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<CategoryDTO> categories = categoryServiceImp.readAll().stream()
				.map(c -> new CategoryDTO(c.getId(), c.getName(), c.getDescription())).toList();

		if (categories.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(categories);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado categorias.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<CategoryEntity> recovered = categoryServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado categoria con id: " + id + ".");
	}

	@GetMapping("/read/name/{name}")
	public ResponseEntity<?> readByName(@PathVariable String name) {

		Optional<CategoryEntity> recovered = categoryServiceImp.readByName(name);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado categoria con nombre: " + name + ".");
	}	

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO,
			BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		Optional<CategoryEntity> recovered = categoryServiceImp.readById(id);
		boolean emptyFields = categoryDTO.getName().isBlank() | categoryDTO.getDescription().isBlank();
		boolean repeated = false;

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else if (recovered.isPresent()) {
			repeated = categoryServiceImp.readAll().stream()
					.anyMatch(c -> c != recovered.get() & c.getName().equalsIgnoreCase(categoryDTO.getName()));
		}else
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("no se ha encontrado categoria con id: " + id + ".");

		if (repeated)
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(categoryDTO.getName() + " ya existe, pruebe con otra categoria.");
		else {

			CategoryEntity category = recovered.get();
			category.setName(categoryDTO.getName());
			category.setDescription(categoryDTO.getDescription());

			categoryServiceImp.create(category);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(categoryDTO.getName() + " actualizada correctamente.");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<CategoryEntity> recovered = categoryServiceImp.readById(id);

		if (recovered.isPresent()) {
			categoryServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("categoria con id: " + id + ", eliminada correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("no se ha encontrado categoria con id: " + id + ".");
	}	
}
