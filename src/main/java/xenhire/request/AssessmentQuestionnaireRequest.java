package xenhire.request;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssessmentQuestionnaireRequest {
	
	String question;
	String selectedOption;
	List<String> options;
	List<String> selectedOrder;

}
