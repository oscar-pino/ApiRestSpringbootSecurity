package cl.oscar_pino.apiSecurity.dto;

import cl.oscar_pino.apiSecurity.entities.NationalityEntity;

public class AuthorDTO {

	private Long id;

	private String firstName;

	private String lastName;	

	private NationalityEntity nationality;

	private String webSite;

	private String email;

	public AuthorDTO() {
	}	
	
	public AuthorDTO(String firstName, String lastName, NationalityEntity nationality) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nationality = nationality;
	}
	
	public AuthorDTO(String firstName, String lastName, NationalityEntity nationality, String webSite, String email) {
		this(firstName, lastName, nationality);
		this.webSite = webSite;
		this.email = email;
	}
	
	public AuthorDTO(Long id, String firstName, String lastName, NationalityEntity nationality, String webSite, String email) {
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

	@Override
	public String toString() {
		return "AuthorDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", nationality="
				+ nationality + ", webSite=" + webSite + ", email=" + email + "]";
	}	
}
