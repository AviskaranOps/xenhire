package xenhire.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidatePreferences {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long candidateId;
	String academicQualification;
	String specialization;
	int experience;
	String certificationAndLicence;
	String softwares;
	String roleWorkSettings;
	String workShifts;
	String workLocation;
	boolean relocation;
	String roleTravel;
	String teamPreference;
	String salaryByTime;
	long salary;
	String workSchedule;
	String appealing;
	String workEnvironment;
	String companyOutlook;
	boolean workWithStakeholders;
	int noticePeriod;
	String expectedSalaryByTime;
	long expectedSalary;
	String jobType;
	String teamSize;
	String visa;
	String salaryCurrency;
	String expectedSalaryCurrency;
	Date createdAt;
	Date updatedAt;
}
