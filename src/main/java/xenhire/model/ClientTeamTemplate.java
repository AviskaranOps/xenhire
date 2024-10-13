package xenhire.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientTeamTemplate {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	long clientId;
	String templateName;
	boolean template;
	String templateTag;
	String templateDescription;
	String teamSize;
	String teamLocation;
	String crossFunctionality;
	String domainRole;
	String project;
	String contributions;
	String createdBy;
	String workValues;
	String technicalSkills;
	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	LocalDateTime createdAt;
	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	LocalDateTime updatedAt;
	
	
	 @PrePersist
	    @PreUpdate
	    protected void onPersistOrUpdate() {
	        if (createdAt == null) {
	            createdAt = LocalDateTime.now();
	        }
	        updatedAt = LocalDateTime.now();
	    }
	
}