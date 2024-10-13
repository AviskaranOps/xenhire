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
import xenhire.request.ClinetPreferenceTemplateRequest;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientPreferenceTemplate {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	long clientId;
	String templateName;
	String templateTag;
	String templateDescription;
	String createdBy;
	String crossFunctinality;
	String industrySpecificExperience;
	String jobDescription;
	String responsesibilities;
	String industrySpecificIndepthKnowledge;
	String roleType;
	String roletimings;
	String workSetting;
	String roleLocation;
	String relocation;
	String relocationBudget;
	String roleTravel;
	String visa;
	String compensation;
	String minQualification;
	String requireAcademicQualification;
	String differentAcademic;
	String certifications;
	String softwares;
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
