package xenhire.request;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidateDTPReportData;
import xenhire.model.ScreeningQuestions;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientScreeningQuestionsRequest {
	
	List<ScreeningQuestions> questions;
	boolean proceed;

}
