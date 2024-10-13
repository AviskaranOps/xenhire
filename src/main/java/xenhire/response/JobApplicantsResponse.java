package xenhire.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicantsResponse {
	
	String applicantName;
	String jobTitle;
	String emailAddress;
	String phoneNumber;
	String status;

}
