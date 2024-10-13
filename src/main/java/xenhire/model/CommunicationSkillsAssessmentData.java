package xenhire.model;

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
public class CommunicationSkillsAssessmentData {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	long assessmentBatchId;
	String question;
	String optionSelected;
	String initiatedBy;
	

}
