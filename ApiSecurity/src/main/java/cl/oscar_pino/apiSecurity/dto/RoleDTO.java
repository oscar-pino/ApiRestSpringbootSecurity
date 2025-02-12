package cl.oscar_pino.apiSecurity.dto;

import java.util.Set;

import cl.oscar_pino.apiSecurity.entities.PermissionEntity;
import cl.oscar_pino.apiSecurity.entities.enums.RoleEnum;

public class RoleDTO {

	    private Long id;

	    private RoleEnum roleEnum;

	    private Set<PermissionEntity> permissionList;

		public RoleDTO() {
			
		}

		public RoleDTO(RoleEnum roleEnum) {
			this.roleEnum = roleEnum;
		}

		public RoleDTO(RoleEnum roleEnum, Set<PermissionEntity> permissionList) {
			this(roleEnum);
			this.permissionList = permissionList;
		}
		
		public RoleDTO(Long id, RoleEnum roleEnum, Set<PermissionEntity> permissionList) {
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

		@Override
		public String toString() {
			return "RoleDTO [id=" + id + ", roleEnum=" + roleEnum + ", permissionList=" + permissionList + "]";
		}  		
}
