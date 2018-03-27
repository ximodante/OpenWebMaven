package openadmin.util.lang;

import java.io.Serializable;
import java.text.MessageFormat;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


public class WebMessages implements Serializable{
	
	private static final long serialVersionUID = 16051001L;
	
	public static void messageError(String pMessage) {

        FacesContext.getCurrentInstance().addMessage("ERROR:", new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR: ", new LangTypeEdu().msgError(pMessage)));
	
		//FacesContext context = FacesContext.getCurrentInstance();
		//FacesMessage message = new FacesMessage();
		//message.setDetail(LangType.msgError(pMessage));
		//message.setSeverity(FacesMessage.SEVERITY_ERROR);
		//context.addMessage("ERROR:", message);
		
		//FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_INFO, LangType.msgError(pMessage), "Echoes in eternity.");
        
        //RequestContext.getCurrentInstance().showMessageInDialog(message2);
		
	}
	
	public static void messageErrorDao(String pMessage) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		message.setDetail(pMessage);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		context.addMessage("ERROR:", message);
		
	}

	public static void messageErrorParam(String pMessage, Object[] pParams) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		
		String param = MessageFormat.format( new LangTypeEdu().msgError(pMessage),pParams);
		message.setDetail(param);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		context.addMessage("ERROR:", message);
		
	}
	
	public static void messageInfo(String pMessage) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage();
		message.setDetail( new LangTypeEdu().msgActions(pMessage));
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		context.addMessage("INFO:", message);
		
		
	}
	
}
