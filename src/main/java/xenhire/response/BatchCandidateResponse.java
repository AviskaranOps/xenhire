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
public class BatchCandidateResponse {
	
	long id;
	String name;
	String emailId;
	String mobileNo;
	String linkedIn;

}
