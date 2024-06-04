package xenhire.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.request.CandidateValueAssessmentRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateValuesResponse {
	
	int questionNo;
	String question;
	List<String> options;

}
