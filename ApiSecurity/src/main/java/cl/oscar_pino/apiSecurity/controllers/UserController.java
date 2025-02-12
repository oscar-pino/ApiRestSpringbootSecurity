package cl.oscar_pino.apiSecurity.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.oscar_pino.apiSecurity.dto.UserDTO;
import cl.oscar_pino.apiSecurity.entities.UserEntity;
import cl.oscar_pino.apiSecurity.services.UserServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserServiceImp userServiceImp;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody UserDTO userDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		boolean repeated = false;
		boolean emptyFields = userDTO.getUsername().isBlank() | userDTO.getPassword().isBlank();

		if (emptyFields)			
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else if(userDTO.getRoles() == null | userDTO.getRoles().size() == 0)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("debe agregar como minimo 1 role.");
		else
			repeated = userServiceImp.readAll().stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(userDTO.getUsername()));

		if (repeated)
			return ResponseEntity.status(HttpStatus.CONFLICT).body(userDTO.getUsername()+" ya existe, pruebe con otro username.");
		else {
			userServiceImp.create(new UserEntity(userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getRoles()));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(userDTO.getUsername() + " creado correctamente.");
		}
	}

	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<UserDTO> users = userServiceImp.readAll().stream()
				.map(u -> new UserDTO(u.getId(), u.getUsername(), u.getPassword(), u.getRoles(), u.isEnabled()
						, u.isAccountNoExpired(), u.isAccountNoLocked(), u.isCredentialNoExpired())).toList();

		if (users.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(users);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado users.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<UserEntity> recovered = userServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado user con id: " + id + ".");
	}

	@GetMapping("/read/username/{username}")
	public ResponseEntity<?> readAllByName(@PathVariable String username) {

		Optional<UserEntity> recovered = userServiceImp.readByUsername(username);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado user con username: " + username + ".");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO,
			BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("ha ocurrido un error: "+result.getFieldError().getDefaultMessage());
		
		boolean repeated = false;
		boolean emptyFields = userDTO.getUsername().isBlank() | userDTO.getPassword().isBlank();
		Optional<UserEntity> recovered = userServiceImp.readById(id);		
		List<UserEntity> users = userServiceImp.readAll();
		
		if (emptyFields) 
				return ResponseEntity.status(HttpStatus.CONFLICT).body(("faltan datos."));
		else if(userDTO.getRoles() == null | userDTO.getRoles().size() == 0)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("debe agregar como minimo 1 role.");
		else if(!recovered.isPresent())
				return ResponseEntity.status(HttpStatus.CONFLICT).body(("no existe user con id: " + id));
		else if(userServiceImp.readById(id).isPresent()) {
		
		repeated = users.stream().anyMatch(u -> u != recovered.get() & u.getUsername().equalsIgnoreCase(userDTO.getUsername()));
		
		}

		if (repeated) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(userDTO.getUsername()+" ya existe, pruebe con otro username.");
		} else {
			
			UserEntity user = recovered.get();
			user.setUsername(userDTO.getUsername());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			user.setRoles(userDTO.getRoles());
			user.setAccountNoExpired(userDTO.isAccountNoExpired());
			user.setAccountNoLocked(userDTO.isAccountNoLocked());
			user.setCredentialNoExpired(userDTO.isCredentialNoExpired());
			user.setEnabled(userDTO.isEnabled());
			
			userServiceImp.update(user);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(userDTO.getUsername() + ", actualizado correctamente.");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<UserEntity> recovered = userServiceImp.readById(id);

		if (recovered.isPresent()) {
			userServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("user con id: " + id + ", eliminado correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no se ha encontrado user con id: " + id + ".");
	}
}
