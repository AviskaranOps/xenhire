package xenhire.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.response.CandidateQuestionnaireResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateQuestionnaireData {
	
	int questionNo;
	String question;
	List<String> options;
	String questionType;
	String optionCategory;

	

}
