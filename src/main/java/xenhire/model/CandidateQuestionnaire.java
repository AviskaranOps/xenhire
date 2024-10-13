package xenhire.model;

import java.util.Date;
import java.util.Set;

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
public class CandidateQuestionnaire {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	int questionNo;
	String question;
	int questionCategory;
	String optionType;
	String optionCategory;
	long correctOption;
	String section;
	String experienceLevel;
	Date createdAt;

}
