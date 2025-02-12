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

import cl.oscar_pino.apiSecurity.dto.PermissionDTO;
import cl.oscar_pino.apiSecurity.entities.PermissionEntity;
import cl.oscar_pino.apiSecurity.entities.enums.PermissionEnum;
import cl.oscar_pino.apiSecurity.services.PermissionServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

	@Autowired
	private PermissionServiceImp permissionServiceImp;

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody PermissionDTO permissionDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");
		
		boolean emptyFields = permissionDTO == null | permissionDTO.getPermissionEnum().name().isBlank();

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		else if (PermissionEnum.valueOf(permissionDTO.getPermissionEnum().name().toUpperCase()) == null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("debe ingresar un permiso valido");
		else {
			permissionServiceImp.create(new PermissionEntity(permissionDTO.getPermissionEnum()));
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(permissionDTO.getPermissionEnum().name() + " creado correctamente.");
		}
	}

	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<PermissionDTO> permissions = permissionServiceImp.readAll().stream()
				.map(p -> new PermissionDTO(p.getId(), p.getPermissionEnum())).toList();

		if (permissions.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(permissions);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado permisos.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<PermissionEntity> recovered = permissionServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado permiso con id: " + id + ".");
	}

	@GetMapping("/read/all/name/{name}")
	public ResponseEntity<?> readAllByName(@PathVariable String name) {

		List<PermissionDTO> permissions = permissionServiceImp.readAllPermissionByName(name).stream()
				.map(p -> new PermissionDTO(p.getId(), p.getPermissionEnum())).toList();		

		if (permissions.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(permissions);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado permisos con el nombre "+name+".");
	}

	@GetMapping("/read/unique")
	public ResponseEntity<?> readUnique() {

		Map<Long,String> permissions = new HashMap<Long, String>();

		permissionServiceImp.readAll().forEach(p -> {
			if (!permissions.containsValue(p.getPermissionEnum().name()))
				permissions.put(p.getId(), p.getPermissionEnum().name()); // setea el id de la primera ocurrencia en el Map
		});

		if (permissions.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(permissions);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado permisos.");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody PermissionDTO permissionDTO,
			BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");
		
		boolean emptyFields = permissionDTO.getPermissionEnum() == null | permissionDTO.getPermissionEnum().name().isBlank();
		Optional<PermissionEntity> recovered = permissionServiceImp.readById(id);

		if (!recovered.isPresent())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado permiso con id: "+id+".");
		else if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		
		else if (PermissionEnum.valueOf(permissionDTO.getPermissionEnum().name().toUpperCase()) == null)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("debe ingresar un permiso valido");
		else {

			PermissionEntity permission = recovered.get();
			permission.setPermissionEnum(permissionDTO.getPermissionEnum());
			permissionServiceImp.create(permission);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(permissionDTO.getPermissionEnum().name() + " actualizado correctamente.");
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<PermissionEntity> recovered = permissionServiceImp.readById(id);

		if (recovered.isPresent()) {
			permissionServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("permiso con id: " + id + ", eliminada correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no se ha encontrado permiso con id: " + id + ".");
	}
}
