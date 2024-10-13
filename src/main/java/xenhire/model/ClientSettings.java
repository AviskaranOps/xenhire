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
public class ClientSettings {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long clientId;
	String jobCode;             
	String jobFamily;
	String jobDepartment;
	String jobLocation;
	String defaultCurrency;
	String workSetting;
	String typeRole;
	String roleTiming;
	String travel ;
	String acadamicQualification;
	String teamSize;
	String teamLocation;
	String certifications;
	String softwares ;
	String companyOverview;
	String eeo;
	String onboarding;
	String jobStatus;
	String jobSubStatus;
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
