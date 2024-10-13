package xenhire.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateInfo {
	
	long id;
    String name;
    String email;
    String no;
    String location;
    String experiance;
    String education;
    List<String> skills;
    String status;
    String date;

}
