package cl.oscar_pino.apiSecurity.entities;

import java.util.Set;

import cl.oscar_pino.apiSecurity.entities.enums.RoleEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "el campo no debe ser null")
    private RoleEnum roleEnum;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "roles_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<PermissionEntity> permissionList;

	public RoleEntity() {
		
	}

	public RoleEntity(RoleEnum roleEnum) {
		this.roleEnum = roleEnum;
	}

	public RoleEntity(RoleEnum roleEnum, Set<PermissionEntity> permissionList) {
		this(roleEnum);
		this.permissionList = permissionList;
	}
	
	public RoleEntity(Long id, RoleEnum roleEnum, Set<PermissionEntity> permissionList) {
		this(roleEnum, permissionList);
		this.id=id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleEnum getRoleEnum() {
		return roleEnum;
	}

	public void setRoleEnum(RoleEnum roleEnum) {
		this.roleEnum = roleEnum;
	}

	public Set<PermissionEntity> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(Set<PermissionEntity> permissionList) {
		this.permissionList = permissionList;
	}
}
