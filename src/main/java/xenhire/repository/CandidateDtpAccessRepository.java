package xenhire.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import xenhire.dto.CandidateDTPAccessDTO;
import xenhire.model.CandidateDtpAccess;

public interface CandidateDtpAccessRepository extends JpaRepository<CandidateDtpAccess, Long>{

	
	
	@Query(value="SELECT \r\n"
			+ "    client.id as clientId,\r\n"
			+ "    client.company_name as clientName, \r\n"
			+ "    client.name as managerName,\r\n"
			+ "    client.email,\r\n"
			+ "    ifnull(access.authorized,false) as authorized, \r\n"
			+ "    ifnull(access.declined,false) as declined,\r\n"
			+ "    access.approved_at as approvedAt,\r\n"
			+ "    access.declined_at as declinedAt"	
			+ "  FROM \r\n"
			+ "    client\r\n"
			+ "  LEFT JOIN \r\n"
			+ "    candidate_dtp_access access \r\n"
			+ "ON \r\n"
			+ "    client.id = access.client_id\r\n"
			+ "AND \r\n"
			+ "    access.candidate_id = :candidateId",
			nativeQuery=true
			)
	List<Object[]> getCandidateDtpAccess(@Param("candidateId") long candidateId);
	
	
	public interface DtpAccess {
		
		String getclientId();
		String getclientName();
		String getmanagerName();
		String getemail();
		boolean getauthorized();
		boolean getdeclined();
		LocalDateTime getdate();
	}


	CandidateDtpAccess findByCandidateIdAndClientId(long candidateId, long clientId);


	CandidateDtpAccess findByClientIdAndCandidateId(long clientId, long id);


	@Query(value="select ifnull(count(*), 0) from candidate_dtp_access where candidate_id=:candidateId", nativeQuery=true)
	int getAuthorizedClientCount(long candidateId);


    List<CandidateDtpAccess> findByClientIdAndCandidateIdOrderByCreatedAtDesc(long id, long candidateId);

}
