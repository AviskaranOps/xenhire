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
public class ClientJobDetails {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	long clientId;
	String jobTitle;
	String department;
	String location;
	String jobType;
	int totalPositions;
	boolean jobDetail;
	long jobDetailId;
	boolean workValues;
	long workValuesId;
	boolean team;
	long teamId;
	boolean icp;
	long icpId;
	boolean active;
	boolean closed;
	boolean screening;
	boolean assessment;
	boolean screeningQuestions;
	boolean assessmentAssigned;
	boolean sourcingHelp;
	boolean onboardingHelp;
	boolean fullServiceStaffingHelp;
	boolean publishFeature;
	String jobStatus;
	String jobSubStatus;
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
