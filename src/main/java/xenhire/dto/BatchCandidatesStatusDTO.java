package xenhire.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.response.BatchCandidatesStatusResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchCandidatesStatusDTO {
	
	String name;
	String date;
	String status;
	String timePassed;
	String candidateName;
	long batchId;

}
