package xenhire.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.response.AssessmentsResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssessmentBatchAddCandidateRequest {
	
	 String batchName;
     List<AssessmentsResponse> selectedAssignments;
     boolean addCandidateDatabase;
     String candidateName;
     String candidateEmail;
     String candidateNo;
     String candidateLinkedin;

}
