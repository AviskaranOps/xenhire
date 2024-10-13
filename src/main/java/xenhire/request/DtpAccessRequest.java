package xenhire.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.dto.CandidateDTPAccessDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DtpAccessRequest {
	
	long clientId;
	boolean authorized;
	boolean declined;

}
