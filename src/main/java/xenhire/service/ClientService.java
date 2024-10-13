package xenhire.service;

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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import xenhire.dto.ClientAssessmentDataResponseDTO;
import xenhire.model.Assessments;
import xenhire.model.CandidateAssessmentRanking;
import xenhire.model.CandidateSkills;
import xenhire.model.Client;
import xenhire.model.ClientAssessmentBatch;
import xenhire.model.ClientAssessmentData;
import xenhire.model.ClientAssessmentPerBatch;
import xenhire.model.ClientAssessmentVersion;
import xenhire.model.ClientCompanyDetails;
import xenhire.model.ClientDetails;
import xenhire.model.ClientOptions;
import xenhire.model.ClientPreferences;
import xenhire.model.ClientQuestionnaire;
import xenhire.model.ClientSettings;
import xenhire.model.ClientSkills;
import xenhire.model.ClientTeamDetails;
import xenhire.model.ClientValueAssessmentData;
import xenhire.model.CompetencyScore;
import xenhire.model.PillarScore;
import xenhire.model.User;
import xenhire.repository.CandidateAssessmentRankingRepository;
import xenhire.repository.ClientAssessmentBatchRepository;
import xenhire.repository.ClientAssessmentDataRepository;
import xenhire.repository.ClientAssessmentDataRepository.ClientAssessmentResponseData;
import xenhire.repository.ClientAssessmentPerBatchRepository;
import xenhire.model.ClientAssessmentRanking;
import xenhire.repository.ClientAssessmentRankingRepository;
import xenhire.repository.ClientAssessmentVersionRepository;
import xenhire.repository.ClientCompanyDetailsRepository;
import xenhire.repository.ClientDetailsRepository;
import xenhire.repository.ClientJobDetailsRepository;
import xenhire.repository.ClientOptionsRepository;
import xenhire.repository.ClientPreferencesRepository;
import xenhire.repository.ClientQuestionnaireRepository;
import xenhire.repository.ClientRepository;
import xenhire.repository.ClientSettingsRepository;
import xenhire.repository.ClientSkillsRepository;
import xenhire.repository.ClientTeamDetailsRepository;
import xenhire.repository.ClientValueAssessmentDataRepository;
import xenhire.repository.ClientValuesDataRepository;
import xenhire.repository.CompetencyScoreRepository;
import xenhire.repository.JobAssignedCandidatesRepository;
import xenhire.repository.PillarScoreRepository;
import xenhire.repository.UserRepository;
import xenhire.repository.AssessmentsRepository;
import xenhire.repository.CandidateAssessmentDataRepository.CandidateAssessmentResponseData;
import xenhire.request.ClientAssessmentBatchRequest;
import xenhire.request.ClientAssessmentRequest;
import xenhire.request.ClientCompanyForm;
import xenhire.request.ClientFormRequest;
import xenhire.request.ClientPreferenceForm;
import xenhire.request.ClientTeamForm;
import xenhire.request.ClientValuesRequest;
import xenhire.request.ValuesRating;
import xenhire.response.AssessmentsResponse;
import xenhire.response.ClientDashboardDataResponse;
import xenhire.response.ClientIcpReportResponse;
import xenhire.response.ClientPreferencesForReport;
import xenhire.response.ClientQuestionnaireResponse;
import xenhire.response.CompetencyRanking;
import xenhire.response.PaginatedResponse;
import xenhire.response.PillarRanking;
import xenhire.response.UserSkills;

@Service
public class ClientService {
	
	@Autowired
	ClientQuestionnaireRepository clientQuestionnaireRepository;
	
	@Autowired
	ClientOptionsRepository clientOptionsRepository;
	
	@Autowired
	ClientAssessmentVersionRepository clientAssessmentVersionRepository;
	
	@Autowired
	ClientAssessmentDataRepository clientAssessmentDataRepository;
	
	@Autowired
	ClientDetailsRepository clientDetailsRepository;
	
	@Autowired
	ClientCompanyDetailsRepository clientCompanyDetailsRepository;
	
	@Autowired
	ClientTeamDetailsRepository clientTeamDetailsRepository;
	
	
	@Autowired
	ClientPreferencesRepository clientPreferencesRepository;
	
	@Autowired
	ClientValueAssessmentDataRepository clientValueAssessmentDataRepository;
	
	@Autowired
	ClientSkillsRepository clientSkillsRepository;
	
	@Autowired
	CompetencyScoreRepository competencyScoreRepository;
	
	@Autowired
	PillarScoreRepository pillarScoreRepository;
	
	@Autowired
	ClientAssessmentRankingRepository clientAssessmentRankingRepository;
	
	@Autowired
	ClientValuesDataRepository clientValuesDataRepository;
	
	@Autowired
	UtilityService utilityService;
	
	@Autowired
	AssessmentsRepository assessmentsRepository;
	
	@Autowired
	ClientAssessmentBatchRepository clientAssessmentBatchRepository;
	
	@Autowired
	ClientAssessmentPerBatchRepository clientAssessmentPerBatchRepository;
	
	@Autowired
	ClientSettingsRepository clientSettingsRepository;
	
	@Autowired
	JobAssignedCandidatesRepository jobAssignedCandidatesRepository;
	
	@Autowired
	ClientJobDetailsRepository clientJobDetailsRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ClientRepository clientRepository;
	

	public ResponseEntity<Object> getClientQuestionnaire() {
		// TODO Auto-generated method stub
		List<ClientQuestionnaire> cqList = clientQuestionnaireRepository.findAll();
		//System.out.println(cqList);
		List<ClientQuestionnaireResponse> resp = new ArrayList();
		for(ClientQuestionnaire cq : cqList) {
			List<ClientOptions> coList = clientOptionsRepository.findByQuestionnaireNo(cq.getQuestionNo());
			List<String> options = coList.stream().map(ClientOptions::getOptionDesc).collect(Collectors.toList());
			ClientQuestionnaireResponse cqr = new ClientQuestionnaireResponse();
			cqr.setQuestionNo(cq.getQuestionNo());
			cqr.setQuestion(cq.getQuestion());
			cqr.setQuestionType(cq.getOptionType());
			cqr.setOptions(options);
			cqr.setOptionCategory(cq.getOptionCategory());
			resp.add(cqr);
		}
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> saveClientQuestionnaire(List<ClientAssessmentRequest> reqList, long clientId) {
		// TODO Auto-generated method stub
		int versionNo = clientAssessmentVersionRepository.getMaxVersionofClient(clientId);
		ClientAssessmentVersion cav = new ClientAssessmentVersion();
		cav.setClientId(clientId);
		cav.setVersionNo(versionNo + 1);
		cav.setCreatedAt(new Date());
		cav.setAssessmentCategory("default");
		clientAssessmentVersionRepository.save(cav);
		for(ClientAssessmentRequest req : reqList) {
			if(req.getQuestionType().equals("RANKING")) {
				for(String opts : req.getOptions()) {
					System.out.println("questionNo = " + req.getQuestionNo() + " option = " + opts);
					ClientOptions co = clientOptionsRepository.findByQuestionnaireNoAndOptionDesc(req.getQuestionNo(), opts);
					//ClientOptions co = clientOptionsRepository.findByOptionDesc(opts);
					ClientAssessmentData cad = new ClientAssessmentData();
					cad.setClientId(clientId);
					cad.setClientAssessmentVersionId(cav.getId());
					cad.setQuestionNo(req.getQuestionNo());
					cad.setOptionType(req.getOptionCategory());
					cad.setOptionNo(co.getId());	
					clientAssessmentDataRepository.save(cad);
				}
			}
			else {
				System.out.println("questionNo = " + req.getQuestionNo() + " option = " + req.getSelectedOption());
				//ClientOptions co = clientOptionsRepository.findByQuestionnaireNoAndOptionDesc(req.getQuestionNo(), req.getSelectedOption());
				ClientOptions co = clientOptionsRepository.findByOptionDesc(req.getSelectedOption());
				ClientAssessmentData cad = new ClientAssessmentData();
				cad.setClientId(clientId);
				cad.setClientAssessmentVersionId(cav.getId());
				cad.setQuestionNo(req.getQuestionNo());
				cad.setOptionType(req.getQuestionType());
				cad.setOptionNo(co.getId());	
				clientAssessmentDataRepository.save(cad);
			}
			
			
		}
		
		extractClientAssessmentScoreAndSave(clientId);
		
		return new ResponseEntity<>("data saved successfully", null, HttpStatus.CREATED);
	}
	
	
	public void extractClientAssessmentScoreAndSave(long clientId) {
		
		System.out.println("client ranking is called = " + clientId);
		
		List<Map<String, Object>> dataList = clientAssessmentDataRepository.getClientAssessment(clientId);
		List<ClientAssessmentDataResponseDTO> respList = new ArrayList();
		
		for(Map<String, Object> m : dataList) {
			ClientAssessmentDataResponseDTO data = new ClientAssessmentDataResponseDTO();
			data.setCompetencyId((long) m.get("competency_id"));
			data.setCorrectOption((int) m.get("correct_option"));
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
		
		respList.forEach(System.out::println);
//		System.out.println(rankingCompetency);
//		System.out.println(rating);
		 int pillarRank = 1;
		 int competencyRank = 1;
		 String competency = "";
		for(ClientAssessmentDataResponseDTO data1 : rankingPillar) {
			System.out.println("pillar iteration " + pillarRank);
			if(data1.getPillarName().equals("Values") || data1.getPillarName().equals("Skills and Proficiency")) {
				CompetencyScore cs = competencyScoreRepository.findByCompetencyRank(competencyRank);
				PillarScore ps = pillarScoreRepository.findByPillarRank(pillarRank);
				
				ClientAssessmentRanking car = new ClientAssessmentRanking();
				car.setCompetency(data1.getCompetencyName());
				car.setCompetencyRank(competencyRank);
				car.setPillar(data1.getPillarName());
				car.setPillarRank(pillarRank);
				car.setCompetencyScore(cs.getCompetencyScore());
				car.setPillarScore(ps.getPillarScore());
				car.setMaxScore(cs.getCompetencyScore());
				car.setClientId(clientId);
				car.setCreatedAt(LocalDateTime.now());
				car.setRating("Low");
				clientAssessmentRankingRepository.save(car);
				
				if(pillarRank == 4) { competencyRank ++; }
				else { competencyRank = competencyRank + 4; }
			}
			
			for(ClientAssessmentDataResponseDTO data2 : rankingCompetency) {
				
				if(data2.getPillarName().equals(data1.getPillarName())) {
					System.out.println(data2.getCompetencyName());
					for(ClientAssessmentDataResponseDTO data3 : rating) {
//						System.out.println(data2.getCompetencyName());
//						System.out.println(data3.getCompetencyName());
						if(data2.getCompetencyName().equals(data3.getCompetencyName())) {
							System.out.println(competencyRank);
							CompetencyScore cs = competencyScoreRepository.findByCompetencyRank(competencyRank);
							PillarScore ps = pillarScoreRepository.findByPillarRank(pillarRank);
							ClientAssessmentRanking car = new ClientAssessmentRanking();
							car.setCompetency(data3.getCompetencyName());
							car.setCompetencyRank(competencyRank);
							car.setPillar(data1.getPillarName());
							car.setPillarRank(pillarRank);
							car.setCompetencyScore(cs.getCompetencyScore());
							car.setPillarScore(ps.getPillarScore());
							car.setMaxScore(cs.getCompetencyScore());
							car.setClientId(clientId);
							car.setCreatedAt(LocalDateTime.now());
							car.setRating(data3.getOptionLevel());
							clientAssessmentRankingRepository.save(car);
						}
					
					}
					
					 if(pillarRank == 5) pillarRank ++;
					 competencyRank ++; 
				}
				
			}
			pillarRank ++;
		}
	}
	
	
	public ResponseEntity<Object> saveClientValues(ClientValuesRequest reqList, long clientId){
		ClientValueAssessmentData cvd = new ClientValueAssessmentData();
		for(ValuesRating req : reqList.getRatingList()) {
				if(req.getValue().equals("stimulation")) cvd.setStimulation(req.getRating());
				if(req.getValue().equals("selfDirection")) cvd.setSelfDirection(req.getRating());
				if(req.getValue().equals("hedonism")) cvd.setHedonism(req.getRating());
				if(req.getValue().equals("achievement")) cvd.setAchievement(req.getRating());
				if(req.getValue().equals("power")) cvd.setPower(req.getRating());
				if(req.getValue().equals("security")) cvd.setSecurity(req.getRating());
				if(req.getValue().equals("conformity")) cvd.setConformity(req.getRating());
				if(req.getValue().equals("tradition")) cvd.setTradition(req.getRating());
				if(req.getValue().equals("benevolence")) cvd.setBenevolence(req.getRating());
				if(req.getValue().equals("universalism")) cvd.setUniversalism(req.getRating());
		}
		cvd.setTemplateName(reqList.getTemplateName());
		cvd.setTemplateTag(reqList.getTemplateTag());
		cvd.setTemplateDescription(reqList.getTemplateDescription());
		clientValuesDataRepository.save(cvd);
		
		return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
		
	}

	public ResponseEntity<Object> saveClientForm(ClientFormRequest req, long clientId) {
		// TODO Auto-generated method stub
		ClientCompanyDetails cd = clientCompanyDetailsRepository.findByClientId(clientId);
		System.out.println(cd);
		if(cd == null) {
			cd = new ClientCompanyDetails();
			cd.setCreatedAt(new Date());
		}
		ClientCompanyForm ccf = req.getFormOne();
		cd.setBackgroundCheck((ccf.getBackgroundCheck() == "yes") ? true : false);
		cd.setCompanyName("");
		cd.setCompanyDomain(ccf.getCompanyDomain());
		cd.setCompanyOperation(ccf.getCompanyOperations());
		cd.setCompanySize(ccf.getCompanySize());
		cd.setCompanyPhase(ccf.getCompanyPhase());
		cd.setDrugTest((ccf.getDrugTest() == "yes") ? true : false);
		cd.setDrugTestType(String.join(",", ccf.getDrugTestList()));
		cd.setClientId(clientId);
		cd.setUpdatedAt(new Date());
		clientCompanyDetailsRepository.save(cd);
		
		ClientTeamDetails ctd = clientTeamDetailsRepository.findByClientId(clientId);
		System.out.println(ctd);
		if(ctd == null) {
			ctd = new ClientTeamDetails();
			ctd.setCreatedAt(new Date());
		}
		
		ClientTeamForm ctf = req.getFormTwo();
		ctd.setCrossFunctionality((ctf.getCrossFunctionality() == "yes") ? true : false);
		ctd.setDomainRoles(ctf.getDomainRole());
		ctd.setProjectName(ctf.getProject());
		ctd.setTeamLocation(ctf.getLocationOfTeam());
		ctd.setTeamMemberContribution(ctf.getTeamMembercontribution());
		ctd.setTeamSize(ctf.getTeamSize());
		ctd.setClientId(clientId);
		ctd.setUpdatedAt(new Date());
		clientTeamDetailsRepository.save(ctd);
		
		ClientPreferences cp = new ClientPreferences();
		ClientPreferenceForm cpf = req.getFormThree();
		cp.setClientId(clientId);
		cp.setCandidateAcademicBackground(cpf.getCandidateAcademicBackground());
		cp.setCompensationCurrency(cpf.getCompensationCurrency());
		cp.setEnvision(cpf.getEnvision());
		cp.setIndustrySpecificExperience((cpf.getSpecificIndustry() == "yes") ? true : false);
		cp.setIndustrySpecificIndepthKnowledge((cpf.getCandidateDepthKnowledge() == "yes") ? true : false);
		cp.setJobDescription(cpf.getJobDescription());
		cp.setMinAcademicQualification(cpf.getMinAcedamicQualification());
		cp.setParticularSoftwares(cpf.getParticularSoftwares());
		cp.setSpecificIndustry(cpf.getSpecificIndustry());
		cp.setSpecificCertifications(cpf.getSpecificCertifications());
		cp.setRoleWorkShifts(cpf.getRoleWorkSettings());
		cp.setRoleWorkLocation(cpf.getRoleWorkingLocation());
		cp.setRoleType(cpf.getRoleType());
		cp.setRoleTravel(cpf.getRoleTravel());
		cp.setRoleTimings(cpf.getRoleTimings());
		cp.setRoleCompensation(cpf.getRoleCompensation());
		cp.setRegulatoryReq((cpf.getRegulatoryReq() == "yes") ? true : false);
		cp.setRelocationBudget((cpf.getRelocationBudget() == "yes") ? true : false);
		cp.setRelocationCurrency(cpf.getRelocationCurrency());
		cp.setRelocationExpenses(Long.parseLong(cpf.getRelocationExpenses()));
		cp.setRoleCompensation(cpf.getRoleCompensation());
		cp.setVisa(cpf.getVisa());
		cp.setUpdatedAt(new Date());
		cp.setCreatedAt(new Date());
		clientPreferencesRepository.save(cp);
		for(Map<String,String> skill : req.getPrimeSkills()) {
			ClientSkills cs = new ClientSkills();
			cs.setClientId(clientId);
			cs.setClientPreferencesId(cp.getId());
			cs.setSkill(skill.get("skill"));
			cs.setSkillLevel(skill.get("experience"));
			cs.setSkillType("primary");
			clientSkillsRepository.save(cs);
		}
		for(Map<String,String> skill : req.getSecondSkills()) {
			ClientSkills cs = new ClientSkills();
			cs.setClientId(clientId);
			cs.setClientPreferencesId(cp.getId());
			cs.setSkill(skill.get("skill"));
			cs.setSkillLevel(skill.get("experience"));
			cs.setSkillType("secondary");
			clientSkillsRepository.save(cs);
		}
		return null;
		
	}

	public ResponseEntity<Object> saveClientValueAssessment(ClientValueAssessmentData req, long clientId) {
		// TODO Auto-generated method stub
		int versionNo = clientValueAssessmentDataRepository.getMaxVersion(clientId);
		req.setClientId(clientId);
		req.setCreatedAt(LocalDateTime.now());
		req.setVersionNo(versionNo + 1);
		clientValueAssessmentDataRepository.save(req);
		return new ResponseEntity<>("data saved successfully", null, HttpStatus.CREATED);
	}

	public ResponseEntity<Object> getMatchingScore(long clientId, long candidateId) {
		// TODO Auto-generated method stub
//		int score = utilityService.getJobMatchingScores(clientId, candidateId);
//		Map resp = new HashMap();
//		resp.put("score", score);
		return new ResponseEntity<>(null, null, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> getClientIcpReport(long clientId) {
		
		List<CompetencyRanking> crList = new ArrayList<CompetencyRanking>();
        List<PillarRanking> prList = new ArrayList<PillarRanking>();
        List<ClientSkills> primarySkills = new ArrayList<ClientSkills>();
        List<ClientSkills> secondarySkills = new ArrayList<ClientSkills>();
        List<String> certificationsList = new ArrayList<String>();
        int pillarRank = 1;
        int i = 1;
		List<ClientAssessmentRanking> rankingList = clientAssessmentRankingRepository.findByClientId(clientId);
		for(ClientAssessmentRanking r : rankingList) {
			
			if(pillarRank == r.getPillarRank() && pillarRank <= 5) {
				PillarRanking pillar = new PillarRanking();
				pillar.setPillar(r.getPillar());
				pillar.setRank(r.getPillarRank());
				pillar.setScore(r.getPillarScore());
				prList.add(pillar);
				
				pillarRank ++ ;
			}
			
			if(i <= 5 && r.getCompetency().isEmpty() == false) {
				CompetencyRanking competency = new CompetencyRanking();
				
				competency.setCompetency(r.getCompetency());
				competency.setRank(r.getCompetencyRank());
				
				crList.add(competency);
				i++;
			}
		}
		
		List<ClientSkills> skillList = clientSkillsRepository.findByClientId(clientId);
		
//		primarySkills = skillList.stream().filter(skill -> skill.getSkillType().equals("primary")).collect(Collectors.toList());
//		
//		secondarySkills = skillList.stream().filter(skill -> skill.getSkillType().equals("secondary")).collect(Collectors.toList());
//		
//		
		
		List<ClientPreferences> cpList = clientPreferencesRepository.findByClientId(clientId);
		
		ClientPreferences cp = cpList.get(0);
		
		ClientPreferencesForReport cpfr = new ClientPreferencesForReport();
		
		cpfr.setLocation(cp.getRoleWorkLocation());
		cpfr.setRelocation((cp.isRelocationBudget() == true) ? "Yes" : "No");
		cpfr.setVisa(cp.getVisa());
		cpfr.setCompensation(cp.getRoleCompensation() + " " + cp.getCompensationCurrency());
		
		cp.getSpecificCertifications();
		
		String[] itemsArray = cp.getSpecificCertifications().split(",");

        // Step 2: Convert the array to a list, trimming whitespace and removing empty elements
        certificationsList = Arrays.stream(itemsArray)
                                       .map(String::trim)
                                       .filter(item -> !item.isEmpty())
                                       .collect(Collectors.toList());
        
        ClientValueAssessmentData values = clientValuesDataRepository.findByClientId(clientId);
        
        Map<String, Integer> value = new HashMap();
        if(values.getAchievement() > 0) value.put("achievement", values.getAchievement());
        if(values.getBenevolence() > 0) value.put("benevolence", values.getBenevolence());
        if(values.getConformity() > 0) value.put("conformity", values.getConformity());
        if(values.getHedonism() > 0) value.put("hedonism", values.getHedonism());
        if(values.getPower() > 0) value.put("power", values.getPower());
        if(values.getSecurity() > 0) value.put("security", values.getSecurity());
        if(values.getSelfDirection() > 0) value.put("selfDirection", values.getSelfDirection());
        if(values.getStimulation() > 0) value.put("stimulation", values.getStimulation());
        if(values.getTradition() > 0) value.put("tradition", values.getTradition());
        if(values.getUniversalism() > 0) value.put("universalism", values.getUniversalism());
        AtomicInteger counter = new AtomicInteger(1);
        Map<String, Integer> sortedMap = value.entrySet()
                .stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    entry -> counter.getAndIncrement(),
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
//        )
        ClientIcpReportResponse resp = new ClientIcpReportResponse();
        resp.setPillarRanking(prList);
        resp.setCompetencyRanking(crList);
        resp.setCertifications(certificationsList);
        resp.setSkills(skillList);
        resp.setPreferences(cpfr);
        resp.setValues(new ArrayList<>(sortedMap.keySet()));
		
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> getClientJobs(long clientId) {
		// TODO Auto-generated method stub
		List<ClientPreferences> cp = clientPreferencesRepository.findByClientId(clientId);
		
		return null;
	}

	public ResponseEntity<Object> saveClientSettings(long clientId, ClientSettings req) {
		// TODO Auto-generated method stub
		System.out.println(req);
		Optional<User> uOpt = userRepository.findById(clientId);
		Optional<Client> cliOpt = clientRepository.findById(uOpt.get().getClientId());
		req.setClientId(cliOpt.get().getId());
		clientSettingsRepository.save(req);
		return new ResponseEntity<>(req, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> getClientSettings(long clientId) {
		// TODO Auto-generated method stub
		Optional<User> uOpt = userRepository.findById(clientId);
		Optional<Client> cliOpt = clientRepository.findById(uOpt.get().getClientId());
		ClientSettings cs = clientSettingsRepository.findByClientId(cliOpt.get().getId());
		return new ResponseEntity<>(cs, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> getClientDashboardData(long clientId) {
		// TODO Auto-generated method stub
		Optional<User> uOpt = userRepository.findById(clientId);
		Optional<Client> cliOpt = clientRepository.findById(uOpt.get().getClientId());
		int assignedCandidates = jobAssignedCandidatesRepository.getCandidatesCount(clientId);
		int jobCount = clientJobDetailsRepository.getJobsCount(clientId);
		int assessmentCount = clientAssessmentBatchRepository.getAssessmentsCount(clientId);
		ClientDashboardDataResponse resp = new ClientDashboardDataResponse();
		resp.setAssessmentCount(assessmentCount);
		resp.setCandidatesCount(assignedCandidates);
		resp.setJobCount(jobCount);
		resp.setCompanyName(cliOpt.isPresent() ? cliOpt.get().getCompanyName() : "");
		
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
	}
	
	
	
	

}
