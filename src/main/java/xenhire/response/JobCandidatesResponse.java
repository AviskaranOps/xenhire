package xenhire.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.ClientJobDetails;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobCandidatesResponse {
	
	long id;
	long clientId;
	long candidateId;
	String username;
	String requestedData;
	boolean dtpAccess;
	String matchingScore;
	String dtpStatus;
	String alignmentDate;
	String notify;
	long dtpReportId;
	String createdTime;
	String applicationStatus;
	String applicationSubStatus;
	String profileStatus;
	String completedStatus;
	String role;	
	String recommendationStatus;
	String applicationSource;
	int recommendationRank;
	String resume;
	
}
