package xenhire.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidateAssessmentData;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateAssessmentResponse {
	
	int versionNo;
	int savedSection;
	boolean nextSection;
}
