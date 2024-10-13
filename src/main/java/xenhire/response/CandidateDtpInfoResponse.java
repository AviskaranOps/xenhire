package xenhire.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidateDTPReportData;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateDtpInfoResponse {
	
	long assessmentVersionId;
	long valuesVersionId;
	long preferencesVersionId;
	boolean assessment;
	boolean valueAssessment;
	boolean preferences;
	boolean personalInfo;
	long personalInfoId;
	boolean resume;
	String linkedIn;

}
