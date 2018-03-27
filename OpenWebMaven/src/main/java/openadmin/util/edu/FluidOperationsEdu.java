package openadmin.util.edu;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
//import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;

import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.beanutils.ConstructorUtils;
//import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.reflect.FieldUtils;

import lombok.Getter;
import openadmin.dao.operation.DaoOperationFacadeEdu;
import openadmin.model.Base;

/**
 * @see https://en.wikipedia.org/wiki/Fluent_interface
 * @author eduard
 *
 */
public class FluidOperationsEdu {
	
	@Getter private Map <String, String> map=null;
	@Getter private Base myBean=null;
	@Getter private List<Base> myBeans = new ArrayList<Base>();
	@Getter private String myString=null;
	@Getter private String[] myStringArray=null;
	
	private PropertyDescriptor[] propsDesc=null;
	
	private String fieldDelimiter=",";
	private String fileValueDelimiter="\\|";
	private String commentStr="#";
	
	//private EntityManager em=null;
	private DaoOperationFacadeEdu connection = null; 
	
	/**
	public FluidOperations(EntityManager pEm) {
		this.em=pEm;
	}
	**/
	
	public FluidOperationsEdu(DaoOperationFacadeEdu pConnection) {
		this.connection=pConnection;
	}
	
	private static Object DefaultValue (String key) {
		Object obj;
		switch (key.toLowerCase()) {
         	case "date"   : obj=LocalDate.now(); break;
         	case "true"   : obj=new Boolean("true"); break;
         	case "false"  : obj=new Boolean("false"); break;
         	default: obj=key;
		}
		return obj;
	}
	
	private void createMyBean(Class<? extends Object> pClass) 
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, 
			       NoSuchMethodException, SecurityException, ClassNotFoundException, IntrospectionException {
		//this.myBean=ConstructorUtils.invokeConstructor(pClass, null);
		this.myBean=(Base)ReflectionUtilsEdu.createObject(pClass);
		this.propsDesc=ReflectionUtilsEdu.getPropertiesDescriptor(this.myBean);
	}
	
	private void setProperty(Object oBean, String fieldName, Object fieldValue) 
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, RuntimeException,
			       IntrospectionException, InstantiationException, ClassNotFoundException {
		
		PropertyDescriptor propDesc=ReflectionUtilsEdu.getPropertyDescriptor(this.propsDesc, fieldName);
		Class klass=propDesc.getPropertyType();
		//Field fld=FieldUtils.getDeclaredField(this.myBean.getClass(), fieldName, true);
		//Class klass=fld.getType();
				
		String myValue=fieldValue.toString().trim();
		Object myFieldValue=fieldValue;
		
		System.out.println(fieldName + "-_>" + myValue);
		if (Base.class.isAssignableFrom(klass)) {
			/**
			String qry="SELECT o FROM " + klass.getSimpleName() + " o WHERE o.description LIKE :pDescription";
			System.out.println(qry);
			
			List<Base> lBase=
				em.createQuery("SELECT o FROM " + klass.getSimpleName() + " o WHERE o.description LIKE :pDescription")
					.setParameter("pDescription", myValue)
					.setMaxResults(1)
					.getResultList();
			
			myFieldValue=lBase.get(0);
			**/
			
			myFieldValue=this.connection.findObjectDescription(
					(Base)(ReflectionUtilsEdu.newObjectByDescription(klass,myValue)));
			
						
		} else if (klass.equals(String.class))	{
			myFieldValue=myValue;
		} else {
			System.out.println("klass ->" + klass.getCanonicalName() );
		}
		ReflectionUtilsEdu.setProperty(oBean, this.propsDesc, fieldName, myFieldValue);
		//FieldUtils.writeField(fld, this.myBean, myFieldValue, true);
		System.out.println(this.myBean.toString());
		
	}
	
	private void setProperty(String fieldName, Object fieldValue) 
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, 
			       RuntimeException, IntrospectionException, InstantiationException, 
			       ClassNotFoundException {
		this.setProperty(this.myBean, fieldName, fieldValue);
	}
	
	// çç A groupField is formed of :g-field1-field2-..-fieldN 
	// Begin with g: to indicate that it is a group and not a field.
	// The fields of a group are separated by "-"
	// A new entity is created for each group
	// Only 1 group is allowed and it should be the last element of the bean
	private void createBeanArray(String groupField, String[] groupValues) 
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, 
			       RuntimeException, IntrospectionException, InstantiationException, 
			       ClassNotFoundException {
		String[] groupFields=groupField.split("-");
		int i=0;
		
		for (String value :  groupValues) { // Arrays.copyOfRange(groupValues, 1, groupValues.length)) {
			String sValue=value.trim();
			if (sValue.length()==0) break;
			this.setProperty(groupFields[++i].trim(), sValue);
			if (i==groupFields.length-1) {
				this.myBeans.add(myBean);
				i=0;
			}
		}
	}
	
	public FluidOperationsEdu clear() {
		this.myBean=null;
		this.myBeans.clear();
		this.myString=null;
		this.myStringArray=null;
		
		return this;
		
	}
	
	public FluidOperationsEdu stringArraysToMap (String[] pValues, String[] pFieldNames) {
		this.map =IntStream.range(0, pFieldNames.length)
				.boxed()
                .collect(Collectors.toMap(i -> pFieldNames[i], i -> pValues[i].trim()));
		return this;
	}
	
	public FluidOperationsEdu mapToBean(Class<? extends Object> pClass, Map<String, String> map, boolean pNewBean) 
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, 
		       IllegalArgumentException, SecurityException, ClassNotFoundException, IntrospectionException  {
		
		if (pNewBean) this.createMyBean(pClass);
		BeanUtils.populate(this.myBean, map);
		return this;
	}
	
	//çç
	public FluidOperationsEdu stringArraysToBean (Class<? extends Object> pClass, String[] pValues, String[] pFieldNames, boolean pNewBean) 
		throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, 
		       IllegalArgumentException, SecurityException, ClassNotFoundException, IntrospectionException	{	
	
		if (pNewBean) this.createMyBean(pClass);
		
		int i=0;
		for(String s: pFieldNames) 	{
			// Si el field name es un grup
			if (StringUtils.startsWith(s.trim(),":g-")) {
				this.createBeanArray(s, Arrays.copyOfRange(pValues, i, pValues.length));
				break;
			} else this.setProperty(s.trim(), pValues[i++].trim());
		}
		
		return this;
	}
	
	public FluidOperationsEdu csvStringsToBean (Class<? extends Object> pClass, String pFieldNames, boolean pNewBean) 
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, 
			       IllegalArgumentException, SecurityException, ClassNotFoundException, IntrospectionException	{	
		
		if (this.myString==null ||pFieldNames==null) return this;	
		
		String [] aFieldNames=pFieldNames.split(this.fieldDelimiter);
		String [] aValues    =this.myString.split(this.fileValueDelimiter);
			
		return this.stringArraysToBean(pClass, aValues, aFieldNames, pNewBean);
	}
	
	
	public FluidOperationsEdu csvStringsToBean (Class<? extends Object> pClass, String pValues, String pFieldNames, boolean pNewBean) 
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, 
			       IllegalArgumentException, SecurityException, ClassNotFoundException, IntrospectionException	{	
		
		if (pValues==null ||pFieldNames==null) return this;	
		this.myString=pValues;
		return this.csvStringsToBean (pClass, pFieldNames, pNewBean); 
	}
	
	public FluidOperationsEdu csvStringsToBean (Class<? extends Object> pClass, String pValues, String pFieldNames, String valSep, String fldSep, boolean pNewBean) 
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, 
			       IllegalArgumentException, SecurityException, ClassNotFoundException, IntrospectionException	{	
		this.fieldDelimiter=fldSep;
		this.fileValueDelimiter=valSep;
		this.myString=pValues;
			
		return this.csvStringsToBean (pClass, pFieldNames, pNewBean); 
	}
	
	public FluidOperationsEdu csvStringsToBean (Class<? extends Object> pClass, String pFieldNames, String valSep, String fldSep, boolean pNewBean) 
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, 
			       IllegalArgumentException, SecurityException, ClassNotFoundException, IntrospectionException	{	
			
		return this.csvStringsToBean(pClass, this.myString, pFieldNames, valSep, fldSep, pNewBean);
	}
	public FluidOperationsEdu defaultStringArraysToBean (Class<? extends Object> pClass, String[] pValues, String[] pFieldNames, boolean pNewBean) 
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException,
			       IllegalArgumentException, SecurityException, ClassNotFoundException, IntrospectionException	{	
		
		if (pNewBean) this.createMyBean(pClass);
		int i=0;
			
		for(String s: pFieldNames) {
			Object oValue=DefaultValue(pValues[i++]);
			this.setProperty(s, oValue);
			for(Object oBean: this.myBeans) setProperty(oBean, s, oValue);
		}
		return this;
	}
	
	public FluidOperationsEdu csvDefaultValueStringsToBean (Class<? extends Object> pClass, String pValues, String pFieldNames, boolean pNewBean) 
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException,
                   IllegalArgumentException, SecurityException, ClassNotFoundException, IntrospectionException	{	
			
		    if (pValues==null ||pFieldNames==null) return this;
			String [] aFieldNames=pFieldNames.split(this.fieldDelimiter); //Comma (,) as separator in both cases
			String [] aValues    =pValues.split(this.fieldDelimiter);     //Comma (,)
			
			return this.defaultStringArraysToBean(pClass, aValues, aFieldNames, pNewBean);
	}
	
	public FluidOperationsEdu removeComment(String line) { 
		this.myString=StringUtils.substringBefore(line,this.commentStr);
		return this;
	}
	
    public FluidOperationsEdu removeComment(String line, String pComment) { 
		this.commentStr=pComment;
		return this.removeComment(line);
	}
	
	public FluidOperationsEdu split (String pDelimiter) {
		this.myStringArray=this.myString.split(pDelimiter);
		return this;
	}
	
	public FluidOperationsEdu setDelimiters(String pFieldDelimiter, String pFileValueDelimiter) {
		this.fieldDelimiter=pFieldDelimiter;
		this.fileValueDelimiter=pFileValueDelimiter;
		return this;
	}
	
	public FluidOperationsEdu persist() {
		System.out.println("Persisting");
		if (this.myBeans.size()==0)  this.myBeans.add(myBean);
		//for(Object obj: this.myBeans) em.persist(obj);
		
		for(Base obj: this.myBeans) {
			if (connection.findObjectDescription(obj) == null){
				connection.persistObject(obj);
				System.out.println("....Persisting:" + obj.toString());	
			}
		}
		return this;
	}
}
