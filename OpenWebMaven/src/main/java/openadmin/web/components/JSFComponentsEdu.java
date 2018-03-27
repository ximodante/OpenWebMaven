package openadmin.web.components;

import java.lang.reflect.Field;

import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;

import org.primefaces.component.inputnumber.InputNumber;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.selectbooleancheckbox.SelectBooleanCheckbox;

import openadmin.util.edu.JSFUtils;

public class JSFComponentsEdu {
	
	//Labels
		public static HtmlOutputLabel HtmlLabel01(String value){
			
			HtmlOutputLabel label = new HtmlOutputLabel();
			label.setValue(value);
			label.setStyleClass("txt14Gris");
			
			return label;
		
		}
		
		public static HtmlOutputLabel labelTitle(String value){
			
			HtmlOutputLabel label = new HtmlOutputLabel();
			label.setValue(value);
			label.setStyleClass("textTitle");
			
			return label;
		
		}
		
		public static HtmlOutputText HtmlOutputText01(String value){
			
			HtmlOutputText outputText = new HtmlOutputText();
			outputText.setValue(value);
			return outputText;
		
		}
		
		public static HtmlInputTextarea textArea01(boolean readOnly, String value, Class<?> typeClass){
			
			Application app = FacesContext.getCurrentInstance().getApplication();
			
			HtmlInputTextarea textArea = new HtmlInputTextarea();
			
			textArea.setReadonly(readOnly);
			textArea.setCols(80);
			textArea.setRows(2);
			textArea.setReadonly(readOnly);
			
			
			textArea.setValueExpression("value", app.getExpressionFactory().createValueExpression(
					FacesContext.getCurrentInstance().getELContext(), value, typeClass));
			
			
			return textArea;
			
		}

		//Input texte
		public static InputText inputText01(int pLong, boolean readOnly, String value, Class<?> typeClass){
			
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
		public static InputNumber inputNumber01(int pLong, boolean readOnly, String value, Class<?> typeClass){
			
			Application app = JSFUtils.getApplication();
			
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
		
		public static HtmlPanelGrid panelGrid(int column, String pStyleClass ){
			
			HtmlPanelGrid panelData = new HtmlPanelGrid();
			panelData.setStyleClass(pStyleClass);
			panelData.setColumns(column);
			
			return panelData;
		}
		
		public static HtmlPanelGroup panelGroup(String pId, String pStyleClass){
			
			HtmlPanelGroup panelGroup = new HtmlPanelGroup();
			panelGroup.setId(pId);
			panelGroup.setStyleClass(pStyleClass);
			
			return panelGroup;
		}
		
		/**
		 * To avoid boilerplate code to set Components attributes, here  is a default method for setting attributes
		 * @param pComponent
		 * @param pValue
		 * @param pClass
		 * @param nRows
		 * @param nCols
		 * @param pLength
		 * @param pSize
		 * @param pStyleClass
		 * @param readOnly
		 */
		public static void setComponentAttributes(UIComponent pComponent, 
				String pValue, Class<?> pClass, 
				int nRows,int nCols,  
				int pLength, int pSize, String pStyleClass, boolean readOnly) {
			
			// If componet is null, nothing is done
			if (pComponent!=null) {
				
				//1. TextArea -->(rows & columns, StyleClass, readOnly)
				if (HtmlInputTextarea.class.isAssignableFrom(pComponent.getClass())) {
					System.out.println("TextArea");
					if (nRows>0)((HtmlInputTextarea)pComponent).setRows(nRows);
					if (nCols>0)((HtmlInputTextarea)pComponent).setCols(nCols);
				
					((HtmlInputTextarea)pComponent).setReadonly(readOnly);
				
					if (readOnly) ((HtmlInputTextarea)pComponent).setStyleClass("txtReadOnly");
					else if (pStyleClass!=null && pStyleClass.trim().length()>0)((HtmlInputTextarea)pComponent).setStyleClass(pStyleClass);
				}
			
				//2. InputText-->(Length, Size, StyleClass, readOnly)
				if (HtmlInputText.class.isAssignableFrom(pComponent.getClass())) {
					System.out.println("ImputText");
					if (pLength>0)((HtmlInputText)pComponent).setMaxlength(pLength);
					if (pSize>0)((HtmlInputText)pComponent).setMaxlength(pSize + 3);
				
					((HtmlInputText)pComponent).setReadonly(readOnly);
				
					if (readOnly) ((HtmlInputText)pComponent).setStyleClass("txtReadOnly");
					else if (pStyleClass!=null && pStyleClass.trim().length()>0)((HtmlInputText)pComponent).setStyleClass(pStyleClass);
								
				}
				
				//Boolean Check Box
				if (HtmlSelectBooleanCheckbox.class.isAssignableFrom(pComponent.getClass())) {
					if (readOnly) ((HtmlSelectBooleanCheckbox)pComponent).setStyleClass("txtReadOnly");
					else if (pStyleClass!=null && pStyleClass.trim().length()>0)((HtmlSelectBooleanCheckbox)pComponent).setStyleClass(pStyleClass);
					((HtmlSelectBooleanCheckbox)pComponent).setValueExpression("value", JSFUtils.getValueExpression(pValue, pClass));
				
				} else {
				  //3. Common to every Component not Boolean Check Box 
				  pComponent.setValueExpression("value", JSFUtils.getValueExpression(pValue, pClass));
				}  
			}	
		}
		
		/**
		 * Creates a component for Default View
		 * @param f
		 * @param pValue
		 * @param pClass
		 * @param objectAction
		 * @param nRows
		 * @param nCols
		 * @param pLength
		 * @param pSize
		 * @param pStyleClass
		 * @param readOnly
		 * @return
		 */
		public static UIComponent getDefaultComponent(Field f, String pValue, Class<?> pClass, 
				int nRows,int nCols,  
				int pLength, int pSize, String pStyleClass, boolean readOnly) {
			
			UIComponent myComponent;
			
			System.out.println(f.getName() + "=" + f.getType().getSimpleName());
			String className=f.getType().getSimpleName().toLowerCase();
			
			switch (className) {
				case "string":
					if(pSize>=80) myComponent= new InputTextarea();
					else myComponent= new InputText();
					break;
				case "integer":
				case "double":
				case "long":
				case "short":
					myComponent= new InputNumber();
					break;
				case "boolean":
					myComponent=new SelectBooleanCheckbox();
					break;
				default:
					myComponent=null;
			}	
						
			if (myComponent!=null)System.out.println("Componente=" + myComponent.getClass().getName()); 
			
			setComponentAttributes(myComponent, pValue, pClass, 
					nRows,nCols,  
					pLength, pSize, pStyleClass, readOnly);
			return myComponent;
			
		}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
