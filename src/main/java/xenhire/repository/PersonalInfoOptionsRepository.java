package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.PersonalInfoOptions;

public interface PersonalInfoOptionsRepository extends JpaRepository<PersonalInfoOptions, Long>{

	List<PersonalInfoOptions> findAllByCandidateId(long candidateId);

}
