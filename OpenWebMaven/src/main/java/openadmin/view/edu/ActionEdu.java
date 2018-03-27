package openadmin.view.edu;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ActionEdu {
	
	@Getter @Setter
	private String classMethod=null; //class.method

	@Getter @Setter
	private List<String> lstAffectedFields=new ArrayList<String>(); // names of the affected fields
	
	@Getter @Setter
	private String icon=null; //Button icon
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
