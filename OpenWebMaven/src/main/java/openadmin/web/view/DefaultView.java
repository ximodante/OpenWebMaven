package openadmin.web.view;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.primefaces.component.outputpanel.OutputPanel;

import lombok.Getter;
import openadmin.model.Base;
import openadmin.model.control.MenuItem;
import openadmin.util.lang.LangTypeEdu;
import openadmin.util.reflection.CreateInstance;
import openadmin.web.components.JSFComponentsEdu;
import openadmin.action.ObjectAction;
import openadmin.annotations.Default;

public class DefaultView extends ObjectAction implements Serializable, ViewFacade{

	private static final long serialVersionUID = 23031001L;
	
	private LangTypeEdu lang;
	
	//Out container
	@Getter
	private OutputPanel outPanel;
	
	//private JSFComponents pJSFComponents = new JSFComponents();
	
	/**
	 * Generate view
	 * @param base object type Base to generate view
	 * @param pLstActions actions list to generate tool bar
	 */
	public void executeOld(MenuItem pMenuItem, LangTypeEdu pLang, Integer numberView){
		
		lang = pLang;
		boolean readOnly;
		
		//Object
		//Object obj = CreateInstance.instanceObject(pMenuItem.getClassName().getDescription());
		
		super.setBase((Base) CreateInstance.instanceObject(pMenuItem.getClassName().getDescription()));
		
		createExitComponent();
		
		outPanel.getChildren().add(JSFComponentsEdu.labelTitle(lang.msgLabels(null, super.getBase().getClass().getSimpleName())));
		
		HtmlPanelGroup panelGroup = JSFComponentsEdu.panelGroup("idDatatp1", "panelGroupTp1");
		
		HtmlPanelGrid panelView = JSFComponentsEdu.panelGrid(1, "panelGridTp1");	
		
		//Create view
		for (Field f: super.getBase().getClass().getDeclaredFields()){
			
			readOnly = false;
			
			String objectAction = "#{ctx.getView(" + numberView + ").base." + f.getName() + "}";
			
			System.out.println("Atributs: " + f.getName());
			//Component read only
			if (f.isAnnotationPresent(Default.class)){
				
				if (!f.getAnnotation(Default.class).visible()) continue;
				
				if (f.getAnnotation(Transient.class)!= null) continue;
				
				readOnly = f.getAnnotation(Default.class).readOnly();
				
			}
			
			//View of fields 
			//f.setAccessible(true);
			
			//Exclusions
			if (f.getName().equals ("serialVersionUID") ) continue;
			
			//Label
			panelView.getChildren().add (JSFComponentsEdu.HtmlLabel01(lang.msgLabels(super.getBase().getClass().getSimpleName(), f.getName())));
			
			/*****************************************  Field type String   *******************************************************/
			if (f.getType().getSimpleName().endsWith("String")){
				
				if(f.getAnnotation(Size.class).max()>=80){
					
					panelView.getChildren().add(JSFComponentsEdu.textArea01(readOnly, objectAction , f.getType()));
				
				}
				
				else 
				panelView.getChildren().add(new JSFComponentsEdu().inputText01(f.getAnnotation(Size.class).max(), readOnly, objectAction, f.getType()));
			}
			
			/****************************************   Field type Integer Or Double  **********************************************/
			else if (f.getType().getSimpleName().endsWith("Integer") || f.getType().getSimpleName().endsWith("Double") 
					|| f.getType().getSimpleName().endsWith("Long") || f.getType().getSimpleName().endsWith("Short")){
				
				System.out.println("Tipo de dato: " + f.getType());
				
				if (f.isAnnotationPresent(Size.class)){
					
					panelView.getChildren().add(JSFComponentsEdu.inputText01(f.getAnnotation(Size.class).max(), readOnly, objectAction, f.getType()));

					
				}else {
					
					//Component
					panelView.getChildren().add(JSFComponentsEdu.inputText01(9, readOnly, objectAction, f.getType()));
				
					
				}
				
				continue;
			}
			
			
			panelGroup.getChildren().add(panelView);
			outPanel.getChildren().add(panelGroup);
		}
		
	}
	
	/**
	 * Generate view
	 * @param base object type Base to generate view
	 * @param pLstActions actions list to generate tool bar
	 */
	public void execute(MenuItem pMenuItem, LangTypeEdu pLang, Integer numberView){
		
		lang = pLang;
		boolean readOnly=false;
		int defaultSize=9;
		int size=0;
		
		//Object
		//Object obj = CreateInstance.instanceObject(pMenuItem.getClassName().getDescription());
		
		super.setBase((Base) CreateInstance.instanceObject(pMenuItem.getClassName().getDescription()));
		
		createExitComponent();
		
		outPanel.getChildren().add(JSFComponentsEdu.labelTitle(lang.msgLabels(null, super.getBase().getClass().getSimpleName())));
		
		HtmlPanelGroup panelGroup = JSFComponentsEdu.panelGroup("idDatatp1", "panelGroupTp1");
		
		HtmlPanelGrid panelView = JSFComponentsEdu.panelGrid(1, "panelGridTp1");	
		
		//Create view
		for (Field f: super.getBase().getClass().getDeclaredFields()){
			System.out.println("---->Field:"+ f.getName() + ":" + f.getType().getCanonicalName());
			//Exclusions
			if (f.getName().equals ("serialVersionUID") ) continue;
			
			readOnly = false;
			size=defaultSize;
			String objectAction = "#{ctx.getView(" + numberView + ").base." + f.getName() + "}";
			
			System.out.println("Atributs: " + f.getName());
			
			//Component read only
			if (f.isAnnotationPresent(Default.class)){
				if (!f.getAnnotation(Default.class).visible()) continue;
				readOnly = f.getAnnotation(Default.class).readOnly();
				
			}
			//COmponent Size
			if (f.isAnnotationPresent(Size.class)) size=f.getAnnotation(Size.class).max();
			//Transient fields
			if (f.getAnnotation(Transient.class) != null) continue;
			
			
			//View of fields 
			//f.setAccessible(true);
			
			
			//Label
			panelView.getChildren().add (JSFComponentsEdu.HtmlLabel01(lang.msgLabels(super.getBase().getClass().getSimpleName(), f.getName())));
			
			
			// Component
			UIComponent component= JSFComponentsEdu.getDefaultComponent(f, objectAction, f.getType(), 2, 80, size, size, "txtInputTp1", readOnly);
			if (component !=null) panelView.getChildren().add(component);
			
			
			panelGroup.getChildren().add(panelView);
			outPanel.getChildren().add(panelGroup);
		}
		
	}
	
	private void createExitComponent(){
		
		outPanel = new OutputPanel();
		outPanel.setStyleClass("caixaViewTp1");
		outPanel.setId("idviewdefault");
		
	}
	
	/*
	public OutputPanel getOutPanel() {
		
		return outPanel;
	}
	*/
}
