package xenhire.model;

import java.util.Date;

import jakarta.persistence.Column;
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
public class CompetencyScore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	int competencyScore;
	int competencyRank;
	int pillarRank;
}
