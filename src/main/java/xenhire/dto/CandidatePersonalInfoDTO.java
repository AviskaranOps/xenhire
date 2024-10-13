package xenhire.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidateEducationDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidatePersonalInfoDTO {
	
	String fullName;
	String title;
	String contactNumber;
	String url;
	String summary;
	List<Map<String, Object>> education;
	String resume;
	String workSetting;
    String workShift;
    String prefferedLocation;
    String openToRelocate;
    String requiredForTravel;
    String workSchedule;
    String workIndepandently;
    int yearsOfExperience;
    Map<String, String> expectedSalary;
    Map<String, String> expectedRange;
    String typeOfJobOpening;
    String appealingWork;
    String workEnvironment;
    String companyOutlook;
    String visaStatus;
    String academicQualification;
    String specialization;
    String academicBackGround;
    String specificLicense;
    String experienceRole;
    String workInIndustry;
    String workRole;
    String experienceStackHolder;
    String noticePeriod;
    String teamHandling;
    String teamSize;
    List<Map<String, String>> indusrtyExperience;
    List<Map<String, String>> primarySkills;
    List<Map<String, String>> secoundrySkills;
    Map<String, String> softwareApplication;

}
