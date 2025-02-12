package cl.oscar_pino.apiSecurity.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import cl.oscar_pino.apiSecurity.dto.LoanDTO;
import cl.oscar_pino.apiSecurity.entities.LoanEntity;
import cl.oscar_pino.apiSecurity.services.LoanServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
	
	@Autowired
	private LoanServiceImp loanServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody LoanDTO loanDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");
		
		boolean emptyFields = loanDTO.getBooks() == null | loanDTO.getCustomer() == null | loanDTO.getLoanDate() == null 
				| loanDTO.getReturnEntity() == null;
		
		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");		

		else {
			
			loanServiceImp.create(new LoanEntity(loanDTO.getBooks(), loanDTO.getCustomer(), loanDTO.getLoanDate(), loanDTO.getReturnEntity()));					
			return ResponseEntity.status(HttpStatus.CREATED).body("prestamo creado correctamente.");
		}
	}
	
	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<LoanDTO> loans = loanServiceImp.readAll().stream()
				.map(l -> new LoanDTO(l.getId(), l.getBooks(), l.getCustomer(), l.getLoanDate(), l.getReturnEntity())).toList();

		if (loans.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(loans);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado prestamos.");
	}
	
	@GetMapping("/read/all/first_and_last_name_customer/{firstAndLastName}")
	public ResponseEntity<?> readAllByFirstAndLastNameCustomer(@PathVariable String firstAndLastName) {
		
		boolean emptyField = firstAndLastName.isBlank() | firstAndLastName == null;	
		int cont = 0;
		char caracter = '_';
		
		for(int i=0; i<firstAndLastName.length(); i++)
				if(firstAndLastName.charAt(i) == caracter) 
					cont++;
		
		if (emptyField)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("debe ingresar nombre_apellido!");		
		else if(cont > 1)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("debe ingresar un formato correcto, así nombre_apellido");
		
		String[] fullName = firstAndLastName.split(String.valueOf(caracter));
				
		List<LoanDTO> loans = loanServiceImp.readAllByFirstAndLastNameCustomer(fullName[0].trim(), fullName[1].trim()).stream()
				.map(l -> new LoanDTO(l.getId(), l.getBooks(), l.getCustomer(), l.getLoanDate(), l.getReturnEntity())).toList();

		if (loans.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(loans);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado prestamos con nombre y apellido: "+firstAndLastName+".");
	}	
	
	@GetMapping("/read/all/loan_date/{loanDate}")
	public ResponseEntity<?> readAllByLoanDate(@PathVariable String loanDate) {
		
		 // Definir el formato de la fecha en la URL
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy");

        // Convertir el string de la fecha en un objeto LocalDate
        LocalDate parsedLoanDate = LocalDate.parse(loanDate, formatter);

		List<LoanDTO> loans = loanServiceImp.readAllByLoanDate(parsedLoanDate).stream()
				.map(l -> new LoanDTO(l.getId(), l.getBooks(), l.getCustomer(), l.getLoanDate(), l.getReturnEntity())).toList();

		if (loans.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(loans);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado prestamos con la fecha de prestamo: "+loanDate+".");
	}	

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<LoanEntity> recovered = loanServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado prestamo con id: " + id + ".");
	}	
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody LoanDTO loanDTO,
			BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");
		
		Optional<LoanEntity> recovered = loanServiceImp.readById(id);
		boolean emptyFields = loanDTO.getBooks() == null | loanDTO.getCustomer() == null | loanDTO.getLoanDate() == null 
				| loanDTO.getReturnEntity() == null;
		
		if(!recovered.isPresent())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no existe prestamo con id: "+id+".");
		else if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");		

		else {

			LoanEntity loan = recovered.get();
			loan.setBooks(loanDTO.getBooks());
			loan.setCustomer(loanDTO.getCustomer());
			loan.setLoanDate(loanDTO.getLoanDate());
			loan.setReturnEntity(loanDTO.getReturnEntity());

			loanServiceImp.create(loan);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body("prestamo con id: "+id+" actualizado correctamente.");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<LoanEntity> recovered = loanServiceImp.readById(id);

		if (recovered.isPresent()) {
			loanServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("prestamo con id: " + id + ", eliminado correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("no se ha encontrado prestamo con id: " + id + ".");
	}	
}
