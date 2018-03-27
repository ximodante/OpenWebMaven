package openadmin.action;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import openadmin.model.Base;
import openadmin.util.reflection.SerialClone;


public class ObjectAction implements Serializable, ObjectActionFacade{
	
	private static final long serialVersionUID = 19091001L;
	
	@Getter
	private Base base;
	
	@Getter @Setter
	private ContextActionEdu ctx;
	
	//To edit
	private Base objOriginal;
	
	@Getter
	private String metodo;
	
	
	public void setBase(Base pBase) {
		
		objOriginal = SerialClone.clone(pBase);

		this.base =  pBase;
	
	} 

}
