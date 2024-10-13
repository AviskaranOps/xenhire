package xenhire.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
public class JobTemplate {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	long clientId;
	long jobId;
	int versionId;
	boolean template;
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
	String certifications;
	String softwares;
	String envision;
	String templateName;
	String templateTag;
	String templateDescription;
	String jobDescription;
	String createdBy;
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
