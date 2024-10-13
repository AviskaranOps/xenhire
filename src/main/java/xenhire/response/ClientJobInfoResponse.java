package xenhire.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientJobInfoResponse {

	
	long id;
	long clientId;
	boolean jobDetail;
	long jobDetailId;
	boolean workValues;
	long workValuesId;
	boolean team;
	long teamId;
	boolean icp;
	boolean screening;
	boolean assessment;
	long icpId;
	String jobTitle;
	String companyName;
	boolean screeningQuestions;
	boolean assessmentAssigned;
	boolean sourcingHelp;
	boolean onboardingHelp;
	boolean fullServiceStaffingHelp;
	boolean publishFeature;
	String jobStatus;
	String jobSubStatus;
}
