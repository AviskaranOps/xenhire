package xenhire.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientPreferencesForReport {
	
	
	String relocation;
	String compensation;
	String visa;
	String location;
	
}
