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
public class ClientPreferenceForm {
	String jobDescription;
	String specificExp;
	String specificIndustry;
	String candidateDepthKnowledge;
	String roleType;
	String roleTimings;
	String roleWorkSettings;
	String roleWorkingLocation;
	String relocationBudget;
	String relocationExpenses;
	String relocationCurrency;
	String roleTravel;
	String roleCompensation;
	String compensationCurrency;
	String visa;
	String minAcedamicQualification;
	String regulatoryReq;
	String candidateAcademicBackground;
	String specificCertifications;
	String particularSoftwares;
	String envision;
	List<Map<String, String>> primarySkills;
	List<Map<String, String>> secondarySkills;
	

}
