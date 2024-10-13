package xenhire.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.request.ClientAssessmentBatchRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssessmentsResponse {
	
	long id;
	String name;
	String date;

}
