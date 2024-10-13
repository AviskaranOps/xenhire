package xenhire.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminJobRequest {
	
	String jobName;
	String department;
	String location;
	String clientName;
	String jobType;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	LocalDateTime created;
	int totalPositions;

}
