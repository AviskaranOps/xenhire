package xenhire.response;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.dto.BatchCandidatesStatusDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchCandidatesStatusResponse {
	
	String candidateName;
	List<BatchCandidatesStatusDTO> assessments;

}
