package openadmin.view.edu;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ViewEdu {

	@Getter @Setter
	private String name=null; //view name
	
	@Getter @Setter 
	private String rsbName=null; // Resource Bundle Key
	
	@Getter @Setter
	private List<PanelEdu> lstPanels=new ArrayList<PanelEdu>(); // detail of the panels included in the view
	
	@Getter @Setter
	private List<TabEdu> lstTabs=new ArrayList<TabEdu>(); // detail of the tabs included in the view
		
	@Getter @Setter
	private List<ActionEdu> lstActions=new ArrayList<ActionEdu>(); // detail of the actions included in the view
	
	@Getter @Setter
	private List<EventEdu> lstEvents=new ArrayList<EventEdu>(); // detail of the events included in the view
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
