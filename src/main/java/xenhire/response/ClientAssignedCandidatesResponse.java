package xenhire.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientAssignedCandidatesResponse {
	
	String name;
	String email;
	String phone;
	String linkedIn;
	String resume;
	

}
