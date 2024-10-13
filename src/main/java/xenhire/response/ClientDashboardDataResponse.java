package xenhire.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDashboardDataResponse {
	
	int candidatesCount;
	int jobCount;
	int assessmentCount;
	String companyName;

}
