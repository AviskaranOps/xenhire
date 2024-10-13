package xenhire.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.response.AssessmentsResponse;
import xenhire.response.BatchCandidateResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientAssessmentBatchRequest {
	
	String batchName;
	List<AssessmentsResponse> selectedAssignment;
	List<BatchCandidateResponse> assignCandidate;

}
