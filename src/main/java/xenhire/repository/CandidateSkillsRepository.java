package xenhire.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CandidateSkills;

public interface CandidateSkillsRepository extends JpaRepository<CandidateSkills, Long>{

	List<CandidateSkills> findByCandidateId(long clientId);

	CandidateSkills findByCandidateIdAndSkill(long candidateId, Map<String, String> skill);

	CandidateSkills findByCandidateIdAndSkillAndSkillType(long candidateId, String skill, String skillType);

	List<CandidateSkills> findByCandidatePreferencesId(long preferenceId);

}
