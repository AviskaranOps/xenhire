package xenhire.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.response.BatchCandidateResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssessmentBatchCandidateResponse {
	

	long id;
	String name;
	String emailId;
	String mobileNo;
	String assessmentName;
	String status;
	String score;

}
