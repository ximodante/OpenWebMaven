package openadmin.util.edu;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import openadmin.dao.operation.DBAction;
import openadmin.model.log.LogEdu;

public class JSONUtilsEdu {
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String ObjectToJSONString (Object obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	} 
	
	public static <T extends Object> T JSONStringToObject (String content, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException  {
		return mapper.readValue(content, valueType);
	}
	
	public static <T extends Object> T JSONFileToObject (File src, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException  {
		return mapper.readValue(src, valueType);
	}
	
	public static <T extends Object> T JSONFileToObject (InputStream src, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(src, valueType);
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		for (int i=0;i<1000; i++)
		System.out.println(""+i+ ObjectToJSONString(new LogEdu(null, null, 1, "SELECT * FROM KK", DBAction.FIND_DESC,"Prova 1")));
	}
}
