package xenhire.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CriticalScoring;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateJobInfo {

	   	String title;
	    String family;
	    String code;
	    String status;
	    String department;
	    String date;
	    String location;
}
