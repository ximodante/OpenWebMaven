package openadmin.model.control;

import javax.persistence.Column;

/**
 * <desc>class that stores the basic information of ActionClass</desc>
 * <responsibility>Represents all the classes that have actions for users</responsibility>
 * <coperation>Interface Base - Class Action - Class view </coperation>
 * @version  0.1
 * Created 10-05-2008
 * Modifier 18-09-2009 
 * Author Alfred Oliver
*/

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import openadmin.model.Audit;
import openadmin.model.Base;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@ToString @NoArgsConstructor
@Table(name = "nomClasse", schema = "control")
@Audited
public class ClassName extends Audit implements Base, java.io.Serializable {

	private static final long serialVersionUID = 01011001L;
	
	/** Field that contains the identifier*/
	@Getter @Setter
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/** Field that contains the description*/	
	@Getter @Setter
	@NotNull
	@Size(min= 10, max = 80)
	@Column(unique=true)
	private String description;	

	/**
	 * Constructor of the class ActionClass.
	 * @param pDescription, is the description, (unique value), of the ActionClass
	 */
	public ClassName(String pDescription) {
		
		this.description = pDescription;
	}

	
}
