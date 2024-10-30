package xenhire.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "candidate_skill_details")
public class CandidateSkillDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String technology;
    public String expertise;
}
