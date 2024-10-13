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
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateReferenceRanking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long referenceId;
	long templateNo;
	boolean template;
	String createdBy;
	String templateName;
	String pillar;
	int pillarRank;
	String competency;
	int competencyRank;
	int pillarScore;
	int competencyScore;
	String rating;
	int maxScore;
	int weightage;
	int count;
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
