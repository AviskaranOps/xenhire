package xenhire.request;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.CandidatePreferences;
import xenhire.model.ClientDetails;
import xenhire.model.ClientPreferences;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidatePreferencesRequest {
	
    String workSetting;
    String workShift;
    String prefferedLocation;
    String openToRelocate;
    String requiredForTravel;
    String workSchedule;
    String workIndependantly;
    Map<String, String> expectedSalary;
    String appealingWork;
    String workEnvironment;
    String importantFactor;

}
