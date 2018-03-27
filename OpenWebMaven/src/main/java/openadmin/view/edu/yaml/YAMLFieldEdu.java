package openadmin.view.edu.yaml;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import openadmin.view.edu.EditorTypeEdu;

@ToString
public class YAMLFieldEdu  implements Serializable{

	private static final long serialVersionUID = 20180204L;
	
	@Getter @Setter
	private String name=null; 
	
	@Getter @Setter
	private EditorTypeEdu editor= null; 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
