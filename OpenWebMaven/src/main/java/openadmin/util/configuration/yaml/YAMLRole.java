package openadmin.util.configuration.yaml;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import openadmin.model.control.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString
public class YAMLRole implements Serializable{

	private static final long serialVersionUID = 20180204L;
	
	@Getter @Setter
	private List<String> names = null;      // List of Role 
	
	
	@Getter @Setter
	private List<String> programs =null;    // List of programs of the Role
	
}
