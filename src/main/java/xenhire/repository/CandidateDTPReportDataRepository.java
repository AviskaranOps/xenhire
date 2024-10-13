package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.CandidateDTPReportData;

public interface CandidateDTPReportDataRepository extends JpaRepository<CandidateDTPReportData, Long>{

	
	CandidateDTPReportData findByCandidateId(long candidateId);

	@Query(value="select * from candidatedtpreport_data where candidate_id=:candidateId and id=(select max(id) from candidatedtpreport_data)", nativeQuery=true)
	CandidateDTPReportData getRecentRecord(long candidateId);

	List<CandidateDTPReportData> findAllByCandidateId(long candidateId);


}
