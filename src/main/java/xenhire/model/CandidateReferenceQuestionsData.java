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
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateReferenceQuestionsData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long referenceId;
	String templateName;
	String createdBy;
	long clientAssessmentVersionId;
	int questionNo;
	String optionType;
	long optionNo;
	Date createdAt;

}
