package openadmin.util.configuration.yaml;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class YAMLMenuItem implements Serializable{

	private static final long serialVersionUID = 20180204L;
	
	@Getter @Setter
	private String program = "";        // 
	
	@Getter @Setter
	private String description = "";        // 
	
	@Getter @Setter
	private String icon = null;        // 
	
	@Getter @Setter
	private String className = null;        // 
	
	@Setter 
	private boolean defaultActions = true;        // 
	
	@Getter @Setter
	private String viewType = "default";          // default, custom, action, submenu
	
	@Getter @Setter
	private List<YAMLAction> actions= null;        // Actions
	
	
	@Getter @Setter
	private List<YAMLMenuItem> menuItems= null;        // MenuItems
	
	public boolean isDefaultActions() {
		if (this.defaultActions) {
			String vType = this.viewType.toLowerCase().trim();
			if (vType.equals("submenu") || vType.equals("action") )
					this.defaultActions=false;
		}
		return this.defaultActions;
	}

}
