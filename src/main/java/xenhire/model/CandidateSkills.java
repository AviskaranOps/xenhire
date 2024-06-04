package xenhire.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.request.ClientFormRequest;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateSkills {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long candidateId;
	long candidatePreferencesId;
	String skill;
	String skillLevel;
	String skillType;

}
