package xenhire.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import xenhire.dto.ClientAssessmentDataResponseDTO;
import xenhire.dto.ClientAssessmentDataResponseDTO;
import xenhire.model.CandidateAssessmentData;
import xenhire.model.CandidateAssessmentRanking;
import xenhire.model.CandidateAssessmentSectionTracking;
import xenhire.model.CandidateAssessmentVersion;
import xenhire.model.CandidateDTPReportData;
import xenhire.model.CandidateOptions;
import xenhire.model.CandidatePreferences;
import xenhire.model.CandidateQuestionnaire;
import xenhire.model.CandidateSkills;
import xenhire.model.CandidateValueAssessmentData;
import xenhire.model.ClientAssessmentRanking;
import xenhire.model.ClientPreferences;
import xenhire.model.ClientSkills;
import xenhire.model.Competency;
import xenhire.model.CandidateAssessmentRanking;
import xenhire.model.CompetencyScore;
import xenhire.model.PillarScore;
import xenhire.model.CandidateAssessmentData;
import xenhire.model.CandidateAssessmentVersion;
import xenhire.model.CandidateOptions;
import xenhire.repository.CandidateAssessmentDataRepository;
import xenhire.repository.CandidateAssessmentDataRepository.CandidateAssessmentResponseData;
import xenhire.repository.CandidateAssessmentRankingRepository;
import xenhire.repository.CandidateAssessmentSectionTrackingRepository;
import xenhire.repository.CandidateAssessmentVersionRepository;
import xenhire.repository.CandidateDTPReportDataRepository;
import xenhire.repository.CandidateOptionsRepository;
import xenhire.repository.CandidatePreferencesRepository;
import xenhire.repository.CandidateQuestionnaireRepository;
import xenhire.repository.CandidateSkillsRepository;
import xenhire.repository.CandidateValueAssessmentDataRepository;
import xenhire.repository.ClientAssessmentDataRepository;
import xenhire.repository.ClientAssessmentVersionRepository;
import xenhire.repository.CompetencyRepository;
import xenhire.repository.CompetencyRepository.LikelyValue;
import xenhire.repository.CompetencyScoreRepository;
import xenhire.repository.PillarScoreRepository;
import xenhire.request.CandidateAssessmentRequest;
import xenhire.request.CandidatePreferencesRequest;
import xenhire.request.CandidateQuestionnaireData;
import xenhire.request.CandidateValueAssessmentRequest;
import xenhire.request.ClientValuesRequest;
import xenhire.request.CandidateAssessmentRequest;
import xenhire.response.CandidateAssessmentResponse;
import xenhire.response.CandidateDtpReportResponse;
import xenhire.response.CandidatePreferencesForReport;
import xenhire.response.CandidateQuestionnaireResponse;
import xenhire.response.ClientIcpReportResponse;
import xenhire.response.ClientPreferencesForReport;
import xenhire.response.CompetencyRanking;
import xenhire.response.PillarRanking;

@Service
public class CandidateService {
	
	@Autowired
	CandidateQuestionnaireRepository CandidateQuestionnaireRepository;
	
	@Autowired
	CandidateOptionsRepository candidateOptionsRepository;
	
	@Autowired
	CandidateAssessmentVersionRepository candidateAssessmentVersionRepository;
	
	@Autowired
	CandidateAssessmentDataRepository candidateAssessmentDataRepository;
	
	@Autowired
	CandidatePreferencesRepository candidatePreferencesRepository;
	
	@Autowired
	CandidateSkillsRepository candidateSkillsRepository;
	
	@Autowired
	CandidateValueAssessmentDataRepository candidateValueAssessmentDataRepository;
	
	@Autowired
	CompetencyRepository competencyRepository;
	
	@Autowired
	CandidateAssessmentSectionTrackingRepository candidateAssessmentSectionTrackingRepository;
	
	@Autowired
	CandidateDTPReportDataRepository candidateDTPReportDataRepository;
	
	@Autowired
	CompetencyScoreRepository competencyScoreRepository;
	
	@Autowired
	PillarScoreRepository pillarScoreRepository;
	
	@Autowired
	CandidateAssessmentRankingRepository candidateAssessmentRankingRepository;

	public ResponseEntity<Object> getCandidateQuestionnaire(long candidateId, int versionNo) {
		// TODO Auto-generated method stub
		//System.out.println(cqList);
		CandidateQuestionnaireResponse resp = new CandidateQuestionnaireResponse();
		CandidateAssessmentVersion cst = candidateAssessmentVersionRepository.findByCandidateIdAndVersionNo(candidateId, versionNo);
		String sect = "section 1";
		int section = 1;
		if(cst != null) {
			if(cst.section1 == true && cst.section2 == false) {
				sect = "section 2";
				section = 2;
			}
			if(cst.section1 == true && cst.section2 == true && cst.section3 == false) { 
				sect = "section 3";
				section = 3;
			}
			if(cst.section1 == true && cst.section2 == true && cst.section3 == true) { 
				resp.setQuestionnaire(new ArrayList());
				resp.setSection(-1);
				return new ResponseEntity<>(resp, null, HttpStatus.OK);
			}
		}
		List<CandidateQuestionnaire> cqList = CandidateQuestionnaireRepository.findBySection(sect);
		List<CandidateQuestionnaireData> data = new ArrayList();
		for(CandidateQuestionnaire cq : cqList) {
			List<CandidateOptions> coList = candidateOptionsRepository.findByQuestionnaireNo(cq.getQuestionNo());
			List<String> options = coList.stream().map(CandidateOptions::getOptionDesc).collect(Collectors.toList());
			CandidateQuestionnaireData cqr = new CandidateQuestionnaireData();
			cqr.setQuestionNo(cq.getQuestionNo());
			cqr.setQuestion(cq.getQuestion());
			cqr.setQuestionType(cq.getOptionType());
			cqr.setOptions(options);
			cqr.setOptionCategory(cq.getOptionCategory());
			data.add(cqr);
		}
		resp.setQuestionnaire(data);
		resp.setSection(section);
		
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> getCandidateValues(long candidateId){
		
		List<CandidateQuestionnaire> cqList = CandidateQuestionnaireRepository.findBySection("section 4");
		List<CandidateQuestionnaireData> data = new ArrayList();
		for(CandidateQuestionnaire cq : cqList) {
			List<CandidateOptions> coList = candidateOptionsRepository.findByQuestionnaireNo(cq.getQuestionNo());
			List<String> options = coList.stream().map(CandidateOptions::getOptionDesc).collect(Collectors.toList());
			CandidateQuestionnaireData cqr = new CandidateQuestionnaireData();
			cqr.setQuestionNo(cq.getQuestionNo());
			cqr.setQuestion(cq.getQuestion());
			cqr.setQuestionType(cq.getOptionType());
			cqr.setOptions(options);
			cqr.setOptionCategory(cq.getOptionCategory());
			data.add(cqr);
		}
		
		return new ResponseEntity<>(data, null, HttpStatus.OK);
		
	}

	public ResponseEntity<Object> savecandidateQuestionnaire(List<CandidateAssessmentRequest> reqList, long candidateId, int section, int versionNo) {
		// TODO Auto-generated method stub 
		CandidateAssessmentVersion cav = null;
		int version = 0;
		System.out.println(versionNo);
		if(versionNo == -1) {
			version = candidateAssessmentVersionRepository.getMaxVersionofcandidate(candidateId);
			cav = new CandidateAssessmentVersion();
			cav.setCandidateId(candidateId);
			cav.setVersionNo(version + 1);
			cav.setCreatedAt(new Date());
		}
		else {
			cav = candidateAssessmentVersionRepository.findByCandidateIdAndVersionNo(candidateId, versionNo);
		}
		
		cav.setUpdatedAt(new Date());
		if(section == 1) cav.setSection1(true);
		if(section == 2) cav.setSection2(true);
		if(section == 3) cav.setSection3(true);
		cav.setAssessmentCategory("default");
		candidateAssessmentVersionRepository.save(cav);
		versionNo = cav.getVersionNo();
		for(CandidateAssessmentRequest req : reqList) {
			if(req.getQuestionType().equals("RANKING")) {
				for(String opts : req.getOptions()) {
					System.out.println("question = " + req.getQuestionNo() + " option = " + opts);
//					CandidateOptions co = candidateOptionsRepository.findByQuestionnaireNoAndOptionDesc(req.getQuestionNo(), opts);
					CandidateOptions co = candidateOptionsRepository.findByOptionDesc(opts);
					CandidateAssessmentData cad = new CandidateAssessmentData();
					cad.setCandidateId(candidateId);
					cad.setCandidateAssessmentVersionId(cav.getId());
					cad.setQuestionNo(req.getQuestionNo());
					cad.setOptionType(req.getOptionCategory());
					cad.setOptionNo(co.getId());	
					candidateAssessmentDataRepository.save(cad);
				}
			}
			else {
				CandidateOptions co = candidateOptionsRepository.findByQuestionnaireNoAndOptionDesc(req.getQuestionNo(), req.getSelectedOption());
				System.out.println("question = " + req.getQuestionNo() + " option = " + req.getSelectedOption());
				//CandidateOptions co = candidateOptionsRepository.findByOptionDesc(req.getSelectedOption());
				CandidateAssessmentData cad = new CandidateAssessmentData();
				cad.setCandidateId(candidateId);
				cad.setCandidateAssessmentVersionId(cav.getId());
				cad.setQuestionNo(req.getQuestionNo());
				cad.setOptionType(req.getQuestionType());
				cad.setOptionNo(co.getId());	
				candidateAssessmentDataRepository.save(cad);
			}
		}
		
//		int nextSection = section + 1;
//		if(nextSection > 3) nextSection = -1;
		
		CandidateAssessmentSectionTracking sectionTracking = candidateAssessmentSectionTrackingRepository.findByCandidateIdAndAssessmentVersionId(candidateId, cav.getVersionNo());
		
		if(sectionTracking == null) {
			sectionTracking = new CandidateAssessmentSectionTracking();
			sectionTracking.setCreatedAt(LocalDateTime.now());
			sectionTracking.setCandidateId(candidateId);
		}
		boolean nextSection = true;
		if(section == 1) sectionTracking.section1 = true;
		if(section == 2) sectionTracking.section2 = true;
		if(section == 3) {
			sectionTracking.section3 = true;
			nextSection = false;
		}
		
		sectionTracking.setUpdatedAt(LocalDateTime.now());
		
		candidateAssessmentSectionTrackingRepository.save(sectionTracking);
		
		if(section == 3) {
			
//			CandidateDTPReportData cdrd = candidateDTPReportDataRepository.findByCandidateId(candidateId);
//			if(cdrd == null) {
//				cdrd = new CandidateDTPReportData();
//				cdrd.setCandidateId(candidateId);
//				cdrd.setCreatedAt(LocalDateTime.now());
//			}
//			cdrd.setAssessmentVersionId(version);
//			cdrd.setAssessment(true);
//			cdrd.setUpdatedAt(LocalDateTime.now());
//			candidateDTPReportDataRepository.save(cdrd);
			
			extractCandidateAssessmentScoreAndSave(candidateId, cav.getVersionNo());
			
		}

		CandidateAssessmentResponse resp = CandidateAssessmentResponse.builder().versionNo(versionNo).savedSection(section).nextSection(nextSection).build();
		
		return new ResponseEntity<>(resp, null, HttpStatus.CREATED);
	}

	private void extractCandidateAssessmentScoreAndSave(long candidateId, int versionNo) {
		
		List<Map<String, Object>> dataList = candidateAssessmentDataRepository.getCandidateAssessment(candidateId, versionNo);
		
		
		
		List<ClientAssessmentDataResponseDTO> respList = new ArrayList();
		
		for(Map<String, Object> m : dataList) {
			ClientAssessmentDataResponseDTO data = new ClientAssessmentDataResponseDTO();
			data.setCompetencyId((long) m.get("competency_id"));
			data.setCorrectOption(Integer.parseInt(m.get("correct_option") + ""));
			data.setOptionDesc(m.get("option_desc").toString());
			data.setOptionLevel(m.get("option_level").toString());
			data.setOptionNo((long) m.get("option_no"));
			data.setOptionType(m.get("option_type").toString());
			data.setPillar_id((long) m.get("pillar_id"));
			data.setPillarName(m.get("pillarName").toString());
			data.setCompetencyName(m.get("competencyName").toString());
			respList.add(data);
		}
		
		System.out.println("client ranking is called = " + respList);
		
		
		List<ClientAssessmentDataResponseDTO> rankingPillar = respList.stream().filter(card -> card.getOptionType().equals("Ranking_Pillar")).collect(Collectors.toList());
														
		List<ClientAssessmentDataResponseDTO> rankingCompetency = respList.stream().filter(card -> card.getOptionType().equals("Ranking_Competency")).toList(); 
		List<ClientAssessmentDataResponseDTO> rating = respList.stream().filter(card -> card.getOptionType().equals("RATING")).toList(); 
        
		List<ClientAssessmentDataResponseDTO> smList = rating.stream().filter(card -> card.getCompetencyName().equals("Stress management")).collect(Collectors.toList());
		List<ClientAssessmentDataResponseDTO> reselList = rating.stream().filter(card -> card.getCompetencyName().equals("Resilience")).collect(Collectors.toList());
		List<ClientAssessmentDataResponseDTO> selfList = rating.stream().filter(card -> card.getCompetencyName().equals("Self-awareness")).collect(Collectors.toList());
		List<ClientAssessmentDataResponseDTO> empathList = rating.stream().filter(card -> card.getCompetencyName().equals("Empathy")).collect(Collectors.toList());
		List<ClientAssessmentDataResponseDTO> commList = rating.stream().filter(card -> card.getCompetencyName().equals("Communication skills")).collect(Collectors.toList());
		List<ClientAssessmentDataResponseDTO> collList = rating.stream().filter(card -> card.getCompetencyName().equals("Collaboration")).collect(Collectors.toList());
		List<ClientAssessmentDataResponseDTO> conflList = rating.stream().filter(card -> card.getCompetencyName().equals("Conflict management")).collect(Collectors.toList());
		List<ClientAssessmentDataResponseDTO> relList = rating.stream().filter(card -> card.getCompetencyName().equals("Relationship building")).collect(Collectors.toList());
		List<ClientAssessmentDataResponseDTO> probList = rating.stream().filter(card -> card.getCompetencyName().equals("Problem Solving")).collect(Collectors.toList());
		List<ClientAssessmentDataResponseDTO> decisionList = rating.stream().filter(card -> card.getCompetencyName().equals("Decision Making")).collect(Collectors.toList());
		List<ClientAssessmentDataResponseDTO> adaptList = rating.stream().filter(card -> card.getCompetencyName().equals("Adaptability")).collect(Collectors.toList());
		List<ClientAssessmentDataResponseDTO> timemList = rating.stream().filter(card -> card.getCompetencyName().equals("Time Management")).collect(Collectors.toList());
		
		List<ClientAssessmentDataResponseDTO> ratingScoringList = new ArrayList();
	    int positiveScore = -1;
	    long count = -1;
	    String optionLevel = "Low";
	    
		count = smList.stream().filter(card -> (card.getCorrectOption() == card.getOptionNo())).count();
		ClientAssessmentDataResponseDTO sm = smList.get(0);
		positiveScore = (int) (count*100)/6;
		String optLevel = "Low";
        if(positiveScore > 80) optLevel = "High"; 
        if(positiveScore > 60 && positiveScore < 80) optLevel = "Med";
        sm.setOptionLevel(optLevel);
        ratingScoringList.add(sm);
		
        count = reselList.stream().filter(card -> (card.getCorrectOption() == card.getOptionNo())).count();
		ClientAssessmentDataResponseDTO resel = reselList.get(0);
		positiveScore = (int) (count*100)/6;
		optLevel = "Low";
        if(positiveScore > 80) optLevel = "High"; 
        if(positiveScore > 60 && positiveScore < 80) optLevel = "Med";
        resel.setOptionLevel(optLevel);
        ratingScoringList.add(resel);
        
        count = selfList.stream().filter(card -> (card.getCorrectOption() == card.getOptionNo())).count();
		ClientAssessmentDataResponseDTO self = selfList.get(0);
		positiveScore = (int) (count*100)/6;
		optLevel = "Low";
        if(positiveScore > 80) optLevel = "High"; 
        if(positiveScore > 60 && positiveScore < 80) optLevel = "Med";
        self.setOptionLevel(optLevel);
        ratingScoringList.add(self);
        
        count = empathList.stream().filter(card -> (card.getCorrectOption() == card.getOptionNo())).count();
		ClientAssessmentDataResponseDTO empath = empathList.get(0);
		positiveScore = (int) (count*100)/6;
		optLevel = "Low";
        if(positiveScore > 80) optLevel = "High"; 
        if(positiveScore > 60 && positiveScore < 80) optLevel = "Med";
        empath.setOptionLevel(optLevel);
        ratingScoringList.add(empath);
        
        count = commList.stream().filter(card -> (card.getCorrectOption() == card.getOptionNo())).count();
		ClientAssessmentDataResponseDTO comm = commList.get(0);
		positiveScore = (int) (count*100)/6;
		optLevel = "Low";
        if(positiveScore > 80) optLevel = "High"; 
        if(positiveScore > 60 && positiveScore < 80) optLevel = "Med";
        comm.setOptionLevel(optLevel);
        ratingScoringList.add(comm);
        
        count = collList.stream().filter(card -> (card.getCorrectOption() == card.getOptionNo())).count();
		ClientAssessmentDataResponseDTO coll = collList.get(0);
		positiveScore = (int) (count*100)/6;
		optLevel = "Low";
        if(positiveScore > 80) optLevel = "High"; 
        if(positiveScore > 60 && positiveScore < 80) optLevel = "Med";
        coll.setOptionLevel(optLevel);
        ratingScoringList.add(coll);
        
        count = conflList.stream().filter(card -> (card.getCorrectOption() == card.getOptionNo())).count();
		ClientAssessmentDataResponseDTO confl = conflList.get(0);
		positiveScore = (int) (count*100)/6;
		optLevel = "Low";
        if(positiveScore > 80) optLevel = "High"; 
        if(positiveScore > 60 && positiveScore < 80) optLevel = "Med";
        confl.setOptionLevel(optLevel);
        ratingScoringList.add(confl);
        
        count = relList.stream().filter(card -> (card.getCorrectOption() == card.getOptionNo())).count();
		ClientAssessmentDataResponseDTO rel = relList.get(0);
		positiveScore = (int) (count*100)/6;
		optLevel = "Low";
        if(positiveScore > 80) optLevel = "High"; 
        if(positiveScore > 60 && positiveScore < 80) optLevel = "Med";
        rel.setOptionLevel(optLevel);
        ratingScoringList.add(rel);
        
        count = probList.stream().filter(card -> (card.getCorrectOption() == card.getOptionNo())).count();
		ClientAssessmentDataResponseDTO prob = probList.get(0);
		positiveScore = (int) (count*100)/6;
		optLevel = "Low";
        if(positiveScore > 80) optLevel = "High"; 
        if(positiveScore > 60 && positiveScore < 80) optLevel = "Med";
        prob.setOptionLevel(optLevel);
        ratingScoringList.add(prob);
        
        count = decisionList.stream().filter(card -> (card.getCorrectOption() == card.getOptionNo())).count();
		ClientAssessmentDataResponseDTO decision = decisionList.get(0);
		positiveScore = (int) (count*100)/6;
		optLevel = "Low";
        if(positiveScore > 80) optLevel = "High"; 
        if(positiveScore > 60 && positiveScore < 80) optLevel = "Med";
        decision.setOptionLevel(optLevel);
        ratingScoringList.add(decision);
        
        count = adaptList.stream().filter(card -> (card.getCorrectOption() == card.getOptionNo())).count();
		ClientAssessmentDataResponseDTO adapt = smList.get(0);
		positiveScore = (int) (count*100)/6;
		optLevel = "Low";
        if(positiveScore > 80) optLevel = "High"; 
        if(positiveScore > 60 && positiveScore < 80) optLevel = "Med";
        adapt.setOptionLevel(optLevel);
        ratingScoringList.add(adapt);
        
        count = timemList.stream().filter(card -> (card.getCorrectOption() == card.getOptionNo())).count();
		ClientAssessmentDataResponseDTO timem = timemList.get(0);
		positiveScore = (int) (count*100)/6;
		optLevel = "Low";
        if(positiveScore > 80) optLevel = "High"; 
        if(positiveScore > 60 && positiveScore < 80) optLevel = "Med";
        timem.setOptionLevel(optLevel);
        ratingScoringList.add(timem);

	 int pillarRank = 1;
	 int competencyRank = 1;
	 String competency = "";
	for(ClientAssessmentDataResponseDTO data1 : rankingPillar) {
		System.out.println("pillar iteration " + pillarRank);
		if(data1.getPillarName().equals("Values") || data1.getPillarName().equals("Skills and Proficiency")) {
			CompetencyScore cs = competencyScoreRepository.findByCompetencyRank(competencyRank);
			PillarScore ps = pillarScoreRepository.findByPillarRank(pillarRank);
			
			CandidateAssessmentRanking car = new CandidateAssessmentRanking();
			car.setCompetency(data1.getCompetencyName());
			car.setCompetencyRank(competencyRank);
			car.setPillar(data1.getPillarName());
			car.setPillarRank(pillarRank);
			car.setCompetencyScore(cs.getCompetencyScore());
			car.setPillarScore(ps.getPillarScore());
			car.setMaxScore(cs.getCompetencyScore());
			car.setCandidateId(candidateId);
			car.setCreatedAt(LocalDateTime.now());
			car.setRating("Low");
			candidateAssessmentRankingRepository.save(car);
			
			if(pillarRank == 4) { competencyRank ++; }
			else { competencyRank = competencyRank + 4; }
		}
		
		for(ClientAssessmentDataResponseDTO data2 : rankingCompetency) {
			
			if(data2.getPillarName().equals(data1.getPillarName())) {
				System.out.println(data2.getCompetencyName());
				for(ClientAssessmentDataResponseDTO data3 : ratingScoringList) {
//					System.out.println(data2.getCompetencyName());
//					System.out.println(data3.getCompetencyName());
					if(data2.getCompetencyName().equals(data3.getCompetencyName())) {
						System.out.println(competencyRank);
						CompetencyScore cs = competencyScoreRepository.findByCompetencyRank(competencyRank);
						PillarScore ps = pillarScoreRepository.findByPillarRank(pillarRank);
						CandidateAssessmentRanking car = new CandidateAssessmentRanking();
						car.setCompetency(data3.getCompetencyName());
						car.setCompetencyRank(competencyRank);
						car.setPillar(data1.getPillarName());
						car.setPillarRank(pillarRank);
						car.setCompetencyScore(cs.getCompetencyScore());
						car.setPillarScore(ps.getPillarScore());
						car.setMaxScore(cs.getCompetencyScore());
						car.setCandidateId(candidateId);
						car.setCreatedAt(LocalDateTime.now());
						car.setRating(data3.getOptionLevel());
						candidateAssessmentRankingRepository.save(car);
					}
				
				}
				
				 if(pillarRank == 5) pillarRank ++;
				 competencyRank ++; 
			}
			
		}
		pillarRank ++;
	}
	}


	public ResponseEntity<Object> savecandidateForm(CandidatePreferencesRequest req, long candidateId) {
		// TODO Auto-generated method stub
		CandidatePreferences cp = candidatePreferencesRepository.findByCandidateId(candidateId);
		if(cp == null) {
			cp = new CandidatePreferences();
			cp.getCreatedAt();
		}
		cp.setAcademicQualification(req.getQualification());
		cp.setAppealing(req.getAppealing());
		cp.setCertificationAndLicence(req.getCertifications());
		cp.setCompanyOutlook(req.getCompanyOutlook());
		cp.setExpectedSalary(Long.parseLong(req.getExpectedCompensation().get("amount")));
		cp.setExpectedSalaryByTime(req.getExpectedCompensation().get("rate"));
		cp.setExpectedSalaryCurrency(req.getExpectedCompensation().get("curancy"));
		cp.setExperience(req.getExperience());
		cp.setJobType(req.getJobIntrested());
		cp.setNoticePeriod(req.getNoticePeriod());
		cp.setRelocation((req.getRelocation() == "yes") ? true : false);
		cp.setRoleTravel(req.getTravel());
		cp.setRoleWorkSettings(req.getWorkSchedule());
		cp.setSalary(Long.parseLong(req.getSalary().get("amount")));
		cp.setSalaryByTime(req.getSalary().get("rate"));
		cp.setSalaryCurrency(req.getSalary().get("amount"));
		cp.setSoftwares(req.getTools());
		cp.setSpecialization(req.getSpecialization());
		cp.setTeamPreference(req.getWorkingIndependently());
		cp.setTeamSize(req.getTeamHandling());
		cp.setVisa(req.getVisaStatus());
		cp.setWorkEnvironment(req.getEnvironment());
		cp.setWorkLocation(req.getLocation());
		cp.setWorkSchedule(req.getWorkSchedule());
		cp.setWorkShifts(req.getWorkShift());
		cp.setWorkWithStakeholders((req.getStakeholder() == "yes") ? true : false);
		cp.setRoleWorkSettings(req.getInOffice());
		//cp = req.getPreferences();
		cp.setCandidateId(candidateId);
		cp.setCreatedAt(new Date());
		candidatePreferencesRepository.save(cp);
		for(Map<String,String> skill : req.getPrimarySkill()) {
			CandidateSkills cs = new CandidateSkills();
			cs.setCandidateId(candidateId);
			cs.setCandidatePreferencesId(cp.getId());
			cs.setSkill(skill.get("skill"));
			cs.setSkillLevel(skill.get("level"));
			cs.setSkillType("primary");
			candidateSkillsRepository.save(cs);
		}
		for(Map<String,String> skill : req.getSecoundrySkill()) {
			CandidateSkills cs = new CandidateSkills();
			cs.setCandidateId(candidateId);
			cs.setCandidatePreferencesId(cp.getId());
			cs.setSkill(skill.get("skill"));
			cs.setSkillLevel(skill.get("level"));
			cs.setSkillType("secondary");
			candidateSkillsRepository.save(cs);
		}
		for(Map<String,String> skill : req.getExperienceThree()) {
			CandidateSkills cs = new CandidateSkills();
			cs.setCandidateId(candidateId);
			cs.setCandidatePreferencesId(cp.getId());
			cs.setSkill(skill.get("industry"));
			cs.setSkillLevel(skill.get("level"));
			cs.setSkillType("industry");
			candidateSkillsRepository.save(cs);
		}
		
		return new ResponseEntity<>("data saved successfully", null, HttpStatus.CREATED);
	}

	public ResponseEntity<Object> savecandidateValueAssessment(List<CandidateValueAssessmentRequest> reqList,long candidateId) {
		// TODO Auto-generated method stub
		int versionNo = candidateValueAssessmentDataRepository.getMaxVersionNo(candidateId);
		for(CandidateValueAssessmentRequest req : reqList) {
			System.out.println();
			CandidateOptions optionMost = candidateOptionsRepository.findByQuestionnaireNoAndOptionDesc(req.getQuestionNo(), req.getMostLikely());
			System.out.println(optionMost);
			CandidateOptions optionLeast = candidateOptionsRepository.findByQuestionnaireNoAndOptionDesc(req.getQuestionNo(), req.getLeastLikely());
			System.out.println(optionLeast);
			Optional<Competency> cMost = competencyRepository.findById(optionMost.getCompetencyId());
			System.out.println(cMost);
			Optional<Competency> lMost = competencyRepository.findById(optionLeast.getCompetencyId());
			//LikelyValue mostLikelyValue = competencyRepository.getMostLikelyValue(req.getQuestionnaireNo(), req.getMostLikely());
			System.out.println(lMost);
			//LikelyValue leastLikelyValue = competencyRepository.getLeastLikelyValue(req.getQuestionnaireNo(), req.getLeastLikely());
			CandidateValueAssessmentData cvad = new CandidateValueAssessmentData();
			cvad.setCandidateId(candidateId);
			cvad.setLeastLikely(req.getLeastLikely());
			cvad.setMostLikely(req.getMostLikely());
			cvad.setMostLikelyValue(cMost.get().getName());
			cvad.setLeastLikelyValue(lMost.get().getName());
			cvad.setVersionNo(versionNo + 1);
			cvad.setCreatedAt(LocalDateTime.now());
			cvad.setQuestionNo(req.getQuestionNo());
			candidateValueAssessmentDataRepository.save(cvad);
		}
		
		return new ResponseEntity<>("saved", null, HttpStatus.OK);
	}

	public ResponseEntity<Object> getCandidateSection(long candidateId, int versionNo) {
		// TODO Auto-generated method stub
		CandidateAssessmentVersion cst = candidateAssessmentVersionRepository.findByCandidateIdAndVersionNo(candidateId, versionNo);
		int sect = 1;
		if(cst != null) {
			if(cst.section1 == true && cst.section2 == false) {
				sect = 2;
			}
			if(cst.section1 == true && cst.section2 == true && cst.section3 == false) { 
				sect = 3;
			}
		}
		Map m = new HashMap();
		m.put("section", sect);
		return new ResponseEntity<>(m, null, HttpStatus.OK);
	}


	public ResponseEntity<Object> postCandidateRanking(long candidateId, int versionNo) {
		// TODO Auto-generated method stub
		extractCandidateAssessmentScoreAndSave(candidateId, versionNo);
		return null;
	}
	
	
public ResponseEntity<Object> getCandidateDtpReport(long candidateId) {
		
		List<String> crList = new ArrayList<String>();
        List<PillarRanking> prList = new ArrayList<PillarRanking>();
        List<CandidateSkills> primarySkills = new ArrayList<CandidateSkills>();
        List<CandidateSkills> secondarySkills = new ArrayList<CandidateSkills>();
        List<CandidateSkills> industry = new ArrayList<CandidateSkills>();
        List<String> certificationsList = new ArrayList<String>();
        int pillarRank = 1;
        int i = 1;
		List<CandidateAssessmentRanking> rankingList = candidateAssessmentRankingRepository.findByCandidateId(candidateId);
		for(CandidateAssessmentRanking r : rankingList) {
			
			if(i <= 5 && r.getCompetency().isEmpty() == false) {
				CompetencyRanking competency = new CompetencyRanking();
				
				competency.setCompetency(r.getCompetency());
				competency.setRank(r.getCompetencyRank());
				
				crList.add(r.getCompetency());
				i++;
			}
		}
		
		List<CandidateSkills> skillList = candidateSkillsRepository.findByCandidateId(candidateId);
		
		primarySkills = skillList.stream().filter(skill -> skill.getSkillType().equals("primary")).collect(Collectors.toList());
		
		secondarySkills = skillList.stream().filter(skill -> skill.getSkillType().equals("secondary")).collect(Collectors.toList());
		
		industry = skillList.stream().filter(skill -> skill.getSkillType().equals("industry")).collect(Collectors.toList());
		
		List<CandidateSkills> skills = Stream.concat(primarySkills.stream(), secondarySkills.stream())
                .collect(Collectors.toList());
		
		CandidatePreferences cp = candidatePreferencesRepository.findByCandidateId(candidateId);
		
		CandidatePreferencesForReport cpfr = new CandidatePreferencesForReport();
		
		cpfr.setAcademics(cp.getAcademicQualification());
		cpfr.setExpectedPay(cp.getExpectedSalary() + " " +cp.getExpectedSalaryCurrency()+"/"+cp.getExpectedSalaryByTime());
		cpfr.setLocation(cp.getWorkLocation());
		cpfr.setRelocation((cp.isRelocation() == true) ? "yes" : "no");
		cpfr.setSpecialization(cp.getSpecialization());
		cpfr.setTravel(cp.getRoleTravel());
		cpfr.setVisa(cp.getVisa());
		cpfr.setWork(cp.getRoleWorkSettings());
		cpfr.setWorkEnvironment(cp.getWorkEnvironment());
		
		String[] itemsArray = cp.getCertificationAndLicence().split(",");

        // Step 2: Convert the array to a list, trimming whitespace and removing empty elements
        certificationsList = Arrays.stream(itemsArray)
                                       .map(String::trim)
                                       .filter(item -> !item.isEmpty())
                                       .collect(Collectors.toList());
        
        List<String> mostLikely = new ArrayList<String>();
        List<String> leastLikely = new ArrayList<String>();
        
        List<CandidateValueAssessmentData> candidateValuesList = candidateValueAssessmentDataRepository.findByCandidateId(candidateId);
        System.out.println(candidateValuesList);
        for(CandidateValueAssessmentData cand : candidateValuesList) {
	       	 mostLikely.add(cand.getMostLikelyValue());
	       	 leastLikely.add(cand.getLeastLikelyValue());
        }
        
        List<String> valuesList = new ArrayList<String>();
        valuesList.add("Self-Direction");
        valuesList.add("Stimulation");
        valuesList.add("Hedonism");
        valuesList.add("Achievement");
        valuesList.add("Power");
        valuesList.add("Security");
        valuesList.add("Conformity");
        valuesList.add("Tradition");
        valuesList.add("Benevolence");
        valuesList.add("Universalism");

        
        Map<String, Integer> mostLikelyMap =   mostLikely.stream()
                .collect(Collectors.groupingBy(
                        element -> element,              // Group by the element itself
                        Collectors.collectingAndThen(    // Collect the elements
                                Collectors.counting(),   // Count each group
                                Long::intValue           // Convert Long to Integer
                        )
                ));
		Map<String, Integer> leastLikelyMap =   leastLikely.stream()
			                 .collect(Collectors.groupingBy(
			                         element -> element,              // Group by the element itself
			                         Collectors.collectingAndThen(    // Collect the elements
			                                 Collectors.counting(),   // Count each group
			                                 Long::intValue           // Convert Long to Integer
			                         )
			                 ));
		
		System.out.println(mostLikelyMap);
		System.out.println(leastLikelyMap);
        Map<String, Integer> countDifferences = new HashMap();
        
        for(String key : mostLikelyMap.keySet()) {
       	if(leastLikelyMap.containsKey(key)) {
       		Integer difference = Math.abs(mostLikelyMap.get(key) - leastLikelyMap.get(key));
       		countDifferences.put(key, difference);
       	}
       	else {
       		countDifferences.put(key, mostLikelyMap.get(key));
       	}
        }
        
        for(String key : leastLikelyMap.keySet()) {
       	 if (countDifferences.containsKey(key) && mostLikelyMap.containsKey(key) == false)
              {
                  countDifferences.put(key, leastLikelyMap.get(key) - (2*leastLikelyMap.get(key)));
              }
        }
        
        for(String value : valuesList){
          if(countDifferences.containsKey(value) == false) {
              countDifferences.put(value, 0);
          }
        }
        
        Map<String, Integer> sortedDifferences = countDifferences.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        
        
        CandidateDtpReportResponse resp = new CandidateDtpReportResponse();
       // resp.setPillarRanking(prList);
        resp.setCompetencyRanking(crList);
        resp.setCertifications(certificationsList);
        resp.setSkills(skills);
        resp.setPreferences(cpfr);
        resp.setIndustry(industry);
        resp.setValues(new ArrayList<>(sortedDifferences.keySet()));
		
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
	}

	
	
	
	
	
	
	

}
