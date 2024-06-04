package xenhire.response;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidateOptions;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientQuestionnaireResponse {
	
	int questionNo;
	String question;
	List<String> options;
	String questionType;
	String optionCategory;
}
