package xenhire.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidateValueAssessmentData;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateAssessmentsResponse {
	
	long id;
	String companyName;
	String assessmentName;
	String date;
	String status;
	String result;
	String highestScore;

}
