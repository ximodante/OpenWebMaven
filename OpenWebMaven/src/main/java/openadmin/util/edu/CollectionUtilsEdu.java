package openadmin.util.edu;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class CollectionUtilsEdu {
	
	/**
	 * Extracts the element whose order is index from a collecion
	 * @param col
	 * @param index
	 * @return
	 */
	public static <T extends Object> T get (Collection<T> col, int index) {
		T obj=null;
		Iterator<T> iter = col.iterator();
		if (col!=null && col.size()>index) {
			for (int i=0; i<=index; i++) 
				obj=iter.next();
		}
		return obj;
	}
	
	/**
	 * Extracts the element whose order is index from a list
	 * @param lst
	 * @param index
	 * @return
	 */
	public static <T extends Object> T get (List<T> lst, int index) {
		if (lst!=null && lst.size()>index) return lst.get(index);
		else return null;
	}
	
	/**
	 * Extracts the first element from a list
	 * @param lst
	 * @return
	 */
	public static <T extends Object> T getFirst (List<T> lst) {
		if (lst!=null && !lst.isEmpty()) return lst.get(0);
		else return null;
	}
	
	/**
	 * Extratcs the first element from a collection
	 * @param col
	 * @return
	 */
	public static <T extends Object> T getFirst (Collection<T> col) {
		if (col!=null && !col.isEmpty()) return col.iterator().next();
		else return null;
	}
	
	/**
	 * Update the fields indicated in the fields array to all elements of the list
	 * @param lst
	 * @param fields
	 * @param values
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws RuntimeException
	 * @throws IntrospectionException
	 */
	public static <T extends Object> void updateListFields(List<T> lst, String[] fields, Object[] values) throws IllegalAccessException, InvocationTargetException, RuntimeException, IntrospectionException {
		for (T elem: lst) {
			for (int i=0; i<fields.length; i++) {
				ReflectionUtilsEdu.setProperty(elem, fields[i], values[i]);
			}
		}
	}

	public static void main(String[] args) {
		LocalDateTime now=LocalDateTime.now();
		System.out.println(StringUtils.removeAll(now.toString(), "[\\-:T\\.]"));

	}

}
