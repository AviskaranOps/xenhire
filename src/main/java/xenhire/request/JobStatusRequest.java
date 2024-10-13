package xenhire.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.response.ExternalStaffingResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobStatusRequest {

	long jobId;
	String status;
}
