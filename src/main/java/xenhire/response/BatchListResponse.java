package xenhire.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.request.CandidatesBatchRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BatchListResponse {
	
	long id;
	String batchName;
	String date;
	String createdBy;
	String status;

}
