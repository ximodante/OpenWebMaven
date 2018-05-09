package openadmin.model.log;

import java.io.Serializable;
import java.lang.StackWalker.StackFrame;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import openadmin.dao.operation.DBAction;
import openadmin.model.Base;
import openadmin.util.edu.DateUtilsEdu;
import openadmin.util.edu.JSFUtils;
import openadmin.util.edu.StringUtilsEdu; 

/**
 * This class contains common data to all logs
 * @author eduard
 *
 */
@NoArgsConstructor
@ToString
public class LogEdu implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 20180101L;

	// Date and time of the action/error
	@Getter @Setter
	@NotNull
	//private  LocalDateTime date=null;
	private  String date=null;
	
	// inet address of the caller
	@Getter @Setter
	private String inet=null;	

	@Getter
	private String sessionId=null;
	
	@Getter
	private String userAgent=null;
	// User name
	@Getter @Setter
	@NotNull
	private String user=null;
	
	// Stack of all the classes and procedures  involved in the call to the dbaction
	@Getter 
	@NotNull
	private String[] invocationStack;
	
	//Entity whose persistence is being audited
	@Getter @Setter
	private String entity=null;
	
	//Id of the entity to persist
	@Getter 
	private Long id=null;
		
	
	//Enterprise (entityAdm class) being audited
	@Getter @Setter
	private Short entityAdm=null;
	
	
	//HQL sentence
	@Getter @Setter
	private String hql=null;
	
	/**
	 * DB_CONNECT, DB_DISCONNECT, 
	 * NEW, NEW_SOME, 
	 * UPDATE, UPDATE_SOME, 
	 * DELETE, DELETE_SOME, 
	 * FIND_PK, FIND_PK_SOME,
	 * FIND_DESC, FIND_DESC_SOME,
	 * QUERY,QUERY_SOME,
	 * SQL, SQL_SOME
	 * BEGIN_TRANS, COMMIT_TRANS, ROLL_BACK_TRANS
	 */
	@NotNull
	private DBAction dbAction=null;
	
	@Getter @Setter
	private String errorMessage=null;
	
	@Getter @Setter
	private String notes;
	
	/*********************************************************************
	  Method definitions
	**********************************************************************/
	/**
	 * Constructors
	 */
	
	public <T extends Base> LogEdu(Throwable e, T obj, Short entityAdm, String hql, DBAction dbAction, String notes ) {
		this.date=DateUtilsEdu.getDateTime();
		this.inet=JSFUtils.getClientAddress();
		this.user=JSFUtils.getUser();
		if (dbAction==DBAction.DB_CONNECT) this.userAgent=JSFUtils.getUserAgent();
		this.sessionId=JSFUtils.getSessionId();
		this.setBaseObjectInfo(obj);
		if (e==null) setMaxInvocationStackEdu(2);
		else {
			this.setErrorInfo(e);
			e.printStackTrace();
		}
		this.entityAdm=entityAdm;
		this.hql=hql;
		this.dbAction=dbAction;
		this.notes=notes;
		//System.out.println(this.toString());
		
	}
	
	
	
	/**
	 * Get Error info and trace of error
	 * @param e
	 */
	private void setErrorInfo(Throwable e) {
		
		String[] s=ExceptionUtils.getRootCauseStackTrace(e);
		int len=s.length;
		this.errorMessage=s[0];
		this.invocationStack=new String[len-1];
		for(int i=1; i<len; i++) {
			this.invocationStack[i-1]=s[i];
		}
		
	}
	
	/**
	 * gets object and idobject from a Base Object
	 * @param myEntity
	 */
	private <T extends Base> void setBaseObjectInfo(T myEntity) {
		if (myEntity==null) {
			this.entity=null;
			this.id=null;
		} else {
			this.entity=myEntity.getClass().getSimpleName();
			if(myEntity.getId()!=null) this.id= myEntity.getId().longValue();
			else this.id=null;
		}
	}
	
	public void setMyId(Number pId) {
		this.id = pId.longValue();
	}
	
	public Number getMyId() {
		return this.id;
	}
	
	/**
	 * Get trace from the formIndex Element. Element 0 is this class.procedure
	 * Java 9 - JEP 259: Stack-Walking API
	 * https://stackoverflow.com/questions/421280/how-do-i-find-the-caller-of-a-method-using-stacktrace-or-reflection
	 * @param fromIndex
	 */
	/*
	private void setInvocationStackEdu(int fromIndex) { 
		
		StackWalker sw = StackWalker.getInstance();
    	List<StackFrame> ls=new ArrayList<StackFrame>();
    	sw.forEach(ls::add);
    	//this.invocationStack=ls.subList(fromIndex, ls.size()).toArray(new String[0]);
    	this.invocationStack=
    		ls.stream()
    			.skip(fromIndex)
    			.map(e->e.toString())
    			.toArray(String[]::new);
	}
	*/
	
	/**
	 * Get trace count elements excluding current class elements
	 * Java 9 - JEP 259: Stack-Walking API
	 * https://stackoverflow.com/questions/421280/how-do-i-find-the-caller-of-a-method-using-stacktrace-or-reflection
	 * @param count
	 */
	private void setMaxInvocationStackEdu(int count) { 
		
		StackWalker sw = StackWalker.getInstance();
    	List<StackFrame> ls=new ArrayList<StackFrame>();
    	sw.forEach(ls::add);
    	
    	this.invocationStack=
    		ls.stream()
    			.filter(e-> ! e.getClassName().contains(this.getClass().getCanonicalName()))
    			.limit(count)
    			.map(e->StringUtilsEdu.subStringFromRight(e.toString(),".",2))  // Only obtain class.method 
    			.toArray(String[]::new);
    }
	
	public static void main(String[] args) {
		System.out.println("ta tia");
		System.out.println(new LogEdu(null, null, 1, "SELECT * FROM KK", DBAction.FIND_PK, "Prova").toString());
	}
	
}
