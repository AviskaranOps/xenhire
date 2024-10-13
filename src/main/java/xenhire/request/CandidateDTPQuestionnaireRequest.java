package xenhire.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateDTPQuestionnaireRequest {
	int questionNo;
	String question;
	List<String> options;
	String selectedOption;
	String questionType;
	String optionCategory;
	List<String> selectedOrder;

}
