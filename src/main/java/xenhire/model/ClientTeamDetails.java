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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientTeamDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long clientId;
	String teamSize;
	String teamLocation;
	String projectName;
	boolean crossFunctionality;
	String domainRoles;
	String teamMemberContribution;
	Date createdAt;
	Date updatedAt;

}
