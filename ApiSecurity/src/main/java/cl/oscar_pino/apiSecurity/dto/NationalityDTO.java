package cl.oscar_pino.apiSecurity.dto;

public class NationalityDTO {

	private Long id;	

	private String name;	

	private String language;

	public NationalityDTO() {
	}
	
	public NationalityDTO(String name) {
		this.name=name;
	}

	public NationalityDTO(String name, String language) {
		this(name);
		this.language = language;
	}
	
	public NationalityDTO(Long id, String name, String language) {
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

	@Override
	public String toString() {
		return "NationalityDTO [id=" + id + ", name=" + name + ", language=" + language + "]";
	}
}
