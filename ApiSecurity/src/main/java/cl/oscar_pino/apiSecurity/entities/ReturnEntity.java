package cl.oscar_pino.apiSecurity.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "returns")
public class ReturnEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	@Future(message = "La fecha de devolución debe ser en el futuro.")
	@Column(nullable = false)
	private LocalDate returnDate;
	
	@Column(precision = 3, nullable = false)
	@NotNull(message = "el valor no puede ser nulo")
	@DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor o igual a 0")	
	private Float penalty;
	
	@Min(value = 0, message = "El valor ingresado no puede ser menor o igual a cero")
	@Column(nullable = false)
	private int daysLate;

	public ReturnEntity() {
		this.penalty = 0f;
		this.daysLate = 0;
		this.returnDate = LocalDate.now().plusDays(10l);
	}	

	public ReturnEntity(LocalDate returnDate, Float penalty) {
		this.returnDate = returnDate;
		this.penalty = penalty;
	}
	
	public ReturnEntity(LocalDate returnDate, Float penalty, int daysLate) {
		this(returnDate, penalty);
		this.daysLate = daysLate;
	}
	
	public ReturnEntity(Long id, LocalDate returnDate, Float penalty, int daysLate) {
		this(returnDate, penalty, daysLate);
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id=id;
	}
	
	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public Float getPenalty() {
		return penalty;
	}

	public void setPenalty(Float penalty) {
		this.penalty = penalty;
	}		
	
	public int getDaysLate() {
		return daysLate;
	}

	public void setDaysLate(int daysLate) {
		this.daysLate = daysLate;
	}
}
