package openadmin.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

public class Audit {

	@Getter @Setter
	private String lastUser;
	@Getter @Setter
	private LocalDate data;
	
	public void setChanges(String user) {
		this.lastUser=user;
		this.data=LocalDate.now();
	}
}