package xenhire.response;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidateSkills;
import xenhire.model.ClientSkills;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateDtpReportResponse {
	
	List<String> competencyRanking;
	List<String> certifications;
	List<CandidateSkills> Skills;
	List<CandidateSkills> industry;
	CandidatePreferencesForReport preferences;
	List<String> values;

}
