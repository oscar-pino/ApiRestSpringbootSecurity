package cl.oscar_pino.apiSecurity.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import cl.oscar_pino.apiSecurity.dto.RoleDTO;
import cl.oscar_pino.apiSecurity.entities.RoleEntity;
import cl.oscar_pino.apiSecurity.services.RoleServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
	
	@Autowired
	private RoleServiceImp roleServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody RoleDTO roleDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		boolean isEnum = false;
		boolean emptyFields = roleDTO.getRoleEnum() == null | roleDTO.getPermissionList() == null;

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else
			isEnum = roleServiceImp.readAll().stream().anyMatch(r -> r.getRoleEnum().name().equalsIgnoreCase(roleDTO.getRoleEnum().name()));

		if (!isEnum)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("debe ingresar un role valido");
		else {
			roleServiceImp.create(new RoleEntity(roleDTO.getRoleEnum(), roleDTO.getPermissionList()));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(roleDTO.getRoleEnum().name() + " creado correctamente.");
		}
	}

	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<RoleDTO> roles = roleServiceImp.readAll().stream()
				.map(r -> new RoleDTO(r.getId(), r.getRoleEnum(), r.getPermissionList())).toList();

		if (roles.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(roles);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado roles.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<RoleEntity> recovered = roleServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado role con id: " + id + ".");
	}

	@GetMapping("/read/all/name/{name}")
	public ResponseEntity<?> readAllByName(@PathVariable String name) {

		List<RoleDTO> roles = roleServiceImp.readAllRoleByName(name).stream()
				.map(r -> new RoleDTO(r.getId(), r.getRoleEnum(), r.getPermissionList())).toList();

		if (roles.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(roles);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado roles con el nombre "+name+".");
	}

	@GetMapping("/read/unique")
	public ResponseEntity<?> readUnique() {

		Map<Long,String> roles = new HashMap<Long, String>();

		roleServiceImp.readAll().forEach(r -> {
			if (!roles.containsValue(r.getRoleEnum().name()))
				roles.put(r.getId(), r.getRoleEnum().name()); // setea el id de la primera ocurrencia en el Map
		});

		if (roles.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(roles);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado roles.");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody RoleDTO roleDTO,
			BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

		boolean isEnum = false;
		boolean emptyFields = roleDTO.getRoleEnum() == null | roleDTO.getPermissionList() == null;
		Optional<RoleEntity> recovered = roleServiceImp.readById(id);

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else if (!recovered.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado role con id: "+id+".");
		else
			isEnum = roleServiceImp.readAll().stream().anyMatch(r -> r.getRoleEnum().name().equalsIgnoreCase(roleDTO.getRoleEnum().name()));

		if (!isEnum)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("debe ingresar un role valido");
		else {

			RoleEntity role = recovered.get();
			role.setRoleEnum(roleDTO.getRoleEnum());
			role.setPermissionList(roleDTO.getPermissionList());
			roleServiceImp.create(role);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(roleDTO.getRoleEnum().name() + " actualizado correctamente.");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<RoleEntity> recovered = roleServiceImp.readById(id);

		if (recovered.isPresent()) {
			roleServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("role con id: " + id + ", eliminada correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no se ha encontrado role con id: " + id + ".");
	}
}
