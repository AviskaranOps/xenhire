package xenhire.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.request.CandidateQuestionnaireData;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateQuestionnaireResponse {
	
	int section;
	List<CandidateQuestionnaireData> questionnaire;

}
