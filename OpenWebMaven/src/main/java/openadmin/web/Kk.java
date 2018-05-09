package openadmin.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import lombok.Getter;
import lombok.Setter;

@Named 
@SessionScoped

public class Kk implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 20180219L;
	@Setter @Getter
	MenuModel menuBar = new DefaultMenuModel();
	
	@PostConstruct
    public void init() {
		//First submenu
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("Dynamic Submenu");
         
        DefaultMenuItem item = new DefaultMenuItem("External");
        item.setUrl("http://www.primefaces.org");
        //item.setIcon("ui-icon-home");
        firstSubmenu.addElement(item);
         
        menuBar.addElement(firstSubmenu);
         
        //Second submenu
        DefaultSubMenu secondSubmenu = new DefaultSubMenu("Dynamic Actions");
 
        item = new DefaultMenuItem("Save");
        //item.setIcon("ui-icon-disk");
        //item.setCommand("#{menuView.save}");
        //item.setUpdate("messages");
        secondSubmenu.addElement(item);
        menuBar.addElement(secondSubmenu);
    }
	
	public void  getMenuBar1() {
		
		
		menuBar=new DefaultMenuModel();
		
		//First submenu
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("Dynamic Submenu1");
         
        DefaultMenuItem item = new DefaultMenuItem("External1");
        item.setUrl("http://www.primefaces.org");
        //item.setIcon("ui-icon-home");
        firstSubmenu.addElement(item);
         
        menuBar.addElement(firstSubmenu);
         
        //Second submenu
        DefaultSubMenu secondSubmenu = new DefaultSubMenu("Dynamic Actions1");
 
        item = new DefaultMenuItem("Save1");
        //item.setIcon("ui-icon-disk");
        //item.setCommand("#{menuView.save}");
        //item.setUpdate("messages");
        secondSubmenu.addElement(item);
        menuBar.addElement(secondSubmenu);
        
		
	}
}
