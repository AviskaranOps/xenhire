package xenhire.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDetailsResponse {

	long id;
	String jobName;
	String location;
	long compensation;
	int JobProgress;
	String companyName;
	String image;
	String hiringManager;
	String postedTime;
	String jobStatus;
	String jobSubStatus;
	int applicants;
	List<String> typeOfHire;
	long total;
	long newly;
	long active;
	long hired;
	String createdDate;
	String jobType;
	String jobDepartment;
	boolean newJob;
	String jobDescription;
	String responsibility;
	String requirement;
	
}
