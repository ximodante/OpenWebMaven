package openadmin.web.components;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.toolbar.Toolbar;
import org.primefaces.component.toolbar.ToolbarGroup;

import openadmin.model.control.Access;
import openadmin.model.control.EntityAdm;
//import openadmin.model.control.Action;
//import openadmin.model.control.DatabaseConnection;
import openadmin.model.control.Role;
import openadmin.util.lang.LangTypeEdu;


public class PFMenuBar implements Serializable{
	
	private static final long serialVersionUID = 9081501L;
	
	private LangTypeEdu langType;
	
	
	public PFMenuBar (LangTypeEdu pLangType) {
		
		langType = pLangType;
	}
	
	/**
	 * <desc>class that generates the generic menu</desc>
	 * @author 			Alfred Oliver
	 * @param head    	menu name 
	 * @param lst  		list of items
	 * @return        	HtmlDropDownMenu
	 */
	public static DefaultSubMenu subMenuSimple(String cap, List <String[]> lst){
		
		// El String te tres entrades valor, acci� i icona
		// Si el valor d'acci� �s 0 �s un grup
		//Per finalizar el grup l'acci� val 1
		
		DefaultSubMenu subMenu = new DefaultSubMenu(cap);
		DefaultSubMenu gr = null;
		DefaultMenuItem item = null;
		
		for (String[] array: lst){
			
			//If only is item
			if (!array[1].equals("0") && (gr == null)){
				
				item = new DefaultMenuItem(array[0]);
				item.setIcon(array[2]);
				item.setCommand(array[1]);
				subMenu.addElement(item);
			}
			
			// if is item of group
			else if (!array[1].equals("1") && (gr != null)){
				
				item = new DefaultMenuItem(array[0]);
				item.setIcon(array[2]);
				item.setCommand(array[1]);
				gr.addElement(item);			
			}
			
			//Tancar grup submenu
			else if (array[1].equals("1")){
				
				subMenu.addElement(gr);
				gr = null;
			}
			
			//create group
			else if (array[1].equals("0")){
				
				gr = new DefaultSubMenu();
				gr.setLabel(array[0]);
				
			} 
			
			
		}
		 
		//System.out.println("submenu simple: " + cap);
		return subMenu;
	}
	
	public DefaultSubMenu menuEntities(String head, Set<EntityAdm> listEntities){
		
		DefaultSubMenu subMenu = new DefaultSubMenu(langType.msgGenerals(head));
		
		for (EntityAdm enti: listEntities) {
			System.out.println("Entitat..=" + enti.getDescription());
			String pValorItem = langType.msgGenerals(enti.getDescription());
			DefaultMenuItem item = new DefaultMenuItem(pValorItem); 
			item.setIcon(enti.getIcon());
			item.setId("" + enti.getId());
			//item.setCommand("#{main.loadMenuItems(" + Integer.parseInt(item.getId()) + ",'"  + item.getValue() + "')}");
			subMenu.addElement(item);
		}
		
		
		return subMenu;
	}
	
	/**
	 * <desc>class that generates the applications submenu</desc>
	 * @author 			Alfred Oliver
	 * @param head    	submenu name 
	 * @param listEp  	programs that the user is allowed for each entity
	 * @return        	HtmlDropDownMenu*/
	 
	public DefaultSubMenu menuPrograms(String head, List<Access> listProgram){
		
		DefaultSubMenu subMenu = new DefaultSubMenu(langType.msgGenerals(head));
		
		for (Access ac: listProgram){
				 
				//System.out.println("programa: " + ac.getProgram().getDescription());
			
				//Create menu item
				DefaultMenuItem item = new DefaultMenuItem(langType.msgGenerals(ac.getProgram().getDescription())); 
				item.setIcon(ac.getProgram().getIcon());
				
				//Id menu item (id entity and id program)
				//item.setId("idi" +ac.getEntityAdm().getId() + "_" + ac.getRole().getId());
				item.setId("" + ac.getRole().getId());
				item.setCommand("#{main.loadMenuItems(" + ac.getRole().getId() + ","  + ac.getProgram().getId() + ")}");
				item.setUpdate("form1:idMenuLateral");
				//item.setUpdate("frmplan1:idMenuLateral frmplan1:rutaPrograma");
				
				subMenu.addElement(item);

		}
		
		return subMenu;
		
	}
	
	/**
	 * <desc>class that generates the applications submenu</desc>
	 * @author 			Alfred Oliver
	 * @param head    	submenu name 
	 * @param listEp  	programs that the user is allowed for each entity
	 * @return        	HtmlDropDownMenu
	 
	public static DefaultSubMenuOld subMenuAplicacions(String head, List<EntityRole> listEp, DatabaseConnection entity){
		
		DefaultSubMenu subMenu = new DefaultSubMenu(head);
		
		for (EntityRole er: listEp){
			
			 if (!er.getEntity().equals(entity)) continue; 
			 
			 for (Role ro: er.getRole()){
				 
				//Create menu item
				DefaultMenuItem item = new DefaultMenuItem(MessagesTypes.msgGenerals(ro.getProgram().getDescription())); 
				item.setIcon("ui-icon-newwin");
				
				//Id menu item (id entity and id program)
				item.setId("idi" +er.getEntity().getId() + "_" + ro.getProgram().getId());
				item.setCommand("#{principal.carregaMenuLateral('" + item.getId() + "','"  + item.getValue() + "')}");
				item.setUpdate("frmplan1:idMenuLateral frmplan1:rutaPrograma");
				
				subMenu.addElement(item);
				
			 }
			 
			
		}
		
		return subMenu;
		
	}*/
	
	/**
	public static Toolbar menuAccions(Integer view, List<Action> actions){
		
		Application aplicacio = FacesContext.getCurrentInstance().getApplication();
		
		Toolbar toolBar = (Toolbar)aplicacio.createComponent(Toolbar.COMPONENT_TYPE);
		
		toolBar.setStyleClass("menuBarTipo1");
		
		ToolbarGroup grupAccions = (ToolbarGroup)aplicacio.createComponent(ToolbarGroup.COMPONENT_TYPE);
		
		for (Action ac: actions){	
			
			 CommandButton itemCmd = (CommandButton)aplicacio.createComponent(CommandButton.COMPONENT_TYPE);
			 itemCmd.setIcon(ac.getIcon());
			 itemCmd.setStyle("width: 100px; margin-right: 5px;");
			//Other Actions
			 
			 /**
			 if (ac.getDescription().substring(ac.getDescription().indexOf("_")).trim().equals("_search")){
				 
				 itemCmd.setOncomplete("PF('wdialogo01').show()");
				 itemCmd.setUpdate("frmplan1:idContingut");
			 }*/
			
	/**
			if (ac.getDescription().indexOf(":") > 0){
						

				
			} else {
				
				 itemCmd.setValue( MessagesTypes.msgOperation(ac.getDescription().substring(ac.getDescription().indexOf("_")+1).trim()));
				 				 
				 itemCmd.setActionExpression(aplicacio.getExpressionFactory().createMethodExpression
							(FacesContext.getCurrentInstance().getELContext(), 
								"#{ctx.getVista("+ view + ")." + ac.getDescription().substring(ac.getDescription().indexOf("_")).trim() +"}", String.class ,new Class[]{}));
						
				 System.out.println("accio: " + ac.getDescription());
			}
			 
			 
			 
			 grupAccions.getChildren().add(itemCmd);
		}
		
		
		toolBar.getChildren().add(grupAccions);
		
		return toolBar;
	}*/
}
