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
public class ClientPreferences {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long clientId;
	String jobDescription;
	boolean IndustrySpecificExperience;
	String specificIndustry;
	boolean industrySpecificIndepthKnowledge;
	String roleType;
	String roleTimings;
	String roleWorkShifts;
	String roleWorkLocation;
	boolean relocationBudget;
	long relocationExpenses;
	String relocationCurrency;
	String roleTravel;
	String roleCompensation;
	String compensationCurrency;
	String visa;
	boolean regulatoryReq;
	String candidateAcademicBackground;
	String specificCertifications;
	String particularSoftwares;
	String envision;
	String minAcademicQualification;
	Date createdAt;
	Date updatedAt;
}
