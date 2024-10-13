package xenhire.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.ClientIcpTemplate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobTitleRequest {
	
	long clientId;
	String jobTitle;

}
