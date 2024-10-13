package xenhire.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.ClientAssessmentBatch;
import xenhire.model.CriticalScoring;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateInterviewFeedbackRequest {
	
	long id;
	long candidateId;
	long jobId;
	String createdBy;
	LocalDate createdDate;
	String overallRating;
	String recommendation;
	String recommendationWhy;
	String experience;
	String experienceWhy;
	List<CriticalScoring> criticalScroing;

}
