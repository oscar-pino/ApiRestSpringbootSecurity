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

import cl.oscar_pino.apiSecurity.dto.BookDTO;
import cl.oscar_pino.apiSecurity.entities.AuthorEntity;
import cl.oscar_pino.apiSecurity.entities.BookEntity;
import cl.oscar_pino.apiSecurity.entities.CategoryEntity;
import cl.oscar_pino.apiSecurity.entities.EditorialEntity;
import cl.oscar_pino.apiSecurity.services.AuthorServiceImp;
import cl.oscar_pino.apiSecurity.services.BookServiceImp;
import cl.oscar_pino.apiSecurity.services.CategoryServiceImp;
import cl.oscar_pino.apiSecurity.services.EditorialServiceImp;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookServiceImp bookServiceImp;

	@Autowired
	private EditorialServiceImp editorialServiceImp;

	@Autowired
	private CategoryServiceImp categoryServiceImp;

	@Autowired
	private AuthorServiceImp authorServiceImp;

	@PostMapping("/create") public ResponseEntity<?> create(@Valid @RequestBody
	  BookDTO bookDTO, BindingResult result) {
	  
			if (result.hasErrors())
				return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");

			Optional<EditorialEntity> editorialRecovered = editorialServiceImp
					.readByName(bookDTO.getEditorial().getName());

			Optional<CategoryEntity> categoryRecovered = categoryServiceImp.readByName(bookDTO.getCategory().getName());

			Optional<AuthorEntity> authorRecovered = authorServiceImp
					.findByFirstNameAndLastName(bookDTO.getAuthor().getFirstName(), bookDTO.getAuthor().getLastName());

			boolean emptyFields = bookDTO.getTitle().isBlank() | bookDTO.getIsbm().isBlank()
					| bookDTO.getEditorial() == null | bookDTO.getCategory() == null | bookDTO.getAuthor() == null;

			if (emptyFields)
				return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");

			else if (bookServiceImp.readByIsbm(bookDTO.getIsbm()).isPresent())
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body("debe ingresar otro isbm, " + bookDTO.getIsbm() + " ya existe!");
			
			else if (bookServiceImp.readByTitle(bookDTO.getTitle()).isPresent())
				return ResponseEntity.status(HttpStatus.CONFLICT)
						.body("debe ingresar otro title, " + bookDTO.getTitle() + " ya existe!");
			
			else if (bookDTO.getQuantity() < 0)
				return ResponseEntity.status(HttpStatus.CONFLICT).body("la cantidad ingresada no debe ser menor a cero!");
			
			else if (!editorialRecovered.isPresent())
				return ResponseEntity.status(HttpStatus.CONFLICT).body("debe ingresar una editorial valida");
			
			else if (!categoryRecovered.isPresent())
				return ResponseEntity.status(HttpStatus.CONFLICT).body("debe ingresar una categoria valida");
			
			else if (!authorRecovered.isPresent())
				return ResponseEntity.status(HttpStatus.CONFLICT).body("debe ingresar una categoria valida");			

			bookServiceImp.create(new BookEntity(bookDTO.getTitle(), bookDTO.getIsbm(), bookDTO.getQuantity(),
					editorialRecovered.get(), categoryRecovered.get(), authorRecovered.get()));
			
			return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO.getTitle() + " creado correctamente.");
	  }

	@GetMapping("/read/all")
	public ResponseEntity<?> readAll() {

		List<BookDTO> books = bookServiceImp.readAll().stream().map(b -> new BookDTO(b.getId(), b.getTitle(),
				b.getIsbm(), b.getQuantity(), b.getEditorial(), b.getCategory(), b.getAuthor())).toList();

		if (books.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(books);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se han encontrado libros.");
	}
	
	@GetMapping("/read/id/{id}")
	public ResponseEntity<?> readByEditorialName(@PathVariable Long id) {

		Optional<BookEntity> recovered = bookServiceImp.readById(id);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered);

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se han encontrado libro con id: " + id);
	}

	@GetMapping("/read/editorial_name/{name}")
	public ResponseEntity<?> readByEditorialName(@PathVariable String name) {

		Optional<BookEntity> recovered = bookServiceImp.readByEditorialName(name);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered);

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se han encontrado libro con nombre de editorial: " + name);
	}

	@GetMapping("/read/category_name/{name}")
	public ResponseEntity<?> readByCategoryName(@PathVariable String name) {

		List<BookEntity> recovered = bookServiceImp.readByCategoryName(name);

		if (recovered.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered);

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se han encontrado libro con nombre de categoria: " + name);
	}
	
	@GetMapping("/read/quantity/{quantity}")
	public ResponseEntity<?> readByEditorialName(@PathVariable int quantity) {

		List<BookEntity> books = bookServiceImp.readByQuantity(quantity);

		if (books.size() > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(books);

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se han encontrado libros con cantidad de: " + quantity);
	}
	
	@GetMapping("/number_of_books")
	public ResponseEntity<?> getTotalNumberOfBooks() {

		int quantity = bookServiceImp.getTotalNumberOfBooks();

		if (quantity > 0)
			return ResponseEntity.status(HttpStatus.ACCEPTED).body("cantidad de libros totales registrados: "+quantity);

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se han encontrado libros registrados!");
	}

	@GetMapping("/read/full_name_author/{firstName}{lastName}")
	public ResponseEntity<?> readByAuthorFirstNameAndLastName(@PathVariable String firstName,
			@PathVariable String lastName) {

		Optional<BookEntity> recovered = bookServiceImp.readByAuthorFirstNameAndLastName(firstName, lastName);

		if (recovered.isPresent())
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(recovered);

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body("no se han encontrado libro con full name de author: " + firstName.concat("-").concat(lastName));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody BookDTO bookDTO,
			BindingResult result){		
		
		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("ha ocurrido un error!");
		
		Optional<BookEntity> bookRecovered = bookServiceImp.readById(id);
		boolean[] repeated = new boolean[2]; // 0: isbm, 1: title
		
		
		if (!bookRecovered.isPresent())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("no existe book con id: "+id);

		Optional<EditorialEntity> editorialRecovered = editorialServiceImp
				.readByName(bookDTO.getEditorial().getName());

		Optional<CategoryEntity> categoryRecovered = categoryServiceImp.readByName(bookDTO.getCategory().getName());

		Optional<AuthorEntity> authorRecovered = authorServiceImp
				.findByFirstNameAndLastName(bookDTO.getAuthor().getFirstName(), bookDTO.getAuthor().getLastName());

		boolean emptyFields = bookDTO.getTitle().isBlank() | bookDTO.getIsbm().isBlank()
				| bookDTO.getEditorial() == null | bookDTO.getCategory() == null | bookDTO.getAuthor() == null;

		if (emptyFields)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("faltan datos.");

		repeated[0] = bookServiceImp.readAll().stream().filter(b -> !bookDTO.getIsbm().equals(b.getIsbm())).anyMatch(b -> b.getIsbm().equalsIgnoreCase(bookDTO.getIsbm()));
		repeated[1] = bookServiceImp.readAll().stream().filter(b -> !bookDTO.getTitle().equals(b.getTitle())).anyMatch(b -> b.getTitle().equalsIgnoreCase(bookDTO.getTitle()));
		
		
		if (repeated[0])
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("no se puede modificar el isbm, " + bookDTO.getIsbm());
		
		else if (repeated[1])
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("no se puede modificar el title, " + bookDTO.getTitle());
		
		else if (bookDTO.getQuantity() < 0)
			return ResponseEntity.status(HttpStatus.CONFLICT).body("la cantidad ingresada no debe ser menor a cero!");
		
		else if (!editorialRecovered.isPresent())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("debe ingresar una editorial valida");
		
		else if (!categoryRecovered.isPresent())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("debe ingresar una categoria valida");
		
		else if (!authorRecovered.isPresent())
			return ResponseEntity.status(HttpStatus.CONFLICT).body("debe ingresar una categoria valida");
		
		BookEntity book = bookRecovered.get();
		book.setAuthor(authorRecovered.get());
		book.setCategory(categoryRecovered.get());
		book.setEditorial(editorialRecovered.get());
		book.setIsbm(bookDTO.getIsbm());
		book.setQuantity(bookDTO.getQuantity());
		book.setTitle(bookDTO.getTitle());

		bookServiceImp.create(book);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO.getTitle() + " actualizado correctamente.");
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		Optional<BookEntity> recovered = bookServiceImp.readById(id);

		if (recovered.isPresent()) {
			bookServiceImp.deleteById(id);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body("book con id: " + id + ", eliminado correctamente.");
		} else
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("no se ha encontrado book con id: " + id + ".");
	}	
}
