package openadmin.web.edu;

import java.util.LinkedHashMap;

import lombok.Getter;
import lombok.Setter;
import openadmin.model.control.Program;

public class UserProgram {
	@Getter @Setter
	private Program program;
	
	@Getter @Setter
	private LinkedHashMap<Long, UserMenuItem> lstUserMenuItems;
}
