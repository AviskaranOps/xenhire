package xenhire.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateValuesData {
	
	int rank;
	String valueName;
	int staticScore;
	int dynamicScore;
	

}
