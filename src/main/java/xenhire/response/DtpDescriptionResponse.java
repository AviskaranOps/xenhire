package xenhire.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.dto.CandidatePersonalInfoDTO;
import xenhire.request.CandidatePreferencesRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DtpDescriptionResponse {
	
	CandidatePersonalInfoDTO personalInfo;
	CandidatePreferencesRequest preferences;
	List<CandidateValueResultResponse> values;
	CandidateSpectrumResults dtpResult;

}
