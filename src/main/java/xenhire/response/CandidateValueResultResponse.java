package xenhire.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateValueResultResponse {
	
	String statement;
	long rating;
	
//	long stimulation ;
//	long selfDirection;
//	long hedonism ;
//	long achievement ;
//	long power ;
//	long security ;
//	long conformity ;
//	long tradition ;
//	long benevolence ;
//	long universalism;

}
