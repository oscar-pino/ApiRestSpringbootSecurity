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

import cl.oscar_pino.apiSecurity.dto.NationalityDTO;
import cl.oscar_pino.apiSecurity.entities.NationalityEntity;
import cl.oscar_pino.apiSecurity.services.NationalityServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/nationalities")
public class NationalityController {

	@Autowired
	private NationalityServiceImp nationalityServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody NationalityDTO nationalityDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		boolean repeated = false;
		boolean emptyFields = nationalityDTO.getName().isBlank() | nationalityDTO.getLanguage().isBlank();

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else
			repeated = nationalityServiceImp.readAll().stream()
					.anyMatch(n -> n.getName().equalsIgnoreCase(nationalityDTO.getName()));

		if (repeated)
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(nationalityDTO.getName() + " ya existe, pruebe con otra nationalidad.");
		else {
			nationalityServiceImp.create(new NationalityEntity(nationalityDTO.getName(), nationalityDTO.getLanguage()));
			return ResponseEntity.status(HttpStatus.CREATED).body(nationalityDTO.getName() + " creada correctamente.");
		}
	}
	
	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<NationalityDTO> nationes = nationalityServiceImp.readAll().stream()
				.map((n) -> new NationalityDTO(n.getId(), n.getName(), n.getLanguage())).toList();

		if (nationes.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(nationes);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado nacionalidades.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<NationalityEntity> recovered = nationalityServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado nacionalidad con id: " + id + ".");
	}

	@GetMapping("/read/name/{name}")
	public ResponseEntity<?> readByName(@PathVariable String name) {

		Optional<NationalityEntity> recovered = nationalityServiceImp.readByName(name);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado nacionalidad con nombre: " + name + ".");
	}	

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody NationalityDTO nationalityDTO,
			BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		Optional<NationalityEntity> recovered = nationalityServiceImp.readById(id);
		boolean emptyFields = nationalityDTO.getName().isBlank() | nationalityDTO.getLanguage().isBlank();
		boolean repeated = false;

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else if (recovered.isPresent()) {
			repeated = nationalityServiceImp.readAll().stream()
					.anyMatch(n -> n != recovered.get() & n.getName().equalsIgnoreCase(nationalityDTO.getName()));
		}else
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("no se ha encontrado nacionalidad con id: " + id + ".");

		if (repeated)
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body(nationalityDTO.getName() + " ya existe, pruebe con otra nationalidad.");
		else {

			NationalityEntity nationality = recovered.get();
			nationality.setName(nationalityDTO.getName());
			nationality.setLanguage(nationalityDTO.getLanguage());

			nationalityServiceImp.create(nationality);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(nationalityDTO.getName() + " actualizada correctamente.");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<NationalityEntity> recovered = nationalityServiceImp.readById(id);

		if (recovered.isPresent()) {
			nationalityServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("nacionalidad con id: " + id + ", eliminada correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("no se ha encontrado nacionalidad con id: " + id + ".");
	}
}
