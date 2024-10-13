package xenhire.response;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidateSkills;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateSpectrumResults {
	
	List<Object> cognitiveAgility;
	List<Object> sociabilitySkills;
	List<Object> emtionalFlexibility;
	List<String> pillars;
	List<String> behaviourAttributes;
	String createdBy;
	String templateName;
	long id;
	String templateTag;
	String templateDescription;
	

}
