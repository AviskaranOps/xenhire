package xenhire.request;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidatePreferences;
import xenhire.model.ClientDetails;
import xenhire.model.ClientPreferences;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidatePreferencesRequest {
	
	CandidatePreferences preferences;
	List<Map<String, String>> primarySkill;
	List<Map<String, String>> secoundrySkill;
	List<Map<String, String>> industry;
	String qualification;
	String specialization;
	int experience;
	String certifications;
	String tools;
	String inOffice;
	String workShift;
	String location;
	String relocation;
	String travel;
	String workingIndependently;
	Map<String, String> salary;
	String workSchedule;
	String appealing;
	String environment;
	String companyOutlook;
	String stakeholder;
	int noticePeriod;
	String jobIntrested;
	String teamHandling;
	Map<String, String> expectedCompensation;
	String visaStatus;
	List<Map<String, String>> experienceThree; // industry

}
