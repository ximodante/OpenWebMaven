package openadmin.util.configuration;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import lombok.Getter;
import lombok.Setter;
import openadmin.model.control.EntityAdm;
import openadmin.model.control.Role;

public class EntityRole implements Serializable{
	
	private static final long serialVersionUID = 01011101L;

	@Getter @Setter
	private EntityAdm entity;

	@Getter @Setter
	private Set<Role> role = new TreeSet<Role>();
	
	public EntityRole(EntityAdm entity, Set<Role> role) {
		
		this.entity = entity;
		this.role = role;
	}

}
