package xenhire.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.request.AssessmentBatchCandidateResponse;
import xenhire.request.ClientAssessmentBatchRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientAssessmentBatchResponse {

	long id;
	String batchName;
	String createdBy;
	List<AssessmentsResponse> selectedAssignment;
	List<AssessmentBatchCandidateResponse> assignCandidate;
}
