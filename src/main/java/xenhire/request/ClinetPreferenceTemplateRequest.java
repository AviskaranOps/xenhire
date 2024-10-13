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
public class ClinetPreferenceTemplateRequest {
	
	 long id;
	 String templateName;
	 String templateTag;
	 String templateDescription;
	 String createdBy;
	 String experianceInIndustry; // crossFunctinality
	 String specifyIndusrtyExp; // industrySpecificExperience
	 String jobDescription; // jobDescription
	 String scopOfRole; // responsesibilities
	 String depthKnowledge; // industrySpecificIndepthKnowledge
	 String typeOfRoles; // roleType
	 String timeOfRole;  // roletimings
	 String workSetting; // workSetting
	 String locationRole;  // roleLocation
	 String relocation;  // relocation
	 String relocationBudget;  // relocationBudget
	 String travelRole; // roleTravel
	 String visa;   // visa
	 String compensationOffered; // compensation
	 List<Map<String, String>> primarySkills;  
	 List<Map<String, String>> secoundrySkills;
	String minimumLevelQualification;    // minQualification
	String requireAcademicQualification;  // requireAcademicQualification
	String differentAcademic;  // differentAcademic
	List<Map<String, String>> certificationsOrLicenses; // certifications
	List<Map<String, String>> toolsOrSoftwaresetToolsOrSoftware;  // softwares
	String successThreeyear;  // envision
}
