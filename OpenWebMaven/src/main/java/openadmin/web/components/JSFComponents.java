package openadmin.web.components;

import java.io.Serializable;

import javax.faces.application.Application;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputnumber.InputNumber;


public class JSFComponents implements Serializable{

	
	private static final long serialVersionUID = 16081501L;
	
	//Labels
	public HtmlOutputLabel HtmlLabel01(String value){
		
		HtmlOutputLabel label = new HtmlOutputLabel();
		label.setValue(value);
		label.setStyleClass("txt14Gris");
		
		return label;
	
	}
	
	public HtmlOutputLabel labelTitle(String value){
		
		HtmlOutputLabel label = new HtmlOutputLabel();
		label.setValue(value);
		label.setStyleClass("textTitle");
		
		return label;
	
	}
	
	public HtmlOutputText HtmlOutputText01(String value){
		
		HtmlOutputText outputText = new HtmlOutputText();
		outputText.setValue(value);
		return outputText;
	
	}
	
	public HtmlInputTextarea textArea01(boolean readOnly, String value, Class<?> typeClass){
		
		Application app = FacesContext.getCurrentInstance().getApplication();
		
		HtmlInputTextarea textArea = new HtmlInputTextarea();
		
		textArea.setReadonly(readOnly);
		textArea.setCols(80);
		textArea.setRows(2);
		
		textArea.setValueExpression("value", app.getExpressionFactory().createValueExpression(
				FacesContext.getCurrentInstance().getELContext(), value, typeClass));
		
		return textArea;
		
	}

	//Input texte
	public InputText inputText01(int pLong, boolean readOnly, String value, Class<?> typeClass){
		
		Application app = FacesContext.getCurrentInstance().getApplication();
		
		InputText input = (InputText)app.createComponent(InputText.COMPONENT_TYPE);
				
		input.setMaxlength(pLong);
		
		input.setSize(pLong + 3);
		
		input.setReadonly(readOnly);
		
		input.setStyleClass("txtInputTp1");
		
		if (readOnly) input.setStyleClass("txtReadOnly");
		
		input.setValueExpression("value", app.getExpressionFactory().createValueExpression(
				FacesContext.getCurrentInstance().getELContext(), value, typeClass));
		
		return input;
	
	}
	
	//Input number
	public InputNumber inputNumber01(int pLong, boolean readOnly, String value, Class<?> typeClass){
		
		Application app = FacesContext.getCurrentInstance().getApplication();
		
		InputNumber input = (InputNumber)app.createComponent(InputText.COMPONENT_TYPE);
		
		input.setMaxlength(pLong);
		
		input.setSize(pLong + 3);
		
		input.setReadonly(readOnly);
		
		input.setStyleClass("txtInputTp1");	

		if (readOnly) input.setStyleClass("txtReadOnly");
		
		input.setValueExpression("value", app.getExpressionFactory().createValueExpression(
				FacesContext.getCurrentInstance().getELContext(), value, typeClass));
		
		
		return input;
	}
	
	public HtmlPanelGrid panelGrid(int column, String pStyleClass ){
		
		HtmlPanelGrid panelData = new HtmlPanelGrid();
		panelData.setStyleClass(pStyleClass);
		panelData.setColumns(column);
		
		return panelData;
	}
	
	public HtmlPanelGroup panelGroup(String pId, String pStyleClass){
		
		HtmlPanelGroup panelGroup = new HtmlPanelGroup();
		panelGroup.setId(pId);
		panelGroup.setStyleClass(pStyleClass);
		
		return panelGroup;
	}
}
