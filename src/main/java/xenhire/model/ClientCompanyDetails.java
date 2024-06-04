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
public class ClientCompanyDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	long clientId;
	String companyName;
	String companyDomain;
	String companyOperation;
	String companySize;
	String companyPhase;
	boolean drugTest;
	String drugTestType;
	boolean backgroundCheck;
	Date createdAt;
	Date updatedAt;

}
