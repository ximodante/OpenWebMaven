package openadmin.util.configuration;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import openadmin.dao.operation.DaoJpaEdu;
import openadmin.dao.operation.DaoOperationFacadeEdu;
import openadmin.model.control.User;
import openadmin.util.configuration.yaml.YAMLControlLoad;
import openadmin.util.edu.FileUtilsEdu;
import openadmin.util.edu.PropertyUtilsEdu;
import openadmin.util.edu.YAMLUtilsEdu;
import openadmin.util.lang.LangTypeEdu;

public class FirstControlLoadYAML {
	
	private static User firstLoadUser = new User("FirstLoader","123456","First Load User");
	
	private static DaoOperationFacadeEdu connection = null; 	
	
	// Route to src/main/resources
	private static final String Path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	
	private static final String PropertyPath = Path + "properties/yaml.properties";
	
	// Property file
	private static final Properties Props = PropertyUtilsEdu.getPropertiesNoException(PropertyPath);
	
	// Folder from src/main/resources where data in csv format is stored
	private static final String DataFolder = Props.getProperty("data_folder");
	
	//NO Maven
	//private static final String fileName = Path + DataFolder + "/" + Props.getProperty("yaml.file");
		
	// Maven
	private static final String fileName = DataFolder + "/" + Props.getProperty("yaml.file");
	
	public static void dataLoad()
			throws ClassNotFoundException, IOException, IntrospectionException, InstantiationException, 
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, RuntimeException {	
		
		
		
		YAMLControlLoad yc=null;
		
		//1. Read YAML File
		//InputStream in = FileUtilsEdu.getStreamFromWebContentFolder(fileName);
		InputStream in = FileUtilsEdu.getStreamFromResourcesFolder(fileName);
		//byte[] myBytes= in.readAllBytes();
		//String myStr = new String(myBytes);
		//System.out.println(myStr);
		//InputStream in = YAMLUtilsEdu.class.getResourceAsStream("/data/Application.yaml");
		try {
			//yc = YAMLUtilsEdu.YAMLStringToObject(myStr, YAMLControlLoad.class);
			yc = YAMLUtilsEdu.YAMLFileToObject(in, YAMLControlLoad.class);
			System.out.println(yc.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (yc.checkErrors(false).trim().length()>10)
			System.out.println(yc.checkErrors(true));
		else {
		
		//2. Open BD Connection	
			LangTypeEdu langType = new LangTypeEdu();
			langType.changeMessageLog(TypeLanguages.es);
			//1.0- Open connections
			connection = new DaoJpaEdu(firstLoadUser, "control_post", (short) 0,langType);
		
		//3. Assign current connection to YAMLControlLoad
			yc.setConnection(connection);
		
			connection.begin();
		
		//4. Persist configuration in DB	
			yc.Init();
		
		
		//5. Close DB Connection	
			connection.commit();	
			connection.finalize();

	}	}
	
	
	
	public static void main(String[] args) {
		try {
			dataLoad();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException
				| NoSuchMethodException | IOException | IntrospectionException | RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
