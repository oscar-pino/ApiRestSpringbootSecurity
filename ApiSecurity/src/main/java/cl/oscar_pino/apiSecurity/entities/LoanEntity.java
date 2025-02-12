package cl.oscar_pino.apiSecurity.entities;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "loans")
public class LoanEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "loans_books", joinColumns = @JoinColumn(name = "loan_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
	private Set<BookEntity> books;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private CustomerEntity customer;

	@PastOrPresent(message = "la fecha ingresada, no puede ser para futuro")
	@Column(nullable = false)
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate loanDate;

	@OneToOne(targetEntity = ReturnEntity.class)
	@JoinColumn(name = "return_id", nullable = false)
	private ReturnEntity returns;

	public LoanEntity() {

		this.loanDate = LocalDate.now();
	}

	public LoanEntity(Set<BookEntity> books, CustomerEntity customer, LocalDate loanDate) {
		this.books = books;
		this.customer = customer;
		this.loanDate = loanDate;
	}

	public LoanEntity(Set<BookEntity> books, CustomerEntity customer, LocalDate loanDate, ReturnEntity returns) {
		this(books, customer, loanDate);
		this.returns = returns;
	}

	public LoanEntity(Long id, Set<BookEntity> books, CustomerEntity customer, LocalDate loanDate,
			ReturnEntity returns) {
		this(books, customer, loanDate, returns);
		this.id = id;
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
}
