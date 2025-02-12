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

import cl.oscar_pino.apiSecurity.dto.AuthorDTO;
import cl.oscar_pino.apiSecurity.entities.AuthorEntity;
import cl.oscar_pino.apiSecurity.entities.NationalityEntity;
import cl.oscar_pino.apiSecurity.services.AuthorServiceImp;
import cl.oscar_pino.apiSecurity.services.NationalityServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

	@Autowired
	private AuthorServiceImp authorServiceImp;

	@Autowired
	private NationalityServiceImp nationalityServiceImp;	
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody AuthorDTO authorDTO, BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");
		
		Optional<NationalityEntity> nationalityRecovered = nationalityServiceImp.readByName(authorDTO.getNationality().getName());
		
		boolean emptyFields = authorDTO.getFirstName().isBlank() | authorDTO.getLastName().isBlank()
				| authorDTO.getNationality() == null;

		boolean[] repeated = new boolean[3]; // 0: natinality, 1: webSite, 2:email, 
		String email = "sin_email";
		String webSite = "sin_website";
		
		repeated[0] = nationalityServiceImp.readAll().stream()
				.anyMatch(n -> n.getName().equalsIgnoreCase(authorDTO.getNationality().getName()));
		
		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		
		else if (!repeated[0])
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("no existe nationality: " + authorDTO.getNationality().getName() + ", ingrese una valida");
		
		else { // datos básicos correctos	
			
			if(authorServiceImp.findByFirstNameAndLastName(authorDTO.getFirstName(), authorDTO.getLastName()).isPresent())
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(authorDTO.getFirstName() + " "+authorDTO.getLastName()+" ya existe, pruebe con otro nombre!");
			
			else if (authorDTO.getNationality().getLanguage() != null && !nationalityRecovered.get().getLanguage().equalsIgnoreCase(authorDTO.getNationality().getLanguage()))
				
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body("nationality: " + authorDTO.getNationality().getName() + ", no corresponde con language: "
								+ authorDTO.getNationality().getLanguage());			
			
			if(authorDTO.getWebSite() != null & authorDTO.getEmail() != null) {
				
				String correctEmail = "^[a-zA-ZñÑ]+[a-zA-ZñÑ0-9_&.-]+@[a-zA-ZñÑ]+[a-zA-Z0-9ñÑ_&-]+\\.[a-zA-ZñÑ]{2,3}$";
				String correctWebSite = "(https?://)?www\\.[A-Za-zñÑ]+[A-Za-z0-9ñÑ_&]+\\.[A-Za-zñÑ]{2,3}";
				
				repeated[1] = authorServiceImp.readAll().stream()
					    .filter(a -> !a.getWebSite().equalsIgnoreCase("sin_website"))
					    .anyMatch(a -> a.getWebSite().equalsIgnoreCase(authorDTO.getWebSite()));			
				
				repeated[2] = authorServiceImp.readAll().stream()
						.filter(a -> !a.getEmail().equalsIgnoreCase("sin_email"))
						.anyMatch(a -> a.getEmail().equalsIgnoreCase(authorDTO.getEmail()));			
					
					if(repeated[1])
					return ResponseEntity.status(HttpStatus.CONFLICT)
							.body(authorDTO.getWebSite() + " ya existe, pruebe con otro sitio web.");	
					else if(!authorDTO.getWebSite().matches(correctWebSite))
						return ResponseEntity.status(HttpStatus.CONFLICT)
								.body(authorDTO.getWebSite() + ", no tiene un formato de sitio web valido!");					
					
					if(repeated[2])
						return ResponseEntity.status(HttpStatus.CONFLICT)
								.body(authorDTO.getEmail() + " ya existe, pruebe con otro email.");	
						else if(!authorDTO.getEmail().matches(correctEmail))
							return ResponseEntity.status(HttpStatus.CONFLICT)
									.body(authorDTO.getEmail() + ", no tiene un formato de email valido!");	
					
					webSite = authorDTO.getWebSite();
					email = authorDTO.getEmail();
									
			}
				
			
			authorServiceImp.create(new AuthorEntity(authorDTO.getFirstName(), authorDTO.getLastName(), nationalityRecovered.get(), webSite, email));
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(authorDTO.getFirstName() + " " + authorDTO.getLastName() + ", creado correctamente.");
		}				
	}

	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<AuthorDTO> authors = authorServiceImp.readAll().stream().map(a -> new AuthorDTO(a.getId(),
				a.getFirstName(), a.getLastName(), a.getNationality(), a.getWebSite(), a.getEmail())).toList();

		if (authors.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(authors);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado autores.");
	}

	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readById(@PathVariable Long id) {

		Optional<AuthorEntity> recovered = authorServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado author con id: " + id + ".");
	}

	@GetMapping("/read/email/{email}")
	public ResponseEntity<?> readByEmail(@PathVariable String email) {

		Optional<AuthorEntity> recovered = authorServiceImp.readByEmail(email);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se ha encontrado author con email: " + email + ".");
	}

	@GetMapping("/read/web/{web}")
	public ResponseEntity<?> readByWebSite(@PathVariable String web) {

		Optional<AuthorEntity> recovered = authorServiceImp.readByWebSite(web);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se ha encontrado author con sitio web: " + web + ".");
	}

	@GetMapping("/read/first_name/{firstName}")
	public ResponseEntity<?> readByFisrstName(@PathVariable String firstName) {

		List<AuthorDTO> authors = authorServiceImp.readByFirstName(firstName).stream().map(a -> new AuthorDTO(a.getId(),
				a.getFirstName(), a.getLastName(), a.getNationality(), a.getWebSite(), a.getEmail())).toList();

		if (authors.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(authors);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado autores con firstName: " + firstName + ".");

	}
	
	@GetMapping("/read/full_name/{firstName} {lastName}")
	public ResponseEntity<?> readByFisrstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {	

		Optional<AuthorEntity> recovered = authorServiceImp.findByFirstNameAndLastName(firstName, lastName);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered.get());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado autores con full name: " + firstName + " "+lastName);

	}
	
	@GetMapping("/read/last_name/{lastName}")
	public ResponseEntity<?> readByLastName(@PathVariable String lastName) {

		List<AuthorDTO> authors = authorServiceImp.readByLastName(lastName).stream().map(a -> new AuthorDTO(a.getId(),
				a.getFirstName(), a.getLastName(), a.getNationality(), a.getWebSite(), a.getEmail())).toList();

		if (authors.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(authors);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado autores con lastName: " + lastName + ".");
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO,
			BindingResult result) {

		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");
		
		Optional<AuthorEntity> authorRecovered = authorServiceImp.readById(id);
		
		if (!authorRecovered.isPresent())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no se ha encontrado author con id: "+id+"!");		
		
		Optional<NationalityEntity> nationalityRecovered = nationalityServiceImp.readByName(authorDTO.getNationality().getName());
		
		boolean emptyFields = authorDTO.getFirstName().isBlank() | authorDTO.getLastName().isBlank()
				| authorDTO.getNationality() == null;

		boolean[] repeated = new boolean[3]; // 0: natinality, 1: webSite, 2:email, 
		String email = "sin_email";
		String webSite = "sin_website";
		
		repeated[0] = nationalityServiceImp.readAll().stream()
				.anyMatch(n -> n.getName().equalsIgnoreCase(authorDTO.getNationality().getName()));
		
		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");
		
		else if (!repeated[0])
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("no existe nationality: " + authorDTO.getNationality().getName() + ", ingrese una valida");
		
		else { // datos básicos correctos	
			
			if(authorServiceImp.findByFirstNameAndLastName(authorDTO.getFirstName(), authorDTO.getLastName()).isPresent())
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body(authorDTO.getFirstName() + " "+authorDTO.getLastName()+" ya existe, pruebe con otro nombre!");
			
			else if (authorDTO.getNationality().getLanguage() != null && !nationalityRecovered.get().getLanguage().equalsIgnoreCase(authorDTO.getNationality().getLanguage()))
				
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body("nationality: " + authorDTO.getNationality().getName() + ", no corresponde con language: "
								+ authorDTO.getNationality().getLanguage());			
			
			if(authorDTO.getWebSite() != null & authorDTO.getEmail() != null) {
				
				String correctEmail = "^[a-zA-ZñÑ]+[a-zA-ZñÑ0-9_&.-]+@[a-zA-ZñÑ]+[a-zA-Z0-9ñÑ_&-]+\\.[a-zA-ZñÑ]{2,3}$";
				String correctWebSite = "(https?://)?www\\.[A-Za-zñÑ]+[A-Za-z0-9ñÑ_&]+\\.[A-Za-zñÑ]{2,3}";
				
				repeated[1] = authorServiceImp.readAll().stream()
					    .filter(a -> !a.getWebSite().equalsIgnoreCase("sin_website"))
					    .anyMatch(a -> a.getWebSite().equalsIgnoreCase(authorDTO.getWebSite()));			
				
				repeated[2] = authorServiceImp.readAll().stream()
						.filter(a -> !a.getEmail().equalsIgnoreCase("sin_email"))
						.anyMatch(a -> a.getEmail().equalsIgnoreCase(authorDTO.getEmail()));			
					
					if(repeated[1])
					return ResponseEntity.status(HttpStatus.CONFLICT)
							.body(authorDTO.getWebSite() + " ya existe, pruebe con otro sitio web.");	
					else if(!authorDTO.getWebSite().matches(correctWebSite))
						return ResponseEntity.status(HttpStatus.CONFLICT)
								.body(authorDTO.getWebSite() + ", no tiene un formato de sitio web valido!");					
					
					if(repeated[2])
						return ResponseEntity.status(HttpStatus.CONFLICT)
								.body(authorDTO.getEmail() + " ya existe, pruebe con otro email.");	
						else if(!authorDTO.getEmail().matches(correctEmail))
							return ResponseEntity.status(HttpStatus.CONFLICT)
									.body(authorDTO.getEmail() + ", no tiene un formato de email valido!");	
					
					webSite = authorDTO.getWebSite();
					email = authorDTO.getEmail();
									
			}				
		}
		
		AuthorEntity authorUpdated = authorRecovered.get();

		authorUpdated.setFirstName(authorDTO.getFirstName());
		authorUpdated.setLastName(authorDTO.getLastName());		
		authorUpdated.setNationality(nationalityRecovered.get());
		authorUpdated.setWebSite(webSite);
		authorUpdated.setEmail(email);	
		
		authorServiceImp.update(authorUpdated);
		
				return ResponseEntity.status(HttpStatus.CREATED)
						.body(authorDTO.getFirstName() + " " + authorDTO.getLastName() + ", actualizado correctamente.");
		}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<AuthorEntity> recovered = authorServiceImp.readById(id);

		if (recovered.isPresent()) {
			authorServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("author con id: " + id + ", eliminado correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no se ha encontrado author con id: " + id + ".");
	}

}
