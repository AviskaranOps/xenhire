package xenhire.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientReferenceRequest {

	long id;
	long candidateId;
	long clientId;
	String candidateName;
	String position;
	String referenceName;
	String designation;
	String email;
	String phoneNumber;
	String professionalNetwork;
	String company;
	String connectionType;
	String hasSuperior;
	String workDuration;
	String capacity;
	String formallyEvaluated;
	LocalDate workDurationStart;
	LocalDate workDurationEnd;
	LocalDateTime createdAt;
	LocalDateTime updatedAt;
	List<ClientReferenceQuestionsRequest> referenceQuestions;
}
