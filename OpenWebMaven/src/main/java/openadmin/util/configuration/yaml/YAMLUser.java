package openadmin.util.configuration.yaml;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

public class YAMLUser implements Serializable{

	private static final long serialVersionUID = 20180204L;
	
	@Getter @Setter
	private String description =null;        // 
	
	@Getter @Setter
	private String password =null;        // 
	
	@Getter @Setter
	private String fullName =null;        // 
	
	@Getter @Setter
	private String identifier =null;        // 
	
	@Getter @Setter
	private String dateBegin =LocalDate.now().toString();        //
	
	@Getter @Setter
	private String dateEnd =null;        //
	
	@Getter @Setter
	private String language ="es";        // 
	
	
	public static void main(String[] args) {
		YAMLUser yu=new YAMLUser();
		System.out.println(LocalDate.now().toString());
		for (int i=1;i<1000; i++) System.out.print(i+"-");
		System.out.println(LocalDate.now().toString());
		for (int i=1;i<1000; i++) System.out.print(i+"-");
		System.out.println(LocalDate.now().toString());
		for (int i=1;i<1000; i++) System.out.print(i+"-");
		System.out.println(LocalDate.now().toString());
		for (int i=1;i<1000; i++) System.out.print(i+"-");
		System.out.println(LocalDate.now().toString());
	}
	
	

}
