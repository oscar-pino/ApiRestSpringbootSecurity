package cl.oscar_pino.apiSecurity.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "nationalities")
public class NationalityEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	

	@Column(unique = true, nullable = false)
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 4, max = 20, message = "ingrese 4 caracteres como mínimo y 20 como máximo")
	private String name;	

	@Column(nullable = false)	
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 4, max = 20, message = "ingrese 4 caracteres como mínimo y 20 como máximo")
	private String language;

	public NationalityEntity() {
	}
	
	public NationalityEntity(String name) {
		this.name = name;
	}

	public NationalityEntity(String name, String language) {
		this(name);
		this.language = language;
	}
	
	public NationalityEntity(Long id, String name, String language) {
		this(name, language);
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Long getId() {
		return id;
	}
}
