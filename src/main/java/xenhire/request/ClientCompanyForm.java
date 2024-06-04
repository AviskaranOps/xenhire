package xenhire.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientCompanyForm {
	
	String companyDomain;
	String companyOperations;
	String companySize;
	String companyPhase;
	String drugTest;
	List<String> drugTestList;
	String backgroundCheck; 

}
