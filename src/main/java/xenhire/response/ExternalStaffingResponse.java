package xenhire.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.ClientJobDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExternalStaffingResponse {

	long jobId;
	String jobName;
	String department;
	String status;
	String location;
	String postedDate;
	String declineDate;
	String externalService;
	String sourcing;
	String onboarding;
}
