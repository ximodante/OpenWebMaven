package openadmin.model;


import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public class Audit {

	@Getter @Setter
	private String lastUser;
	@Getter @Setter
	private LocalDateTime data ;
	
	public void setChanges(String user) {
		this.lastUser=user;
		this.data=LocalDateTime.now();
	}
}