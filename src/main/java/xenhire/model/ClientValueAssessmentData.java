package xenhire.model;

import java.time.LocalDateTime;
import java.util.Date;

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
public class ClientValueAssessmentData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String templateName;
	String templateTag;
	String templateDescription;
	boolean template;
	String createdBy;
	long clientId;
	int versionNo;
	int selfDirection;
	int stimulation;
	int hedonism;
	int achievement;
	int power;
	int security;
	int conformity;
	int tradition;
	int benevolence;
	int universalism;
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
