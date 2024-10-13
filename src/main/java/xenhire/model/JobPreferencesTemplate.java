package xenhire.model;

import java.time.LocalDateTime;

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
public class JobPreferencesTemplate {
	
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	long clientId;
	String templateName;
	String createdBy;
	boolean specificIndustryExperience;
	String industryExperience;
	String jobDescription;
	String responsibilities;
	boolean industryDepthKnowledge;
	String roleType;
	String roleTimings;
	String workSetting;
	String roleLocation;
	boolean relocationBenefits;
	int relocationBudget;
	String roleTravel;
	String visa;
	String roleCompensation;
	String compensationPerTime;
	String academicQualification;
	boolean regulatoryRequirements;
	boolean diffAcademicBackground;
	String certifications;
	String softwareApplications;
	String envision;
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
