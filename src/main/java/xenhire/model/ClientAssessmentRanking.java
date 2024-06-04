package xenhire.model;

import java.time.LocalDateTime;

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
public class ClientAssessmentRanking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long clientId;
	String pillar;
	int pillarRank;
	String competency;
	int competencyRank;
	int pillarScore;
	int competencyScore;
	String rating;
	int maxScore;
	LocalDateTime createdAt;
	
}
