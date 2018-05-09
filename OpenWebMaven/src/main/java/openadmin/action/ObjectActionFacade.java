package openadmin.action;

import openadmin.model.Base;

public interface ObjectActionFacade {
	
	public Base getBase();
	public void setBase(Base pBase);
	public ContextActionEdu getCtx();
	public void setCtx(ContextActionEdu ctx);
	public String getMetodo();
	
}
