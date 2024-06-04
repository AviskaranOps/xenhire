package xenhire.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import xenhire.model.PillarScore;

public interface PillarScoreRepository extends JpaRepository<PillarScore, Long>{


	PillarScore findByPillarRank(int pillarRank);

}
