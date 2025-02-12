package cl.oscar_pino.apiSecurity.dto;

public class CategoryDTO {

	private Long id;

	private String name;	

	private String description;

	public CategoryDTO() {
	}
	
	public CategoryDTO(String name, String description) {
	
		this.name = name;
		this.description = description;
	}

	public CategoryDTO(Long id, String name, String description) {
		
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

	@Override
	public String toString() {
		return "CategoryDTO [id=" + id + ", name=" + name + ", description=" + description + "]";
	}	
}
