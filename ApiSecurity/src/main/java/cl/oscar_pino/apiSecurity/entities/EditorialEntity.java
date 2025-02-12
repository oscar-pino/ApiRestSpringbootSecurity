package cl.oscar_pino.apiSecurity.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "editorials")
public class EditorialEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 5, max = 20, message = "ingrese 5 caracteres como mínimo y 20 como máximo")
	private String name;

	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(max = 50, message = "ingrese 50 caracteres como máximo")
	private String address;

	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 8, max = 20, message = "ingrese 8 caracteres como mínimo y 20 como máximo")
	private String phone;

	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(max = 30, message = "ingrese 30 caracteres como máximo")
    @Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[-\\w@:%_\\+.~#?&//=]*)?$", 
             message = "el sitio web debe tener un formato válido")
	@Column(unique = true)
	private String webSite;

	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(max = 30, message = "ingrese 30 caracteres como máximo")
	@Email(message = "el email debe tener un formato válido")
	@Column(unique = true)
	private String email;

	@Column(name = "founding_date")
	@Past(message = "la fecha debe ser anterior a la fecha actual")
	private LocalDate foundingDate;

	public EditorialEntity() {
	}

	public EditorialEntity(String name, String address, String phone, String webSite, String email, LocalDate foundingDate) {
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.webSite = webSite;
		this.email = email;
		this.foundingDate = foundingDate;
	}

	public EditorialEntity(Long id, String name, String address, String phone, String webSite, String email,
			LocalDate foundingDate) {
		this(name, address, phone, webSite, email, foundingDate);
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFoundingDate() {
		return foundingDate;
	}

	public void setFoundingDate(LocalDate foundingDate) {
		this.foundingDate = foundingDate;
	}

	public Long getId() {
		return id;
	}
}
