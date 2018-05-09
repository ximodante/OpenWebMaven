package openadmin.model.control;

import javax.persistence.Column;

/**
 * <desc>class that stores the basic information of access</desc>
 * <responsibility>Represents all the access for users</responsibility>
 * <coperation>Interface Base - Class User - Class Role - Class Entity</coperation>
 * @version  0.1
 * Created 10-05-2008
 * Modifier 07-11-2017 
 * Author Alfred Oliver
*/

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import openadmin.annotations.Default;
import openadmin.annotations.OpenScreen;
import openadmin.model.Base;
import openadmin.model.Audit;
@Entity
@Table(name = "access", schema = "control", 
       uniqueConstraints = @UniqueConstraint(columnNames =  { "programa", "usuari", "entityadm", }),
       indexes = {@Index (name = "idx_usuari_entityadm", columnList = "usuari, entityAdm")})
@Audited
@ToString @NoArgsConstructor

public class Access extends Audit implements Base, java.io.Serializable {

	private static final long serialVersionUID = 01011001L;
	
	/** attribute that contain the identifier*/
	@Getter @Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@Default(visible=true)
	
	private Long id;
	
	/** attribute that contains the access description*/	
	@Getter
	@Size(max = 30)
	@NotNull
	@Column(name= "descripcio", unique = true)
	private String description;
		
	/** attribute that contain the relationship with role*/
	@Getter @Setter
	@ManyToOne
	@JoinColumn(name = "programa", nullable= false)
	@OpenScreen(normal=true)
	private Program program;
	
	@Getter @Setter
	@ManyToOne
	@JoinColumn(name = "role", nullable= false)
	@OpenScreen(normal=true)
	private Role role;
	
	/** attribute that contain the relationship with user*/
	@Getter @Setter
	@ManyToOne
	@JoinColumn(name = "usuari", nullable= false)
	private User user;	
	
	/** attribute that contain the relationship with entity*/
	@Getter @Setter
	@ManyToOne
	@JoinColumn(name = "entityAdm", nullable= false)
	private EntityAdm entityAdm;
	
	/** Getters and setters*/	

	public void setDescription(String pDescription) {
		
		if(getEntityAdm() != null || getUser() != null || getProgram() != null)
			this.description = getProgram().getId() + "_" + getUser().getId() + "_" + getEntityAdm().getId();
	}
	
	@PrePersist
	@PreUpdate
	private void updateDescription(){
		
		setDescription("");
		
	}

		
}
