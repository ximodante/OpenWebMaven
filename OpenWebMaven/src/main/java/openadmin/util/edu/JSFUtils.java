package openadmin.util.edu;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class JSFUtils {
	/**
	 * Get the http request from a JSF application
	 * @return
	 */
	public static HttpServletRequest getRequest () {
		FacesContext fc=FacesContext.getCurrentInstance();
		if (fc==null) return null;
		return (HttpServletRequest)fc.getExternalContext().getRequest();
	}
	
	public static HttpServletResponse getResponse () {
		return (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}
	
	public static Application getApplication() {
		return FacesContext.getCurrentInstance().getApplication();
	}
	
	public static ELContext getELContext() {
		return FacesContext.getCurrentInstance().getELContext();
	}
	
	public static ValueExpression getValueExpression(String pValue, Class<?> klass) {
		return getApplication().getExpressionFactory().createValueExpression(getELContext(), pValue, klass);
	}
	
	/**
	 * Gets the remote address from a HttpServletRequest object. It prefers the 
	 * `X-Forwarded-For` header, as this is the recommended way to do it (user 
	 * may be behind one or more proxies).
	 *
	 * Taken from https://stackoverflow.com/a/38468051/778272
	 *
	 * @param request - the request object where to get the remote address from
	 * @return a string corresponding to the IP address of the remote machine
	 */
	public static String getClientAddress(HttpServletRequest request) {
		if (request==null) return null;
	    String ipAddress = request.getHeader("X-FORWARDED-FOR");
	    if (ipAddress != null) {
	        // cares only about the first IP if there is a list
	        ipAddress = ipAddress.replaceFirst(",.*", "");
	    } else {
	        ipAddress = request.getRemoteAddr();
	    }
	    return ipAddress;
	}
	
	public static String getClientAddress() {
		return getClientAddress(getRequest());
	}
	/**
	 * Get the session object
	 * @param request
	 * @return
	 */
	public static HttpSession getSession(HttpServletRequest request) {
		if (request==null) return null;
		return request.getSession();
	}
	
	public static HttpSession getSession() {
		return getSession(getRequest());
	}
	
	public static String getSessionId() {
		HttpSession session=getSession();
		if (session==null) return null;
		return session.getId();
	}
	
	/**
	 * get authenticated user (if any)
	 * @param request
	 * @return
	 */
	public static String getUser(HttpServletRequest request) {
		if (request==null) return null;
		return request.getRemoteUser();
	}
	
	public static String getUser() {
		return getUser(getRequest());
	}
	
	public static String getUserAgent(HttpServletRequest request) {
		if (request==null) return null;
		return request.getHeader("User-Agent");

	}
	
	public static String getUserAgent() {
		return getUserAgent(getRequest());
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
