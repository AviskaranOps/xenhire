package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.Competency;

public interface CompetencyRepository extends JpaRepository<Competency, Long>{
	
	Competency findByName(String name);

	Competency findByNameAndPillarId(String competency, long id);

	@Query(value="select c.name from candidate_options co inner join competency c on co.competency_id=c.id where co.option_desc=:mostLikely and questionnaire_no=:questionNo", nativeQuery=true)
	LikelyValue getMostLikelyValue(int questionNo, String mostLikely);
	
	
	@Query(value="select c.name from candidate_options co inner join competency c on co.competency_id=c.id where co.option_desc=:leastLikely and questionnaire_no=:questionNo", nativeQuery=true)
	LikelyValue getLeastLikelyValue(int questionNo, String leastLikely);
	
	
	
	public interface LikelyValue{
		String getname();
	}

}
