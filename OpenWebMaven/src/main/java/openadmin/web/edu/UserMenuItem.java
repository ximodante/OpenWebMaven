package openadmin.web.edu;

import java.util.LinkedHashMap;

import lombok.Getter;
import lombok.Setter;
import openadmin.model.control.Action;
import openadmin.model.control.MenuItem;

/**
 * Menu Items available for a user in a program
 * @author eduard
 *
 */
public class UserMenuItem {
	@Getter @Setter
	private MenuItem menuItem;
	
	@Getter @Setter
	private LinkedHashMap<Long, Action> lstActions;
}
