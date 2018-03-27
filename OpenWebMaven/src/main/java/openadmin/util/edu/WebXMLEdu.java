package openadmin.util.edu;

/*
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
*/
/**
 * This class replaces web.xml
 * @see BalusC in https://stackoverflow.com/questions/38348608/how-to-configure-context-parameters-without-web-xml
 * @author eduard
 * We are going to use web.xml as it is more versatile as indicates Piotr in
 * https://stackoverflow.com/questions/13450044/how-to-define-welcome-file-list-and-error-page-in-servlet-3-0s-web-xml-less/13450154#13450154
 *

@WebListener
public class WebXMLEdu  implements ServletContextListener{
	
	private final String Path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private final String PropertyPath = Path + "properties/web.properties";
	// Property file
	private final Properties Props=PropertyUtilsEdu.getPropertiesNoException(PropertyPath);
	
	@Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext(); 
        servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
        //servletContext.setInitParameter("primefaces.THEME", "temaestany");
        //servletContext.setInitParameter("primefaces.THEME", "cupertino");
        servletContext.setSessionTimeout(60* (int)(Props.get("session.timeout")));
       
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // NOOP.
    }

}
*/

public class WebXMLEdu {
	
}