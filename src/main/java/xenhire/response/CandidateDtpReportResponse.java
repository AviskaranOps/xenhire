package xenhire.response;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidateSkills;
import xenhire.model.ClientSkills;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateDtpReportResponse {
	
	String name; 
	String mobileNo;
	String email;
	String location;
	String gender;
	String education;
	String summary;
	int expert;
	List<String> openTo;
	boolean readyToTravel;
	boolean readyToRelocate;
	List<String> credentials;
	List<CandidateTechnicalSkillResponse> technicalSkill;
	List<CandidateValueResultResponse> coreValues;
	List<Object> cognitiveAgility;
	List<Object> sociabilitySkills;
	List<Object> emtionalFlexibility;
	List<String> pillars;
	List<String> behaviourAttributes;
	
	
}
