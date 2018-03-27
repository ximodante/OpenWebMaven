package openadmin.util.configuration.yaml;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class YAMLAllowed implements Serializable{

	private static final long serialVersionUID = 20180204L;
	
	@Getter @Setter
	private String role =null;        // Users
	
	@Getter @Setter
	private List<String> users= null;        // Roles
	

}
