package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.CompetencyScore;

public interface CompetencyScoreRepository extends JpaRepository<CompetencyScore, Long>{


	CompetencyScore findByCompetencyRank(int competencyRank);

}
