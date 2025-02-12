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

import cl.oscar_pino.apiSecurity.dto.EditorialDTO;
import cl.oscar_pino.apiSecurity.entities.EditorialEntity;
import cl.oscar_pino.apiSecurity.services.EditorialServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/editorials")
public class EditorialController {

	@Autowired
	private EditorialServiceImp editorialServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody EditorialDTO editorialDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		boolean[] repeated = new boolean[3]; // 0: name, 1: email, 2: website 
		boolean emptyFields = editorialDTO.getName().isBlank() | editorialDTO.getAddress().isBlank()
				| editorialDTO.getPhone().isBlank() | editorialDTO.getEmail().isBlank()
				| editorialDTO.getWebSite().isBlank() | editorialDTO.getFoundingDate() == null;

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else {
			repeated[0] = editorialServiceImp.readAll().stream()
					.anyMatch(e -> e.getName().equalsIgnoreCase(editorialDTO.getName()));
			
			repeated[1] = editorialServiceImp.readAll().stream()
					.anyMatch(e -> e.getEmail().equalsIgnoreCase(editorialDTO.getEmail()));
			
			repeated[2] = editorialServiceImp.readAll().stream()
					.anyMatch(e -> e.getWebSite().equalsIgnoreCase(editorialDTO.getWebSite()));
			
			if (repeated[0])
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(editorialDTO.getName() + " ya existe, pruebe con otro name.");
			else if (repeated[1])
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(editorialDTO.getEmail() + " ya existe, pruebe con otro email.");
			else if (repeated[2])
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(editorialDTO.getWebSite() + " ya existe, pruebe con otro website.");
		}

			editorialServiceImp.create(
					new EditorialEntity(editorialDTO.getName(), editorialDTO.getAddress(), editorialDTO.getPhone(),
							editorialDTO.getWebSite(), editorialDTO.getEmail(), editorialDTO.getFoundingDate()));
			return ResponseEntity.status(HttpStatus.CREATED).body(editorialDTO.getName() + " creada correctamente.");		
	}

	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<EditorialDTO> editoriales = editorialServiceImp.readAll().stream().map(e -> new EditorialDTO(e.getId(),
				e.getName(), e.getAddress(), e.getPhone(), e.getWebSite(), e.getEmail(), e.getFoundingDate())).toList();

		if (editoriales.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(editoriales);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado editoriales.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<EditorialEntity> recovered = editorialServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado editorial con id: " + id + ".");
	}

	@GetMapping("/read/name/{name}")
	public ResponseEntity<?> readByName(@PathVariable String name) {

		Optional<EditorialEntity> recovered = editorialServiceImp.readByName(name);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado editorial con nombre: " + name + ".");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody EditorialDTO editorialDTO,
			BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		Optional<EditorialEntity> recovered = editorialServiceImp.readById(id);
		boolean[] repeated = new boolean[3]; // 0: name, 1: email, 2: website 
		boolean emptyFields = editorialDTO.getName().isBlank() | editorialDTO.getAddress().isBlank()
				| editorialDTO.getPhone().isBlank() | editorialDTO.getEmail().isBlank()
				| editorialDTO.getWebSite().isBlank() | editorialDTO.getFoundingDate() == null;

		if(!recovered.isPresent())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no existe editorial con id: "+id);
		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else {
			repeated[0] = editorialServiceImp.readAll().stream()
					.anyMatch(e -> e != recovered.get() & e.getName().equalsIgnoreCase(editorialDTO.getName()));
			
			repeated[1] = editorialServiceImp.readAll().stream()
					.anyMatch(e -> e != recovered.get() & e.getEmail().equalsIgnoreCase(editorialDTO.getEmail()));
			
			repeated[2] = editorialServiceImp.readAll().stream()
					.anyMatch(e -> e != recovered.get() & e.getWebSite().equalsIgnoreCase(editorialDTO.getWebSite()));
			
			if (repeated[0])
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(editorialDTO.getName() + " ya existe, pruebe con otro name.");
			else if (repeated[1])
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(editorialDTO.getEmail() + " ya existe, pruebe con otro email.");
			else if (repeated[2])
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(editorialDTO.getWebSite() + " ya existe, pruebe con otro website.");
		}
			EditorialEntity editorial = recovered.get();
			editorial.setName(editorialDTO.getName());
			editorial.setAddress(editorialDTO.getAddress());
			editorial.setEmail(editorialDTO.getEmail());
			editorial.setPhone(editorialDTO.getPhone());
			editorial.setWebSite(editorialDTO.getWebSite());
			editorial.setFoundingDate(editorialDTO.getFoundingDate());

			editorialServiceImp.create(editorial);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(editorialDTO.getName() + " actualizada correctamente.");		
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<EditorialEntity> recovered = editorialServiceImp.readById(id);

		if (recovered.isPresent()) {
			editorialServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("editorial con id: " + id + ", eliminada correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no se ha encontrado editorial con id: " + id + ".");
	}
}
