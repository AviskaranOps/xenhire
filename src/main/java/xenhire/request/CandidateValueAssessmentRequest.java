package xenhire.request;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.ClientDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateValueAssessmentRequest {
	
	int questionNo;
	String question;
	String mostLikely;
	String leastLikely;
	List<String> options;
	String optionCategory;
	String questionType;

}
