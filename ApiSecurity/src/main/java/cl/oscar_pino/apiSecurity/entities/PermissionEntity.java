package cl.oscar_pino.apiSecurity.entities;

import cl.oscar_pino.apiSecurity.entities.enums.PermissionEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "permissions")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "permission_name", updatable = false, unique = true, nullable = false)
    @Enumerated(EnumType.STRING)  
    @NotNull(message = "el campo no debe ser null")
    private PermissionEnum permissionEnum;

	public PermissionEntity() {
	}

	public PermissionEntity(PermissionEnum permissionEnum) {
		this.permissionEnum = permissionEnum;
	}

	public PermissionEntity(Long id, PermissionEnum permissionEnum) {
		this(permissionEnum);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public PermissionEnum getPermissionEnum() {
		return permissionEnum;
	}

	public void setPermissionEnum(PermissionEnum permissionEnum) {
		this.permissionEnum = permissionEnum;
	}  	
}
