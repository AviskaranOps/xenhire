package xenhire.model;

import java.time.LocalDateTime;
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
public class CandidateDTPReportData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long candidateId;
	long assessmentVersionId;
	long valuesVersionId;
	public boolean assessment;
	public boolean valueAssessment;
	public boolean preferenes;
	LocalDateTime createdAt;
	LocalDateTime updatedAt;
	
}
