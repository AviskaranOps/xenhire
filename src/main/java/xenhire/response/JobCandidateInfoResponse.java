package xenhire.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidateInterviewFeedback;
import xenhire.model.CriticalScoring;
import xenhire.request.CandidateInterviewFeedbackRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobCandidateInfoResponse {
	
	CandidateJobInfo jobDetails;
	CandidateInfo candidateDetails;
	List<CandidateInterviewFeedbackRequest> interviewFeedback;

}
