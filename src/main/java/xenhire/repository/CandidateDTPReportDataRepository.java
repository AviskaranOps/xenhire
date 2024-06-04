package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateDTPReportData;

public interface CandidateDTPReportDataRepository extends JpaRepository<CandidateDTPReportData, Long>{

	CandidateDTPReportData findByCandidateId(long candidateId);

}
