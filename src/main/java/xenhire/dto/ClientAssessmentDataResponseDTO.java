package xenhire.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.response.ClientQuestionnaireResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientAssessmentDataResponseDTO {
	
	String optionType;
	int correctOption;
	long optionNo;
	String optionDesc;
	String optionLevel;
	long competencyId;
	String competencyName;
	long pillar_id;
	String pillarName;
	String templateName;
	int weightage;
	String createdBy;

}
