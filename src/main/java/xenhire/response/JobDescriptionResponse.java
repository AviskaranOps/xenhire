package xenhire.response;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.ClientTeamTemplate;
import xenhire.request.JobDetailsRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDescriptionResponse {
	
	JobDetailsRequest jobDetail ;
	ClientTeamTemplate team ;
	List<Map> values ;
	CandidateSpectrumResults icp ;

}
