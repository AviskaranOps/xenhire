package xenhire.request;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDetailsRequest {
	
	String createdBy;
	long id;
	long jobId;
	int versionId;
	String jobTitle;
	String jobCode;
	String jobFamily;
	String jobDepartment;
	String jobLocation;
	long salary;
	String companyInfo;
	String positionSummry;
	String responsibilities;
	String benefits;
	String equalEmployeeOpportunity;
	String specificIndustryExperience;
	String specifyIndustryExp;
	String industryKnowledge;
	String workSetting;
	String roleType;
	String roleTimings;
	String roleTravel;
	String visa;
	String minimumLevelQualification;
	String requireRegulatory;
	String differentAcademic;
	List<String> certifications;
	List<String> softwares;
	String envision;
	String templateName;
	String templateTag;
	String templateDescription;
	String jobDescription;

}
