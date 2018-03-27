package openadmin.action;

import java.beans.IntrospectionException;

/**
*	Classe ContextAction
*   <desc> class that represents the context of application</desc>
*	@version  0.1
*	Creada  21-10-2009
*   Revisió 01-12-2009
*   @author Alfred Oliver 
*/

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import java.util.stream.Collectors;

import openadmin.dao.operation.DaoJpaEdu;
import openadmin.dao.operation.DaoOperationFacadeEdu;

import openadmin.model.control.Access;

import openadmin.model.control.EntityAdm;


import openadmin.model.control.User;
import openadmin.util.configuration.FirstControlLoadEdu;
//import openadmin.util.configuration.FirstControlLoad;
import openadmin.util.configuration.TypeEnvironment;
import openadmin.util.configuration.TypeLanguages;
import openadmin.util.edu.CollectionUtilsEdu;
import openadmin.util.lang.LangTypeEdu;
import openadmin.util.lang.WebMessages;


import openadmin.web.view.ViewFacade;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

@Named(value = "ctx")
@SessionScoped
public class ContextActionEdu implements Serializable {

	private static final long serialVersionUID = 21100901L;

	/** Field that contain the connection*/
	@Getter @Setter
	private DaoOperationFacadeEdu connControl = null;
	
	/** Field that contain the connection with log*/
	//private DaoOperationFacadeEdu connLog = null;
	
	/** Field that contain the connection with log
	@Getter @Setter
	private DaoOperationFacadeEdu connDefault = null;
	*/
	/** Field that contain the menuItems*/
	//private Map<String, List<Base>> menuItems = new HashMap<String, List<Base>>();
	
	/** Field that contain the actions of the view*/
	//private Map<String, List<Base>> actionsViews = new HashMap<String, List<Base>>();
	
	@Getter @Setter
	private Map<EntityAdm, List<Access>> mapEntityAccess = new HashMap<EntityAdm, List<Access>>();
	
	//private Set<EntityAdm> listEntity = new TreeSet<EntityAdm>();
	
	//private  List<EntityRole> lstEntitiRol = new ArrayList<EntityRole>();
	
	//private EntityAdm entityDefault = null;
	
	//private Role rolDefault = null;
	
	/** Field that contain the list of actions*/
	private Map<Integer, ViewFacade> lstView = new HashMap<Integer, ViewFacade>();
	
	/** Field that contain the user*/
	@Getter @Setter
	private User user;

	/** If is connect with database*/
	private boolean connected = false;
	
	/** Field that contain the Log 
	@Getter
	LogDaoEdu log = null;
	*/
	@Inject
	private LangTypeEdu langType;
	
	@PostConstruct
	public void initEdu () {
	    this.connected=false;
	    connControl=null;
	    System.out.println("-99.5 connected=" + connected);
	}

	private void connect() {
		//comprova si hi ha conexi� a la base de dades
		//initEdu();
		System.out.println("-99.4 connected?=" + connected);
		if (!connected){
			
			//hi ha que modificar l'idioma del log per el de la configuracio manual, la connexió també  
			langType.changeMessageLog(TypeLanguages.es);
			
			/*
			//Log connection
			connLog = new DaoJpaEdu(user, "log_post", null, langType);									
			log = new LogDaoEdu(connLog, "clientweb", langType);
			*/
			//connection
			System.out.println("-99.3 connect before DaoJpaEdu control_post");
			connControl = new DaoJpaEdu(user, "control_post", (short)0, langType);
			System.out.println("-99.2 connect after DaoJpaEdu control_post");
			connControl.setEnvironment(TypeEnvironment.WEB);
			connected = true;
			System.out.println("-99.1 connect after connected=true");
		}
		
	}
	
	/**<desc> Performs the application login</desc>
 	 * @return true if login user is correct
	 */
	public boolean login (User pUser) {
		
		//Usuari que es connecta
		user = pUser;
		user.setActive(true);
		user.setFirma(false);
		
		// Open JPA db connections if closed
		System.out.println("-100 login before connect");
		this.connect();
		System.out.println("-99 login after connect");
		
		user=CollectionUtilsEdu.get(connControl.findObjects(user),0);
		System.out.println("-98 login after collectionutils");		
		try {
			
			if (null != user) {
				loadPrograms();
				return true;
			}
			
			//if (connControl.isEmpty(user)) FirstControlLoadEdu.PersistConfiguration();
			else if (connControl.isEmpty(User.class)) FirstControlLoadEdu.dataLoad();
			
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | IntrospectionException | RuntimeException e) {
			
			e.printStackTrace();
		
		}
		
		WebMessages.messageError("error_validation_login");
		
		
		return false;
	
	}
	/*
	private void jpqlProva() {
		String jpql_="SELECT "
		this.connControl.findObjectPersonalized(pSentencia)
	}
	*/
	
	/**
	public boolean loginFirma(String pIdentificador) {
		
		boolean exist = false;
		
		//Database connection 
		if (!connected){
				
			//hi ha que modificar l'idioma del log per el de la configuracio manual, la connexió també  
			MessagesTypes.changeMessageLog(TypeLanguages.es);
			MessagesTypes.changeMessage("es");
			
			//Log connection
			connLog = new DaoJpaHibernate(user, "log_post", null);									
			log = new LogDao(connLog, "clientweb");
			
			//connection
			connControl = new DaoJpaHibernate(user, "control_post", log);
			connControl.setEnvironment(TypeEnvironment.WEB);
			connected = true;
		
		}
		
		//if user is activate
		user.setActive(true);
								
		//Identificador
		user.setIdentifier(pIdentificador);
		
		//Find user
		List <Base> listUsers = connControl.findObjects(user);
		
		//If user exist
		if (connControl.isResultOperation()){
			
			user = (User)listUsers.get(0);
			
			if (user.getActive()){
				
				//Locale default user
				connLog.setUser(user);
				connControl.setUser(user);
				connEntityDefault(user.getEntityDefault());
				loadPrograms();
				exist = true;
				
				//load Messages languages 
				MessagesTypes.changeMessage(connControl.getUser().getLanguage());
				return exist;
			}	
		}
		
		return exist;
		
	}*/
	
	
	/**<desc> Performs the logout</desc>
 	 */
	public String logout () {
				
		user = new User();
		connControl.finalize();
		//connDefault.finalize();
		/*
		log.finalizeLog(); 
		connControl = null;
		//connDefault = null;
		connLog = null;
		*/
		connected = false;
		System.out.println("-Connected=false");
		
		System.out.println("  Eixir 100 ");
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "index";
		
	}


	private void loadPrograms() {
				
		/*****************************************************************
		 * Find all the entities that has the user, in the table access
		 *****************************************************************/
		
		Access access = new Access();
		access.setUser(user);
				
		mapEntityAccess = 
				connControl.findObjects(access).stream()
				.collect(Collectors.groupingBy(Access::getEntityAdm));
		
		/********************************
		 * Load items of menu for each program
		 ******************************
		//loadMenuItems(access.getRole(), en);
		
		
		
		//Segona opcio
		/**Map<EntityAdm, Set<Role>> mapEntityRole = lstAccess.stream()
													.collect(Collectors.groupingBy(Access::getEntityAdm,
																					Collectors.mapping(Access::getRole, Collectors.toSet())));
		
		
		//Set<Access> list = new TreeSet<Access>((Collection<? extends Access>) mapEntityAccess.values());
		
		List <Access> _access = null;
		
		for (EntityAdm ent: mapEntityAccess.keySet()) {
			
			System.out.println("Entitat: " + ent.getDescription());
			
			_access =  mapEntityAccess.get(ent);
			
			for (Access ac : _access) {
				
				System.out.println("\tAccess: " + ac.getProgram().getDescription());
			}
			
			//System.out.println("\tAccess: " + mapEntityAccess.get(ent));
			
		}
		
		System.out.println("Programes carregats");
		*/
	}
	
	
	/**
	 * <desc> Load menu items for each application</desc>
	  * @param pRole, role of application
	  * @param entity, selected entity
	 
	private void loadMenuItems(Role pRole, EntityAdm entity) {
		
		/*****************************************************************
		 * Find all items of menu for each entity, program and user, in the table ActionViewRole
		 ***************************************************************
		ActionViewRole actionViewRole = new ActionViewRole();
		
		actionViewRole.setRole(pRole);
		
		//Load of items menu with the key (id entity and id program)
		menuItems.put("idi" + entity.getId() + "_" + pRole.getProgram().getId(), connControl.findObjects(actionViewRole));
		
		/*****************************************************************
		 * Find all actions for each view and role, in the table ActionViewRole
		 ***************************************************************
		//List of menu items
		Set<MenuItem> listMenuItem = new TreeSet<MenuItem>();
		
		for (Base b : connControl.findObjects(actionViewRole)) {
			
			actionViewRole = (ActionViewRole)b;
			listMenuItem.add(actionViewRole.getMenuItem());
			
		}
		
		List<Base> lst = new ArrayList<Base>();
		
		lst.addAll(listMenuItem);
		
		for (MenuItem item: listMenuItem){
			
			actionViewRole = new ActionViewRole();
			actionViewRole.setMenuItem(item);
			actionViewRole.setRole(pRole);
			
			//actionsViews.put("ida" + item.getDescription() + "_" + pRole.getId(), connControl.findObjects(actionViewRole));
			actionsViews.put("ida" + item.getDescription(), connControl.findObjects(actionViewRole));
			//System.out.println("CARREGA" + "ida" + item.getDescription());
		}
			
	}
	
	/**
	public List<EntityRole> getPrograms() {
		return programs;
	}
	
	public List<Base> getLoadMenuItems(String pKey){
		
		
		return menuItems.get(pKey);

	} 
	*/
	/**
	  * <desc> /Loads all actions of the view</desc>
	  * @param pKey, id view and id role
	  * @return List the actions
	  
	public List<Base> getLoadActionsView(String pKey){

		
		return actionsViews.get(pKey);
		
	}

	
	/**-----------------------------------------------------
	 * ---------------- gets i sets --------------------
	 * -----------------------------------------------------
	 */

	/**
	public List<EntityAdm> getListEntity() {
		
		List<EntityAdm> lst = new ArrayList<EntityAdm>();
		
		for (EntityAdm b : listEntity) {
			
			lst.add(b);
			
		}
		
		return lst;
	}

	public EntityAdm getEntityDefault() {
		return entityDefault;
	}


	public void setEntityDefault(EntityAdm entityDefault) {
		this.entityDefault = entityDefault;
	}


	public Role getRolDefault() {
		return rolDefault;
	}


	public void setRolDefault(Role rolDefault) {
		this.rolDefault = rolDefault;
	}

	*/
	
	//Work view
	public ViewFacade getView(Integer key) {
		
		return lstView.get(key);
	
	}

	public void setView(Integer key, ViewFacade pVista) {
		
		lstView.put(key, pVista);
		
	}
	
	public void deleteView() {
		
		lstView.remove(lstView.size());
		
	}
	
	public void deleteAllView() {
		
		lstView.clear();
		
	}
	
	public Integer numberView() {
		
		return lstView.size();
		
	}
	
}
