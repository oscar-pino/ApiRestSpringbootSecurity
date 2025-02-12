package cl.oscar_pino.apiSecurity.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "authors")
public class AuthorEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 3, max = 20, message = "ingrese 3 caracteres como mínimo")
	private String firstName;
	
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 3, max = 20, message = "ingrese 3 caracteres como mínimo")
	private String lastName;	
	
	@ManyToOne
	@JoinColumn(name = "nationality_id", nullable = false)
	@NotNull(message = "el campo no debe ser null")
	private NationalityEntity nationality;
	
	@Column(name = "web_site")
	@Size(max = 30, message = "ingrese 30 caracteres como máximo")
    //@Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[-\\w@:%_\\+.~#?&//=]* | sin_website)?$", message = "el sitio web debe tener un formato válido")
	private String webSite;
	
	@Size(max = 30, message = "ingrese 30 caracteres como máximo")
	//@Email(message = "el email debe tener un formato válido")
	//@Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[-\\w@:%_\\+.~#?&//=]* | sin_email)?$", message = "el email debe tener un formato válido")
	private String email;
	
	public AuthorEntity() {
	}
	
	public AuthorEntity(String firstName, String lastName, NationalityEntity nationality) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nationality = nationality;
	}

	public AuthorEntity(String firstName, String lastName, NationalityEntity nationality, String webSite, String email) {
		this(firstName, lastName, nationality);
		this.webSite = webSite;
		this.email = email;
	}
	
	public AuthorEntity(Long id, String firstName, String lastName, NationalityEntity nationality, String webSite, String email) {
		this(firstName, lastName, nationality, webSite, email);		
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}	

	public void setId(Long id) {
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
	
	
	public NationalityEntity getNationality() {
		return nationality;
	}

	public void setNationality(NationalityEntity nationality) {
		this.nationality = nationality;
	}	
}
