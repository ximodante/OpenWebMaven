package openadmin.util.configuration;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

import openadmin.dao.operation.DaoJpaEdu;
import openadmin.dao.operation.DaoOperationFacadeEdu;
import openadmin.model.Base;
import openadmin.model.control.User;
import openadmin.util.edu.ClassAndFieldsEdu;
import openadmin.util.edu.FileUtilsEdu;
import openadmin.util.edu.PropertyUtilsEdu;
import openadmin.util.edu.ReflectionUtilsEdu;
import openadmin.util.edu.StringUtilsEdu;
import openadmin.util.lang.LangTypeEdu;

public class FirstControlLoadEdu {
	private static User firstLoadUser = new User("FirstLoader","123456","First Load User");
	//private static DaoOperationFacadeEdu connectionLog = null;	
	//private static LogOperationFacadeEdu log =null; 
	private static DaoOperationFacadeEdu connection = null; 	
	
	
	
	
	// Route to src/main/resources
	private static final String Path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	
	private static final String PropertyPath = Path + "properties/importcsv.properties";
	
	//private static final String CSVPath = Path + "config_data/";
	// Property file
	private static final Properties Props=PropertyUtilsEdu.getPropertiesNoException(PropertyPath);
	
	// Comment char
	private static final String CommentString=Props.getProperty("comment");
	
	// Folder from src/main/resources where data in csv format is stored
	private static final String DataFolder=Props.getProperty("data_folder");
	
	// csv delimiter in the file
	private static final String FileDelimiter=Props.getProperty("delimiter");
	
	// comma delimiter
	private static final String commaDelimiter=",";
			
	// number of classes or files to be loaded/populated and persisted
	private static final int NFiles=Integer.parseInt(Props.getProperty("class_count"));
	
	//Apache Expressions parser
	private static JexlEngine jexlEngine = new JexlBuilder().create();
	
	// Create a context and add data
	private static Map<String, Object> baseJexlContext = new HashMap<String, Object>();
    
	/*
	// if a line to read is good or not
	private static boolean isGoodLine(String line) {
		line=line.trim();
		return(!line.startsWith(CommentString) && line.length()>0);
	}
	*/
	
	//Get from property file the classes to be read from csv files
	private static List<ClassAndFieldsEdu> getConfigContent() throws FileNotFoundException, IOException{
		List <ClassAndFieldsEdu> myList= new ArrayList<ClassAndFieldsEdu>();
		
		// Fill class properties
		for (int i=1; i<=NFiles; i++) {
			
			String prefix="class." + String.format("%02d", i) + ".";
			
			//1. Read the class name 
			String clssName=Props.getProperty(prefix + "name");
			
			//2. Read the package name of the class
			String pckgName=Props.getProperty(prefix + "package");
			
			//3. Read the fields to read from the csv file
			String fields=Props.getProperty(prefix + "fields");
			
			//4. Read the expressions
			String expressions=Props.getProperty(prefix + "expressions",null);
			
			//5. Read group fields
			String groupFields=Props.getProperty(prefix + "groupFields",null);
			
			//6. Read group expressions
			String groupExpressions=Props.getProperty(prefix + "groupExpressions",null);
			
			//7. Read group begin
			int groupBeginPos=Integer.parseInt(Props.getProperty(prefix + "groupBeginPos","-1"));
			
			//8. Read group length
			int groupLength=Integer.parseInt(Props.getProperty(prefix + "groupLength","-1"));
			
			
			//9. Read the file name 
			String fileName=Props.getProperty(prefix + "fileName");
			
			
			myList.add(new ClassAndFieldsEdu(clssName, pckgName, fields, expressions, groupFields, 
					                          groupExpressions, groupBeginPos, groupLength, fileName )); 
		}
		return myList;
	}
	
	/**
	 * Read information from csv files and persists in the DB
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws IntrospectionException 
	 * @throws RuntimeException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	
	//public static void PersistConfiguration(EntityManagerFactory emf) throws ClassNotFoundException, IOException {
	//public static void PersistConfiguration() 
	public static void dataLoad()
			throws ClassNotFoundException, IOException, IntrospectionException, InstantiationException, 
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, RuntimeException {	
		
		LangTypeEdu langType = new LangTypeEdu();
		langType.changeMessageLog(TypeLanguages.es);
		
		
		//1.0- Open connections
		//connectionLog = new DaoJpaEdu(firstLoadUser, "log_post", null, langType);	
		//log = new LogDaoEdu(connectionLog, "control", langType);
		connection = new DaoJpaEdu(firstLoadUser, "control_post", (short) 0,langType);
		
		//baseJexlContext.put("jpa", connection );
		baseJexlContext.put("integer", Integer.valueOf(1) );
		baseJexlContext.put("localDate", LocalDate.now());
		
	
		int i=0;
		for (ClassAndFieldsEdu cf: getConfigContent()) {
			connection.begin();
			populateClass(cf,i++);
			connection.commit();	
			i++;
		}
		
		//log.finalizeLog();
		connection.finalize();
		
	}
	/**
	 * Populates only one class a a time
	 * @param cf
	 * @param i
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws IntrospectionException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws RuntimeException
	 */
	private static void  populateClass(ClassAndFieldsEdu cf, int i) 
			throws ClassNotFoundException, FileNotFoundException, IOException, IntrospectionException, 
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, 
			RuntimeException {
		
		String myClassName=cf.getName();
		String MyFullClassName=cf.getPackageName() + "." + myClassName;
		//Class<? extends Base> myClass = (Class<? extends Base>) Class.forName(MyFullClassName);
		Class<?> myClass = Class.forName(MyFullClassName);
		String myReadFields=cf.getReadFields();
		String myExpressions=cf.getExpressions();
		String myGroupFields=cf.getGroupFields();
		String myGroupExpressions=cf.getGroupExpressions();
		int groupBeginPos=cf.getGroupBeginPos();
		int groupLenght=cf.getGroupLength();
		
		String fileName=Path + DataFolder + "/";
		if (cf.getFileName()==null) fileName=fileName+ myClassName + "Data.txt";
		else fileName=fileName+ cf.getFileName();
		
		System.out.println();
		System.out.println("---------------------------------------------------------------------------------------------------------");
		System.out.println(i + ". Pesisting " + myClassName + " from " + fileName);
		System.out.println("---------------------------------------------------------------------------------------------------------");
		
		PropertyDescriptor[] propsDesc=ReflectionUtilsEdu.getPropertiesDescriptor(myClass);
		// Use default variables
		JexlContext context = new MapContext(baseJexlContext);
		//JexlContext context = new MapContext();
		//context.set("integer", new Integer(1) );
		//context.set("localDate", LocalDate.now());
		
		
		for (String s[]: FileUtilsEdu.ReadData(fileName, FileDelimiter, CommentString)){
			List<Base> myList=
				readBean(myClass, propsDesc, context, myReadFields, myExpressions, 
						myGroupFields, myGroupExpressions, groupBeginPos, groupLenght, s);
			for(Base obj: myList)
				myPersist(obj);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T extends Base> List<T> readBean (Class<?> pClass, PropertyDescriptor[] propsDesc, 
			JexlContext context, String myReadFields, String myExpressions, String myGroupFields, String myGroupExpressions,
			int groupBeginPos,	int groupLenght, String[] values) 
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, 
			       ClassNotFoundException, RuntimeException, IntrospectionException {
		
		List<T> myList=new ArrayList<T>();
		
		
		String[] fields=StringUtilsEdu.splitAndTrim(myReadFields,commaDelimiter);
		String[] expressions=null;
		Object[] objs=values;
		
		 
		// If there are expressions, should be evaluated
		if (myExpressions!=null) {
			expressions=StringUtilsEdu.splitAndTrim(myExpressions,commaDelimiter);
			context.set("value", values);
			objs=new Object[expressions.length];
			int i=0;
			for (String expression: expressions) {
				JexlExpression expr=jexlEngine.createExpression(expression);
				objs[i++]=expr.evaluate(context);
				//System.out.println("objs[" + (i-1) + "].class=" + objs[i-1].getClass() + "=" + objs[i-1].toString());
			}
		}
		// See if there are groups
		String[] groupFields=null;
		String[] groupExpressions=null;
		boolean isGroup=false;
		if (myGroupFields!=null && groupBeginPos>=0) {
			groupFields=StringUtilsEdu.splitAndTrim(myGroupFields,commaDelimiter);
			groupExpressions=StringUtilsEdu.splitAndTrim(myGroupExpressions,commaDelimiter);
			if (groupFields.length>0) isGroup=true;
		}
		
		if (isGroup) {
			// Extract groups
			List<String[]> lstGroup=StringUtilsEdu.extractGroups(values, groupBeginPos, groupLenght);
			for(String[] gValues: lstGroup) {
				if (gValues[0].trim().length()>0) {
					// create an object for each group
					T myBean=(T) ReflectionUtilsEdu.createObject(pClass);
					context.set("gValue", gValues);
					int i=0;
					for (String gField: groupFields) {
						JexlExpression expr=jexlEngine.createExpression(groupExpressions[i]);
						Object obj=expr.evaluate(context);
						setProperty(myBean,gField,obj, propsDesc);
						i++;
					} 
					int j=0;
					for(String fld:fields) setProperty(myBean,fld,objs[j++], propsDesc);
					myList.add(myBean);   
				}
			}
		// if not group
		} else {
			T myBean=(T)ReflectionUtilsEdu.createObject(pClass);
			int j=0;
			for(String fld:fields) setProperty(myBean,fld,objs[j++], propsDesc);
			myList.add(myBean);
		}
		
		return myList;
		
	}
	
	private static void setProperty(Object oBean, String fieldName, Object fieldValue, PropertyDescriptor[] propsDesc) 
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, RuntimeException,
			       IntrospectionException, InstantiationException, ClassNotFoundException {
		
		PropertyDescriptor propDesc=ReflectionUtilsEdu.getPropertyDescriptor(propsDesc, fieldName);
		Class<?> klass=propDesc.getPropertyType();
		
		//System.out.println("fieldValue=" + fieldValue.getClass()+" = "+ fieldValue.toString());
		String myValue=fieldValue.toString().trim();
		Object myFieldValue=fieldValue;
		
		//System.out.println(fieldName + "-_>" + myValue + " ----" + propDesc.getName() );
		if (Base.class.isAssignableFrom(klass)) {
			
			myFieldValue=connection.findObjectDescription(
					(Base)(ReflectionUtilsEdu.newObjectByDescription(klass,myValue)));
								
		} else if (klass.equals(String.class))	{
			myFieldValue=myValue;
		} else {
			//System.out.println("klass ->" + klass.getCanonicalName() );
			if (myFieldValue.getClass().equals(String.class))
				myFieldValue=ConvertUtils.convert(myFieldValue, klass);
				
		}
		ReflectionUtilsEdu.setProperty(oBean, propDesc, myFieldValue);
		//System.out.println(oBean.toString());
		
	}
	 
		
	private static <T extends Base> void myPersist(T obj) {
		if (connection.findObjectDescription(obj) == null) {
			System.out.println("Persisting..." + obj.toString());
			connection.persistObject(obj);
		}
	}
	
	public static void main(String[] args) {
		try {
			//PersistConfiguration();
			dataLoad();
		} catch (ClassNotFoundException | IOException | InstantiationException 
				| IllegalAccessException | InvocationTargetException | NoSuchMethodException 
				| IntrospectionException | RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}

