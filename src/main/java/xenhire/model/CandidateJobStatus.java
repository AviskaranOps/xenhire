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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateJobStatus {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long candidateId;
	long jobId;
	long clientId;
	String interviewPhase;
	LocalDateTime givenDate;
	String phaseStatus;
	int referencesGiven;
	String referencesStatus;
	String candidateName;
	String positionApplied;
	String passedDate;
	boolean active;
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
