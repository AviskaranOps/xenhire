package xenhire.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidateSkills;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApproveClientRequest {
	
	long clientId;
	String clientName;
	boolean approve;
	boolean decline;

}
