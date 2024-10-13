package xenhire.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CriticalScoring {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	long id;
	String criteria;
	int rating;
	String comments;
	long interviewFeedbackId;

}
