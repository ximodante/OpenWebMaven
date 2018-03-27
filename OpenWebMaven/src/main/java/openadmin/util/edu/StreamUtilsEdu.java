package openadmin.util.edu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;


public class StreamUtilsEdu {
	/**
	 * 1. Reads a delimited file
	 * 2. Extracts lines that doesn't begin with a comment symbol
	 * 3. Discards the trailing part of a line that has a comment symbol
	 * 4. Transform the line into an array of strings if the information is delimited by the delimiter symbol
	 * @param pFileName ->Name of the file
	 * @param pDelimiter ->Delimiter symbol or string
	 * @param pComment -> Comment symbol or string
	 * @return a List of string values
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<String[]> ReadData(String pFileName, String pDelimiter, String pComment) throws FileNotFoundException, IOException{
		//String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "config_data/" + pFileName;
		
		List<String[]> lst =
			Files.lines(Paths.get(pFileName)) // Stream<String>
			.filter(line-> !line.trim().startsWith(pComment))
			
			.map(line -> StringUtils.substringBefore(line, pComment) // Remove the trailing part of the line that has a pComment code AND
				.split(pDelimiter)) // Split the result in array	
			.collect(Collectors.toList());

        return lst;		
	} 
	
	
	
	/**
	 * Transform an array of string values and an array of string names of fields in a Map
	 * @param pValues ->Values
	 * @param pFieldNames -> keys
	 * @return
	 */
	public static Map<String,String> arrayToMap (String[] pValues, String[] pFieldNames) {
		return IntStream.range(0, pFieldNames.length)
				.boxed()
                .collect(Collectors.toMap(i -> pFieldNames[i], i -> pValues[i]));
    }
	
	
	public static Object mapToObject(Class<? extends Object> pClass, Map<String, String> map) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		Object myBean=null;
		ConstructorUtils.invokeConstructor(pClass, myBean);
		BeanUtils.populate(myBean, map);
		return myBean;
	}
	
	public static Object arrayToObject (Class<? extends Object> pClass, String[] pValues, String[] pFieldNames) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Object myBean=null;
		ConstructorUtils.invokeConstructor(pClass, myBean);
		int i=0;
		for(String s: pFieldNames) PropertyUtils.setSimpleProperty(myBean, s, pValues[i++]);
		return myBean;
	}
	
	public static void persist() {
		
	}
}
