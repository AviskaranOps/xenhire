package xenhire.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.ClientSkills;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidatePreferencesForReport {
	
	String academics;
	String specialization;
	String work;
	String location;
	String visa;
	String relocation;
	String expectedPay;
	String travel;
	String workEnvironment;
	

}
