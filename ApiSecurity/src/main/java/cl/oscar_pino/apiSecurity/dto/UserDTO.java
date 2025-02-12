package cl.oscar_pino.apiSecurity.dto;

import java.util.Set;

import cl.oscar_pino.apiSecurity.entities.RoleEntity;

public class UserDTO {

	private Long id;

	private String username;

	private String password;

	private boolean isEnabled;

	private boolean accountNoExpired;

	private boolean accountNoLocked;

	private boolean credentialNoExpired;

	private Set<RoleEntity> roles;

	public UserDTO() {

	}

	public UserDTO(String username, String password, Set<RoleEntity> roles) {

		this.username = username;
		this.password = password;
		this.isEnabled = true;
		this.accountNoExpired = true;
		this.accountNoLocked = true;
		this.credentialNoExpired = true;
		this.roles = roles;
	}

	public UserDTO(Long id, String username, String password, Set<RoleEntity> roles) {

		this(username, password, roles);
		this.id = id;
	}

	public UserDTO(Long id, String username, String password, Set<RoleEntity> roles, boolean isEnabled,
			boolean accountNoExpired, boolean accountNoLocked, boolean credentialNoExpired) {

		this(id, username, password, roles);
		this.isEnabled = isEnabled;
		this.accountNoLocked = accountNoLocked;
		this.accountNoExpired = accountNoExpired;
		this.credentialNoExpired = credentialNoExpired;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isAccountNoExpired() {
		return accountNoExpired;
	}

	public void setAccountNoExpired(boolean accountNoExpired) {
		this.accountNoExpired = accountNoExpired;
	}

	public boolean isAccountNoLocked() {
		return accountNoLocked;
	}

	public void setAccountNoLocked(boolean accountNoLocked) {
		this.accountNoLocked = accountNoLocked;
	}

	public boolean isCredentialNoExpired() {
		return credentialNoExpired;
	}

	public void setCredentialNoExpired(boolean credentialNoExpired) {
		this.credentialNoExpired = credentialNoExpired;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", password=" + password + ", isEnabled=" + isEnabled
				+ ", accountNoExpired=" + accountNoExpired + ", accountNoLocked=" + accountNoLocked
				+ ", credentialNoExpired=" + credentialNoExpired + ", roles=" + roles + "]";
	}
}
