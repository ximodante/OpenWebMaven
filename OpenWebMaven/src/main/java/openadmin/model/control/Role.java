package openadmin.model.control;

import javax.persistence.Column;

/**
 * <desc>class that stores the basic information of role</desc>
 * <responsibility>Represents all the roles of programs</responsibility>
 * <cooperation>Interface Base</cooperation>
 * <desc>class that stores the basic information of role</desc>
 * <responsibility>Represents all the roles of programs</responsibility>
 * <coperation>Interface Base</coperation>
 * @version  0.2
 * Created 10-05-2008
 * Modifier 07-11-2017 
 * Author Alfred Oliver
*/

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "role", schema = "control")
@Audited
public class Role extends Audit implements Base, java.io.Serializable {

	private static final long serialVersionUID = 01011001L;
	
	/** attribute that contains the identifier*/
	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	
	/** attribute that contains the description, unique value*/
	@Getter @Setter
	@NotNull
	@Size(min = 3, max = 30)
	@Column(name = "descipcion", unique = true)
	private String description; 
	
	/** Transient attribute that means that the system should make a log on any JPA operation of this class*/
	/*
	@Transient
	@Getter
	private boolean debugLog = true;
	*/
	
	/*
	@Transient
	@Getter
	private boolean detailLog = false;
	*/

	/**
	 * Constructor of the class Program.
	 * @param pDescription, is the description, (unique value), of the Program
	 */
	public Role(String pDescription) {
		
		setDescription(pDescription);
		
	}

	
	/** Getters and setters*/
	
}
