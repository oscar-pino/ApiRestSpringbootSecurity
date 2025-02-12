package cl.oscar_pino.apiSecurity.dto;

import java.time.LocalDate;

public class ReturnDTO {

	private Long id;	

	private LocalDate returnDate;

	private Float penalty;

	private int daysLate;

	public ReturnDTO() {
		this.penalty = 0f;
		this.daysLate = 0;
		this.returnDate = LocalDate.now().plusDays(10l);
	}	

	public ReturnDTO(LocalDate returnDate, Float penalty) {
		this.returnDate = returnDate;
		this.penalty = penalty;
	}
	
	public ReturnDTO(LocalDate returnDate, Float penalty, int daysLate) {
		this(returnDate, penalty);
		this.daysLate = daysLate;
	}
	
	public ReturnDTO(Long id, LocalDate returnDate, Float penalty, int daysLate) {
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

	@Override
	public String toString() {
		return "ReturnDTO [id=" + id + ", returnDate=" + returnDate + ", penalty=" + penalty + ", daysLate=" + daysLate
				+ "]";
	}	
}
