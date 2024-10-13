package xenhire.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.ClientValueAssessmentData;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientValueResponse {
	
	long id;
	String templateName;
	String templateTag;
	String templateDescription;
	List<Map> valuesData;
	String createdBy;

}
