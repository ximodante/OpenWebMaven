package openadmin.util.lang;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import lombok.Setter;
import openadmin.util.configuration.TypeLanguages;

@Named (value = "lang")
@SessionScoped
public class LangTypeEdu implements Serializable {
	
	private static final long serialVersionUID = 2707201501L;
	private static final String MSG_GENERAL="openadmin.languages.generals_";
	private static final String MSG_TAB_VIEW="messagesTypeViewTabular_";
	private static final String MSG_LABEL="openadmin.languages.labels_";
	private static final String MSG_ERROR="openadmin.languages.errors_";
	private static final String MSG_VIEW_ERROR="messagesviewerror_";
	private static final String MSG_ACTION="openadmin.languages.actions_";
	private static final String MSG_DAO="openadmin.languages.messagesDao_";
	private static final String MSG_LOG="openadmin.languages.messagesLog_";
	
	
	
	
	private static Map<String,Object> countries;
	
	/** Field that contain the working language*/
	@Setter
	private String lang = "es";
	
	/** Field that contain the working language log*/
	private static TypeLanguages langLog = TypeLanguages.es;
	
	private String getResource(String pRsBundleName, String pKey, boolean returnNull){
		System.out.println("...Bundle:" + pRsBundleName + " clau a buscar:"+pKey );
		String result = pKey+" Not Found!"; 
		if (returnNull) result=null;
		ResourceBundle rsBundle= null;
		System.out.println(pRsBundleName+lang + "--->"+ pKey);
		try {
			rsBundle = ResourceBundle.getBundle(pRsBundleName + lang);
			result = rsBundle.getString(pKey);
		}catch (Exception ex) {
			//if (!returnNull )ex.printStackTrace();
			if (!returnNull )System.out.println("No trobat====="+pRsBundleName+lang + "--->"+ pKey);
		}
		System.out.println("...tornem:" +result );		
		return result;
	
	}
	
	public String msgGenerals(String pValue){ return getResource(MSG_GENERAL, pValue, false); }
	
	public String msgViewTabular(String pValue){return getResource(MSG_TAB_VIEW, pValue, false); }
	
	public String msgLabels(String pDomain, String pValue) {
		String result = getResource(MSG_LABEL, pDomain + "_" + pValue, true); 
		if (result==null) result=getResource(MSG_LABEL,pValue, false);
		return result;
	}
	
	public String msgError(String pValue){return getResource(MSG_ERROR, pValue, false); }
    	
	public String msgViewError(String pValue){return getResource(MSG_VIEW_ERROR, pValue, false); }
		
	public String msgActions(String pValue){return getResource(MSG_ACTION, pValue, false); }
	//message dao
	public String msgDao(String pValue){return getResource(MSG_DAO, pValue, false); }
	//Message log
	public String msgLog(String pValue){return getResource(MSG_LOG, pValue, false); }
	
	public void changeMessageLog(TypeLanguages pLanguage) {langLog = pLanguage;}
	
	public void changeLocale(String pLocale) {

		for (Map.Entry<String, Object> entry : countries.entrySet()) {

			if (entry.getValue().toString().equals(pLocale)) {

				setLang(pLocale);
				FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale)entry.getValue());

			}
		}

	}
	
	static {
		
		countries = new LinkedHashMap<String,Object>();
		countries.put("spain", new Locale("es")); 
		countries.put("valencia", new Locale("ca"));
	}
	
}
