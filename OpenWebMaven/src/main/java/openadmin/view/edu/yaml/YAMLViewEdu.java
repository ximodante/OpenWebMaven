package openadmin.view.edu.yaml;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import openadmin.util.edu.CollectionUtilsEdu;
import openadmin.view.edu.ElementTypeEdu;

/**
 * A custom view
 * @author eduard
 *
 */
@ToString
public class YAMLViewEdu implements Serializable{

	private static final long serialVersionUID = 20180204L;

	@Getter @Setter
	private String name=null; //view name
	
	@Getter @Setter
	private List<YAMLPanelEdu> panels= null; // panels in the view
	
	@Getter @Setter
	private List<YAMLTabEdu> tabs= null;     // tabs in the view
	
	@Getter @Setter
	private List<YAMLFieldEdu> fields= null; // all the fields in the view
	
	//----- Events
	@Getter @Setter
	private List<YAMLEventEdu> formEvents= null; 
	
	@Getter @Setter
	private List<YAMLEventEdu> panelEvents= null; 
	
	@Getter @Setter
	private List<YAMLEventEdu> tabEvents= null; 
	
	@Getter @Setter
	private List<YAMLEventEdu> fieldEvents= null; 
	
	//----- Actions
	@Getter @Setter
	private List<YAMLActionEdu> formActions= null; 
	
	@Getter @Setter
	private List<YAMLActionEdu> panelActions= null; 
	
	@Getter @Setter
	private List<YAMLActionEdu> tabActions= null; 
	
	@Getter @Setter
	private List<YAMLActionEdu> fieldActions= null; 
	
	
	
	// Procedures
	
	/******************************************************************
	 * 0. Set the correct values to collections
	 * @throws IntrospectionException 
	 * @throws RuntimeException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * 
	 ******************************************************************/
	public void serDefaultInfo() throws IllegalAccessException, InvocationTargetException, RuntimeException, IntrospectionException {
		
		//1. Panels actions and events
		String[] attrNames = {"elementType"};
		Object[] attrValues1= {ElementTypeEdu.PANEL };
		CollectionUtilsEdu.updateListFields(panelActions, attrNames, attrValues1);
		CollectionUtilsEdu.updateListFields(panelEvents,  attrNames, attrValues1);
		
		//2. Tabs actions and events
		Object[] attrValues2= {ElementTypeEdu.TAB };
		CollectionUtilsEdu.updateListFields(tabActions, attrNames, attrValues2);
		CollectionUtilsEdu.updateListFields(tabEvents,  attrNames, attrValues2);
		
		//3. Fields actions and events
		Object[] attrValues3= {ElementTypeEdu.FIELD };
		CollectionUtilsEdu.updateListFields(fieldActions, attrNames, attrValues3);
		CollectionUtilsEdu.updateListFields(fieldEvents,  attrNames, attrValues3);
		 
	}
	
	
	/******************************************************************
	 * 1. FIELDS:
	 ******************************************************************/
	
	/**
	 * Get additional info form a field
	 * @param fieldName
	 * @return
	 */
	public Optional<YAMLFieldEdu> getFieldInfo(String fieldName) {
		return fields.stream()
				.filter(e-> e.getName().equalsIgnoreCase(fieldName))
				.findFirst();
	}
	
	/**
	 * Return field events of a field
	 * @param fieldName
	 * @return
	 */
	public List<YAMLEventEdu> getFieldEvents (String fieldName) {
		return fieldEvents.stream()
				.filter(e-> e.getElement().equalsIgnoreCase(fieldName))
				.collect(Collectors.toList());
	}
	/**
	 * Return actions for the field
	 * @param fieldName
	 * @return
	 */
	public List<YAMLActionEdu> getFieldActions (String fieldName) {
		return fieldActions.stream()
				.filter(e-> e.getElement().equalsIgnoreCase(fieldName))
				.collect(Collectors.toList());
	}
	
	/******************************************************************
	 * 2. PANELS
	 ******************************************************************/
	/**
	 * Return panel events of a panel
	 * @param panelName
	 * @return
	 */
	public List<YAMLEventEdu> getPanelEvents (String panelName) {
		return panelEvents.stream()
				.filter(e-> e.getElement().equalsIgnoreCase(panelName))
				.collect(Collectors.toList());
	}
	/**
	 * Return actions for the panel
	 * @param panelName
	 * @return
	 */
	public List<YAMLActionEdu> getPanelActions (String panelName) {
		return panelActions.stream()
				.filter(e-> e.getElement().equalsIgnoreCase(panelName))
				.collect(Collectors.toList());
	}
	

	/******************************************************************
	 * 3. TABS
	 ******************************************************************/
	/**
	 * Return tab events of a tab
	 * @param fieldName
	 * @return
	 */
	public List<YAMLEventEdu> getTabEvents (String tabName) {
		return tabEvents.stream()
				.filter(e-> e.getElement().equalsIgnoreCase(tabName))
				.collect(Collectors.toList());
	}
	/**
	 * Return actions for the panel
	 * @param fieldName
	 * @return
	 */
	public List<YAMLActionEdu> getTabActions (String tabName) {
		return tabActions.stream()
				.filter(e-> e.getElement().equalsIgnoreCase(tabName))
				.collect(Collectors.toList());
	}
	
	/******************************************************************
	 * 4. FORM: 
	 ******************************************************************/
	// We only need the getters of
	// formEvents and formActions
	//

}
