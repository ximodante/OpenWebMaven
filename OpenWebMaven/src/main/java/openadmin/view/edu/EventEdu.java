package openadmin.view.edu;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class EventEdu {
	
	@Getter @Setter
	private EventTypeEdu name=null;
	
	@Getter @Setter
	private String classMethod=null; //class.method

	@Getter @Setter
	private List<String> lstAffectedFields=new ArrayList<String>(); // names of the affected fields
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
