package xenhire.request;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.ClientDetails;
import xenhire.model.ClientPreferences;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientFormRequest {
	
	
	ClientCompanyForm formOne;
	ClientTeamForm	formTwo;
	ClientPreferenceForm formThree;
	List<Map<String, String>> primeSkills;
	List<Map<String, String>> secondSkills;

}
