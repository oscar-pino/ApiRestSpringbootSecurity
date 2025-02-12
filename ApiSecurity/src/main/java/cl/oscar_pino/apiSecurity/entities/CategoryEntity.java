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
@Table(name = "categories")
public class CategoryEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(max = 100, message = "ingrese 100 caracteres como máximo")
	private String name;	
	
	@Column(unique=true)
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(max = 100, message = "ingrese 100 caracteres como máximo")
	private String description;

	public CategoryEntity() {
	}
	
	public CategoryEntity(String name, String description) {
	
		this.name = name;
		this.description = description;
	}

	public CategoryEntity(Long id, String name, String description) {
		
		this(name, description);
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}
}
