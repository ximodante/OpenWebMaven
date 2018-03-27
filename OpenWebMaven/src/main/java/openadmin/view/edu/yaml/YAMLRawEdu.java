package openadmin.view.edu.yaml;

import java.io.IOException;
//import java.lang.invoke.MethodHandles;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import openadmin.util.edu.YAMLUtilsEdu;


public class YAMLRawEdu extends LinkedHashMap<String, Object>{

	private static final long serialVersionUID = 20180204L;
	/**
	@Override
	public String toString() {
		String a="";
		this.keySet()
			.stream()
			.map(e -> "\n"+ e.toString()+": " + this.get(e))
			.collect(Collectors.joining("\n"));
		return a;
	}
	*/
	
	@Override
	public String toString() {
		String a=this.keySet()
			.stream()
			.map(e -> e.toString()+": " + this.get(e))
			.collect(Collectors.joining("\n"));
		return "Result="+ a;
	}
	
	public YAMLRawEdu getMapProp(String key) throws JsonParseException, JsonMappingException, IOException {
		// Gets this class 
		//Class<?> klass=MethodHandles.lookup().lookupClass();
		//return YAMLUtilsEdu.YAMLStringToObject(key, klass);
		return YAMLUtilsEdu.YAMLStringToObject(key, YAMLRawEdu.class);
	}
	
	public YAMLRawEdu[] getMapArrayProp(String key) throws JsonParseException, JsonMappingException, IOException {
		
		return YAMLUtilsEdu.YAMLStringToObject(key, YAMLRawEdu[].class);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
