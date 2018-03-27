package openadmin.util.configuration.yaml;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import openadmin.model.control.User;

@ToString
public class YAMLEntityAdm implements Serializable{

	private static final long serialVersionUID = 20180204L;
	
	@Getter @Setter
	private String name=null;
	
	@Getter @Setter
	private String conn=null;
	
	@Getter @Setter
	private String icon=null;
	
	@Getter @Setter
	private String theme=null;
	
	@Getter @Setter
	private List<YAMLProgram> programs= null;        // Users

}
