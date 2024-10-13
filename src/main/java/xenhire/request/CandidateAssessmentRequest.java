package xenhire.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateAssessmentRequest {
	
	String assessmentName;
	String batchName;
	List<AssessmentQuestionnaireRequest> questionList;
}
