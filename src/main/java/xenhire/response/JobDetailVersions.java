package xenhire.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.Competency;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDetailVersions {
	
	String version;
	int value;

}
