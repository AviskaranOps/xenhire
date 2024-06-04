package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.ClientValueAssessmentData;

public interface ClientValuesDataRepository extends JpaRepository<ClientValueAssessmentData, Long>{

	ClientValueAssessmentData findByClientId(long clientId);

}
