package xenhire.model;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateValueAssessmentData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long candidateId;
	int questionNo;
	int versionNo;
	String mostLikely;
	String leastLikely;
	String mostLikelyValue;
	String leastLikelyValue;
	LocalDateTime createdAt;

}
