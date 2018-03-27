package openadmin.view.edu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class FieldEdu implements Serializable{
	
	private static final long serialVersionUID = 20180203L;
	
	@Getter @Setter
	private String name=null;
	
	@Getter @Setter 
	private String rsbName=null; // Resource Bundle Key
	
	@Getter @Setter 
	private int length=0;
	
	@Getter @Setter 
	private int nChar=0;
	
	@Getter @Setter
	private FontEdu font;
	
	@Getter @Setter
	private EditorTypeEdu editor;
		
	@Getter @Setter
	private String mask;
	
	@Getter @Setter
	private boolean required=false;
	
	@Getter @Setter
	private boolean enabled=true;
		
	@Getter @Setter
	private boolean visible=true;
	
	@Getter @Setter
	private List<ActionEdu> lstActions=new ArrayList<ActionEdu>();
	
	@Getter @Setter
	private List<EventEdu> lstEvents=new ArrayList<EventEdu>();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
