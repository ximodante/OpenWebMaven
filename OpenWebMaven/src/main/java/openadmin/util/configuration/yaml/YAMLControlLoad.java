package openadmin.util.configuration.yaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import openadmin.dao.operation.DaoJpaEdu;
import openadmin.dao.operation.DaoOperationFacadeEdu;
import openadmin.model.Base;
import openadmin.model.control.Access;
import openadmin.model.control.Action;
import openadmin.model.control.ActionViewRole;
import openadmin.model.control.ClassName;
import openadmin.model.control.EntityAdm;
import openadmin.model.control.MenuItem;
import openadmin.model.control.Program;
import openadmin.model.control.Role;
import openadmin.model.control.User;
import openadmin.util.configuration.TypeLanguages;
import openadmin.util.edu.YAMLUtilsEdu;
import openadmin.util.lang.LangTypeEdu;


@ToString
public class YAMLControlLoad implements Serializable{

	private static final long serialVersionUID = 20180204L;
	
	@Getter @Setter
	private List<YAMLUser> users= null;        // Users
	
	@Getter @Setter
	private List<YAMLRole> roles= null;        // Roles
		
	@Getter @Setter
	private List<YAMLEntityAdm> entities= null;       // EntityAdm 
	
	@Getter @Setter
	private List<YAMLAction> defaultActions= null;         // Programs

	@Getter @Setter
	private List<YAMLMenuItem> menuItems= null;         // Programs

	
	@Getter
	private HashMap<String,User>cUsers=null;
	
	@Getter
	private HashMap<String,Role>cRoles=null;
	
	@Getter
	private HashMap<String,EntityAdm>cEntityAdms=null;
	
	@Getter
	private HashMap<String,Program>cPrograms=null;
	
	@Getter
	private Set<Access>cAccesses=null;
	
	@Getter
	private HashMap<String,MenuItem> cMenuItems=null;
	
	@Getter
	private HashMap<String,ClassName> cClassNames=null;
	 
	@Getter
	private HashMap<String,Action> cActions=null;
	
	@Getter
	private HashMap<String,ActionViewRole> cActionViewRoles=null;
	
	private String defaultProgram="control";
	
	private Integer orden=1;
	
	@Setter
	private DaoOperationFacadeEdu connection = null; 	
	
	/**
	private User firstLoadUser = new User("FirstLoader","123456","First Load User");
	*/
	
	// ========================================
	// 1. Helpers
	// ========================================
	/**
	 * Get all Role Names
	 * @return
	 */
	
	private Set<String> getRoleNames() {
		Set<String> roleNames= new HashSet<String>();
		for (YAMLRole ymlRol: this.roles) {
			for (String sRol: ymlRol.getNames()) {
				roleNames.add(sRol.trim().toLowerCase());
		}	}
		return roleNames;
	}
	
	
	
	/**
	 * Get all Program Names
	 * @return
	 */
	private Set<String> getProgramNames() {
		Set<String> programNames= new HashSet<String>();
		for (YAMLRole ymlRol: this.roles) {
			for (String sProgram: ymlRol.getPrograms()) {
				programNames.add(sProgram.trim().toLowerCase());
		}	}
		return programNames;
	}
	
	// Get Class Names
	private Set<String> getClassNamesPriv(Set<String> classNames, List<YAMLMenuItem> lMenuItems) {
		if (lMenuItems !=null) {
			for (YAMLMenuItem ymlMenu: lMenuItems) {
				if (ymlMenu.getClassName() !=null)
					classNames.add(ymlMenu.getClassName().trim());
				classNames=getClassNamesPriv(classNames , ymlMenu.getMenuItems()); 
		}	}
		return classNames;
	}
	
	public Set<String> getClassNames() {
		Set<String> classNames= new HashSet<String>();
		classNames=getClassNamesPriv(classNames, this.menuItems);
		return classNames;
	}
	
	
	
	
	// ======================================================
	// 2. Control classes extraction and populate to DB
	//    Also delete old configuration records from DB
	// ======================================================
	public void Init() {
		LocalDateTime myDate = LocalDateTime.now();
		
		/**
		//0. open connection
		LangTypeEdu langType = new LangTypeEdu();
		langType.changeMessageLog(TypeLanguages.es);
		connection = new DaoJpaEdu(firstLoadUser, "control_post", (short) 0,langType);
		
		connection.begin();
		*/
		//1. Users
		this.cUsers=this.getControlUsers();
			
		//2. Roles
		this.cRoles=this.getControlRoles();
		
		//3. Entity & Program & Access
		this.EntityAdmProgramAccess();
		
		
		//4. MenuItems & ClassName & Action
		this.MenuItemsClassNameActions();
		
		
				
		//5. Delete old configuration
		connection.deleteOlderThan(ActionViewRole.class, myDate);
		connection.deleteOlderThan(Access.class        , myDate);
		connection.deleteOlderThan(Program.class       , myDate);
		connection.deleteOlderThan(User.class          , myDate);
		connection.deleteOlderThan(EntityAdm.class     , myDate);
		connection.deleteOlderThan(Role.class          , myDate);
		connection.deleteOlderThan(Action.class        , myDate);
		connection.deleteOlderThan(MenuItem.class      , myDate);
		connection.deleteOlderThan(ClassName.class     , myDate);
		
		
		/**
		
		//5. Commit connection
		connection.commit();
		connection.finalize();
		*/
	}
	
	//2.1 Control Users -_> ja està
	private HashMap<String,User> getControlUsers() {
		HashMap<String,User>lUsers=new HashMap<String,User>();
		if (this.users != null) {
			for (YAMLUser ymlUser: this.users) {
				User myUser=new User (ymlUser.getDescription(), ymlUser.getPassword(), ymlUser.getFullName());
				myUser.setIdentifier(ymlUser.getIdentifier());
				myUser.setLanguage(ymlUser.getLanguage());
				
				// DateBegin
				if (ymlUser.getDateBegin() != null) { 
					myUser.setDateBegin(LocalDate.parse(ymlUser.getDateBegin()));
				} else {
					myUser.setDateBegin(LocalDate.now());
				}
				// DateEnd
				if (ymlUser.getDateEnd() != null) { 
					myUser.setDateEnd(LocalDate.parse(ymlUser.getDateEnd()));
				} else {
					// By default a user is active for 5 years
					myUser.setDateEnd(myUser.getDateBegin().plusYears(5));
				}
				lUsers.put(myUser.getDescription(), this.connection.persist(myUser));
		}	}	
		return lUsers;
	}
	
	
	//2.2 ContolRoles
	private HashMap<String,Role> getControlRoles() {
		HashMap<String,Role>lRoles=new HashMap<String,Role>();
		if(this.roles !=null) {
			for (YAMLRole ymlRole : this.roles) {
				/*
				for (String sRol: ymlRole.getNames()) { 
					for (String sProg: ymlRole.getPrograms()) {
						Role myRole=new Role(sProg.trim().toLowerCase()+ "." + sRol.trim().toUpperCase());
						if (lRoles.get(myRole.getDescription()) == null)
								lRoles.put(myRole.getDescription(), this.connection.persist(myRole));
		}	}	}	}*/
				for (String sRol: ymlRole.getNames()) { 
					Role myRole=new Role(sRol.trim().toUpperCase());
						if (lRoles.get(myRole.getDescription()) == null)
								lRoles.put(myRole.getDescription(), this.connection.persist(myRole));
		}	}	}	
		return lRoles;
	}
	
	// 2.3 EntityAdm & Program & Accesses
	public void EntityAdmProgramAccess() {
		this.cEntityAdms = new HashMap<String,EntityAdm>();
		this.cPrograms   = new HashMap<String,Program>() ;
		this.cAccesses   = new HashSet<Access>() ;
		
		if(this.entities !=null) {
			for (YAMLEntityAdm ymlEnt : this.entities) {
				
				// Add EntityAdm
				EntityAdm myEnt=new EntityAdm(ymlEnt.getName().trim().toLowerCase());
				myEnt.setConn(ymlEnt.getConn());
				myEnt.setIcon(ymlEnt.getIcon());
				myEnt.setTheme(ymlEnt.getTheme());
				this.cEntityAdms.put(myEnt.getDescription(), this.connection.persist(myEnt));
				
				if(ymlEnt.getPrograms() !=null) {
					for (YAMLProgram ymlProg: ymlEnt.getPrograms()) {
						
						// Add program
						Program myProg=new Program (ymlProg.getName().trim().toLowerCase());
						myProg.setIcon(ymlProg.getIcon());
						this.cPrograms.put(myProg.getDescription(), this.connection.persist(myProg));
						
						if(ymlProg.getAlloweds() !=null) {
							for (YAMLAllowed ymlAllow: ymlProg.getAlloweds()) {
								for (String sUser: ymlAllow.getUsers()) {
									Access myAcc=new Access ();
									myAcc.setEntityAdm(myEnt);
									myAcc.setProgram(myProg);
									myAcc.setUser(this.cUsers.get(sUser));
									//myAcc.setRole(this.cRoles.get(myProg.getDescription().trim().toLowerCase()+"."+ymlAllow.getRole().trim().toUpperCase()));
									myAcc.setRole(this.cRoles.get(ymlAllow.getRole().trim().toUpperCase()));
									myAcc.setDescription("");
									System.out.println("------ACCESS: EntityAdm:" + myEnt.getDescription() + " - User:" + sUser + " - Program:"+ myProg.getDescription() + " - Role:"+ ymlAllow.getRole());
									this.cAccesses.add(this.connection.persist(myAcc));
									
		
		
				
	}	}	}	}	}	}	}	}
	
	
	// 2.4 MenuItems & ClassNames & Actions
	public void MenuItemsClassNameActions() {
		this.defaultProgram="control";
		this.cMenuItems =new HashMap<String,MenuItem>();
		this.cActions   =new HashMap<String,Action>();
		this.cClassNames=new HashMap<String,ClassName>();
		this.cActionViewRoles=new HashMap<String,ActionViewRole>();
		
		Set<String> myRoles=new TreeSet<String>();
		
		if(this.menuItems !=null) {
			for (YAMLMenuItem ymlMenu : this.menuItems) {
				if (ymlMenu.getViewType().trim().toLowerCase().equals("submenu")) {
					myRoles =this.SubMenuItemsClassNameActionsPriv(ymlMenu, null, myRoles);
				} else {
					myRoles = MenuItemsClassNameActionsPriv(ymlMenu, null, myRoles);
	}	}	}	} 
	
	//2.4.1 SubMenu MenuItems
	private Set<String> SubMenuItemsClassNameActionsPriv(YAMLMenuItem ymlMenu, MenuItem parent, Set<String> myParentRoles ) {
		MenuItem myMenu=new MenuItem();
		ClassName myClass=new ClassName();
		
		Set<String> myRoles=new TreeSet<String>();
		
		if (ymlMenu.getProgram().trim().length()>0) 
			this.defaultProgram=ymlMenu.getProgram().trim().toLowerCase();
		
		// Add ClassName
		myClass.setDescription(ymlMenu.getClassName().trim());
		this.cClassNames.put(myClass.getDescription(), this.connection.persist(myClass));
		
		// Add MenuItems
		myMenu.setClassName(myClass);
		myMenu.setIcon(ymlMenu.getIcon());
		myMenu.setParent(parent);
		myMenu.setOrden(this.orden++);
		myMenu.setTypeNode("p");
		myMenu.setViewType(ymlMenu.getViewType());
		String mySuffix=ymlMenu.getViewType().trim().toLowerCase();
		myMenu.setDescription((myClass.getDescription().trim() + "_" + mySuffix).replaceAll("\\.", "_").toLowerCase());
		this.cMenuItems.put(myMenu.getDescription(), this.connection.persist(myMenu));
		
		
		
		if(ymlMenu.getMenuItems() !=null) {
			for (YAMLMenuItem ymlMenu1 : ymlMenu.getMenuItems()) {
				if (ymlMenu1.getViewType().trim().toLowerCase().equals("submenu")) {
					myRoles=SubMenuItemsClassNameActionsPriv(ymlMenu1, myMenu, myRoles);
				} else if (ymlMenu1.getViewType().trim().toLowerCase().equals("action")) {
					myRoles=MenuItemsClassNameActionsPriv(ymlMenu1, myMenu, myRoles);
				} else {	
					myRoles=MenuItemsClassNameActionsPriv(ymlMenu1, myMenu, myRoles);
		}	}	}
		
		//Submenu Gets roles from children. All the roles of the children can access a parent submenu
		List<String> lRol=new ArrayList<String>(myRoles);
				
		//Add Submenu action
		YAMLAction ymlAct=new YAMLAction();
		ymlAct.setName("submenu");
		ymlAct.setGroup(0); // No matter the group value as there is only 1 action
		ymlAct.setRoles(lRol);
		myParentRoles= this.setMyActions(ymlAct, myClass, myMenu, myParentRoles);
		
		return myParentRoles;
	}	
		
	
	//2.4.2 Simple MenuItems
	private Set<String> MenuItemsClassNameActionsPriv(YAMLMenuItem ymlMenu, MenuItem parent, Set<String> myParentRoles ) {
		
		MenuItem myMenu=new MenuItem();
		ClassName myClass=new ClassName();
		
		if (ymlMenu.getProgram().trim().length()>0) 
			this.defaultProgram=ymlMenu.getProgram().trim().toLowerCase();
		
		// Add ClassName
		myClass.setDescription(ymlMenu.getClassName().trim());
		this.cClassNames.put(myClass.getDescription(), this.connection.persist(myClass));
		
		// Add MenuItems
		myMenu.setClassName(myClass);
		myMenu.setIcon(ymlMenu.getIcon());
		myMenu.setParent(parent);
		myMenu.setOrden(this.orden++);
		//myMenu.setTypeNode(ymlMenu.getViewType());
		myMenu.setTypeNode("c");
		myMenu.setViewType(ymlMenu.getViewType());
		
		String mySuffix=ymlMenu.getViewType().trim();
		// On action menuitems the suffix is the action name
		//   and viewType is ":" + action name        
		if (ymlMenu.getViewType().trim().equalsIgnoreCase("action")) {
			mySuffix=ymlMenu.getActions().get(0).getName().trim();
			myMenu.setViewType(":" + mySuffix); 
		}
		myMenu.setDescription((myClass.getDescription().trim() + "_" + mySuffix).replaceAll("\\.", "_").toLowerCase());
		
		
		//myMenu.setDescription((myClass.getDescription().trim() + "_" + ymlMenu.getViewType().trim()).replace(".", "_").toLowerCase());
		this.cMenuItems.put(myMenu.getDescription(), this.connection.persist(myMenu));
		
		// Add default actions only if viewType is not action nor submenu
		if(ymlMenu.isDefaultActions() && 
		   !ymlMenu.getViewType().trim().equalsIgnoreCase("action") && 
		   !ymlMenu.getViewType().trim().equalsIgnoreCase("submenu")) 
			myParentRoles=this.setMyDefaultActions(ymlMenu, myClass, myMenu, myParentRoles);
		
		//Add other actions
		if (ymlMenu.getActions() != null) {
			for (YAMLAction ymlAct: ymlMenu.getActions()) {
				myParentRoles=this.setMyActions(ymlAct, myClass, myMenu, myParentRoles);
		}	}
		return myParentRoles;
	}	
		
	// 2.4.3 Retrieve Default Actions
	private Set<String> setMyDefaultActions(YAMLMenuItem ymlMenu, ClassName myClass, MenuItem myMenu, Set<String> myParentRoles) {
		if (ymlMenu.isDefaultActions()) {
			if (this.getDefaultActions() != null) {
				for (YAMLAction ymlAct: this.getDefaultActions() ) {
					myParentRoles=this.setMyActions(ymlAct, myClass, myMenu, myParentRoles);
		}	}	}	
		return myParentRoles;
	}
		
	//2.4.4 Create Actions and ActionviewRole
	private Set<String> setMyActions(YAMLAction ymlAct, ClassName myClass, MenuItem myMenu, Set<String> myParentRoles) {
		// Add action
		Action myAct=new Action (); 
		//myAct.setDescription((myClass.getDescription().trim() + "_" + ymlAct.getName()).trim().replace(".", "_").toLowerCase());
		myAct.setDescription((myClass.getDescription().trim() + "_" + ymlAct.getName()).trim().toLowerCase());
		myAct.setClassName(myClass);
		myAct.setGrup(ymlAct.getGroup());
		myAct.setIcon(ymlAct.getIcon());
		this.cActions.put(myAct.getDescription(), this.connection.persist(myAct));
		
		// Add ActionViewRole
		if (ymlAct.getRoles() != null) {
			for (String sRole: ymlAct.getRoles()) {
				//String roleDesc=this.defaultProgram +"."+ sRole.trim().toUpperCase();
				String roleDesc=sRole.trim().toUpperCase();
				Role myRole=this.cRoles.get(roleDesc);
				// If the role exists for this program
				if (myRole!=null) {
					ActionViewRole myAVR = new ActionViewRole();
					myAVR.setAction(myAct);
					myAVR.setMenuItem(myMenu);
					myAVR.setRole(myRole);
					myAVR.setDescription("");
					this.cActionViewRoles.put(myAVR.getDescription(), this.connection.persist(myAVR));
					myParentRoles.add(sRole);
		}	}	}
		return myParentRoles;
	}
	
	
	// ========================================
	// 3. Error detection
	// ========================================
	/**
		
	// Error detection A: Duplicates
	/**
	 * Detects duplicated users
	 * @return
	 */
	public String DuplicatedUsers(String myErrors) {
		Set<String> mySet= new HashSet<String>();
		for( YAMLUser ymlUser: this.users) {
			String myDesc=ymlUser.getDescription().trim().toLowerCase();
			if (mySet.add(myDesc)==false) 
				myErrors=myErrors + "\n" + "->Duplicated User:" + myDesc;
		}	
		return myErrors;
	}
	
	/**
	 * Detects duplicated combination of Role + Program
	 * @param myErrors
	 * @return
	 */
	public String DuplicatedRoles(String myErrors) {
		Set<String[]> mySet= new HashSet<String[]>();
		for (YAMLRole ymlRol: this.roles) {
			for (String sRol: ymlRol.getNames()) {
				for (String sProgram: ymlRol.getPrograms()) {
					String[]str=new String[2];
					str[0]=sRol;
					str[1]=sProgram;
					if (mySet.add(str)==false)
						myErrors=myErrors + "\n" + "->Duplicated Rol:" + sRol + "-" + sProgram; 
				}
			}
			
		}
		return myErrors;
	}
	
	/**
	 * Detects duplicated EntityAdm
	 * @param myErrors
	 * @return
	 */
	public String DuplicatedEntities(String myErrors) {
		Set<String> mySet= new HashSet<String>();
		for (YAMLEntityAdm ymlEnt: this.entities) {
			if (mySet.add(ymlEnt.getName())==false)
						myErrors=myErrors + "\n" + "->Duplicated EntityAdm:" + ymlEnt.getName(); 
		}
		return myErrors;
	}
	
	/**
	 * Detects duplicated Program per EntityAdm
	 * @param myErrors
	 * @return
	 */
	public String DuplicatedEntityProgram(String myErrors) {
		for (YAMLEntityAdm ymlEnt: this.entities) {
			Set<String> mySet= new HashSet<String>();
			for (YAMLProgram ymlProg: ymlEnt.getPrograms()) {
				if (mySet.add(ymlProg.getName())==false)
						myErrors=myErrors + "\n" + "->Duplicated Program in EntityAdm:" + ymlEnt.getName() + "-" + ymlProg.getName();
			}
		}
		return myErrors;
	}
	
	/**
	 * Detects duplicated User per Program per EntityAdm
	 * @param myErrors
	 * @return
	 */
	public String DuplicatedEntityProgramUser(String myErrors) {
		for (YAMLEntityAdm ymlEnt: this.entities) {
			for (YAMLProgram ymlProg: ymlEnt.getPrograms()) {
				Set<String> mySet= new HashSet<String>();
				for (YAMLAllowed ymlAllow: ymlProg.getAlloweds()) {
					for (String user: ymlAllow.getUsers()) {
						if (mySet.add(user)==false)
							myErrors=myErrors + "\n" + "->Duplicated User in Program in EntityAdm:" + ymlEnt.getName() + "-" + ymlProg.getName() + "-" + user;
					}
				}	
			}
		}
		return myErrors;
	}
	
	/**
	 * Detects duplicated Default Actions
	 * @return
	 */
	public String DuplicatedDefaultAction(String myErrors) {
		Set<String> mySet= new HashSet<String>();
		for( YAMLAction action: this.defaultActions) {
			String name=action.getName().trim().toLowerCase();
			if (mySet.add(name)==false) 
				myErrors=myErrors + "\n" + "->Duplicated DefaultAction:" + name;
		}	
		return myErrors;
	}
	
	/**
	 * Detects duplicated Roles in Default Actions
	 * @return
	 */
	public String DuplicatedDefaultActionRole(String myErrors) {
		if (this.defaultActions!=null) {
			for( YAMLAction action: this.defaultActions) {
				Set<String> mySet= new HashSet<String>();
				for (String sRol: action.getRoles()) {
					String name= sRol.trim().toLowerCase();
					if (mySet.add(name)==false) 
						myErrors=myErrors + "\n" + "->Duplicated DefaultAction Role:" + action.getName() + "-" + sRol;
				}	
			}
		}	
		return myErrors;
	}
	
	// Error detection B: Nonexistent elements
	
	/**
	 * Detects nonexistent roles in Entity-Program-Alloweds
	 * @param myErrors
	 * @return
	 */
	public String NoEntityProgramRole(String myErrors) {
		Set<String> mySet= this.getRoleNames();
		for (YAMLEntityAdm ymlEnt: this.entities) {
			for (YAMLProgram ymlProg: ymlEnt.getPrograms()) {
				for (YAMLAllowed ymlAllow: ymlProg.getAlloweds()) {
					if (! mySet.contains(ymlAllow.getRole().trim().toLowerCase()))
							myErrors=myErrors + "\n" + "->Rol in Program in EntityAdm NOT defined in Roles:" + ymlEnt.getName() + "-" + ymlProg.getName() + "-" + ymlAllow.getRole();
					
				}	
			}
		}
		return myErrors;
	}
	
	
	/**
	 * Detects nonexistent roles in Entity-Program-Alloweds
	 * @param myErrors
	 * @return
	 */
	public String NoDefaultActionRole(String myErrors) {
		Set<String> mySet= this.getRoleNames();
		if (this.defaultActions !=null) { 
			for (YAMLAction ymlAct: this.defaultActions) {
				for (String sRol: ymlAct.getRoles()) {
					if (! mySet.contains(sRol.trim().toLowerCase()))
						myErrors=myErrors + "\n" + "->Role in DefaultActions NOT defined in Roles:" + 
								ymlAct.getName() +  "-" + sRol;
				}
			}
		}	
		return myErrors;
	}
	
	/**
	 * Detects nonexistent programs in MenuItem
	 * @param myErrors
	 * @return
	 */
	private String NoMenuItemProgramPriv(String myErrors, List<YAMLMenuItem> lMenuItems, Set<String> programSet ) {
		if (lMenuItems !=null) {
			for (YAMLMenuItem ymlMenu: lMenuItems) {
				if (ymlMenu.getProgram() != null && ymlMenu.getProgram().trim().length()>0) {
					if (! programSet.contains(ymlMenu.getProgram().trim().toLowerCase()))
						myErrors=myErrors + "\n" + "->Program in MenuItems NOT defined in Roles:" + 
								ymlMenu.getDescription() +"-" + ymlMenu.getClassName() +  "-" + 
								ymlMenu.getProgram();
				}
				myErrors= NoMenuItemProgramPriv(myErrors, ymlMenu.getMenuItems(), programSet);
			}	
		}
		return myErrors;
	}
	
	/**
	 * Detects nonexistent programs in MenuItem
	 * @param myErrors
	 * @return
	 */
	public String NoMenuItemProgram(String myErrors) {
		myErrors=NoMenuItemProgramPriv(myErrors, this.menuItems,this.getProgramNames());
		return myErrors;
	}	
	
	
	/**
	 * Detects nonexistent roles in menitems
	 * @param myErrors
	 * @return
	 */
	private String NoMenuItemActionRolePriv(String myErrors, List<YAMLMenuItem> lMenuItems, Set<String> roleSet ) {
		if (lMenuItems !=null) {
			for (YAMLMenuItem ymlMenu: lMenuItems) {
				if (ymlMenu.getActions() !=null) {
					for (YAMLAction ymlAction: ymlMenu.getActions()) {
						for (String sRol:ymlAction.getRoles()) {
							if (! roleSet.contains(sRol.trim().toLowerCase())) 
								myErrors=myErrors + "\n" + "->Roles in Actions in MenuItems NOT defined in Roles:" + 
										ymlMenu.getDescription() +"-" + ymlMenu.getClassName() +  "-" + 
										ymlAction.getName() + "-" + ymlMenu.getProgram();
						}
					}
					myErrors= NoMenuItemProgramPriv(myErrors, ymlMenu.getMenuItems(), roleSet);
				}	
			}
		}	
		return myErrors;
	}
	
	
	/**
	 * Detects nonexistent roles in MenuItem - Action 
	 * @param myErrors
	 * @return
	 */
	public String NoMenuItemActionRole(String myErrors) {
		myErrors=NoMenuItemActionRolePriv(myErrors, this.menuItems,this.getRoleNames());
		return myErrors;
	}
	
	public String checkErrors(boolean verbose) {
		String s="";
		if (verbose) s=s+
			"======================================================\n" +
			"1. Duplicated Users:\n" + 
			"======================================================\n";
		this.DuplicatedUsers(s);
		
		if (verbose) s=s + "\n\n" + 
			"======================================================\n" +
			"2. Duplicated Roles:\n" + 
			"======================================================\n";
		this.DuplicatedRoles(s);
		
		if (verbose) s= s + "\n\n" + 
			"======================================================\n" +
			"3. Duplicated EntityAdm:\n" + 
			"======================================================\n";
		this.DuplicatedEntities(s);
			
		if (verbose) s= s + "\n\n" + 
			"======================================================\n" +
			"4. Duplicated EntityProgram:\n" + 
			"======================================================\n";
		this.DuplicatedEntityProgram(s);
			
		
		if (verbose) s= s + "\n\n" + 
			"======================================================\n" +
			"5. Duplicated EntityProgramUser:\n" + 
			"======================================================\n";
		this.DuplicatedEntityProgramUser(s);
			
		if (verbose) s= s + "\n\n" + 
			"======================================================\n" +
			"6. Duplicated DefaultAction:\n" + 
			"======================================================\n";
		this.DuplicatedDefaultAction(s);
			
		
		if (verbose) s= s + "\n\n" + 
			"======================================================\n" +
			"7. Duplicated DefaultActionRole:\n" + 
			"======================================================\n";
		this.DuplicatedDefaultActionRole(s);
			
		if (verbose) s= s + "\n\n" + 
			"======================================================\n" +
			"8. NoEntityProgramRole:\n" + 
			"======================================================\n";
		this.NoEntityProgramRole(s);
		
		if (verbose) s= s + "\n\n" + 
			"======================================================\n" +
			"9. NoDefaultActionRole:\n" + 
			"======================================================\n";
		this.NoDefaultActionRole(s);
			
		if (verbose) s= s + "\n\n" + 
			"======================================================\n" +
			"10. NoMenuItemProgram:\n" + 
			"======================================================\n";
		this.NoDefaultActionRole(s);
			
		if (verbose) s= s + "\n\n" + 
			"======================================================\n" +
			"11. NoMenuItemActionRole:\n" + 
			"======================================================\n";
		this.NoMenuItemActionRole(s);
			
		return s;
	}	
	
		
	public static void main(String[] args) {
		
		YAMLControlLoad yc=null;
		//1. Read YAML File
		InputStream in = YAMLUtilsEdu.class.getResourceAsStream("/data/Application.yaml");
		try {
			yc = YAMLUtilsEdu.YAMLFileToObject(in, YAMLControlLoad.class);
			System.out.println(yc.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//2. Display sets
		System.out.println("Displaying Roles...........");
		for (String s1: yc.getRoleNames()) System.out.println(s1);
		
		System.out.println("Displaying Programs...........");
		for (String s1: yc.getProgramNames()) System.out.println(s1);
		
		System.out.println("Displaying ClassNames...........");
		for (String s1: yc.getClassNames()) System.out.println(s1);
		
		//3. Test Errors
		System.out.println(yc.checkErrors(true));
				
		//4.- Load Control data
		yc.Init();
		System.out.println(yc.toString());

	}

}
