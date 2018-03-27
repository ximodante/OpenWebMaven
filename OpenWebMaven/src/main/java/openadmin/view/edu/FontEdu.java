package openadmin.view.edu;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Defines font attributes
 * @author eduard
 *
 */
@NoArgsConstructor
public class FontEdu implements Serializable{
	
	private static final long serialVersionUID = 20180203L;

	@Getter @Setter
	private String name=null;
	
	@Getter @Setter
	private int size=0;
	
	@Getter @Setter
	private boolean bold=false;
	
	@Getter @Setter
	private boolean italic=false;
	
	@Getter @Setter
	private boolean underline=false;
	
	@Getter @Setter
	private boolean strikethrough=false;
	
	@Getter @Setter
	private boolean superscript=false;
		
	@Getter @Setter
	private boolean subscript=false;
	
	@Getter @Setter
	private String color=null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
