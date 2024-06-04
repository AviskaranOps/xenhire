package xenhire.response;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.ClientSkills;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientIcpReportResponse {
	
	List<PillarRanking> pillarRanking;
	List<CompetencyRanking> competencyRanking;
	List<String> certifications;
	List<ClientSkills> skills;
	ClientPreferencesForReport preferences;
	List<String> values;
	

}
