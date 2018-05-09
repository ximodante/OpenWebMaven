package openadmin.util.edu;

import java.lang.StackWalker.StackFrame;
import java.util.ArrayList;
import java.util.List;

public class SWDemo
{
   private List<String> ls=new ArrayList<String>();
   
   private void addElement(StackFrame e) {
	   ls.add(e.toString());
   }
   public static void main(String[] args)
   {
      a();
   }

   public static void a()
   {
      b();
   }

   public static void b()
   {
      c();
   }

   public String myName() {
	   return this.getClass().getCanonicalName();
   }
   public static void c()
   {
	  SWDemo s=new SWDemo(); 
      StackWalker sw = StackWalker.getInstance();
      List<StackFrame> lss=new ArrayList<StackFrame>();
      //sw.forEach(System.out::println);
      sw.forEach(s::addElement);
      sw.forEach(lss::add);
      for(String e: s.ls) {
    	  System.out.println(e);
      }
      System.out.println("-----------------------");
      for(StackFrame e: lss) {
    	  System.out.println(e.toString() + "-XXXXXX-" + new SWDemo().myName());
      }
      //sw.forEach(e -> System.out.println(e.getDeclaringClass()));
     
   }
}
