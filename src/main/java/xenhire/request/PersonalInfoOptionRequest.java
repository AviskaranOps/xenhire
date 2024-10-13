package xenhire.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.PersonalInfoOptions;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalInfoOptionRequest {
	
	String name;
	String optionType;

}
