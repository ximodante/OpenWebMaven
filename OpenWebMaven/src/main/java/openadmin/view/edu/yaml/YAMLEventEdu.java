package openadmin.view.edu.yaml;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import openadmin.view.edu.ElementTypeEdu;
import openadmin.view.edu.EventTypeEdu;

@ToString
public class YAMLEventEdu  implements Serializable{

	private static final long serialVersionUID = 20180204L;

	@Getter @Setter
	private EventTypeEdu event=null; 
	
	@Getter @Setter
	private String element= "form"; 
	
	@Getter @Setter
	private ElementTypeEdu elementType= ElementTypeEdu.FORM; 
		
	@Getter @Setter
	private String action= null; 
	
	@Getter @Setter
	private List<String> fields= null; 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
