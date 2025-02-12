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

import cl.oscar_pino.apiSecurity.dto.ReturnDTO;
import cl.oscar_pino.apiSecurity.entities.ReturnEntity;
import cl.oscar_pino.apiSecurity.services.ReturnServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/returns")
public class ReturnController {


	@Autowired
	private ReturnServiceImp returnServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody ReturnDTO returnDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		if (returnDTO.getReturnDate() == null | returnDTO.getDaysLate() < 0 | returnDTO.getPenalty() < 0) {
			if(returnDTO.getReturnDate() == null)
				return ResponseEntity.status(HttpStatus.CONFLICT).body("la fecha no puede ser nula!");
			else
				return ResponseEntity.status(HttpStatus.CONFLICT).body("el valor ingresado no puede ser nulo!");
		}
		else {
			returnServiceImp.create(new ReturnEntity(returnDTO.getReturnDate(), returnDTO.getPenalty(), returnDTO.getDaysLate()));
			return ResponseEntity.status(HttpStatus.CREATED).body(returnDTO.getReturnDate() + " creada correctamente.");
		}
	}
	
	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<ReturnDTO> returns = returnServiceImp.readAll().stream()
				.map(r -> new ReturnDTO(r.getId(), r.getReturnDate(), r.getPenalty(), r.getDaysLate())).toList();

		if (returns.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(returns);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado devoluciones.");
	}
	
	@GetMapping("/read/all/return_date/{date}")
	public ResponseEntity<?> readAllByReturnDate(@PathVariable String date) {

		List<ReturnDTO> returns = returnServiceImp.readAllByReturnDate(date).stream()
				.map(r -> new ReturnDTO(r.getId(), r.getReturnDate(), r.getPenalty(), r.getDaysLate())).toList();

		if (returns.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(returns);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado devoluciones con la fecha ingresada!");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<ReturnEntity> recovered = returnServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado devolucion con id: " + id + ".");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ReturnDTO returnDTO,
			BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");
		
		Optional<ReturnEntity> recovered = returnServiceImp.readById(id);
		
		if(!recovered.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado devolucion con id: " + id + ".");
		else if (returnDTO.getReturnDate() == null | returnDTO.getDaysLate() < 0 | returnDTO.getPenalty() < 0) {
			if(returnDTO.getReturnDate() == null)
				return ResponseEntity.status(HttpStatus.CONFLICT).body("la fecha no puede ser nula!");
			else
				return ResponseEntity.status(HttpStatus.CONFLICT).body("el valor ingresado no puede ser nulo!");
		}
		else {

			ReturnEntity devolucion = recovered.get();
			devolucion.setReturnDate(returnDTO.getReturnDate());
			devolucion.setPenalty(returnDTO.getPenalty());
			devolucion.setDaysLate(returnDTO.getDaysLate());

			returnServiceImp.create(devolucion);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("devolucion con id: "+returnDTO.getId()+", actualizada correctamente.");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<ReturnEntity> recovered = returnServiceImp.readById(id);

		if (recovered.isPresent()) {
			returnServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("devolucion con id: " + id + ", eliminada correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("no se ha encontrado devolucion con id: " + id + ".");
	}
}
