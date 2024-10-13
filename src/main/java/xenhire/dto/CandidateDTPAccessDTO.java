package xenhire.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidateSkills;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateDTPAccessDTO {
	
	long clientId;
	String clientName;
	String managerName;
	String email;
	Boolean authorized;
	Boolean declined;
	String date;
	boolean dtpData;
	
//	public CandidateDTPAccessDTO(long clientId, String clientName, String managerName, String email, boolean authorized, boolean decliend, String date) {
//		this.clientId = clientId;
//		this.clientName = clientName;
//		this.date = date;
//		this.declined = declined;
//		this.authorized = authorized;
//		this.email = email;
//		this.managerName = managerName;
//	}
}
