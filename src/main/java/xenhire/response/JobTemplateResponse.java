package xenhire.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.JobPreferencesTemplate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobTemplateResponse {

	long id;
	String name;
	String createdBy;
}
