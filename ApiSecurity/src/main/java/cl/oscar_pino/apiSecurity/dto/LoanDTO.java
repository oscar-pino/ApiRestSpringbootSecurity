package cl.oscar_pino.apiSecurity.dto;

import java.time.LocalDate;
import java.util.Set;

import cl.oscar_pino.apiSecurity.entities.BookEntity;
import cl.oscar_pino.apiSecurity.entities.CustomerEntity;
import cl.oscar_pino.apiSecurity.entities.ReturnEntity;

public class LoanDTO {

	private Long id;
	
	private Set<BookEntity> books;

	private CustomerEntity customer;

	private LocalDate loanDate;

	private ReturnEntity returns;

	public LoanDTO() {
		
		this.loanDate = LocalDate.now();
	}

	public LoanDTO(Set<BookEntity> books, CustomerEntity customer, LocalDate loanDate) {
		this.books = books;
		this.customer = customer;
		this.loanDate = loanDate;
	}
	
	public LoanDTO(Set<BookEntity> books, CustomerEntity customer, LocalDate loanDate, ReturnEntity returns) {
		this(books, customer, loanDate);
		this.returns=returns;
	}
	
	public LoanDTO(Long id, Set<BookEntity> books, CustomerEntity customer, LocalDate loanDate, ReturnEntity returns) {
		this(books, customer, loanDate, returns);
		this.id=id;
	}

	public LocalDate getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(LocalDate loanDate) {
		this.loanDate = loanDate;
	}

	public Long getId() {
		return id;
	}

	public Set<BookEntity> getBooks() {
		return books;
	}

	public void setBooks(Set<BookEntity> books) {
		this.books = books;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public ReturnEntity getReturnEntity() {

		return returns;
	}

	public void setReturnEntity(ReturnEntity returns) {

		this.returns = returns;
	}

	@Override
	public String toString() {
		return "LoanDTO [id=" + id + ", books=" + books + ", customer=" + customer + ", loanDate=" + loanDate
				+ ", returns=" + returns + "]";
	}
}
