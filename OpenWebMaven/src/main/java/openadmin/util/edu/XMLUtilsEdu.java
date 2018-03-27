package openadmin.util.edu;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import openadmin.dao.operation.DBAction;
import openadmin.model.log.LogEdu;


public class XMLUtilsEdu {

	private static XmlMapper mapper = new XmlMapper();
	
	public static String ObjectToXMLString (Object obj) throws JsonProcessingException  {
		return mapper.writeValueAsString(obj);
	} 
	
	public static <T extends Object> T XMLStringToObject (String content, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException   {
		// if not enabled this aspect, deserialization fails in arrays in XML !!!
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		return mapper.readValue(content, valueType);
	}
	
	public static <T extends Object> T XMLFileToObject (File src, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(src, valueType);
	}
	
	public static <T extends Object> T XMLFileToObject (InputStream src, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(src, valueType);
	}
	
	public static void main(String[] args) throws JsonProcessingException  {
		for (int i=0;i<1000; i++)
		System.out.println(""+i+ ObjectToXMLString(new LogEdu(null, null, 1, "SELECT * FROM KK", DBAction.FIND_DESC,"Prova 1")));
	}

}
