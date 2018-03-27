package openadmin.view.edu;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PanelEdu {

	@Getter @Setter
	private String name=null; //
	
	@Getter @Setter 
	private String rsbName=null; // Resource Bundle Key
	

	@Getter @Setter
	private List<FieldEdu> lstFields=new ArrayList<FieldEdu>(); // detail of the fields included in the tab
	
	
	@Getter @Setter
	private List<ActionEdu> lstActions=new ArrayList<ActionEdu>(); // detail of the Actions included in the tab
	
	@Getter @Setter
	private List<EventEdu> lstEvents=new ArrayList<EventEdu>(); // detail of the Events included in the tab
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
