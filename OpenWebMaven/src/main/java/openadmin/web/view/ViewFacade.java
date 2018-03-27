package openadmin.web.view;

import org.primefaces.component.outputpanel.OutputPanel;

import openadmin.action.ObjectActionFacade;
import openadmin.model.control.MenuItem;
import openadmin.util.lang.LangTypeEdu;

//import openadmin.action.ObjectActionFacade;

public interface ViewFacade extends ObjectActionFacade{

	/**
	 * Get all the fields initialized
	 * @param base  object type Base
	 * @param pLstActions actions list
	 * @param pObjectDestination object destination
	 * */
	public void execute(MenuItem pMenuItem, LangTypeEdu pLang, Integer numberView);
	
	public OutputPanel getOutPanel();
}
