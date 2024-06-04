package xenhire.request;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidateQuestionnaire;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientAssessmentRequest {
	
	int questionNo;
	String question;
	List<String> options;
	String selectedOption;
	String questionType;
	String optionCategory;

}
