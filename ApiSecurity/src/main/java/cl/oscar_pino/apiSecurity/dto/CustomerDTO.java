package cl.oscar_pino.apiSecurity.dto;

import cl.oscar_pino.apiSecurity.entities.NationalityEntity;

public class CustomerDTO {

	private Long id;

	private String firstName;

	private String lastName;	

	private NationalityEntity nationality;

	private String address;

	private String email;

	private String phone;

	public CustomerDTO() {
	}	
	
	public CustomerDTO(String firstName, String lastName, NationalityEntity nationality, String address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.nationality = nationality;
		this.address = address;
	}

	public CustomerDTO(String firstName, String lastName, NationalityEntity nationality, String address, String email,
			String phone) {
		this(firstName, lastName, nationality, address);
		this.email = email;
		this.phone = phone;
	}
	
	public CustomerDTO(Long id, String firstName, String lastName, NationalityEntity nationality, String address, String email,
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

	@Override
	public String toString() {
		return "CustomerDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", nationality="
				+ nationality + ", address=" + address + ", email=" + email + ", phone=" + phone + "]";
	}	
}
