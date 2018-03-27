package openadmin.web.edu;

import java.util.LinkedHashMap;

import lombok.Getter;
import lombok.Setter;
import openadmin.model.control.EntityAdm;


/**
 * Stores 
 * @author eduard
 *
 */
public class UserEntity {
	@Getter @Setter
	private EntityAdm entity;
	
	@Getter @Setter
	private LinkedHashMap<Long, UserProgram> Programs;
}
