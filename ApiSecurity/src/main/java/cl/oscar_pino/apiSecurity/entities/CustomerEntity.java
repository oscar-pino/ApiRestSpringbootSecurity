package cl.oscar_pino.apiSecurity.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "customers")
public class CustomerEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 3, max = 20, message = "ingrese 3 caracteres como mínimo y 20 como máximo")
	private String firstName;
	
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 3, max = 20, message = "ingrese 3 caracteres como mínimo y 20 como máximo")
	private String lastName;	
	
	@ManyToOne
	@JoinColumn(name = "nationality_id", nullable = false)
	private NationalityEntity nationality;

	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(max = 30, message = "ingrese 30 caracteres como máximo")
	private String address;
	
	@Column(unique=true)
	@Size(max = 30, message = "ingrese 30 caracteres como máximo")
	@Email(message = "el email debe tener un formato válido")
	private String email;
	
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 8, max = 20, message = "ingrese 8 caracteres como mínimo y 20 como máximo")
	@Column(unique=true)
	private String phone;

	public CustomerEntity() {
	}	
	
	public CustomerEntity(String firstName, String lastName, NationalityEntity nationality, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nationality = nationality;
		this.address = address;
	}

	public CustomerEntity(String firstName, String lastName, NationalityEntity nationality, String address, String email,
			String phone) {
		this(firstName, lastName, nationality, address);
		this.email = email;
		this.phone = phone;
	}
	
	public CustomerEntity(Long id, String firstName, String lastName, NationalityEntity nationality, String address, String email,
			String phone) {
		this(firstName, lastName, nationality, address, email, phone);
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getCustomerId() {
		return id;
	}

	public NationalityEntity getNationality() {
		return nationality;
	}

	public void setNationality(NationalityEntity nationality) {
		this.nationality = nationality;
	}
}
