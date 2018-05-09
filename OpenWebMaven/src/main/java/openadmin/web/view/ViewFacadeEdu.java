package openadmin.web.view;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.primefaces.component.outputpanel.OutputPanel;

//import openadmin.action.ContextActionEdu;
import openadmin.action.ObjectActionFacade;
import openadmin.model.control.Action;
//import openadmin.model.control.MenuItem;
import openadmin.util.lang.LangTypeEdu;

//import openadmin.action.ObjectActionFacade;

public interface ViewFacadeEdu extends ObjectActionFacade {

	/**
	 * Get all the fields initialized
	 * @param base  object type Base
	 * @param pLstActions actions list
	 * @param pObjectDestination object destination
	 * */
	public void execute(LangTypeEdu pLang, Integer numberView,  List<Action> lstAction)  
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException;
	
	public OutputPanel getOutPanel();
}
