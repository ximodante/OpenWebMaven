package openadmin.util.edu;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import openadmin.dao.operation.DBAction;
import openadmin.model.log.LogEdu;

public class LogUtilsEdu {
	
	private static Logger errorLogger=null;
	private static Logger auditLogger =null;
	
	// Route to src/main/resources
	private static final String Path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		
	private static final String PropertyPath = Path + "properties/logger.properties";
		
	private static final Properties Props=PropertyUtilsEdu.getPropertiesNoException(PropertyPath);
				
	
	static {
		
		//1. Formater
		Formatter formatter=
			new Formatter() {
	    		@Override
	    		public String format(LogRecord record) {
	    			return record.getMessage() + "\n";
	    		}
	    	};
		
	    //2. Error Logger
	    errorLogger=Logger.getLogger("error");
		errorLogger.setUseParentHandlers(false);
		
		String loggerFile=Props.getProperty("errorlog.file");
		int loggerLimit=Integer.parseInt(Props.getProperty("errorlog.limit"));
		int loggerCount=Integer.parseInt(Props.getProperty("errorlog.count"));
		
		FileHandler errorFileHandler=null;
		
		try {
			errorFileHandler = new FileHandler(loggerFile,loggerLimit,loggerCount,true);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		errorFileHandler.setFormatter(formatter);
	    errorLogger.addHandler(errorFileHandler);
	 
	    
	    //3. Audit LoggerString loggerFile=Props.getProperty("errorlog.file");
	    auditLogger=Logger.getLogger("audit");
	    auditLogger.setUseParentHandlers(false);
	    
	    loggerFile=Props.getProperty("auditlog.file");
		loggerLimit=Integer.parseInt(Props.getProperty("errorlog.limit"));
		loggerCount=Integer.parseInt(Props.getProperty("errorlog.count"));
		
	    
	    FileHandler auditFileHandler=null;
		try {
			auditFileHandler = new FileHandler(loggerFile,loggerLimit,loggerCount,true);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		auditFileHandler.setFormatter(formatter);
	    auditLogger.addHandler(auditFileHandler);
	    
	     
	}
	
	
	public static void LogError(LogEdu log) {
		try {
			errorLogger.log(Level.SEVERE, JSONUtilsEdu.ObjectToJSONString(log));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void LogAudit(LogEdu log) {
		try {
			auditLogger.log(Level.INFO, JSONUtilsEdu.ObjectToJSONString(log));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		LogError(new LogEdu(null, null, 1, "SELECT * FROM KK", DBAction.FIND_DESC, "Prova 2"));
		LogError(new LogEdu(null, null, 1, "SELECT * FROM KK", DBAction.FIND_DESC, "Prova 2"));
		LogError(new LogEdu(null, null, 1, "SELECT * FROM KK", DBAction.FIND_DESC, "Prova 2"));
		LogError(new LogEdu(null, null, 1, "SELECT * FROM KK", DBAction.FIND_DESC, "Prova 2"));

	}
}
