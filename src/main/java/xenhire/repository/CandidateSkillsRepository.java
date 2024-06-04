package xenhire.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateSkills;

public interface CandidateSkillsRepository extends JpaRepository<CandidateSkills, Long>{

	List<CandidateSkills> findByCandidateId(long clientId);

}
