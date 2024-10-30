package xenhire.model;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidatePersonalInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long candidateId;
	long versionId;
	String academicQualification;
	String academicBackgroundAlignsToRole;
	String specialization;
	boolean RoleRelatedIndustry;
	String industryRole;
	int experience;
	String certificationAndLicence;
	String softwares;
	String softwareExperienceLevel;
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
	String noticePeriod;
	String expectedSalaryByTime;
	long expectedSalary;
	String jobType;
	boolean teamHandling;
	String teamSize;
	String visa;
	String salaryCurrency;
	String expectedSalaryCurrency;
	String degree;
	String fieldOfStudy;
	String institution;
	String city;
	String state;
	String certificates;
	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	LocalDateTime createdAt;
	@Column(columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	LocalDateTime updatedAt;

	 @PrePersist
	    @PreUpdate
	    protected void onPersistOrUpdate() {
	        if (createdAt == null) {
	            createdAt = LocalDateTime.now();
	        }
	        updatedAt = LocalDateTime.now();
	    }

}
