package openadmin.util.edu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;

import openadmin.dao.operation.DaoJpaEdu;
import openadmin.dao.operation.DaoOperationFacadeEdu;
import openadmin.model.control.Program;
import openadmin.model.control.User;
import openadmin.util.configuration.TypeLanguages;
import openadmin.util.lang.LangTypeEdu;

public class ExpressionUtils {

	public static void main(String[] args) {
		String[] strProva = {"aa", "bbccddeeffgghh", "12", "1234"};
		
		// Create or retrieve an engine
	    JexlEngine jexl = new JexlBuilder().create();
	    
	    // Create an expression
	    //String jexlExp = "str[0] + \"->\" + str[1].substring(3)";
	    //String jexlExp ="\"tatiiiiiiiiia\"";
	    //String jexlExp ="integer.parseInt(\"25\")";
	    String jexlExp ="localDate.now()";
	    JexlExpression e = jexl.createExpression( jexlExp );
	    
	    
	    // Create a context and add data
	    JexlContext jc = new MapContext();
	    jc.set("str", strProva );
	    jc.set("integer", new Integer(1) );
	    jc.set("localDate",LocalDate.now());
	    
	      Object o = e.evaluate(jc);
	    
	    
	    //Mostrar
	    System.out.println(o.toString()); 
	    
	    
	    
	    String a="aaaa";
	    List<String> ls=new ArrayList<String>();
	    ls.add(a);
	    a.replace("aa", "b");
	    ls.add(a);
	    for (String s: ls) System.out.println(s);
	    
	    Program p1=new Program("edu");
	    List<Program> lp=new ArrayList<Program>();
	    lp.add(p1);
	    p1.setDescription("Juanito");
	    lp.add(p1);
	    for(Program p: lp) System.out.println(p.toString());
	    
	    
	    
	    
	}

}
