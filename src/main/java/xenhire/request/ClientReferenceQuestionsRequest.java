package xenhire.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientReferenceQuestionsRequest {

	int questionNo;
	String question;
	List<String> options;
	String selectedOption;
	String questionType;
	String optionCategory;
}
