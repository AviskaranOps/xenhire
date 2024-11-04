package xenhire.model;

import jakarta.persistence.Column;
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
@Table(name = "candidate_professional_details")
public class CandidateProfessionalDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String projectTitle;
    public String role;
    public Double yearsOfExperience;
	@Column(columnDefinition = "MEDIUMTEXT")
    public String projectDescription;
	@Column(columnDefinition = "MEDIUMTEXT")
    public String expWithStakeHolders;
    public Boolean teamHandlingExp;
    public Integer teamSize;
}
