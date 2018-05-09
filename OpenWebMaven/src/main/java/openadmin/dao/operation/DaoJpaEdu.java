package openadmin.dao.operation;

import java.beans.IntrospectionException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.Getter;
import lombok.Setter;
import openadmin.model.Base;
import openadmin.model.control.User;
import openadmin.model.log.LogEdu;
import openadmin.util.configuration.TypeEnvironment;
import openadmin.util.edu.LogUtilsEdu;
import openadmin.util.lang.LangTypeEdu;
import openadmin.util.reflection.ReflectionField;

/**
 * <desc>DaoJpaHibernate implement the interface DaoOperationFacade</desc>
 * <responsibility>Implement the operations of database for framework JPA - Hibernate</responsibility>
 * <coperation>All classes that need to work with database</coperation>
 * @version  0.1
 * Create  18-03-2009
 * Author Alfred Oliver
*/
@SuppressWarnings("unchecked")
public class DaoJpaEdu implements DaoOperationFacadeEdu, Serializable{
	
	private static final long serialVersionUID = 20180101;

	/**Field that contain one list of Base objects */
	//private List <Base> listObjects = null;
	private List <? extends Base> listObjects = null;
		
	/**Field used by the application to obtain an application-managed entity manager.*/
	private EntityManagerFactory factory = null;
	
	/**Field that contain the interface used to interact with the persistence context.*/
	private EntityManager em;
	
	/** Field that contain the result to operation*/
	private boolean resultOperation;
	
	/** Field that contain the registered User*/
	@Getter @Setter
	private User user;
	
	/**Field that contain the working environment*/
	@Getter @Setter
	private TypeEnvironment environment = TypeEnvironment.SWING;	
	
	@Getter @Setter
	private LangTypeEdu langType;
	
	@Getter @Setter
	private Short idEntity=null;
	
	/**
	  * Constructor of class DaoJpaHibernate.
	  * @throws DataException. 
	  * @param PUser. Registered User.  	 
	  * @param 	pDataBase. Attribute name of node persistence-unit (file persistence.xml)
	  * @param idEntity. Id of the Entity or enterprise
	  */
	public DaoJpaEdu(User pUser, String pDataBase, Short idEntity, LangTypeEdu pLangType){
		
		langType = pLangType;
		
		this.setUser(pUser);
		this.idEntity=idEntity;
		System.out.println("1.DaoJpaEdu " +pDataBase);		
		connection(pDataBase);
		
	}
	
	
	/**
	  * Procedure that connect to database
	  * {Pre: There isn't an open connection to the database}.
	  * {Post: has connect to database}.
	  * @throws DataException if there isn't a connection to the database.
	  * @param 	pUnitName. Attribute name of node persistence-unit (file persistence.xml)  
	  */
	private void connection(String pUnitName){		
						
		try {
			
			factory = Persistence.createEntityManagerFactory(pUnitName);
			System.out.println("2.factory " );	
			em = factory.createEntityManager();
			System.out.println("3.em " );	
			LogUtilsEdu.LogAudit(new LogEdu(null, null, idEntity, null, DBAction.DB_CONNECT, "Connecting to" + pUnitName));
			System.out.println("4.logaudit " );
		}catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, null, idEntity, null, DBAction.DB_CONNECT, "ERROR Connecting to Unit-Name:" + pUnitName));
		}
	}
	
	/**
	 * Persist object. 
	 * {Pre: The object no exist}.
	 * {Post: The object is persist is not exist. 
	 */
	//public void persistObject(Base obj) {
	public <T extends Base> void persistObject(T obj) {
		
		resultOperation = false;		
		
		try{
			if (em.isOpen()){		
				obj.setChanges(this.user.getDescription());
				em.persist(obj);
				resultOperation = true;
				if (obj.isDebugLog()) 
					LogUtilsEdu.LogAudit(new LogEdu(null, obj, idEntity, null, DBAction.NEW, null));
						
			}
		
		} catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, obj, idEntity, null, DBAction.NEW, "ERROR Persistint entity"));
		}				
		
	}
	public void persistObject2(Base obj) {
		
		resultOperation = false;		
		try{
			if (em.isOpen()){		
				obj.setChanges(this.user.getDescription());
				em.persist(obj);
				resultOperation = true;
				if (obj.isDebugLog()) 
					LogUtilsEdu.LogAudit(new LogEdu(null, obj, idEntity, null, DBAction.NEW, null));
						
			}
		
		} catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, obj, idEntity, null, DBAction.NEW, "ERROR Persistint entity"));
		}	
		
	}	
	
	
	
	/**
	 * Persist object. 
	 * {Pre: The object no exist}.
	 * {Post: The object is persist is not exist. 
	 */
	//public void persistObjectDefault(Base obj) {
	public <T extends Base> void persistObjectDefault(T obj) {
		
		resultOperation = false;		
		try{
			if (em.isOpen()){	
				obj.setChanges(this.user.getDescription());
				em.persist(obj);
				resultOperation = true;	
				if (obj.isDebugLog()) 
					LogUtilsEdu.LogAudit(new LogEdu(null, obj, idEntity, null, DBAction.NEW, null));
			
			}
		
		} catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, obj, idEntity, null, DBAction.NEW, "ERROR Persistint entity"));
		}				
		
	}
	
	/**
	 * Update object. 
	 * {Pre: The object exist}.
	 * {Post: The object is update if exist. 
	 */
	//public void updateObject(Base objOriginal, Base obj){
	public <T extends Base> void updateObject(T objOriginal, T obj) {
			
		resultOperation = false;
			
		try{
			if (em.isOpen() && obj != null){										
				
				obj.setChanges(this.user.getDescription());
				em.merge(obj);
				
				resultOperation = true;
				if (obj.isDebugLog()) 
					LogUtilsEdu.LogAudit(new LogEdu(null, obj, idEntity, null, DBAction.UPDATE, null));				

			}
		
		}catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, obj, idEntity, null, DBAction.UPDATE, "ERROR Updating entity"));
		}
	}
	
	/**
	 * Update object. 
	 * {Pre: The object exist}.
	 * {Post: The object is update if exist. 
	 */
	//public void updateObjectDefault (Base obj){
	public  <T extends Base> void updateObjectDefault(T obj) {
		
		resultOperation = false;
				
		try{
			if (em.isOpen() && obj != null){										
				obj.setChanges(this.user.getDescription());			
				em.merge(obj);
				resultOperation = true;
				if (obj.isDebugLog()) 
					LogUtilsEdu.LogAudit(new LogEdu(null, obj, idEntity, null, DBAction.UPDATE, null));		
			}
		
		}catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, obj, idEntity, null, DBAction.UPDATE, "ERROR Updating entity"));
		}
		
	}
	
	/**
	 * Remove object. 
	 * {Pre: The object exist}.
	 * {Post: The object is remove. 
	 */
	//public void removeObject (Base obj) {
	public <T extends Base> void removeObject(T obj) {	
		resultOperation = false;
		
		try{
			
			obj = findObjectPK(obj);
			
			if (em.isOpen() && (obj) != null){
				// To know the user who has deleted it
				obj.setChanges(this.user.getDescription());
				em.merge(obj);
				
				// Delete object
				em.remove(obj);				
				
				resultOperation = true;
				if (obj.isDebugLog()) 
					LogUtilsEdu.LogAudit(new LogEdu(null, obj, idEntity, null, DBAction.DELETE, null));		
							
			}
			
		} catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, obj, idEntity, null, DBAction.DELETE, "ERROR Deleting entity"));	
		}		
	}	
	
	/**
	 * Remove object. 
	 * {Pre: The object exist}.
	 * {Post: The object is remove. 
	 */
	//public void removeObjectDefault (Base obj) {
	public <T extends Base> void removeObjectDefault(T obj) {	
		resultOperation = false;
		
		try{
			
			obj = findObjectPK(obj);
			
			if (em.isOpen() && (obj) != null){
				
				// To know the user who has deleted it
				obj.setChanges(this.user.getDescription());
				em.merge(obj);
				
				// Delete object
				
				em.remove(obj);								
				resultOperation = true;
				if (obj.isDebugLog()) 
					LogUtilsEdu.LogAudit(new LogEdu(null, obj, idEntity, null, DBAction.DELETE, null));		
							
			}
			
		} catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, obj, idEntity, null, DBAction.DELETE, "ERROR Deleting entity"));	
		}		
	}	
	
	/**
	 * Find object of type Base by primary key. 
	 * {Pre: Primary key of object}.
	 * {Post: return object if exist}. 
	 */
	//public Base findObjectPK (Base obj) {
	public <T extends Base> T findObjectPK (T obj) {	
		resultOperation = false;
		
		//Base new_obj = null;
		T new_obj = null;
		
		try{
			if (em.isOpen()){
																							
				new_obj = (T) em.find(obj.getClass(), obj.getId());
				
				resultOperation = true;
				
				if (obj.isDebugLog()) 
					LogUtilsEdu.LogAudit(new LogEdu(null, obj, idEntity, null, DBAction.FIND_PK, null));		
							
			}
			
		} catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, obj, idEntity, null, DBAction.FIND_PK, "ERROR Finding entity by PK"));	
		}		
		
		return new_obj;
	}
	
	/**
	 * Find object by field unique description. 
	 * {Pre: Field description}.
	 * {Post: return object if exist}. 
	 */
	//public Base findObjectDescription (Base obj) {
	public <T extends Base> T  findObjectDescription (T obj) {	
		resultOperation = false;
		
		List <T> lstObjects = null;
		
		T new_base = null;
		
		String hql="select o from " + obj.getClass().getSimpleName() + " o where o.description like :pDescription";
		try {
								
			lstObjects = em.createQuery(hql)
						.setParameter("pDescription", obj.getDescription())
						.getResultList();
			
			if (lstObjects.size() == 1){
			
				new_base = lstObjects.get(0);
				resultOperation = true;
				if (obj.isDebugLog()) 
					LogUtilsEdu.LogAudit(new LogEdu(null, obj, idEntity, null, DBAction.FIND_DESC, null));	
				
			}
		
		}
		catch (NoResultException Nex){
						
			resultOperation = false;
			
			return null;
		
		}
		
		catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, obj, idEntity, hql + "("+ obj.getDescription()+ ")", DBAction.FIND_DESC, "ERROR Finding entity by Description"));	
		}
		
		return new_base;
		
	}
	
	//public void deleteObjects (Base obj) {
	public <T extends Base> void deleteObjects (T obj) {
						
		resultOperation = false;
		String className = obj.getClass().getSimpleName();		
		AnalyzerConsult c = new AnalyzerConsult();
		String whereClause = c.makeWhere(obj);										
		
		String hqlDel="delete from " + className + " " + className +  " " + whereClause;
		String hqlUpd="update " + className + " c " + 
		              " SET c.lastUser='" + this.user.getDescription() + "'" + 
		              " c.data='" + LocalDate.now().toString() + "'" + 
		              whereClause;
		try{
			if (em.isOpen()){										
				
				//Integer i = em.createQuery("delete from " + className + " " + className +  " " + whereClause)
				// First update to know who is the person that has deleted 
				em.createQuery(hqlUpd)
				  .executeUpdate();		
				
				// Then delete
				em.createQuery(hqlDel)
				  .executeUpdate();		
				
				LogUtilsEdu.LogAudit(new LogEdu(null, obj, idEntity, hqlDel , DBAction.DELETE_SOME, ""));	
				
			}
			
		}catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, obj, idEntity, hqlDel , DBAction.DELETE_SOME, "ERROR Deleting entities by criteria"));				
		}
												
	}
	
	/**
	 * Find objects by some criterion. 
	 * {Pre: Some criterion}.
	 * {Post: return List objects if exist}. 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	
	//public List<Base> findObjects (Base pObj) {
	public <T extends Base> List<T> findObjects (T pObj)	{
		T obj = null;
		String hql="";
				
		try {
			obj = (T) ReflectionField.getCorrectObject(pObj);
			
			listObjects = new ArrayList <T>();
		
			resultOperation = false;
			String className = obj.getClass().getSimpleName();		
			AnalyzerConsult c = new AnalyzerConsult();
			String whereClause = c.makeWhere((Base) obj);								
			
			
			hql="select " +  className + " from " + className + " " + className +  " " + whereClause;
			
			if (em.isOpen()){										
																												
				listObjects = em.createQuery(hql)
							.getResultList();								
				LogUtilsEdu.LogAudit(new LogEdu(null, obj, idEntity, hql , DBAction.QUERY_SOME, ""));	
			}
			
		} catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, obj, idEntity, hql , DBAction.QUERY_SOME, "ERROR Querying entities by criteria"));			
		}
				
		if (listObjects.size() > 0) resultOperation = true;
		
		return (List<T>) listObjects;
	}		
	
	/**
	 * Find object by field unique description. 
	 * {Pre: Field description}.
	 * {Post: return object if exist}. 
	 */
	//public List<Object[]> findObjectPerson (String pSentencia) {
	public <T extends Object> List<T[]> findObjectPersonalized (String pSentencia) {						
		resultOperation = false;
		T obj=null;
		
		try{
			if (em.isOpen()){										
																								
				//System.out.println("select " +  className + " from " + className + " " + className + " " + whereClause + " order by " + className + ".getDescription");				
				
				List<T> lstObjects = em.createQuery(pSentencia).getResultList();																
				
				if (lstObjects.size() > 0) {
					resultOperation = true;
					obj=lstObjects.get(0);
				}
				
				
				LogUtilsEdu.LogAudit(new LogEdu(null, (Base) obj, idEntity, pSentencia , DBAction.QUERY_SOME, ""));									
				
				return (List<T[]>) lstObjects;
			}
			
		}catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, (Base) obj, idEntity, pSentencia , DBAction.QUERY_SOME, "ERROR Querying entities personalised"));
		}
						
		return null;		
	}
	
	//public List<Base> findObjectPerson2 (String pSentencia) {
	public <T extends Base> List<T> findObjectPersonalized2 (String pSentencia) {
		resultOperation = false;
		T obj=null;
		try{
			if (em.isOpen()){										
																								
				//System.out.println("select " +  className + " from " + className + " " + className + " " + whereClause + " order by " + className + ".getDescription");				
				
				List<T> lstObjects = em.createQuery(pSentencia).getResultList();																
				
				if (lstObjects.size() > 0) {
					resultOperation = true;
					obj=lstObjects.get(0);
				}
				
				
				LogUtilsEdu.LogAudit(new LogEdu(null, obj, idEntity, pSentencia , DBAction.QUERY_SOME, ""));									
				
												
				return lstObjects;
			}
			
		}catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, obj, idEntity, pSentencia , DBAction.QUERY_SOME, "ERROR Querying entities personalised"));
		}
						
		return null;		
	}
	
	public void executeSQL (String pSentencia){
		
		resultOperation = false;
				
		try{
			if (em.isOpen()){										
																											
				int result = em.createQuery(pSentencia).executeUpdate();																
				LogUtilsEdu.LogAudit(new LogEdu(null, null, idEntity, pSentencia , DBAction.SQL_SOME, "RESULT:" + result));		
				System.out.println(" RESULT " + result);
			}
			
		}catch(Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, null, idEntity, pSentencia ,  DBAction.SQL_SOME, "ERROR executeSQL"));			
		}
		
		
	}
	
	/**
	  * Procedure that starts the transaction
	  * {Pre: There is an open connection to the database}.
	  * {Post: has initiated the transaction}.
	  * @throws DataException if there isn't a connection to the database.
	  * @throws DataException if there is an error to start the transaction.
	  */
	  public void begin() {
	    
		
		try {
			
			if (em.getTransaction().isActive()) return;
						
			em.getTransaction().begin();
			LogUtilsEdu.LogAudit(new LogEdu(null, null, idEntity, null ,  DBAction.BEGIN_TRANS, null));		
			
			
		} catch (Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, null, idEntity, null , DBAction.BEGIN_TRANS, "ERROR Begin Trans"));	
		}
		
		return;
	  }	
	
	  /**
		* Result of the operation. 
		*/
	  public boolean isResultOperation() {
		
		  return resultOperation;
	  
	  }	
	  
	  
	  /**
		* Procedure that end the transaction accepting updates.
		* {Pre: There is an open connection to the database}.
		* {Post: End the transaction}.
		* @throws DataException if there isn't a connection to the database.
		* @throws DataException if there is an error to end the transaction.
		*/
	  public void commit() {
		
		
		try{  
				
			if ( ! factory.isOpen() && ! em.isOpen()); 				
				
		} catch (Exception ex){
			LogUtilsEdu.LogError(new LogEdu(ex, null, idEntity, null , DBAction.COMMIT_TRANS, "ERROR Commit Trans"));	
		}
		  
		if ( ! em.getTransaction().isActive() )
			  
			return;
		  
		try {
			em.getTransaction().commit();
			LogUtilsEdu.LogAudit(new LogEdu(null, null, idEntity, null , DBAction.COMMIT_TRANS, null));						  	
		
		}catch (Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, null, idEntity, null , DBAction.COMMIT_TRANS, "ERROR Commit Trans"));	
		}
		  
		return;
	  }
	  

	  /**
		* Procedure that end the transaction not accepting updates.
		* {Pre: There is an open connection to the database}.
		* {Post: End the transaction}.
		* @throws DataException if there isn't a connection to the database.
		* @throws DataException if there is an error to end the transaction.
		*/
	  public void rollback() {
		 
		 
		 try{  
				
			  if ( ! factory.isOpen() && ! em.isOpen()); 
				
		} catch (Exception ex){
			LogUtilsEdu.LogError(new LogEdu(ex, null, idEntity, null , DBAction.ROLL_BACK_TRANS, "ERROR Roll Back Trans"));
		}

		if ( ! em.getTransaction().isActive() )
		  return;
		
		try {
			
			em.getTransaction().rollback();
			LogUtilsEdu.LogAudit(new LogEdu(null, null, idEntity, null , DBAction.ROLL_BACK_TRANS, null));			
			
			
		} catch (Exception ex) {
			LogUtilsEdu.LogError(new LogEdu(ex, null, idEntity, null , DBAction.ROLL_BACK_TRANS, "ERROR Roll Back Trans"));
		}
		
		return;
	  }
	  
	  /**
		* Procedure that closed the connection to database. 
		* {Pre: there isn't more references to object in memory}.
		* {Post: Closed the connection to database}. 
		*/
	  public void finalize() {								
		
		em.close();
			
		factory.close();				
		
	  }	  	  	
	
	  
	/**Getters and setters*/ 
	
	
	/**Instance associated with a persistence context*/
	public EntityManager getEntityManager(){
		
		return em;
	}
		
		
	/** Edu 5/12/2017
	 * to know if a table is empty or not using the entity
	 * 
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException **/
	public <T extends Base> boolean isEmpty(T pObj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		Object	obj = ReflectionField.getCorrectObject(pObj);
		return isEmpty(obj.getClass());
		
	}
	/**
	 * To know if a table is empty knowing the class of the entity
	 */
	public  boolean isEmpty(Class<?> klass) {
		return
				em.createQuery("select 1 from " + klass.getSimpleName())
					.setMaxResults(1)
					.getResultList()
					.size() == 0;
	}


	@Override
	/**
	 * base is a class that implements Base
	 * myDate is the LocalDateTime date parameter that specifies records to be deleted
	 * We need to record who has deleted the file so we do not use CriteriaDelete
	 * in https://www.thoughts-on-java.org/criteria-updatedelete-easy-way-to/
	 */
	public <T extends Base> void deleteOlderThan( Class<T> valueType, LocalDateTime myDate) {
		if (myDate !=null) {
			
			CriteriaBuilder qb = em.getCriteriaBuilder();
			
			//@SuppressWarnings("rawtypes")
			CriteriaQuery<T> cq = qb.createQuery(valueType);
			
			//if (valueType.isAssignableFrom(Audit))
			Root<T> rootBase = cq.from(valueType);
			
			//Constructing list of parameters
			List<Predicate> predicates = new ArrayList<Predicate>();

			//Adding predicates in case of parameter not being null
			if (myDate != null) {
				predicates.add(
					qb.lessThan(rootBase.get("auditData"), myDate));
					
			}
			
			//query itself
			cq.select(rootBase)
	            .where(predicates.toArray(new Predicate[]{}));
			
			//execute query and do something with result
			for ( T myBase : (List<T>)em.createQuery(cq).getResultList())
				this.removeObject(myBase);
		}
	}
	/** 
	 * If an object exits, it is updated, else it is inserted as a new object
	 * and return the object with the correct id
	 */
	public <T extends Base> T persist(T t) {
		T obj=this.findObjectDescription(t);
		if (obj==null) {
			this.persistObject(t);
			// t gets its id updated with autoincrement
			//obj=connection.findObjectDescription(t); // NO need to get id value
			obj=t;
		} else {
			t.setId(obj.getId());
			this.updateObjectDefault(t);
		}
		//System.out.println(obj.toString());
		return obj;
	}
}
