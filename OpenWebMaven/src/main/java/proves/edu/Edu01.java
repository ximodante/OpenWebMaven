package proves.edu;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;


@Named
@SessionScoped

public class Edu01 implements Serializable{

	@Getter @Setter
	private String[] a1= {"aa","bb","cc"};
	
	@Getter @Setter
	private String[][] a= {{"a1","a2"},{"a3","a4"}};
	
	@Getter @Setter
	private List<String> le = Arrays.asList ("1","2","3");
	
	@Getter @Setter
	private List<Integer> le1 = Arrays.asList (1,2,3,4);
	
	@Getter @Setter
	private List<String[]> le2 = Arrays.asList (a);
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
