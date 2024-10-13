package xenhire.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InterviewPassedCandidatesDTO implements RowMapper<InterviewPassedCandidatesDTO>{

	long candidateId;
	String candidateName;
	String email;
	String phoneNumber;
	String interviewPassedDate;
	String resume;
	int referenceGiven;
	String referenceStatus;
	
	
        
		@Override
        public InterviewPassedCandidatesDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        	InterviewPassedCandidatesDTO user = new InterviewPassedCandidatesDTO();
        	user.setCandidateId(rs.getLong("id"));
            user.setCandidateName(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPhoneNumber(rs.getString("mobile"));
            user.setInterviewPassedDate(rs.getString("passed_date"));
            user.setResume(rs.getString("resume_name"));
            user.setReferenceGiven(rs.getInt("references_given"));
            user.setReferenceStatus(rs.getString("references_status"));
            return user;
        }

}
