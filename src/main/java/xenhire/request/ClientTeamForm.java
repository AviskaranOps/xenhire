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
public class ClientTeamForm {
	
	String teamSize;
	String locationOfTeam;
	String project;
	String crossFunctionality;
	String domainRole;
	String teamMembercontribution;


}
