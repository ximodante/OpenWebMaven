package openadmin.util.configuration.yaml;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;;

@ToString
public class YAMLAction implements Serializable{

	private static final long serialVersionUID = 20180204L;
	
	@Getter @Setter
	private String name =null;        // 
	
	@Getter @Setter
	private String icon =null;        // 
	
	@Getter @Setter
	private Integer group =0;        // 
		
	@Getter @Setter
	private List<String> roles= null;        // Roles
	

}
