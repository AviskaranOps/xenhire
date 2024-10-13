package xenhire.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import xenhire.dto.CandidateDTPAccessDTO;
import xenhire.dto.CandidatePersonalInfoDTO;
import xenhire.dto.ClientAssessmentDataResponseDTO;
import xenhire.dto.ClientAssessmentDataResponseDTO;
import xenhire.model.BatchAssessmentCandidates;
import xenhire.model.CandidateAssessmentBatch;
import xenhire.model.CandidateAssessmentData;
import xenhire.model.CandidateAssessmentRanking;
import xenhire.model.CandidateAssessmentSectionTracking;
import xenhire.model.CandidateAssessmentVersion;
import xenhire.model.CandidateDTPReportData;
import xenhire.model.CandidateDtpAccess;
import xenhire.model.CandidateEducationDetails;
import xenhire.model.CandidateOptions;
import xenhire.model.CandidatePersonalInfo;
import xenhire.model.CandidatePreferences;
import xenhire.model.CandidateQuestionnaire;
import xenhire.model.CandidateScreeningQuestions;
import xenhire.model.CandidateSkills;
import xenhire.model.CandidateValueAssessmentData;
import xenhire.model.CandidateValueAssessmentVersion;
import xenhire.model.Client;
import xenhire.model.ClientAssessmentBatch;
import xenhire.model.ClientAssessmentRanking;
import xenhire.model.ClientPreferences;
import xenhire.model.ClientSkills;
import xenhire.model.Competency;
import xenhire.model.CandidateAssessmentRanking;
import xenhire.model.CompetencyScore;
import xenhire.model.PersonalInfoOptions;
import xenhire.model.PillarOrderText;
import xenhire.model.PillarScore;
import xenhire.model.User;
import xenhire.model.CandidateAssessmentData;
import xenhire.model.CandidateAssessmentVersion;
import xenhire.model.CandidateOptions;
import xenhire.repository.BatchAssessmentCandidatesRepository;
import xenhire.repository.CandidateAssessmentBatchRepository;
import xenhire.repository.CandidateAssessmentDataRepository;
import xenhire.repository.CandidateAssessmentDataRepository.CandidateAssessmentResponseData;
import xenhire.repository.CandidateAssessmentRankingRepository;
import xenhire.repository.CandidateAssessmentSectionTrackingRepository;
import xenhire.repository.CandidateAssessmentVersionRepository;
import xenhire.repository.CandidateDTPReportDataRepository;
import xenhire.repository.CandidateDtpAccessRepository;
import xenhire.repository.CandidateDtpAccessRepository.DtpAccess;
import xenhire.repository.CandidateEducationDetailsRepository;
import xenhire.repository.CandidateOptionsRepository;
import xenhire.repository.CandidatePersonalInfoRepository;
import xenhire.repository.CandidatePreferencesRepository;
import xenhire.repository.CandidateQuestionnaireRepository;
import xenhire.repository.CandidateScreeningQuestionsRepository;
import xenhire.repository.CandidateSkillsRepository;
import xenhire.repository.CandidateValueAssessmentDataRepository;
import xenhire.repository.CandidateValueAssessmentVersionRepository;
import xenhire.repository.ClientAssessmentDataRepository;
import xenhire.repository.ClientAssessmentVersionRepository;
import xenhire.repository.ClientRepository;
import xenhire.repository.CompetencyRepository;
import xenhire.repository.CompetencyRepository.LikelyValue;
import xenhire.repository.CompetencyScoreRepository;
import xenhire.repository.PersonalInfoOptionsRepository;
import xenhire.repository.PillarOrderTextRepository;
import xenhire.repository.PillarScoreRepository;
import xenhire.repository.UserRepository;
import xenhire.request.ApproveClientRequest;
import xenhire.request.CandidateDTPQuestionnaireRequest;
import xenhire.request.CandidatePreferencesRequest;
import xenhire.request.CandidateQuestionnaireData;
import xenhire.request.CandidateValueAssessmentRequest;
import xenhire.request.ClientValuesRequest;
import xenhire.request.PersonalInfoOptionRequest;
import xenhire.request.CandidateDTPQuestionnaireRequest;
import xenhire.response.CandidateAssessmentsResponse;
import xenhire.response.CandidateDtpInfoResponse;
import xenhire.response.CandidateDtpReportResponse;
import xenhire.response.CandidatePreferencesForReport;
import xenhire.response.CandidateQuestionnaireResponse;
import xenhire.response.CandidateSpectrumResults;
import xenhire.response.CandidateTechnicalSkillResponse;
import xenhire.response.CandidateValueResultResponse;
import xenhire.response.ClientIcpReportResponse;
import xenhire.response.ClientPreferencesForReport;
import xenhire.response.CompetencyRanking;
import xenhire.response.DtpDescriptionResponse;
import xenhire.response.PillarRanking;
import xenhire.response.SpectrumPillar2Components;
import xenhire.response.SpectrumPillarComponents;

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
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CandidateEducationDetailsRepository candidateEducationDetailsRepository;

	@Autowired
	CandidateValueAssessmentVersionRepository candidateValueAssessmentVersionRepository;
	
	@Autowired
	CandidateDtpAccessRepository candidateDtpAccessRepository;
	
	@Autowired
	CandidateAssessmentBatchRepository candidateAssessmentBatchRepository;
	
	@Autowired
	BatchAssessmentCandidatesRepository batchAssessmentCandidatesRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	PillarOrderTextRepository pillarOrderTextRepository;
	
	@Autowired
	CandidateScreeningQuestionsRepository candidateScreeningQuestionsRepository;
	
	@Autowired
	CandidatePersonalInfoRepository candidatePersonalInfoRepository;
	
	@Autowired
	PersonalInfoOptionsRepository personalInfoOptionsRepository;
	
	public ResponseEntity<Object> getCandidateQuestionnaire(long candidateId) {
		CandidateQuestionnaireResponse resp = new CandidateQuestionnaireResponse();
		List<CandidateQuestionnaire> cqList = CandidateQuestionnaireRepository.getAnalysisQuestions();
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
		
		return new ResponseEntity<>(data, null, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> getCandidateValues(long candidateId){
		
		List<CandidateQuestionnaire> cqList = CandidateQuestionnaireRepository.findBySection("section4");
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

	public ResponseEntity<Object> savecandidateQuestionnaire(List<CandidateDTPQuestionnaireRequest> reqList, long candidateId) {
		// TODO Auto-generated method stub 
		CandidateAssessmentVersion cav = null;
		int version = 0;
		//System.out.println(versionNo);
		version = candidateAssessmentVersionRepository.getMaxVersionofcandidate(candidateId);
		cav = new CandidateAssessmentVersion();
		cav.setCandidateId(candidateId);
		cav.setVersionNo(version + 1);

		cav.setAssessmentCategory("default");
		candidateAssessmentVersionRepository.save(cav);
		for(CandidateDTPQuestionnaireRequest req : reqList) {
			if(req.getQuestionType().equals("RANKING")) {
				for(String opts : req.getSelectedOrder()) {
					System.out.println("question = " + req.getQuestionNo() + " option = " + opts);
//					CandidateOptions co = candidateOptionsRepository.findByQuestionnaireNoAndOptionDesc(req.getQuestionNo(), opts);
					CandidateOptions co = candidateOptionsRepository.findByOptionDesc(opts);
					CandidateAssessmentData cad = new CandidateAssessmentData();
					cad.setCandidateId(candidateId);
					cad.setVersionNo(cav.getVersionNo());
					cad.setCandidateAssessmentVersionId(cav.getId());
					cad.setQuestionNo(req.getQuestionNo());
					cad.setOptionType(req.getOptionCategory());
					cad.setOptionNo(co.getId());	
					cad.setCreatedAt(LocalDateTime.now());
					candidateAssessmentDataRepository.save(cad);
				}
			}
			else {
				CandidateOptions co = candidateOptionsRepository.findByQuestionnaireNoAndOptionDesc(req.getQuestionNo(), req.getSelectedOption());
				System.out.println("question = " + req.getQuestionNo() + " option = " + req.getSelectedOption());
				//CandidateOptions co = candidateOptionsRepository.findByOptionDesc(req.getSelectedOption());
				CandidateAssessmentData cad = new CandidateAssessmentData();
				cad.setCandidateId(candidateId);
				cad.setVersionNo(cav.getVersionNo());
				cad.setCandidateAssessmentVersionId(cav.getId());
				cad.setQuestionNo(req.getQuestionNo());
				cad.setOptionType(req.getQuestionType());
				cad.setOptionNo(co.getId());	
				cad.setCreatedAt(LocalDateTime.now());
				candidateAssessmentDataRepository.save(cad);
			}
			
			
		}
		
		CandidateDTPReportData dtpData = candidateDTPReportDataRepository.getRecentRecord(candidateId);
		if(dtpData == null) {
			dtpData = new CandidateDTPReportData();
			dtpData.setCandidateId(candidateId);
			dtpData.setAssessment(true);
			dtpData.setAssessmentVersionId(cav.getVersionNo());
			dtpData.setCreatedAt(LocalDateTime.now());
			candidateDTPReportDataRepository.save(dtpData);
		}
		else {
			if(dtpData.isAssessment() == true && dtpData.isPersonalInfo() == true && dtpData.isPreferences() == true && dtpData.isValueAssessment() == true) {
				CandidateDTPReportData dtpData1 = new CandidateDTPReportData();
				dtpData1.setAssessment(true);
				dtpData1.setAssessmentVersionId(cav.getVersionNo());
				dtpData1.setCandidateId(candidateId);
				dtpData1.setPersonalInfo(dtpData.isPersonalInfo());
				dtpData1.setPreferences(dtpData.isPreferences());
				dtpData1.setPreferencesVersionId(dtpData.getPreferencesVersionId());
				dtpData1.setValueAssessment(dtpData.isValueAssessment());
				dtpData1.setValuesVersionId(dtpData.getValuesVersionId());
				candidateDTPReportDataRepository.save(dtpData1);
			}
			if(dtpData.isAssessment() == false) {
				dtpData.setAssessment(true);
				dtpData.setAssessmentVersionId(cav.getVersionNo());	
				candidateDTPReportDataRepository.save(dtpData);
			}
		}
		
		extractCandidateAssessmentScoreAndSave(candidateId, cav.getVersionNo());
		
		return new ResponseEntity<>(cav, null, HttpStatus.CREATED);
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
		ClientAssessmentDataResponseDTO adapt = adaptList.get(0);
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
			car.setVersionId(versionNo);
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
						car.setVersionId(versionNo);
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
		int maxVersionId = candidatePreferencesRepository.getMaxVersionId();
		CandidatePreferences cp = new CandidatePreferences(); //candidatePreferencesRepository.findByCandidateId(candidateId);
//		if(cp == null) {
//			cp = new CandidatePreferences();
//			cp.getCreatedAt();
//		}
		cp.setAppealingWork(req.getAppealingWork());
		cp.setCandidateId(candidateId);
		cp.setCurrency(req.getExpectedSalary().get("currency"));
		cp.setExpectedSalary(Long.parseLong(req.getExpectedSalary().get("range")));
		cp.setImportantFactor(req.getImportantFactor());
		cp.setOpenToRelocate(req.getOpenToRelocate());
		cp.setPrefferedLocation(req.getPrefferedLocation());	
		cp.setRequiredForTravel(req.getRequiredForTravel());
		cp.setWorkSetting(req.getWorkSetting());
		cp.setWorkShift(req.getWorkShift());
		cp.setWorkSchedule(req.getWorkSchedule());
		cp.setWorkIndependantly(req.getWorkIndependantly());
		cp.setWorkEnvironment(req.getWorkEnvironment());
		cp.setVersionId(maxVersionId + 1);		
		
		
		candidatePreferencesRepository.save(cp);
		
		CandidateDTPReportData dtpData = candidateDTPReportDataRepository.getRecentRecord(candidateId);
		if(dtpData == null) {
			dtpData = new CandidateDTPReportData();
			dtpData.setCandidateId(candidateId);
			dtpData.setPreferences(false);
			dtpData.setPreferencesVersionId(cp.getVersionId());
			candidateDTPReportDataRepository.save(dtpData);
		}
		else {
			if(dtpData.isAssessment() == true && dtpData.isPersonalInfo() == true && dtpData.isPreferences() == true && dtpData.isValueAssessment() == true) {
				CandidateDTPReportData dtpData1 = new CandidateDTPReportData();
				dtpData1.setAssessment(true);
				dtpData1.setAssessmentVersionId(dtpData.getAssessmentVersionId());
				dtpData1.setCandidateId(candidateId);
				dtpData1.setPersonalInfo(dtpData.isPersonalInfo());
				dtpData1.setPreferences(dtpData.isPreferences());
				dtpData1.setPreferencesVersionId(cp.getVersionId());
				dtpData1.setValueAssessment(dtpData.isValueAssessment());
				dtpData1.setValuesVersionId(dtpData.getValuesVersionId());
				candidateDTPReportDataRepository.save(dtpData1);
			}
			if(dtpData.isAssessment() == false) {
				dtpData.setPreferences(true);
				dtpData.setPreferencesVersionId(cp.getVersionId());	
				candidateDTPReportDataRepository.save(dtpData);
			}
		}
		
		return new ResponseEntity<>("data saved successfully", null, HttpStatus.CREATED);
	}

	public ResponseEntity<Object> savecandidateValueAssessment(List<CandidateValueAssessmentRequest> reqList,long candidateId) {
		// TODO Auto-generated method stub
		int versionNo = candidateValueAssessmentDataRepository.getMaxVersionNo(candidateId);
		for(CandidateValueAssessmentRequest req : reqList) {
			System.out.println(req);
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
		
		
		
		versionNo = versionNo+1;
		
		CandidateValueAssessmentVersion cvav = new CandidateValueAssessmentVersion();
		cvav.setCandidateId(candidateId);
		cvav.setVersionNo(versionNo);
		candidateValueAssessmentVersionRepository.save(cvav);
		
		CandidateDTPReportData dtpData = candidateDTPReportDataRepository.getRecentRecord(candidateId);
		if(dtpData == null) {
			dtpData = new CandidateDTPReportData();
			dtpData.setCandidateId(candidateId);
			dtpData.setValueAssessment(true);
			dtpData.setValuesVersionId(versionNo);
			candidateDTPReportDataRepository.save(dtpData);
		}
		else {
			if(dtpData.isAssessment() == true && dtpData.isPersonalInfo() == true && dtpData.isPreferences() == true && dtpData.isValueAssessment() == true) {
				CandidateDTPReportData dtpData1 = new CandidateDTPReportData();
				dtpData1.setAssessment(true);
				dtpData1.setAssessmentVersionId(dtpData.getAssessmentVersionId());
				dtpData1.setCandidateId(candidateId);
				dtpData1.setPersonalInfo(dtpData.isPersonalInfo());
				dtpData1.setPreferences(dtpData.isPreferences());
				dtpData1.setPreferencesVersionId(dtpData.getPreferencesVersionId());
				dtpData1.setValueAssessment(dtpData.isValueAssessment());
				dtpData1.setValuesVersionId(versionNo);
				candidateDTPReportDataRepository.save(dtpData1);
			}
			if(dtpData.isAssessment() == false) {
				dtpData.setValueAssessment(true);
				dtpData.setValuesVersionId(versionNo);	
				candidateDTPReportDataRepository.save(dtpData);
			}
		}
		Map resp = new HashMap();
		resp.put("candidateId", candidateId);
		resp.put("versionNo", versionNo);
		
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
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
	
	
public ResponseEntity<Object> getCandidateDtpReport(long candidateId, long dtpReportId) {
	CandidateDTPReportData dtpReport = null;
		if(dtpReportId == 0) {
			CandidateDTPReportData dtpReportOpt = candidateDTPReportDataRepository.getRecentRecord(candidateId);
		}
		else{
			Optional<CandidateDTPReportData> dtpReportOpt = candidateDTPReportDataRepository.findById(dtpReportId);
			dtpReport = dtpReportOpt.get();
		}

		CandidateDtpReportResponse resp = new CandidateDtpReportResponse();
		Optional<User> userOpt = userRepository.findById(candidateId);
		
		User user = userOpt.get();
		resp.setName(user.getUsername());
		resp.setEmail(user.getEmail());
		resp.setMobileNo(user.getMobile());
		resp.setGender("male");
		resp.setSummary(user.getSummary());
		resp.setExpert(5);
		
		CandidatePersonalInfo preference = candidatePersonalInfoRepository.findByCandidateId(candidateId);
		
		List<String> credentials = new ArrayList();
		credentials.add(preference.getCertificationAndLicence());
		credentials.add(preference.getSpecialization());
		credentials.add(preference.getSoftwares());
		
		List<String> openTo = new ArrayList();
		openTo.add(preference.getRoleWorkSettings());
		openTo.add(preference.getJobType());
		openTo.add(preference.getWorkShifts());
		
		resp.setCredentials(credentials);
		resp.setEducation(preference.getAcademicQualification() + " in " + preference.getSpecialization());
		resp.setLocation(preference.getWorkLocation());
		resp.setOpenTo(openTo);
		resp.setReadyToRelocate(preference.isRelocation());
		resp.setReadyToTravel(preference.getRoleTravel().contains("No Travel") ? false : true);
		
		List<CandidateSkills> csList = candidateSkillsRepository.findByCandidateId(candidateId);
//		List<CandidateSkills> primarySkills = csList.stream().filter(skill -> skill.getSkillType().equals("primary")).collect(Collectors.toList());
//		List<CandidateSkills> secondarySkills = csList.stream().filter(skill -> skill.getSkillType().equals("secondary")).collect(Collectors.toList());
//		
		List<CandidateTechnicalSkillResponse> skills = new ArrayList();
		
		for(CandidateSkills skill : csList) {
			if(skill.getSkillType().equals("primary") || skill.getSkillType().equals("secondary")) {
				CandidateTechnicalSkillResponse res = new CandidateTechnicalSkillResponse();
				res.setSkill(skill.getSkill());
				res.setSkillLevel(skill.getSkillLevel());
				int value = 40;
				if(skill.getSkillLevel().equals("Expert")) value=60;
				if(skill.getSkillLevel().equals("Proficient")) value=80;
				if(skill.getSkillLevel().equals("Competent")) value=90;
				if(skill.getSkillLevel().equals("Limited")) value=50;
				
				res.setValue(value);
				
				skills.add(res);
				
			}
		}
		
		resp.setTechnicalSkill(skills);
		List<CandidateValueResultResponse> values = getCandidateValueResult(candidateId, dtpReport.getValuesVersionId());
		CandidateSpectrumResults talentSpectrum = getCandidateSpectrumResults(candidateId, dtpReport.getAssessmentVersionId());
		resp.setCognitiveAgility(talentSpectrum.getCognitiveAgility());
		resp.setSociabilitySkills(talentSpectrum.getSociabilitySkills());
		resp.setEmtionalFlexibility(talentSpectrum.getEmtionalFlexibility());
		resp.setCoreValues(values);
		resp.setPillars(talentSpectrum.getPillars());
//		List<CandidateAssessmentRanking> carList = candidateAssessmentRankingRepository.findByCandidateId(candidateId);
//		
//		String pillar = "";
//		
//		List<String> pillars = new ArrayList();
//		
//		for(CandidateAssessmentRanking car : carList) {
//			if(car.getPillar().equals(pillar) == false) {
//				pillars.add(car.getPillar());
//				pillar = car.getPillar();
//			}
//		}
				
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
	}




//List<String> crList = new ArrayList<String>();
//List<PillarRanking> prList = new ArrayList<PillarRanking>();
//List<CandidateSkills> primarySkills = new ArrayList<CandidateSkills>();
//List<CandidateSkills> secondarySkills = new ArrayList<CandidateSkills>();
//List<CandidateSkills> industry = new ArrayList<CandidateSkills>();
//List<String> certificationsList = new ArrayList<String>();
//int pillarRank = 1;
//int i = 1;
//Optional<User> userOpt = userRepository.findById(candidateId);
//User user = userOpt.get();
//
//List<CandidateAssessmentRanking> rankingList = candidateAssessmentRankingRepository.findByCandidateId(candidateId);
//for(CandidateAssessmentRanking r : rankingList) {
//	
//	if(i <= 5 && r.getCompetency().isEmpty() == false) {
//		CompetencyRanking competency = new CompetencyRanking();
//		
//		competency.setCompetency(r.getCompetency());
//		competency.setRank(r.getCompetencyRank());
//		
//		crList.add(r.getCompetency());
//		i++;
//	}
//}
//
//List<CandidateSkills> skillList = candidateSkillsRepository.findByCandidateId(candidateId);
//
//primarySkills = skillList.stream().filter(skill -> skill.getSkillType().equals("primary")).collect(Collectors.toList());
//
//secondarySkills = skillList.stream().filter(skill -> skill.getSkillType().equals("secondary")).collect(Collectors.toList());
//
//industry = skillList.stream().filter(skill -> skill.getSkillType().equals("industry")).collect(Collectors.toList());
//
//List<CandidateSkills> skills = Stream.concat(primarySkills.stream(), secondarySkills.stream())
//        .collect(Collectors.toList());
//
//CandidatePreferences cp = candidatePreferencesRepository.findByCandidateId(candidateId);
//
//CandidatePreferencesForReport cpfr = new CandidatePreferencesForReport();
//
//cpfr.setAcademics(cp.getAcademicQualification());
//cpfr.setExpectedPay(cp.getExpectedSalary() + " " +cp.getExpectedSalaryCurrency()+"/"+cp.getExpectedSalaryByTime());
//cpfr.setLocation(cp.getWorkLocation());
//cpfr.setRelocation((cp.isRelocation() == true) ? "yes" : "no");
//cpfr.setSpecialization(cp.getSpecialization());
//cpfr.setTravel(cp.getRoleTravel());
//cpfr.setVisa(cp.getVisa());
//cpfr.setWork(cp.getRoleWorkSettings());
//cpfr.setWorkEnvironment(cp.getWorkEnvironment());
//
//String[] itemsArray = cp.getCertificationAndLicence().split(",");
//
//// Step 2: Convert the array to a list, trimming whitespace and removing empty elements
//certificationsList = Arrays.stream(itemsArray)
//                               .map(String::trim)
//                               .filter(item -> !item.isEmpty())
//                               .collect(Collectors.toList());
//
//List<String> mostLikely = new ArrayList<String>();
//List<String> leastLikely = new ArrayList<String>();
//
//List<CandidateValueAssessmentData> candidateValuesList = candidateValueAssessmentDataRepository.findByCandidateId(candidateId);
//System.out.println(candidateValuesList);
//for(CandidateValueAssessmentData cand : candidateValuesList) {
//   	 mostLikely.add(cand.getMostLikelyValue());
//   	 leastLikely.add(cand.getLeastLikelyValue());
//}
//
//List<String> valuesList = new ArrayList<String>();
//valuesList.add("Self-Direction");
//valuesList.add("Stimulation");
//valuesList.add("Hedonism");
//valuesList.add("Achievement");
//valuesList.add("Power");
//valuesList.add("Security");
//valuesList.add("Conformity");
//valuesList.add("Tradition");
//valuesList.add("Benevolence");
//valuesList.add("Universalism");
//
//
//Map<String, Integer> mostLikelyMap =   mostLikely.stream()
//        .collect(Collectors.groupingBy(
//                element -> element,              // Group by the element itself
//                Collectors.collectingAndThen(    // Collect the elements
//                        Collectors.counting(),   // Count each group
//                        Long::intValue           // Convert Long to Integer
//                )
//        ));
//Map<String, Integer> leastLikelyMap =   leastLikely.stream()
//	                 .collect(Collectors.groupingBy(
//	                         element -> element,              // Group by the element itself
//	                         Collectors.collectingAndThen(    // Collect the elements
//	                                 Collectors.counting(),   // Count each group
//	                                 Long::intValue           // Convert Long to Integer
//	                         )
//	                 ));
//
//System.out.println(mostLikelyMap);
//System.out.println(leastLikelyMap);
//Map<String, Integer> countDifferences = new HashMap();
//
//for(String key : mostLikelyMap.keySet()) {
//	if(leastLikelyMap.containsKey(key)) {
//		Integer difference = Math.abs(mostLikelyMap.get(key) - leastLikelyMap.get(key));
//		countDifferences.put(key, difference);
//	}
//	else {
//		countDifferences.put(key, mostLikelyMap.get(key));
//	}
//}
//
//for(String key : leastLikelyMap.keySet()) {
//	 if (countDifferences.containsKey(key) && mostLikelyMap.containsKey(key) == false)
//      {
//          countDifferences.put(key, leastLikelyMap.get(key) - (2*leastLikelyMap.get(key)));
//      }
//}
//
//for(String value : valuesList){
//  if(countDifferences.containsKey(value) == false) {
//      countDifferences.put(value, 0);
//  }
//}
//
//Map<String, Integer> sortedDifferences = countDifferences.entrySet()
//        .stream()
//        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
//        .collect(Collectors.toMap(
//                Map.Entry::getKey,
//                Map.Entry::getValue,
//                (e1, e2) -> e1,
//                LinkedHashMap::new
//        ));
//
//
//CandidateDtpReportResponse resp = new CandidateDtpReportResponse();
//// resp.setPillarRanking(prList);
//resp.setCompetencyRanking(crList);
//resp.setCertifications(certificationsList);
//resp.setSkills(skills);
//resp.setPreferences(cpfr);
//resp.setIndustry(industry);
//resp.setValues(new ArrayList<>(sortedDifferences.keySet()));

	public ResponseEntity<Object> postCandidatePersonalInfo(long candidateId, CandidatePersonalInfoDTO req) {
		// TODO Auto-generated method stub
		int maxVersionNo = candidatePersonalInfoRepository.getMaxVersion();
		Optional<User> userOpt = userRepository.findById(candidateId); 
		if(userOpt.isPresent()) {
			String[] name = req.getFullName().split(" ");
			User user = userOpt.get();
			user.setFirstName((name.length >= 1 == true) ? name[0] : "");
			user.setLastName((name.length >= 2 == true) ? name[1] : "");
			user.setLastName((name.length >= 3 == true) ? name[2] : "");
			user.setFullName(req.getFullName());
			user.setLinkedIn(req.getUrl());
			user.setMobile(req.getContactNumber()+"");
			user.setTitle(req.getTitle());
			user.setSummary(req.getSummary());
			
			userRepository.save(user);
			
//			for(Map<String, Object> ced : req.getEducation()) {
//				CandidateEducationDetails ced1 = new CandidateEducationDetails();
//				Optional<CandidateEducationDetails> cedOpt = candidateEducationDetailsRepository.findById((long) ced.get("Id"));
//				if(cedOpt.isPresent()) {
//					ced1 = cedOpt.get();
//				}
//				else {
//					ced1.setCandidateId(candidateId);
//				}
//				ced1.setCertificate(ced.get("certificate").get("label"));
//				ced1.setCity(ced.get("city").get("label"));
//				ced1.setFieldOfStudy(ced.get("fieldOfStudy").get("label"));
//				ced1.setDegree(ced.get("degree").get("label"));
//				ced1.setInstitution(ced.get("institution").get("label"));
//				ced1.setState(ced.get("state").get("label"));
//				candidateEducationDetailsRepository.save(ced1);
//				
//			}
			CandidatePersonalInfo cp = new CandidatePersonalInfo();
			cp.setAcademicQualification(req.getAcademicQualification());
			cp.setAcademicBackgroundAlignsToRole(req.getAcademicBackGround());
			cp.setRoleRelatedIndustry(req.getWorkInIndustry().equals("Yes") ? true : false);
			cp.setIndustryRole(req.getWorkRole());
			cp.setAppealing(req.getAppealingWork());
			cp.setCertificationAndLicence(req.getSpecificLicense());
			cp.setCompanyOutlook(req.getCompanyOutlook());
			cp.setExpectedSalary(Long.parseLong(req.getExpectedRange().get("range")));
			cp.setExpectedSalaryByTime("");
			cp.setExpectedSalaryCurrency(req.getExpectedRange().get("currency"));
			cp.setExperience(req.getYearsOfExperience());
			cp.setJobType(req.getTypeOfJobOpening());
			cp.setNoticePeriod(req.getNoticePeriod());
			cp.setRelocation(req.getOpenToRelocate().equals("Yes") ? true : false);
			cp.setRoleTravel(req.getRequiredForTravel());
			cp.setRoleWorkSettings(req.getWorkSchedule());
			cp.setSalary(Long.parseLong(req.getExpectedSalary().get("range")));
			cp.setSalaryByTime("");
			cp.setSalaryCurrency(req.getExpectedSalary().get("currency"));
			cp.setSpecialization(req.getSpecialization());
			cp.setTeamPreference(req.getWorkIndepandently());
			cp.setTeamHandling(req.getTeamHandling().equals("Yes") ? true : false);
			cp.setTeamSize(req.getTeamSize());
			cp.setVisa(req.getVisaStatus());
			cp.setWorkEnvironment(req.getWorkEnvironment());
			cp.setWorkLocation(req.getPrefferedLocation());
			cp.setWorkSchedule(req.getWorkSchedule());
			cp.setWorkShifts(req.getWorkShift());
			cp.setWorkWithStakeholders(req.getExperienceStackHolder().equals("Yes") ? true : false);
			cp.setRoleWorkSettings(req.getWorkSetting());
			cp.setSoftwares(req.getSoftwareApplication().get("app"));
			cp.setSoftwareExperienceLevel(req.getSoftwareApplication().get("experience"));
			//cp = req.getPreferences();
			cp.setVersionId(maxVersionNo + 1);
			cp.setCandidateId(candidateId);
			candidatePersonalInfoRepository.save(cp);
			for(Map<String,String> skill : req.getPrimarySkills()) {
				CandidateSkills cs = candidateSkillsRepository.findByCandidateIdAndSkillAndSkillType(candidateId, skill.get("skill"), "primary");
				if(cs != null) {
					cs.setSkillLevel(skill.get("expertise"));
					candidateSkillsRepository.save(cs);
				}
				else {
					cs = new CandidateSkills();
					cs.setCandidateId(candidateId);
					cs.setCandidatePreferencesId(cp.getId());
					cs.setSkill(skill.get("skill"));
					cs.setSkillLevel(skill.get("expertise"));
					cs.setSkillType("primary");
					candidateSkillsRepository.save(cs);
				}
			}
			for(Map<String,String> skill : req.getSecoundrySkills()) {
				CandidateSkills cs = candidateSkillsRepository.findByCandidateIdAndSkillAndSkillType(candidateId, skill.get("skill"), "secondary");
				if(cs != null) {
					cs.setSkillLevel(skill.get("expertise"));
					candidateSkillsRepository.save(cs);
				}
				else {
					cs = new CandidateSkills();
					cs.setCandidateId(candidateId);
					cs.setCandidatePreferencesId(cp.getId());
					cs.setSkill(skill.get("skill"));
					cs.setSkillLevel(skill.get("expertise"));
					cs.setSkillType("secondary");
					candidateSkillsRepository.save(cs);
				}
			}
			for(Map<String,String> skill : req.getIndusrtyExperience()) {
				CandidateSkills cs = candidateSkillsRepository.findByCandidateIdAndSkillAndSkillType(candidateId, skill.get("app"), "industry");
				if(cs != null) {
					cs.setSkillLevel(skill.get("experience"));
					candidateSkillsRepository.save(cs);
				}
				else {
					cs = new CandidateSkills();
					cs.setCandidateId(candidateId);
					cs.setCandidatePreferencesId(cp.getId());
					cs.setSkill(skill.get("type"));
					cs.setSkillLevel(skill.get("experience"));
					cs.setSkillType("industry");
					candidateSkillsRepository.save(cs);
				}
			}
			
			CandidateDTPReportData dtpData = candidateDTPReportDataRepository.getRecentRecord(candidateId);
			if(dtpData == null) {
				dtpData = new CandidateDTPReportData();
				dtpData.setCandidateId(candidateId);
				dtpData.setPreferences(false);
				dtpData.setPersonalInfo(true);
				dtpData.setPersonalInfoId(cp.getId());
				dtpData.setCreatedAt(LocalDateTime.now());
				candidateDTPReportDataRepository.save(dtpData);
			}
			else {
				dtpData.setPersonalInfo(true);
				dtpData.setPersonalInfoId(cp.getId());
				dtpData.setUpdatedAt(LocalDateTime.now());
				candidateDTPReportDataRepository.save(dtpData);
			}
			
			return new ResponseEntity<>("candidate info saved", null, HttpStatus.CREATED);
			
		}
		else {
			return new ResponseEntity<>("user not found", null, HttpStatus.BAD_REQUEST);
		}
	}
	
	public CandidatePersonalInfoDTO getCandidatePersonalInfo(long candidateId, long personalInfoId){
		Optional<User> userOpt = userRepository.findById(candidateId); 
		if(userOpt.isPresent()) {
			User user = userOpt.get();
			CandidatePersonalInfoDTO resp = new CandidatePersonalInfoDTO();
			resp.setFullName(user.getFullName());
			resp.setContactNumber(user.getMobile());
			resp.setTitle(user.getTitle());
			resp.setUrl(user.getLinkedIn());
			resp.setSummary(user.getSummary());
			resp.setResume(user.getResumeName());
			List<CandidateEducationDetails> cedList = candidateEducationDetailsRepository.findByCandidateId(candidateId);
			
			if(personalInfoId == -1) {
				CandidateDTPReportData dtp = candidateDTPReportDataRepository.getRecentRecord(candidateId);
				if(dtp == null) {
					return new CandidatePersonalInfoDTO();
				}
				
				personalInfoId = dtp.getPersonalInfoId();			
			}
			
			//CandidatePreferencesRequest preferences = null;
			Optional<CandidatePersonalInfo> cpOpt = candidatePersonalInfoRepository.findById(personalInfoId);
			if(cpOpt.isPresent()) {
				CandidatePersonalInfo cp = cpOpt.get();
				List<CandidateSkills> csList = candidateSkillsRepository.findByCandidatePreferencesId(personalInfoId);
				List<Map<String, String>> primary = new ArrayList();
				List<Map<String, String>> secondary = new ArrayList();
				List<Map<String, String>> industry = new ArrayList();
				for(CandidateSkills skill : csList) {
					Map<String,String> m = new HashMap();
					if(skill.getSkillType().equals("primary")) {
						m.put("skill", skill.getSkill());
						m.put("expertise", skill.getSkillLevel());
						primary.add(m);
					}
					if(skill.getSkillType().equals("secondary")) {
						m.put("skill", skill.getSkill());
						m.put("expertise", skill.getSkillLevel());
						secondary.add(m);
					}
					if(skill.getSkillType().equals("industry")) {
						m.put("type", skill.getSkill());
						m.put("experience", skill.getSkillLevel());
						industry.add(m);
					}
				}
				
				Map<String, String> expectedRange = new HashMap();
				expectedRange.put("range", cp.getExpectedSalary()+"");
				expectedRange.put("currency", cp.getExpectedSalaryCurrency());
				
				Map<String, String> salary = new HashMap();
				salary.put("range", cp.getSalary()+"");
				salary.put("currency", cp.getSalaryCurrency());
				
				Map<String, String> software = new HashMap();
				software.put("app", cp.getSoftwares());
				software.put("experience", cp.getSoftwareExperienceLevel());
				
				resp.setAcademicBackGround(cp.getAcademicBackgroundAlignsToRole());
				resp.setSpecialization(cp.getSpecialization());
				resp.setAcademicQualification(cp.getAcademicQualification());
				resp.setAppealingWork(cp.getAppealing());
				resp.setCompanyOutlook(cp.getCompanyOutlook());
				resp.setExpectedRange(expectedRange);
				resp.setExpectedSalary(salary);
				resp.setExperienceRole(cp.getSoftwareExperienceLevel());
				resp.setExperienceStackHolder(cp.isWorkWithStakeholders() ? "Yes" : "No");
				resp.setIndusrtyExperience(industry);
				resp.setNoticePeriod(cp.getNoticePeriod());
				resp.setOpenToRelocate(cp.isRelocation() ? "Yes" : "No");
				resp.setPrefferedLocation(cp.getWorkLocation());
				resp.setPrimarySkills(primary);
				resp.setYearsOfExperience(cp.getExperience());
				resp.setWorkShift(cp.getWorkShifts());
				resp.setWorkSetting(cp.getRoleWorkSettings());
				resp.setWorkSchedule(cp.getWorkSchedule());
				resp.setWorkRole(cp.getIndustryRole());
				resp.setWorkInIndustry(cp.isRoleRelatedIndustry() ? "Yes" : "No");
				resp.setWorkIndepandently(cp.getTeamPreference());
				resp.setWorkEnvironment(cp.getWorkEnvironment());
				resp.setVisaStatus(cp.getVisa());
				resp.setTypeOfJobOpening(cp.getJobType());
				resp.setTeamSize(cp.getTeamSize());
				resp.setTeamHandling(cp.isTeamHandling() ? "Yes" : "No");
				resp.setSpecificLicense(cp.getCertificationAndLicence());
				resp.setSoftwareApplication(software);
				resp.setSecoundrySkills(secondary);
				resp.setRequiredForTravel(cp.getRoleTravel());
				System.out.println(resp);
			
				resp.setEducation(null);
			
				return resp;
			}
			else {
				return null;
			}
		}	
		else {
			return null;
		}
		
	}


	public ResponseEntity<Object> getCandidateDTPInfo(long candidateId) {
		// TODO Auto-generated method stub
		CandidateDTPReportData dtpData = candidateDTPReportDataRepository.getRecentRecord(candidateId);
		if(dtpData == null) {
			dtpData = new CandidateDTPReportData();
		}
		Optional<User> uOpt = userRepository.findById(candidateId);
		User user = uOpt.get();
		CandidateDtpInfoResponse resp = new CandidateDtpInfoResponse();
		resp.setAssessment(dtpData.isAssessment());
		resp.setAssessmentVersionId(dtpData.getAssessmentVersionId());
		resp.setLinkedIn(user.getLinkedIn());
		resp.setPersonalInfo(dtpData.isPersonalInfo());
		resp.setPersonalInfoId(dtpData.getPersonalInfoId());
		resp.setPreferences(dtpData.isPreferences());
		resp.setPreferencesVersionId(dtpData.getPreferencesVersionId());
		boolean resume = false;
		if(user.getResumeName() != null && user.getResumeName() != "") {
			resume = true;
		}
		resp.setResume(resume);
		resp.setValueAssessment(dtpData.isValueAssessment());
		resp.setValuesVersionId(dtpData.getValuesVersionId());		
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> getCandidateAsssessmentsCount(long candidateId){
		int selfCount = candidateAssessmentBatchRepository.getAssessmentsCountofCandidate(candidateId);
		int clientCount = batchAssessmentCandidatesRepository.getCandidateAssessmentCount(candidateId);
		int authorizedCount = candidateDtpAccessRepository.getAuthorizedClientCount(candidateId);
		Map resp = new HashMap();
		resp.put("selfCount", selfCount);
		resp.put("clientCount", clientCount);
		resp.put("authorizedCount", authorizedCount);
		
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
	}


	public CandidatePreferencesRequest getCandidatePreferences(long candidateId, long preferenceId) {
		// TODO Auto-generated method stub
		System.out.println("in candidate preferences");
		CandidatePreferencesRequest preferences = null;
		if(preferenceId == -1) {
			CandidateDTPReportData dtp = candidateDTPReportDataRepository.getRecentRecord(candidateId);
			if(dtp == null) {
				return preferences;
			}
			
			preferenceId = dtp.getPreferencesVersionId();			
		}
		
		Optional<CandidatePreferences> cpOpt = candidatePreferencesRepository.findById(preferenceId);
		if(cpOpt.isPresent()) {
			CandidatePreferences cp = cpOpt.get();
			
			Map<String, String> expectedRange = new HashMap();
			expectedRange.put("range", cp.getExpectedSalary()+"");
			expectedRange.put("currency", cp.getCurrency());
			
			
			preferences = new CandidatePreferencesRequest();
			preferences.setAppealingWork(cp.getAppealingWork());
			preferences.setImportantFactor(cp.getImportantFactor());
			preferences.setOpenToRelocate(cp.getOpenToRelocate());
			preferences.setPrefferedLocation(cp.getPrefferedLocation());	
			preferences.setRequiredForTravel(cp.getRequiredForTravel());
			preferences.setWorkSetting(cp.getWorkSetting());
			preferences.setWorkShift(cp.getWorkShift());
			preferences.setWorkSchedule(cp.getWorkSchedule());
			preferences.setWorkIndependantly(cp.getWorkIndependantly());
			preferences.setWorkEnvironment(cp.getWorkEnvironment());
			preferences.setExpectedSalary(expectedRange);
			
		}
		return preferences;
	}


	public ResponseEntity<Object> getCandidateSelfAssessments(long candidateId) {
		// TODO Auto-generated method stub
		
		List<CandidateAssessmentBatch>  candidateList = candidateAssessmentBatchRepository.findByCandidateId(candidateId);
		List<BatchAssessmentCandidates> clientList = batchAssessmentCandidatesRepository.findByCandidateId(candidateId);
		List<CandidateAssessmentsResponse> candList = new ArrayList();
		List<CandidateAssessmentsResponse> cliList = new ArrayList();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm a", Locale.ENGLISH);
		for(CandidateAssessmentBatch batch : candidateList) {
			CandidateAssessmentsResponse resp = new CandidateAssessmentsResponse();
			resp.setId(batch.getId());			
			resp.setAssessmentName(batch.getAssessmentName());
			resp.setCompanyName(null);
			resp.setStatus(batch.getTakenAt() != null ? batch.getTakenAt().format(formatter) : "Not Taken");
			resp.setDate(batch.getCreatedAt().format(formatter));	
			resp.setResult(batch.getResult());
			candList.add(resp);
		}
		
		for(BatchAssessmentCandidates batch : clientList) {
			CandidateAssessmentsResponse resp = new CandidateAssessmentsResponse();
			Optional<User> uOpt = userRepository.findById(batch.getClientId());
			Optional<Client>  cli = clientRepository.findById(uOpt.get().getClientId());
			resp.setId(batch.getId());
			resp.setCompanyName(cli.get().getCompanyName());
			resp.setAssessmentName(batch.getAssessmentName());
			resp.setStatus(batch.getTakenAt() != null ? batch.getTakenAt().format(formatter) : "Not Taken");
			resp.setDate(batch.getCreatedAt().format(formatter));	
			resp.setResult(batch.getResult());
			cliList.add(resp);
		}
		
		Map<String, List<CandidateAssessmentsResponse>> respMap = new HashMap();
		respMap.put("clientList", cliList);
		respMap.put("candidateList", candList);
		
		return new ResponseEntity<>(respMap, null, HttpStatus.OK);
	}


	public List<CandidateValueResultResponse> getCandidateValueResult(long candidateId, long versionNo) {
		// TODO Auto-generated method stub
		List<CandidateValueResultResponse> cvadList = new ArrayList();
		CandidateDTPReportData dtp = candidateDTPReportDataRepository.getRecentRecord(candidateId);
		if(dtp == null) {
			return cvadList;
		}
			
		versionNo = dtp.getValuesVersionId();			
		System.out.println(versionNo);
		List<CandidateValueAssessmentData> cvad = candidateValueAssessmentDataRepository.findByCandidateIdAndVersionNo(candidateId, versionNo);
		long stimulation = cvad.stream().filter(cv -> cv.getMostLikelyValue().equals("Stimulation") || cv.getLeastLikelyValue().equals("Stimulation")).count();
		CandidateValueResultResponse c1 = new CandidateValueResultResponse();
		c1.setStatement("Stimulation");
		c1.setRating(stimulation);
		cvadList.add(c1);
		long selfDirection = cvad.stream().filter(cv -> cv.getMostLikelyValue().equals("Self-Direction") || cv.getLeastLikelyValue().equals("Self-Direction")).count();
		CandidateValueResultResponse c2 = new CandidateValueResultResponse();
		c2.setStatement("Self-Direction");
		c2.setRating(selfDirection);
		cvadList.add(c2);
		long hedonism = cvad.stream().filter(cv -> cv.getMostLikelyValue().equals("Hedonism") || cv.getLeastLikelyValue().equals("hedonism")).count();
		CandidateValueResultResponse c3 = new CandidateValueResultResponse();
		c3.setStatement("Hedonism");
		c3.setRating(hedonism);
		cvadList.add(c3);
		long achievement = cvad.stream().filter(cv -> cv.getMostLikelyValue().equals("Achievement") || cv.getLeastLikelyValue().equals("Achievement")).count();
		CandidateValueResultResponse c4 = new CandidateValueResultResponse();
		c4.setStatement("Achievement");
		c4.setRating(achievement);
		cvadList.add(c4);
		long power = cvad.stream().filter(cv -> cv.getMostLikelyValue().equals("Power") || cv.getLeastLikelyValue().equals("Power")).count();
		CandidateValueResultResponse c5 = new CandidateValueResultResponse();
		c5.setStatement("Power");
		c5.setRating(power);
		cvadList.add(c5);
		long security = cvad.stream().filter(cv -> cv.getMostLikelyValue().equals("Security") || cv.getLeastLikelyValue().equals("Security")).count();
		CandidateValueResultResponse c6 = new CandidateValueResultResponse();
		c6.setStatement("Security");
		c6.setRating(security);
		cvadList.add(c6);
		long conformity = cvad.stream().filter(cv -> cv.getMostLikelyValue().equals("Conformity") || cv.getLeastLikelyValue().equals("Conformity")).count();
		CandidateValueResultResponse c7 = new CandidateValueResultResponse();
		c7.setStatement("Conformity");
		c7.setRating(conformity);
		cvadList.add(c7);
		long tradition = cvad.stream().filter(cv -> cv.getMostLikelyValue().equals("tradition") || cv.getLeastLikelyValue().equals("tradition")).count();
		CandidateValueResultResponse c8 = new CandidateValueResultResponse();
		c8.setStatement("Tradition");
		c8.setRating(tradition);
		cvadList.add(c8);
		long benevolence = cvad.stream().filter(cv -> cv.getMostLikelyValue().equals("Benevolence") || cv.getLeastLikelyValue().equals("Benevolence")).count();
		CandidateValueResultResponse c9 = new CandidateValueResultResponse();
		c9.setStatement("Benevolence");
		c9.setRating(benevolence);
		cvadList.add(c9);
		long universalism = cvad.stream().filter(cv -> cv.getMostLikelyValue().equals("Universalism") || cv.getLeastLikelyValue().equals("Universalism")).count();
		CandidateValueResultResponse c10 = new CandidateValueResultResponse();
		c10.setStatement("Universalism");
		c10.setRating(universalism);
		cvadList.add(c10);
		
		return cvadList;

	}


	public CandidateSpectrumResults getCandidateSpectrumResults(long candidateId, long versionNo) {
		// TODO Auto-generated method stub
		List<CandidateAssessmentRanking> dataList = candidateAssessmentRankingRepository.findByCandidateIdAndVersionId(candidateId, versionNo);
		CandidateSpectrumResults results = new CandidateSpectrumResults();
		Map sbarGraph = new HashMap();
		Map cbarGraph = new HashMap();
		Map ebarGraph = new HashMap();
		List<Map> sCircular = new ArrayList();
		List<Map> cCircular = new ArrayList();
		List<Map> eCircular = new ArrayList();
		for(CandidateAssessmentRanking data : dataList) {
			Competency c = competencyRepository.findByName(data.getCompetency());
			if(data.getPillar().equals("Sociability")) {
				System.out.println(data.getCompetency());
				Map circularGraph = new HashMap();
				float rating = 0F;
				String attribute = "";
				if(data.getRating().equals("Low")) {
					rating = 2.5F;
					attribute = c.getLow();
				}
				if(data.getRating().equals("Med")) {
					rating = 3.5F;
					attribute = c.getMedium();
				}
				if(data.getRating().equals("High")) {
					rating = 4.5F;
					attribute = c.getHigh();
				}
				sbarGraph.put(data.getCompetency(), rating);
				circularGraph.put("name", data.getCompetency());
				circularGraph.put("rating", rating);
				circularGraph.put("percentage", (rating/5) * 100);
				circularGraph.put("attribute", attribute);
				sCircular.add(circularGraph);	
			}
			if(data.getPillar().equals("Cognitive Agility")) {
				Map circularGraph = new HashMap();
				float rating = 0F;
				String attribute = "";
				if(data.getRating().equals("Low")) {
					rating = 2.5F;
					attribute = c.getLow();
				}
				if(data.getRating().equals("Med")) {
					rating = 3.5F;
					attribute = c.getMedium();
				}
				if(data.getRating().equals("High")) {
					rating = 4.5F;
					attribute = c.getHigh();
				}
				cbarGraph.put(data.getCompetency(), rating);
				circularGraph.put("name", data.getCompetency());
				circularGraph.put("rating", rating);
				circularGraph.put("percentage", (rating/5) * 100);
				circularGraph.put("attribute", attribute);
				cCircular.add(circularGraph);			
			}
			if(data.getPillar().equals("Emotional Flexibility")) {
				Map circularGraph = new HashMap();
				float rating = 0F;
				String attribute = "";
				if(data.getRating().equals("Low")) {
					rating = 2.5F;
					attribute = c.getLow();
				}
				if(data.getRating().equals("Med")) {
					rating = 3.5F;
					attribute = c.getMedium();
				}
				if(data.getRating().equals("High")) {
					rating = 4.5F;
					attribute = c.getHigh();
				}
				ebarGraph.put(data.getCompetency(), rating);
				circularGraph.put("name", data.getCompetency());
				circularGraph.put("rating", rating);
				circularGraph.put("percentage", (rating/5) * 100);
				circularGraph.put("attribute", attribute);
				eCircular.add(circularGraph);
			}
		}
		
		SpectrumPillarComponents sociabilityCompBar = new SpectrumPillarComponents();
		SpectrumPillar2Components sociabilityCompCircular = new SpectrumPillar2Components();
		SpectrumPillarComponents congnitiveCompBar = new SpectrumPillarComponents();
		SpectrumPillar2Components cognitiveCompCircular = new SpectrumPillar2Components();
		SpectrumPillarComponents emotionalCompBar = new SpectrumPillarComponents();
		SpectrumPillar2Components emotionalCompCircular = new SpectrumPillar2Components();
		List<Map> sCompetency = new ArrayList();
		sCompetency.add(sbarGraph);
		List<Map> cCompetency = new ArrayList();
		cCompetency.add(cbarGraph);
		List<Map> eCompetency = new ArrayList();
		eCompetency.add(ebarGraph);
		sociabilityCompBar.setPillar1("Sociability Skill");
		sociabilityCompBar.setCompetencies(sCompetency);
		congnitiveCompBar.setPillar1("Cognitive Agility");
		congnitiveCompBar.setCompetencies(cCompetency);
		emotionalCompBar.setPillar1("Emtional Flexibility");
		emotionalCompBar.setCompetencies(eCompetency);
		sociabilityCompCircular.setPillar2("Sociability Skill");
		sociabilityCompCircular.setCompetencies(sCircular);
		cognitiveCompCircular.setPillar2("Cognitive Agility");
		cognitiveCompCircular.setCompetencies(cCircular);
		emotionalCompCircular.setPillar2("Emtional Flexibility");
		emotionalCompCircular.setCompetencies(eCircular);
//		Map socialMap = new HashMap();
//		socialMap.put("pillar1", sociabilityCompBar);
//		socialMap.put("pillar2", sociabilityCompCircular);
//		Map cognitiveMap = new HashMap();
//		cognitiveMap.put("pillar1", congnitiveCompBar);
//		cognitiveMap.put("pillar2", cognitiveCompCircular);
//		Map emotionalMap = new HashMap();
//		emotionalMap.put("pillar1", emotionalCompBar);
//		emotionalMap.put("pillar2", emotionalCompCircular);
		List<Object> socialList = new ArrayList();
		List<Object> cognitiveList = new ArrayList();
		List<Object> emotionalList = new ArrayList();
		//socialList.add(sociabilityCompBar);
		socialList.add(sociabilityCompCircular);
		//cognitiveList.add(congnitiveCompBar);
		cognitiveList.add(cognitiveCompCircular);
		//emotionalList.add(emotionalCompBar);
		emotionalList.add(emotionalCompCircular);
		results.setSociabilitySkills(socialList);
		results.setCognitiveAgility(cognitiveList);
		results.setEmtionalFlexibility(emotionalList);
		//List<CandidateAssessmentRanking> carList = candidateAssessmentRankingRepository.findByCandidateId(candidateId);
		
		String pillar = "";
		String pillarOrder = "";
		List<String> pillars = new ArrayList();
		for(CandidateAssessmentRanking car : dataList) {
			if(car.getPillar().equals(pillar) == false) {
				pillarOrder = pillarOrder + car.getPillar() + ",";
				pillar = car.getPillar();
				pillars.add(pillar);
			}
		}
		pillar = "";
		pillarOrder = pillarOrder.substring(0,pillarOrder.length() - 1);
		System.out.println(pillarOrder);
		List<PillarOrderText> orderList = pillarOrderTextRepository.findAll();
		String attributes = "";
		for(PillarOrderText order : orderList) {
			//System.out.println(order.getOrderText());
			if(order.getPillarOrder().equals(pillarOrder)) {
				System.out.println(order.getOrderText());
				attributes = order.getOrderText();
				break;
			}
		}
		List<String> behaviourAttributes = new ArrayList();
		
		System.out.println(attributes.split("\n"));
		for(String str : attributes.split("\n")) {
			behaviourAttributes.add(str);
		}
		
		results.setPillars(pillars);
		results.setBehaviourAttributes(behaviourAttributes);
		
		return results;
	}


	public CandidateSpectrumResults approveCandidateDtpAccess(long candidateId, ApproveClientRequest req) {
		// TODO Auto-generated method stub
		return null;
	}


	public ResponseEntity<Object> getCandidateDTPAccess(long candidateId) {
		// TODO Auto-generated method stub
		List<Object[]> cdaList = candidateDtpAccessRepository.getCandidateDtpAccess(candidateId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm a", Locale.ENGLISH);
		final boolean dtp = candidateDTPReportDataRepository.findAllByCandidateId(candidateId).size() > 0;
		 
//		for(Object[] obj : cdaList) {
//			System.out.println(obj[4] + "  " + obj[5]);
//		}
		List<CandidateDTPAccessDTO> respList = cdaList.stream()
                .map(result -> new CandidateDTPAccessDTO((long) result[0], (String) result[1], (String) result[2], (String) result[3], ((BigDecimal) result[4]).equals(new BigDecimal(0)) ? false : true, ((BigDecimal) result[5]).equals(new BigDecimal(0)) ? false : true, ((Timestamp) result[6])==null ? "Not Authorized" : ((Timestamp) result[6]).toLocalDateTime().format(formatter), dtp))                  
                .collect(Collectors.toList());
	
		return new ResponseEntity<>(respList, null, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> saveCandidateDtpAccess(long candidateId, List<CandidateDTPAccessDTO> reqList){
		
		for(CandidateDTPAccessDTO req : reqList) {
			CandidateDtpAccess cda = candidateDtpAccessRepository.findByCandidateIdAndClientId(candidateId, req.getClientId());
			if(cda == null) cda = new CandidateDtpAccess();
			if(req.getAuthorized()) cda.setApprovedAt(LocalDateTime.now());
			cda.setAuthorized(req.getAuthorized());
			if(req.getDeclined()) cda.setDeclinedAt(LocalDateTime.now());
			cda.setDeclined(req.getDeclined());
			cda.setCandidateId(candidateId);
			cda.setClientName(req.getClientName());
			cda.setClientId(req.getClientId());
			
			candidateDtpAccessRepository.save(cda);
		}
		
		return new ResponseEntity<>("saved", null, HttpStatus.OK);
	}


	public User getCandidate(long candidateId) {
		// TODO Auto-generated method stub
		Optional<User> uOpt = userRepository.findById(candidateId);
		return uOpt.get();
	}


	public ResponseEntity<Object> getCandidateDTPDescription(long candidateId) {
		// TODO Auto-generated method stub
		System.out.println(candidateId);
		CandidateDTPReportData dtp = candidateDTPReportDataRepository.getRecentRecord(candidateId);
		System.out.println(dtp);
		CandidatePersonalInfoDTO personalInfo = getCandidatePersonalInfo(candidateId, dtp.getPersonalInfoId());
		CandidatePreferencesRequest preferences = getCandidatePreferences(candidateId, dtp.getPreferencesVersionId());
		List<CandidateValueResultResponse> values = getCandidateValueResult(candidateId, dtp.getValuesVersionId());
		CandidateSpectrumResults dtpResult = getCandidateSpectrumResults(candidateId, dtp.getAssessmentVersionId());
		
		DtpDescriptionResponse resp = new DtpDescriptionResponse();
		resp.setDtpResult(dtpResult);
		resp.setPersonalInfo(personalInfo);
		resp.setPreferences(preferences);
		resp.setValues(values);		
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
	}


	public ResponseEntity<Object> getCandidateScreeningQuestions(long candidateId, long jobId) {
		// TODO Auto-generated method stub
		List<CandidateScreeningQuestions> quesList = candidateScreeningQuestionsRepository.findByCandidateIdAndJobId(candidateId, jobId);
		return new ResponseEntity<>(quesList, null, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> saveCandidateScreeningQuestions(long candidateId, long jobId, List<CandidateScreeningQuestions> reqList){
		for(CandidateScreeningQuestions csq : reqList) {
			candidateScreeningQuestionsRepository.save(csq);
		}
		
		return new ResponseEntity<>("saved", null, HttpStatus.OK);
	}


	public ResponseEntity<Object> getPersonalInfoOptions(long candidateId) {
		// TODO Auto-generated method stub
		List<PersonalInfoOptions> optionsList = personalInfoOptionsRepository.findAllByCandidateId(candidateId);
		return new ResponseEntity<>(optionsList, null, HttpStatus.OK);
	}


	public ResponseEntity<Object> addPersonalOptionRequest(long candidateId, PersonalInfoOptionRequest req) {
		// TODO Auto-generated method stub
		PersonalInfoOptions opt = new PersonalInfoOptions();
		opt.setCandidateId(candidateId);
		opt.setLabel(req.getName());
		opt.setOptionType(req.getOptionType());	
		personalInfoOptionsRepository.save(opt);
		
		return getPersonalInfoOptions(candidateId);
	}


	public ResponseEntity<Object> getCandidateProfileInfo(long candidateId) {
		// TODO Auto-generated method stub
		CandidateDTPReportData dtp = candidateDTPReportDataRepository.findByCandidateId(candidateId);
		return new ResponseEntity<>(dtp, null, HttpStatus.OK);
	}
}