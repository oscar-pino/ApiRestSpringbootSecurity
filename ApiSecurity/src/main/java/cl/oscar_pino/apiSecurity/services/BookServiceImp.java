package cl.oscar_pino.apiSecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.oscar_pino.apiSecurity.entities.BookEntity;
import cl.oscar_pino.apiSecurity.repositories.IBookRepository;

@Service
public class BookServiceImp implements IDAO<BookEntity> {

	@Autowired
	private IBookRepository bookRepository;

	@Override
	public void create(BookEntity bookEntity) {

		bookRepository.save(bookEntity);
	}

	@Override
	public List<BookEntity> readAll() {

		return (List<BookEntity>) bookRepository.findAll();
	}

	@Override
	public Optional<BookEntity> readById(Long id) {

		return bookRepository.findById(id);
	}

	public Optional<BookEntity> readByEditorialName(String name) {

		return bookRepository.findByEditorialName(name);
	}

	public List<BookEntity> readByCategoryName(String name) {

		return bookRepository.findByCategoryName(name);
	}

	public Optional<BookEntity> readByAuthorFirstNameAndLastName(String firstName, String lastName) {

		return bookRepository.findByAuthorFirstNameAndLastName(firstName, lastName);
	}

	public Optional<BookEntity> readByTitle(String title) {

		return bookRepository.findByTitle(title);
	}
	
	public Optional<BookEntity> readByIsbm(String isbm) {

		return bookRepository.findByIsbm(isbm);
	}
	
	public List<BookEntity> readByQuantity(int quantity) {

		return bookRepository.findByQuantity(quantity);
	}
	
	public int getTotalNumberOfBooks() {
		
		return bookRepository.getTotalNumberOfBooks();
	}

	@Override
	public void update(BookEntity bookEntity) {

		bookRepository.save(bookEntity);
	}

	@Override
	public void deleteById(Long id) {

		bookRepository.deleteById(id);
	}

}
