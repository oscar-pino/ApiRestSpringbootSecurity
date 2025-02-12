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

import cl.oscar_pino.apiSecurity.dto.CustomerDTO;
import cl.oscar_pino.apiSecurity.entities.CustomerEntity;
import cl.oscar_pino.apiSecurity.services.CustomerServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	@Autowired
	private CustomerServiceImp customerServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody CustomerDTO customerDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		boolean[] repeated = new boolean[2]; // 0: email, 1: phone
		boolean emptyFields = customerDTO.getFirstName().isBlank() | customerDTO.getLastName().isBlank() | customerDTO.getAddress().isBlank() | 
				customerDTO.getNationality() == null | customerDTO.getPhone().isBlank() | customerDTO.getEmail().isBlank();

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else {
			repeated[0] = customerServiceImp.readAll().stream().anyMatch(c -> c.getEmail().equalsIgnoreCase(customerDTO.getEmail()));
			repeated[1] = customerServiceImp.readAll().stream().anyMatch(c -> c.getPhone().equalsIgnoreCase(customerDTO.getPhone()));
		}
		
		if (repeated[0] | repeated[1]) {
			if (repeated[0])
			return ResponseEntity.status(HttpStatus.CONFLICT).body(customerDTO.getEmail()+" ya existe, pruebe con otro email.");
			else
				return ResponseEntity.status(HttpStatus.CONFLICT).body(customerDTO.getPhone()+" ya existe, pruebe con otro teléfono.");
		}else {
			customerServiceImp.create(new CustomerEntity(customerDTO.getFirstName(), customerDTO.getLastName(), customerDTO.getNationality(), 
					customerDTO.getAddress(), customerDTO.getEmail(), customerDTO.getPhone()));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(customerDTO.getFirstName() + " creado correctamente.");
		}
	}

	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<CustomerDTO> customers = customerServiceImp.readAll().stream()
				.map(c -> new CustomerDTO(c.getFirstName(), c.getLastName(), c.getNationality(), c.getAddress(), c.getEmail(), c.getPhone())).toList();

		if (customers.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(customers);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado clientes.");
	}
	
	@GetMapping("/read/all/nationality_name/{name}")
	public ResponseEntity<?> readAllByNationalityName(String name) {

		List<CustomerDTO> customers = customerServiceImp.readAllByNationalityName(name).stream()
				.map(c -> new CustomerDTO(c.getFirstName(), c.getLastName(), c.getNationality(), c.getAddress(), c.getEmail(), c.getPhone())).toList();

		if (customers.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(customers);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado clientes con nombre de nacionalidad: "+name+".");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<CustomerEntity> recovered = customerServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado cliente con id: " + id + ".");
	}

	@GetMapping("/read/email/{email}")
	public ResponseEntity<?> readByEmail(@PathVariable String email) {

		Optional<CustomerEntity> recovered = customerServiceImp.readByEmail(email);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado cliente con email: " + email + ".");
	}
	
	@GetMapping("/read/phone/{phone}")
	public ResponseEntity<?> readByPhone(@PathVariable String phone) {

		Optional<CustomerEntity> recovered = customerServiceImp.readByPhone(phone);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado cliente con phone: " + phone + ".");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO,
			BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		boolean[] repeated = new boolean[2]; // 0: email, 1: phone
		boolean emptyFields = customerDTO.getFirstName().isBlank() | customerDTO.getLastName().isBlank() | customerDTO.getAddress().isBlank() | 
				customerDTO.getNationality() == null | customerDTO.getPhone().isBlank() | customerDTO.getEmail().isBlank();
		Optional<CustomerEntity> recovered = customerServiceImp.readById(id);

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		if(!recovered.isPresent())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no se ha encontrado cliente con id: " + id + ".");
		else {
			repeated[0] = customerServiceImp.readAll().stream().anyMatch(c -> c.getEmail().equalsIgnoreCase(customerDTO.getEmail()));
			repeated[1] = customerServiceImp.readAll().stream().anyMatch(c -> c.getPhone().equalsIgnoreCase(customerDTO.getPhone()));
		}
		
		if (repeated[0] | repeated[1]) {
			if (repeated[0])
			return ResponseEntity.status(HttpStatus.CONFLICT).body(customerDTO.getEmail()+" ya existe, pruebe con otro email.");
			else
				return ResponseEntity.status(HttpStatus.CONFLICT).body(customerDTO.getPhone()+" ya existe, pruebe con otro teléfono.");
		}else {
			
			CustomerEntity customer = recovered.get();
			customer.setFirstName(customerDTO.getFirstName());
			customer.setLastName(customerDTO.getLastName());
			customer.setNationality(customerDTO.getNationality());
			customer.setAddress(customerDTO.getAddress());
			customer.setEmail(customerDTO.getEmail());
			customer.setPhone(customerDTO.getPhone());
			
			customerServiceImp.update(customer);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(customerDTO.getFirstName() + ", actualizado correctamente.");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<CustomerEntity> recovered = customerServiceImp.readById(id);

		if (recovered.isPresent()) {
			customerServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("user con id: " + id + ", eliminado correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no se ha encontrado user con id: " + id + ".");
	}
}
