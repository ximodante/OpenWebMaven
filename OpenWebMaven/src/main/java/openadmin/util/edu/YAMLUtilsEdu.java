package openadmin.util.edu;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;


import openadmin.view.edu.yaml.YAMLViewEdu;

public class YAMLUtilsEdu {
	private static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
	
	public static String ObjectToYAMLString (Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	} 
	
	public static <T extends Object> T YAMLStringToObject (String content, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException  {
		return mapper.readValue(content, valueType);
	}
	
	public static <T extends Object> T YAMLFileToObject (File src, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException  {
		return mapper.readValue(src, valueType);
	}
	
	public static <T extends Object> T YAMLFileToObject (InputStream src, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(src, valueType);
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		//YAMLRawEdu yr=null;
		YAMLViewEdu yv=null;
		//Object obj=null;
		try {
			
			for (int i=0; i<2; i ++) {
				System.out.println("-------------------------------------");
				Instant start = Instant.now();
				InputStream in = YAMLUtilsEdu.class.getResourceAsStream("/view/genericView.yaml");
				Instant end = Instant.now();
				System.out.println("Input File "+ Duration.between(start, end)); // prints PT1M3.553S
			
				start = Instant.now();
				//yr = YAMLFileToObject(in, YAMLRawEdu.class);
				yv = YAMLFileToObject(in, YAMLViewEdu.class);
				end = Instant.now();
				System.out.println("YAML File to object "+Duration.between(start, end)); // prints PT1M3.553S
			
				//System.out.println(yr.toString());
				start = Instant.now();
				System.out.println(yv.toString());
				end = Instant.now();
				System.out.println("Object to string " + Duration.between(start, end)); // prints PT1M3.553S
				
				System.out.println("...............................");
				start = Instant.now();
				String ss=JSONUtilsEdu.ObjectToJSONString(yv);
				end = Instant.now();
				System.out.println("Object to JSON string " + Duration.between(start, end)); // prints PT1M3.553S
				System.out.println(ss);
				
				
				start = Instant.now();
				yv=JSONUtilsEdu.JSONStringToObject(ss, yv.getClass());
				end = Instant.now();
				System.out.println("JSON string to object "+ Duration.between(start, end)); // prints PT1M3.553S
				System.out.println(yv.toString());
				
				
				System.out.println("::::::::::::::::::::::::::::");
				start = Instant.now();
				ss=YAMLUtilsEdu.ObjectToYAMLString(yv);
				end = Instant.now();
				System.out.println("Object to YAML string "+ Duration.between(start, end)); // prints PT1M3.553S
				System.out.println(ss.replaceAll("\n", ""));
				
				
				start = Instant.now();
				yv=YAMLUtilsEdu.YAMLStringToObject(ss, yv.getClass());
				end = Instant.now();
				System.out.println("YAML string to Object"+ Duration.between(start, end)); // prints PT1M3.553S
				System.out.println(yv.toString());
				
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
				start = Instant.now();
				ss=XMLUtilsEdu.ObjectToXMLString(yv);
				end = Instant.now();
				System.out.println("Object to XML string "+ Duration.between(start, end)); // prints PT1M3.553S
				System.out.println(ss.replaceAll("\n", ""));
				
				
				start = Instant.now();
				yv=XMLUtilsEdu.XMLStringToObject(ss, yv.getClass());
				end = Instant.now();
				System.out.println("XML string to Object"+ Duration.between(start, end)); // prints PT1M3.553S
				System.out.println(yv.toString());
							
				
			}
			/*
			in = YAMLUtilsEdu.class.getResourceAsStream("/view/genericView.yaml");
			System.out.println("--------------------------------");
			obj=YAMLFileToObject(in, Object.class);
			System.out.println(obj.toString());
			System.out.println(obj);
			System.out.println("--------------------------------");
			
			String s=obj.toString();
			System.out.println("s0="+s);
			s=s.substring(0, s.lastIndexOf("}"));
			System.out.println("s1="+s);
			s=s.replaceFirst("\\{", "");
			System.out.println("s2="+s);
				
			LinkedHashMap<String,Object> lhm=YAMLStringToObject(s,LinkedHashMap.class);
			
			String a=lhm.keySet()
					.stream()
					.map(e -> e.toString()+": " + lhm.get(e).toString())
					.collect(Collectors.joining("\n"));
			
			System.out.println(a);
			
			*/
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/**
		for (int i=0;i<1000; i++)
		System.out.println(""+i+ ObjectToJSONString(new LogEdu(null, null, 1, "SELECT * FROM KK", DBAction.FIND_DESC,"Prova 1")));
	   */
	}
	

}
