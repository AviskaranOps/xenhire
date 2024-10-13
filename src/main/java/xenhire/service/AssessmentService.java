package xenhire.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import xenhire.model.Adaptability;
import xenhire.model.AgreeBasedOptions;
import xenhire.model.Assessments;
import xenhire.model.BatchAssessmentCandidates;
import xenhire.model.CandidateAssessmentBatch;
import xenhire.model.CommunicationSkills;
import xenhire.model.CommunicationSkillsAssessmentData;
import xenhire.model.ConflictManagement;
import xenhire.model.ConflictManagementAssessmentData;
import xenhire.model.ConflictManagementOptions;
import xenhire.model.DecisionMaking;
import xenhire.model.DecisionMakingAssessmentData;
import xenhire.model.Empathy;
import xenhire.model.EmpathyAssessmentData;
import xenhire.model.EmpathyOptions;
import xenhire.model.RelationshipBuilding;
import xenhire.model.RelationshipBuildingAssessmentData;
import xenhire.model.RelationshipBuildingOptions;
import xenhire.model.Resilience;
import xenhire.model.ResilienceAssessmentData;
import xenhire.model.SelfAwarenessAssessmentData;
import xenhire.model.SelfAwarenessOptions;
import xenhire.model.StressManagement;
import xenhire.model.StressManagementAssessmentData;
import xenhire.model.StressManagementOptions;
import xenhire.model.TimeBasedOptions;
import xenhire.model.TimeManagement;
import xenhire.model.TimeManagementAssessmentData;
import xenhire.model.selfAwareness;
import xenhire.repository.AdaptabilityAssessmentDataRepository;
import xenhire.repository.AdaptabilityRepository;
import xenhire.repository.AgreeBasedOptionsRepository;
import xenhire.repository.AssessmentsRepository;
import xenhire.repository.BatchAssessmentCandidatesRepository;
import xenhire.repository.CandidateAssessmentBatchRepository;
import xenhire.repository.CommunicationSkillsAssessmentDataRepository;
import xenhire.repository.CommunicationSkillsRepository;
import xenhire.repository.ConflictManagementAssessmentDataRepository;
import xenhire.repository.ConflictManagementOptionsRepository;
import xenhire.repository.ConflictManagementRepository;
import xenhire.repository.DecisionMakingAssessmentDataRepository;
import xenhire.repository.DecisionMakingRepository;
import xenhire.repository.EmpathyAssessmentDataRepository;
import xenhire.repository.EmpathyOptionsRepository;
import xenhire.repository.EmpathyRepository;
import xenhire.repository.RelationshipBuildingAssessmentDataRepository;
import xenhire.repository.RelationshipBuildingOptionsRepository;
import xenhire.repository.RelationshipBuildingRepository;
import xenhire.repository.ResilienceAssessmentDataRepository;
import xenhire.repository.ResilienceRepository;
import xenhire.repository.SelfAwarenessAssessmentDataRepository;
import xenhire.repository.SelfAwarenessOptionsRepository;
import xenhire.repository.SelfAwarenessRepository;
import xenhire.repository.StressManagementAssessmentDataRepository;
import xenhire.repository.StressManagementOptionsRepository;
import xenhire.repository.StressManagementRepository;
import xenhire.repository.TimeBasedOptionsRepository;
import xenhire.repository.TimeManagementAssessmentDataRepository;
import xenhire.repository.TimeManagementRepository;
import xenhire.request.AssessmentQuestionnaireRequest;
import xenhire.request.CandidateAssessmentRequest;
import xenhire.response.AssessmentQuestoinnaireResponse;
import xenhire.response.AssessmentsResponse;
import xenhire.response.PaginatedResponse;
import xenhire.model.AdaptabilityAssessmentData;

@Service
public class AssessmentService {

	@Autowired
	AdaptabilityRepository adaptabilityRepository; 
	
	@Autowired
	AdaptabilityAssessmentDataRepository adaptabilityAssessmentDataRepository ; 
	
	@Autowired
	AgreeBasedOptionsRepository agreeBasedOptionsRepository;
	
	@Autowired
	SelfAwarenessRepository selfAwarenessRepository;
	
	@Autowired
	SelfAwarenessOptionsRepository selfAwarenessOptionsRepository;
	
	@Autowired
	SelfAwarenessAssessmentDataRepository selfAwarenessAssessmentDataRepository;
	
	@Autowired
	EmpathyRepository empathyRepository;
	
	@Autowired
	EmpathyAssessmentDataRepository empathyAssessmentDataRepository;
	
	@Autowired
	EmpathyOptionsRepository empathyOptionsRepository;
	
	@Autowired
	ResilienceRepository resilienceRepository;
	
	@Autowired
	ResilienceAssessmentDataRepository resilienceAssessmentDataRepository;
	
	@Autowired
	StressManagementRepository stressManagementRepository;
	
	@Autowired
	StressManagementOptionsRepository stressManagementOptionsRepository;
	
	@Autowired
	StressManagementAssessmentDataRepository stressManagementAssessmentDataRepository;
	
	@Autowired
	RelationshipBuildingRepository relationshipBuildingRepository;
	
	@Autowired
	RelationshipBuildingAssessmentDataRepository relationshipBuildingAssessmentDataRepository;
	
	@Autowired
	RelationshipBuildingOptionsRepository relationshipBuildingOptionsRepository;
	
	@Autowired
	CommunicationSkillsRepository communicationSkillsRepository;
	
	@Autowired
	CommunicationSkillsAssessmentDataRepository communicationSkillsAssessmentDataRepository;
	
	@Autowired
	ConflictManagementRepository conflictManagementRepository;
	
	@Autowired
	ConflictManagementAssessmentDataRepository conflictManagementAssessmentDataRepository;
	
	@Autowired
	ConflictManagementOptionsRepository conflictManagementOptionsRepository;
	
	@Autowired
	DecisionMakingRepository decisionMakingRepository;
	
	@Autowired
	DecisionMakingAssessmentDataRepository decisionMakingAssessmentDataRepository;
	
	@Autowired
	TimeManagementRepository timeManagementRepository;
	
	@Autowired
	TimeManagementAssessmentDataRepository timeManagementAssessmentDataRepository;
	
	@Autowired
	TimeBasedOptionsRepository timeBasedOptionsRepository;
	
	@Autowired
	CandidateAssessmentBatchRepository candidateAssessmentBatchRepository;
	
	@Autowired
	AssessmentsRepository assessmentsRepository;
	
	@Autowired
	BatchAssessmentCandidatesRepository batchAssessmentCandidatesRepository;
	
	
	
	public ResponseEntity<Object> getAssessments(long clientId, int pageNo, int pageSize) {

		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
		Page<Assessments> assessmentsList = assessmentsRepository.findAllByActive(true, paging);
		List<AssessmentsResponse> respList = new ArrayList();
		for(Assessments ass :  assessmentsList.getContent()){
			AssessmentsResponse resp = new AssessmentsResponse();
			resp.setId(ass.getId());
			resp.setName(ass.getName());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm a", Locale.ENGLISH);
			resp.setDate(ass.getCreatedAt().format(formatter));
			respList.add(resp);
		}
		PaginatedResponse paginationResponse = PaginatedResponse.builder().data(respList)
				.pageNo(assessmentsList.getNumber() + 1).pageSize(assessmentsList.getSize())
				.totalCount(assessmentsList.getTotalElements()).message("found").result("success")
				.build();
		return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);
		
	}
	
	
	public ResponseEntity<Object> getAdaptability() {
		// TODO Auto-generated method stub
		List<Adaptability> adaptList = adaptabilityRepository.findAll();
		List<AgreeBasedOptions> optionsList = agreeBasedOptionsRepository.findAll();
		List<AssessmentQuestoinnaireResponse> respList = new ArrayList();
		for(Adaptability ada : adaptList) {
			AssessmentQuestoinnaireResponse resp = new AssessmentQuestoinnaireResponse();
			resp.setQuestion(ada.getQuestion());
			resp.setOptions(optionsList.stream().map(AgreeBasedOptions::getOptionDesc).collect(Collectors.toList()));
			respList.add(resp);
		}
		
		return new ResponseEntity<>(respList, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> getSelfAwareness() {
		// TODO Auto-generated method stub
		List<selfAwareness> quesList = selfAwarenessRepository.findAll();
		List<SelfAwarenessOptions> optionsList = selfAwarenessOptionsRepository.findAll();
		List<AssessmentQuestoinnaireResponse> respList = new ArrayList();
		for(selfAwareness ques : quesList) {
			AssessmentQuestoinnaireResponse resp = new AssessmentQuestoinnaireResponse();
			resp.setQuestion(ques.getQuestion());
			resp.setOptions(optionsList.stream().map(SelfAwarenessOptions::getOptionDesc).collect(Collectors.toList()));
			respList.add(resp);
		}
		return new ResponseEntity<>(respList, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> getEmpathy() {
		// TODO Auto-generated method stub
		List<Empathy> quesList = empathyRepository.findAll();
		List<EmpathyOptions> optionsList = empathyOptionsRepository.findAll();
		List<AssessmentQuestoinnaireResponse> respList = new ArrayList();
		for(Empathy ques : quesList) {
			AssessmentQuestoinnaireResponse resp = new AssessmentQuestoinnaireResponse();
			resp.setQuestion(ques.getQuestion());
			resp.setOptions(optionsList.stream().map(EmpathyOptions::getOptionDesc).collect(Collectors.toList()));
			respList.add(resp);
		}
		return new ResponseEntity<>(respList, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> getResilience() {
		// TODO Auto-generated method stub
		List<Resilience> quesList = resilienceRepository.findAll();
		List<AgreeBasedOptions> optionsList = agreeBasedOptionsRepository.findAll();
		List<AssessmentQuestoinnaireResponse> respList = new ArrayList();
		for(Resilience ques : quesList) {
			AssessmentQuestoinnaireResponse resp = new AssessmentQuestoinnaireResponse();
			resp.setQuestion(ques.getQuestion());
			List<String> options = optionsList.stream().map(AgreeBasedOptions::getOptionDesc).collect(Collectors.toList());
			options.remove(2);
			options.remove(3);
			resp.setOptions(options);
			respList.add(resp);
		}
		return new ResponseEntity<>(respList, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> getStressManagement() {
		// TODO Auto-generated method stub
		List<StressManagement> quesList = stressManagementRepository.findAll();
		List<StressManagementOptions> optionsList = stressManagementOptionsRepository.findAll();
		List<AssessmentQuestoinnaireResponse> respList = new ArrayList();
		for(StressManagement ques : quesList) {
			AssessmentQuestoinnaireResponse resp = new AssessmentQuestoinnaireResponse();
			resp.setQuestion(ques.getQuestion());
			resp.setOptions(optionsList.stream().map(StressManagementOptions::getOptionDesc).collect(Collectors.toList()));
			respList.add(resp);
		}
		return new ResponseEntity<>(respList, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> getRelationshipBuilding() {
		// TODO Auto-generated method stub
		List<RelationshipBuilding> quesList = relationshipBuildingRepository.findAll();
		List<RelationshipBuildingOptions> optionsList = relationshipBuildingOptionsRepository.findAll();
		List<AssessmentQuestoinnaireResponse> respList = new ArrayList();
		for(RelationshipBuilding ques : quesList) {
			AssessmentQuestoinnaireResponse resp = new AssessmentQuestoinnaireResponse();
			resp.setQuestion(ques.getQuestion());
			resp.setOptions(optionsList.stream().map(RelationshipBuildingOptions::getOptionDesc).collect(Collectors.toList()));
			respList.add(resp);
		}
		return new ResponseEntity<>(respList, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> getCommunicationSkills() {
		// TODO Auto-generated method stub
		List<CommunicationSkills> quesList = communicationSkillsRepository.findAll();
		List<AgreeBasedOptions> optionsList = agreeBasedOptionsRepository.findAll();
		List<AssessmentQuestoinnaireResponse> respList = new ArrayList();
		for(CommunicationSkills ques : quesList) {
			AssessmentQuestoinnaireResponse resp = new AssessmentQuestoinnaireResponse();
			resp.setQuestion(ques.getQuestion());
			List<String> options = optionsList.stream().map(AgreeBasedOptions::getOptionDesc).collect(Collectors.toList());
			options.remove(2);
			options.remove(3);
			resp.setOptions(options);
			respList.add(resp);
		}
		return new ResponseEntity<>(respList, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> getConflictManagement() {
		// TODO Auto-generated method stub
		List<ConflictManagement> quesList = conflictManagementRepository.findAll();
		List<ConflictManagementOptions> optionsList = conflictManagementOptionsRepository.findAll();
		List<AssessmentQuestoinnaireResponse> respList = new ArrayList();
		for(ConflictManagement ques : quesList) {
			AssessmentQuestoinnaireResponse resp = new AssessmentQuestoinnaireResponse();
			resp.setQuestion(ques.getQuestion());
			resp.setOptions(optionsList.stream().map(ConflictManagementOptions::getOptionDesc).collect(Collectors.toList()));
			respList.add(resp);
		}
		return new ResponseEntity<>(respList, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> getDecisionMaking() {
		// TODO Auto-generated method stub
		List<DecisionMaking> quesList = decisionMakingRepository.findAll();
		List<AgreeBasedOptions> optionsList = agreeBasedOptionsRepository.findAll();
		List<AssessmentQuestoinnaireResponse> respList = new ArrayList();
		for(DecisionMaking ques : quesList) {
			AssessmentQuestoinnaireResponse resp = new AssessmentQuestoinnaireResponse();
			resp.setQuestion(ques.getQuestion());
			List<String> options = optionsList.stream().map(AgreeBasedOptions::getOptionDesc).collect(Collectors.toList());
			System.out.println(options);
			options.remove(2);
			options.remove(3);
			resp.setOptions(options);
			respList.add(resp);
		}
		return new ResponseEntity<>(respList, null, HttpStatus.OK);
	}

	public ResponseEntity<Object> getTimeManagement() {
		// TODO Auto-generated method stub
		List<TimeManagement> quesList = timeManagementRepository.findAll();
		List<TimeBasedOptions> optionsList = timeBasedOptionsRepository.findAll();
		List<AssessmentQuestoinnaireResponse> respList = new ArrayList();
		for(TimeManagement ques : quesList) {
			AssessmentQuestoinnaireResponse resp = new AssessmentQuestoinnaireResponse();
			resp.setQuestion(ques.getQuestion());
			resp.setOptions(optionsList.stream().map(TimeBasedOptions::getOptionDesc).collect(Collectors.toList()));
			respList.add(resp);
		}
		return new ResponseEntity<>(respList, null, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> saveAdaptability(long candidateId, long batchId, List<AssessmentQuestionnaireRequest> reqList){
		CandidateAssessmentBatch asses = new CandidateAssessmentBatch();
		BatchAssessmentCandidates bac = null;
		String initiatedBy = "candidate";
		long id = -1;
		Assessments assessment = assessmentsRepository.findByName("Stress Management");
		if(batchId == 0) {
			asses.setAssessmentId(assessment.getId());
			asses.setAssessmentName("Stress Management");
			asses.setCandidateId(candidateId);
			asses.setTakenAt(LocalDateTime.now());
			candidateAssessmentBatchRepository.save(asses);
			id = asses.getId();
		}
		else {
			initiatedBy = "client";
			Optional<BatchAssessmentCandidates> bacOpt = batchAssessmentCandidatesRepository.findById(batchId);
			bac = bacOpt.get();
			id = bac.getId();
		}
		List<AdaptabilityAssessmentData> aadList = new ArrayList();
		for(AssessmentQuestionnaireRequest req : reqList) {
			AdaptabilityAssessmentData aad = new AdaptabilityAssessmentData();
			aad.setAssessmentBatchId(asses.getId());
			aad.setQuestion(req.getQuestion());
			aad.setOptionSelected(req.getSelectedOption());
			aad.setInitiatedBy(initiatedBy);
			adaptabilityAssessmentDataRepository.save(aad);
			aadList.add(aad);
		}
		int score = getAdaptabilityResult(aadList);
		if(batchId == 0) {
			asses.setResult(score + "");
			candidateAssessmentBatchRepository.save(asses);
		}
		else {
			bac.setResult(score + "");
			bac.setStatus("Completed");
			bac.setTakenAt(LocalDateTime.now());
			batchAssessmentCandidatesRepository.save(bac);
		}
		return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Object> saveConflictManagement(long candidateId, long batchId, List<AssessmentQuestionnaireRequest> reqList){
		CandidateAssessmentBatch asses = new CandidateAssessmentBatch();
		BatchAssessmentCandidates bac = null;
		String initiatedBy = "candidate";
		long id = -1;
		Assessments assessment = assessmentsRepository.findByName("Stress Management");
		if(batchId == 0) {
			asses.setAssessmentId(assessment.getId());
			asses.setAssessmentName("Stress Management");
			asses.setCandidateId(candidateId);
			asses.setTakenAt(LocalDateTime.now());
			candidateAssessmentBatchRepository.save(asses);
			id = asses.getId();
		}
		else {
			initiatedBy = "client";
			Optional<BatchAssessmentCandidates> bacOpt = batchAssessmentCandidatesRepository.findById(batchId);
			bac = bacOpt.get();
			id = bac.getId();
		}
		List<ConflictManagementAssessmentData> cmadList = new ArrayList();
		for(AssessmentQuestionnaireRequest req : reqList) {
			ConflictManagementAssessmentData cmad = new ConflictManagementAssessmentData();
			cmad.setAssessmentBatchId(asses.getId());
			cmad.setOptionSelected(req.getSelectedOption());
			cmad.setQuestion(req.getQuestion());
			cmad.setInitiatedBy(initiatedBy);
			conflictManagementAssessmentDataRepository.save(cmad);
			cmadList.add(cmad);
		}
		int score = getConflictManagementScore(cmadList);
		if(batchId == 0) {
			asses.setResult(score + "");
			candidateAssessmentBatchRepository.save(asses);
		}
		else {
			bac.setResult(score + "");
			bac.setStatus("Completed");
			bac.setTakenAt(LocalDateTime.now());
			batchAssessmentCandidatesRepository.save(bac);
		}
		return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Object> saveDecisionMaking(long candidateId, long batchId, List<AssessmentQuestionnaireRequest> reqList){
		CandidateAssessmentBatch asses = new CandidateAssessmentBatch();
		BatchAssessmentCandidates bac = null;
		String initiatedBy = "candidate";
		long id = -1;
		Assessments assessment = assessmentsRepository.findByName("Stress Management");
		if(batchId == 0) {
			asses.setAssessmentId(assessment.getId());
			asses.setAssessmentName("Stress Management");
			asses.setCandidateId(candidateId);
			asses.setTakenAt(LocalDateTime.now());
			candidateAssessmentBatchRepository.save(asses);
			id = asses.getId();
		}
		else {
			initiatedBy = "client";
			Optional<BatchAssessmentCandidates> bacOpt = batchAssessmentCandidatesRepository.findById(batchId);
			bac = bacOpt.get();
			id = bac.getId();
		}
		List<DecisionMakingAssessmentData> dmadList = new ArrayList();
		for(AssessmentQuestionnaireRequest req : reqList) {
			DecisionMakingAssessmentData dmad = new DecisionMakingAssessmentData();
			dmad.setAssessmentBatchId(asses.getId());
			dmad.setQuestion(req.getQuestion());
			dmad.setOptionSelected(req.getSelectedOption());
			dmad.setInitiatedBy(initiatedBy);
			decisionMakingAssessmentDataRepository.save(dmad);
			dmadList.add(dmad);
		}
		int score = getDecisionMakingScore(dmadList);
		if(batchId == 0) {
			asses.setResult(score + "");
			candidateAssessmentBatchRepository.save(asses);
		}
		else {
			bac.setResult(score + "");
			bac.setStatus("Completed");
			bac.setTakenAt(LocalDateTime.now());
			batchAssessmentCandidatesRepository.save(bac);
		}
		return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Object> saveEmpathy(long candidateId, long batchId, List<AssessmentQuestionnaireRequest> reqList){
		CandidateAssessmentBatch asses = new CandidateAssessmentBatch();
		BatchAssessmentCandidates bac = null;
		String initiatedBy = "candidate";
		long id = -1;
		Assessments assessment = assessmentsRepository.findByName("Stress Management");
		if(batchId == 0) {
			asses.setAssessmentId(assessment.getId());
			asses.setAssessmentName("Stress Management");
			asses.setCandidateId(candidateId);
			asses.setTakenAt(LocalDateTime.now());
			candidateAssessmentBatchRepository.save(asses);
			id = asses.getId();
		}
		else {
			initiatedBy = "client";
			Optional<BatchAssessmentCandidates> bacOpt = batchAssessmentCandidatesRepository.findById(batchId);
			bac = bacOpt.get();
			id = bac.getId();
		}
		List<EmpathyAssessmentData> easdList = new ArrayList();
		for(AssessmentQuestionnaireRequest req : reqList) {
			EmpathyAssessmentData easd = new EmpathyAssessmentData();
			easd.setAssessmentBatchId(asses.getId());
			easd.setQuestion(req.getQuestion());
			easd.setOptionSelected(req.getSelectedOption());
			easd.setInitiatedBy(initiatedBy);
			empathyAssessmentDataRepository.save(easd);
			easdList.add(easd);
		}
		int score = getEmpathyScore(easdList);
		if(batchId == 0) {
			asses.setResult(score + "");
			candidateAssessmentBatchRepository.save(asses);
		}
		else {
			bac.setResult(score + "");
			bac.setStatus("Completed");
			bac.setTakenAt(LocalDateTime.now());
			batchAssessmentCandidatesRepository.save(bac);
		}
		return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Object> saveRelationshipBuilding(long candidateId, long batchId,List<AssessmentQuestionnaireRequest> reqList){
		CandidateAssessmentBatch asses = new CandidateAssessmentBatch();
		BatchAssessmentCandidates bac = null;
		String initiatedBy = "candidate";
		long id = -1;
		Assessments assessment = assessmentsRepository.findByName("Stress Management");
		if(batchId == 0) {
			asses.setAssessmentId(assessment.getId());
			asses.setAssessmentName("Stress Management");
			asses.setCandidateId(candidateId);
			asses.setTakenAt(LocalDateTime.now());
			candidateAssessmentBatchRepository.save(asses);
			id = asses.getId();
		}
		else {
			initiatedBy = "client";
			Optional<BatchAssessmentCandidates> bacOpt = batchAssessmentCandidatesRepository.findById(batchId);
			bac = bacOpt.get();
			id = bac.getId();
		}
		List<RelationshipBuildingAssessmentData> rbdList = new ArrayList();
		for(AssessmentQuestionnaireRequest req : reqList) {
			RelationshipBuildingAssessmentData rbd =  new RelationshipBuildingAssessmentData();
			rbd.setAssessmentBatchId(asses.getId());
			rbd.setQuestion(req.getQuestion());
			rbd.setOptionSelected(req.getSelectedOption());
			rbd.setInitiatedBy(initiatedBy);
			relationshipBuildingAssessmentDataRepository.save(rbd);
			rbdList.add(rbd);
		}
		int score = getRelationshipBuildingScore(rbdList);
		if(batchId == 0) {
			asses.setResult(score + "");
			candidateAssessmentBatchRepository.save(asses);
		}
		else {
			bac.setResult(score + "");
			bac.setStatus("Completed");
			bac.setTakenAt(LocalDateTime.now());
			batchAssessmentCandidatesRepository.save(bac);
		}
		return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Object> saveResilience(long candidateId, long batchId, List<AssessmentQuestionnaireRequest> reqList){
		CandidateAssessmentBatch asses = new CandidateAssessmentBatch();
		BatchAssessmentCandidates bac = null;
		String initiatedBy = "candidate";
		long id = -1;
		Assessments assessment = assessmentsRepository.findByName("Stress Management");
		if(batchId == 0) {
			asses.setAssessmentId(assessment.getId());
			asses.setAssessmentName("Stress Management");
			asses.setCandidateId(candidateId);
			asses.setTakenAt(LocalDateTime.now());
			candidateAssessmentBatchRepository.save(asses);
			id = asses.getId();
		}
		else {
			initiatedBy = "client";
			Optional<BatchAssessmentCandidates> bacOpt = batchAssessmentCandidatesRepository.findById(batchId);
			bac = bacOpt.get();
			id = bac.getId();
		}
		List<ResilienceAssessmentData> radList = new ArrayList();
		for(AssessmentQuestionnaireRequest req : reqList) {
			ResilienceAssessmentData rad = new ResilienceAssessmentData();
			rad.setAssessmentBatchId(asses.getId());
			rad.setQuestion(req.getQuestion());
			rad.setOptionSelected(req.getSelectedOption());
			rad.setInitiatedBy("candidateId");
			resilienceAssessmentDataRepository.save(rad);
			radList.add(rad);
		}
		int score = getResilienceScore(radList);
		if(batchId == 0) {
			asses.setResult(score + "");
			candidateAssessmentBatchRepository.save(asses);
		}
		else {
			bac.setStatus("Completed");
			bac.setResult(score + "");
			bac.setTakenAt(LocalDateTime.now());
			batchAssessmentCandidatesRepository.save(bac);
		}
		return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Object> saveSelfAwareness(long candidateId, long batchId, List<AssessmentQuestionnaireRequest> reqList){
		CandidateAssessmentBatch asses = new CandidateAssessmentBatch();
		BatchAssessmentCandidates bac = null;
		String initiatedBy = "candidate";
		long id = -1;
		Assessments assessment = assessmentsRepository.findByName("Stress Management");
		if(batchId == 0) {
			asses.setAssessmentId(assessment.getId());
			asses.setAssessmentName("Stress Management");
			asses.setCandidateId(candidateId);
			asses.setTakenAt(LocalDateTime.now());
			candidateAssessmentBatchRepository.save(asses);
			id = asses.getId();
		}
		else {
			initiatedBy = "client";
			Optional<BatchAssessmentCandidates> bacOpt = batchAssessmentCandidatesRepository.findById(batchId);
			bac = bacOpt.get();
			id = bac.getId();
		}
		List<SelfAwarenessAssessmentData> saadList = new ArrayList();
		for(AssessmentQuestionnaireRequest req : reqList) {
			SelfAwarenessAssessmentData saad = new SelfAwarenessAssessmentData();
			saad.setAssessmentBatchId(asses.getId());
			saad.setQuestion(req.getQuestion());
			saad.setOptionSelected(req.getSelectedOption());
			saad.setInitiatedBy(initiatedBy);
			selfAwarenessAssessmentDataRepository.save(saad);
			saadList.add(saad);
		}
		int score = getSelfAwarenessScore(saadList);
		if(batchId == 0) {
			asses.setResult(score + "");
			candidateAssessmentBatchRepository.save(asses);
		}
		else {
			bac.setResult(score + "");
			bac.setStatus("Completed");
			bac.setTakenAt(LocalDateTime.now());
			batchAssessmentCandidatesRepository.save(bac);
		}
		return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Object> saveStressManagement(long candidateId, long batchId, List<AssessmentQuestionnaireRequest> reqList){
		CandidateAssessmentBatch asses = new CandidateAssessmentBatch();
		BatchAssessmentCandidates bac = null;
		String initiatedBy = "candidate";
		long id = -1;
		Assessments assessment = assessmentsRepository.findByName("Stress Management");
		if(batchId == 0) {
			asses.setAssessmentId(assessment.getId());
			asses.setAssessmentName("Stress Management");
			asses.setCandidateId(candidateId);
			asses.setTakenAt(LocalDateTime.now());
			candidateAssessmentBatchRepository.save(asses);
			id = asses.getId();
		}
		else {
			initiatedBy = "client";
			Optional<BatchAssessmentCandidates> bacOpt = batchAssessmentCandidatesRepository.findById(batchId);
			bac = bacOpt.get();
			id = bac.getId();
		}
		List<StressManagementAssessmentData> smadList = new ArrayList();
		for(AssessmentQuestionnaireRequest req : reqList) {
			StressManagementAssessmentData smad = new StressManagementAssessmentData();
			smad.setAssessmentBatchId(id);
			smad.setQuestion(req.getQuestion());
			smad.setOptionSelected(req.getSelectedOption());
			smad.setInitiatedBy(initiatedBy);
			stressManagementAssessmentDataRepository.save(smad);
			smadList.add(smad);
		}
		int score = getStressManagementScore(smadList);
		if(batchId == 0) {
			asses.setResult(score + "");
			candidateAssessmentBatchRepository.save(asses);
		}
		else {
			bac.setResult(score + "");
			bac.setStatus("Completed");
			bac.setTakenAt(LocalDateTime.now());
			batchAssessmentCandidatesRepository.save(bac);
		}
		return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Object> saveTimeManagement(long candidateId, long batchId, List<AssessmentQuestionnaireRequest> reqList){
		CandidateAssessmentBatch asses = new CandidateAssessmentBatch();
		BatchAssessmentCandidates bac = null;
		String initiatedBy = "candidate";
		long id = -1;
		Assessments assessment = assessmentsRepository.findByName("Stress Management");
		if(batchId == 0) {
			asses.setAssessmentId(assessment.getId());
			asses.setAssessmentName("Stress Management");
			asses.setCandidateId(candidateId);
			asses.setTakenAt(LocalDateTime.now());
			candidateAssessmentBatchRepository.save(asses);
			id = asses.getId();
		}
		else {
			initiatedBy = "client";
			Optional<BatchAssessmentCandidates> bacOpt = batchAssessmentCandidatesRepository.findById(batchId);
			bac = bacOpt.get();
			id = bac.getId();
		}
		List<TimeManagementAssessmentData> tmadList = new ArrayList();
		for(AssessmentQuestionnaireRequest req : reqList) {
			TimeManagementAssessmentData tmad = new TimeManagementAssessmentData();
			tmad.setAssessmentBatchId(asses.getId());
			tmad.setQuestion(req.getQuestion());
			tmad.setOptionSelected(req.getSelectedOption());
			tmad.setInitiatedBy(initiatedBy);
			timeManagementAssessmentDataRepository.save(tmad);
			tmadList.add(tmad);
		}
		int score = getTimeManagementScore(tmadList);
		if(batchId == 0) {
			asses.setResult(score + "");
			candidateAssessmentBatchRepository.save(asses);
		}
		else {
			bac.setResult(score + "");
			bac.setStatus("Completed");
			bac.setTakenAt(LocalDateTime.now());
			batchAssessmentCandidatesRepository.save(bac);
		}
		return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Object> saveCommunicationSkills(long candidateId, long batchId, List<AssessmentQuestionnaireRequest> reqList){
		CandidateAssessmentBatch asses = new CandidateAssessmentBatch();
		BatchAssessmentCandidates bac = null;
		String initiatedBy = "candidate";
		long id = -1;
		Assessments assessment = assessmentsRepository.findByName("Stress Management");
		if(batchId == 0) {
			asses.setAssessmentId(assessment.getId());
			asses.setAssessmentName("Stress Management");
			asses.setCandidateId(candidateId);
			asses.setTakenAt(LocalDateTime.now());
			candidateAssessmentBatchRepository.save(asses);
			id = asses.getId();
		}
		else {
			initiatedBy = "client";
			Optional<BatchAssessmentCandidates> bacOpt = batchAssessmentCandidatesRepository.findById(batchId);
			bac = bacOpt.get();
			id = bac.getId();
		}
		List<CommunicationSkillsAssessmentData> csadList = new ArrayList();
		for(AssessmentQuestionnaireRequest req : reqList) {
			CommunicationSkillsAssessmentData csad = new CommunicationSkillsAssessmentData();
			csad.setAssessmentBatchId(asses.getId());
			csad.setQuestion(req.getQuestion());
			csad.setOptionSelected(req.getSelectedOption());
			csad.setInitiatedBy(initiatedBy);
			communicationSkillsAssessmentDataRepository.save(csad);
			csadList.add(csad);
		}
		int score = getCommunicationSkillsScore(csadList);
		if(batchId == 0) {
			asses.setResult(score + "");
			candidateAssessmentBatchRepository.save(asses);
		}
		else {
			bac.setResult(score + "");
			bac.setStatus("Completed");
			bac.setTakenAt(LocalDateTime.now());
			batchAssessmentCandidatesRepository.save(bac);
		}		
		
		return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
	}
	
	
	public int getAdaptabilityResult(List<AdaptabilityAssessmentData> dataList){
		//List<AdaptabilityAssessmentData> dataList = adaptabilityAssessmentDataRepository.findByAssessmentId(assessmentId);
		int totalScore = 0;
		for(AdaptabilityAssessmentData data: dataList) {
			Adaptability ada = adaptabilityRepository.findByQuestion(data.getQuestion());
			int score = 0;
			if(data.getOptionSelected().equals("strongly disagree")) {
				score = 7;
				if(ada.getQuestionOrder().equals("Reverse")) score = 1;
			}
			if(data.getOptionSelected().equals("disagree")) {
				score = 6;
				if(ada.getQuestionOrder().equals("Reverse")) score = 2;
			}
			if(data.getOptionSelected().equals("somewhat disagree")) {
				score = 5;
				if(ada.getQuestionOrder().equals("Reverse")) score = 3;
			}
			if(data.getOptionSelected().equals("neutral")) {
				score = 4;
				if(ada.getQuestionOrder().equals("Reverse")) score = 4;
			}
			if(data.getOptionSelected().equals("somewhat agree")) {
				score = 3;
				if(ada.getQuestionOrder().equals("Reverse")) score = 5;
			}
			if(data.getOptionSelected().equals("agree")) {
				score = 2;
				if(ada.getQuestionOrder().equals("Reverse")) score = 6;
			}
			if(data.getOptionSelected().equals("strongly agree")) {
				score = 1;
				if(ada.getQuestionOrder().equals("Reverse")) score = 7;
			}
			
			totalScore = totalScore + score;
		}
		
//		Map m = new HashMap();
//		m.put("score", totalScore);
//		return new ResponseEntity<>(m, null, HttpStatus.OK);
		return totalScore;
	}
	
	
	public int getConflictManagementScore(List<ConflictManagementAssessmentData> dataList){
		//List<ConflictManagementAssessmentData> dataList = conflictManagementAssessmentDataRepository.findByAssessmentId(assessmentId); 
		int totalScore = 0;
		for(ConflictManagementAssessmentData data : dataList) {
			int score = 0;
			if(data.getOptionSelected().equals("Rarely")) {
				score = 1;
			}
			if(data.getOptionSelected().equals("Sometimes")) {
				score = 2;
			}
			if(data.getOptionSelected().equals("Often")) {
				score = 3;
			}
			if(data.getOptionSelected().equals("Always")) {
				score = 4;
			}
			totalScore = totalScore + score;
		}
		
		return totalScore;
	}
	
	
	public int getDecisionMakingScore(List<DecisionMakingAssessmentData> dataList){
		//List<DecisionMakingAssessmentData> dataList = decisionMakingAssessmentDataRepository.findByAssessmentId(assessmentId); 
		int totalScore = 0;
		for(DecisionMakingAssessmentData data: dataList) {
			int score = 0;
			if(data.getOptionSelected().equals("strongly disagree")) {
				score = 5;
			}
			if(data.getOptionSelected().equals("disagree")) {
				score = 4;
			}
			if(data.getOptionSelected().equals("neutral")) {
				score = 3;
			}
			if(data.getOptionSelected().equals("agree")) {
				score = 2;
			}
			if(data.getOptionSelected().equals("strongly agree")) {
				score = 1;
			}
			
			totalScore = totalScore + score;
		}
		
		return totalScore;
	}
	
	
	public int getEmpathyScore(List<EmpathyAssessmentData> dataList){
		//List<EmpathyAssessmentData> dataList = empathyAssessmentDataRepository.findByAssessmentId(assessmentId); 
		int totalScore = 0;
		for(EmpathyAssessmentData data: dataList) {
			int score = 0;
			if(data.getOptionSelected().equals("Does not describe me well")) {
				score = 0;
			}
			if(data.getOptionSelected().equals("somewhat describes me")) {
				score = 1;
			}
			if(data.getOptionSelected().equals("moderately describes me")) {
				score = 2;
			}
			if(data.getOptionSelected().equals("describes me well")) {
				score = 3;
			}
			if(data.getOptionSelected().equals("describes me very  well")) {
				score = 4;
			}
			
			totalScore = totalScore + score;
		}
		
		return totalScore;
	}
	
	
	public int getRelationshipBuildingScore(List<RelationshipBuildingAssessmentData> dataList){
		//List<RelationshipBuildingAssessmentData> dataList = relationshipBuildingAssessmentDataRepository.findByAssessmentId(assessmentId); 
		int totalScore = 0;
		for(RelationshipBuildingAssessmentData data: dataList) {
			int score = 0;
			if(data.getOptionSelected().equals("Never")) {
				score = 1;
			}
			if(data.getOptionSelected().equals("Rarely")) {
				score = 2;
			}
			if(data.getOptionSelected().equals("Sometimes")) {
				score = 3;
			}
			if(data.getOptionSelected().equals("Always")) {
				score = 4;
			}
			
			totalScore = totalScore + score;
		}
		
		return totalScore;
	}

	
	public int getResilienceScore(List<ResilienceAssessmentData> dataList){
		//List<ResilienceAssessmentData> dataList = resilienceAssessmentDataRepository.findByAssessmentId(assessmentId); 
		int totalScore = 0;
		
		for(ResilienceAssessmentData data: dataList) {
			int score = 0;
			if(data.getOptionSelected().equals("strongly disagree")) {
				score = 5;
			}
			if(data.getOptionSelected().equals("disagree")) {
				score = 4;
			}
			if(data.getOptionSelected().equals("neutral")) {
				score = 3;
			}
			if(data.getOptionSelected().equals("agree")) {
				score = 2;
			}
			if(data.getOptionSelected().equals("strongly agree")) {
				score = 1;
			}
			
			totalScore = totalScore + score;
		}
		
		return totalScore;
	}
	
	public int getStressManagementScore(List<StressManagementAssessmentData> dataList){
//		List<StressManagementAssessmentData> dataList = stressManagementAssessmentDataRepository.findByAssessmentId(assessmentId); 
		int totalScore = 0;
		for(StressManagementAssessmentData data: dataList) {
			System.out.println(data.getOptionSelected());
			int score = 0;
			if(data.getOptionSelected().equals("I haven't been doing this at all")) {
				score = 1;
			}
			if(data.getOptionSelected().equals("I've been doing this a little bit")) {
				score = 2;
			}
			if(data.getOptionSelected().equals("I've been doing this a medium amount")) {
				score = 3;
			}
			if(data.getOptionSelected().equals("I've been doing this a lot")) {
				score = 4;
			}
			System.out.println(totalScore);
			totalScore = totalScore + score;
		}
		
		return totalScore;
	}
	
	
	public int getSelfAwarenessScore(List<SelfAwarenessAssessmentData> dataList){
//		List<SelfAwarenessAssessmentData> dataList = selfAwarenessAssessmentDataRepository.findByAssessmentId(assessmentId); 
		int totalScore = 0;
		
		for(SelfAwarenessAssessmentData data: dataList) {
			int score = 0;
			if(data.getOptionSelected().equals("Never")) {
				score = 1;
			}
			if(data.getOptionSelected().equals("Very Little")) {
				score = 2;
			}
			if(data.getOptionSelected().equals("Sometimes")) {
				score = 3;
			}
			if(data.getOptionSelected().equals("Often")) {
				score = 4;
			}
			if(data.getOptionSelected().equals("A Lot")) {
				score = 5;
			}
			totalScore = totalScore + score;
		}
		
		return totalScore;
	}

	
	public int getTimeManagementScore(List<TimeManagementAssessmentData> dataList){
//		List<TimeManagementAssessmentData> dataList = timeManagementAssessmentDataRepository.findByAssessmentId(assessmentId); 
		int totalScore = 0;
		
		for(TimeManagementAssessmentData data: dataList) {
			int score = 0;
			if(data.getOptionSelected().equals("None of the time")) {
				score = 1;
			}
			if(data.getOptionSelected().equals("Some of the time")) {
				score = 2;
			}
			if(data.getOptionSelected().equals("Most of the time")) {
				score = 3;
			}
			if(data.getOptionSelected().equals("All of the time")) {
				score = 4;
			}
			
			totalScore = totalScore + score;
		}
		
		return totalScore;
	}

	
	public int getCommunicationSkillsScore(List<CommunicationSkillsAssessmentData> dataList){
//		List<CommunicationSkillsAssessmentData> dataList = communicationSkillsAssessmentDataRepository.findByAssessmentId(assessmentId); 
		int totalScore = 0;
		
		for(CommunicationSkillsAssessmentData data: dataList) {
			int score = 0;
			if(data.getOptionSelected().equals("strongly disagree")) {
				score = 5;
			}
			if(data.getOptionSelected().equals("disagree")) {
				score = 4;
			}
			if(data.getOptionSelected().equals("neutral")) {
				score = 3;
			}
			if(data.getOptionSelected().equals("agree")) {
				score = 2;
			}
			if(data.getOptionSelected().equals("strongly agree")) {
				score = 1;
			}
			
			totalScore = totalScore + score;
		}
		
		return totalScore;
	}


	public ResponseEntity<Object> getAssessmentQuestionnaire(String assessmentType) {
		// TODO Auto-generated method stub
		
		if(assessmentType.equals("Stress Management")){
		    return getStressManagement();
		}
		if(assessmentType.equals("Resilience")){
		    return getResilience();
		}
		if(assessmentType.equals("Empathy")){
		    return getEmpathy();
		}
		if(assessmentType.equals("Self Awareness")){
		    return getSelfAwareness();
		}
		if(assessmentType.equals("Conflict management")){
		    return getConflictManagement();
		}
		if(assessmentType.equals("Communication Skills")){
		    return getCommunicationSkills();
		}
		if(assessmentType.equals("Relationship Building")){
		    return getRelationshipBuilding();
		}
		if(assessmentType.equals("Adaptability")){
		    return getAdaptability();
		}
		if(assessmentType.equals("Time management")){
		    return getTimeManagement();
		}
		if(assessmentType.equals("Decision Making")){
		    return getDecisionMaking();
		}
		return new ResponseEntity<>("not found", null, HttpStatus.NOT_FOUND);
	}
	
	
	public ResponseEntity<Object> saveAssessment(long batchId, long candidateId, CandidateAssessmentRequest req) {
		// TODO Auto-generated method stub
		String assessmentType = req.getAssessmentName();
		List<AssessmentQuestionnaireRequest> reqList = req.getQuestionList();
		
		if(assessmentType.equals("Stress Management")){
		    return saveStressManagement(candidateId, batchId, reqList);
		}
		if(assessmentType.equals("Resilience")){
		    return saveResilience(candidateId, batchId, reqList);
		}
		if(assessmentType.equals("Empathy")){
		    return saveEmpathy(candidateId, batchId, reqList);
		}
		if(assessmentType.equals("Self Awareness")){
		    return saveSelfAwareness(candidateId, batchId, reqList);
		}
		if(assessmentType.equals("Conflict management")){
		    return saveConflictManagement(candidateId, batchId, reqList);
		}
		if(assessmentType.equals("Communication Skills")){
		    return saveCommunicationSkills(candidateId, batchId, reqList);
		}
		if(assessmentType.equals("Relationship Building")){
		    return saveRelationshipBuilding(candidateId, batchId, reqList);
		}
		if(assessmentType.equals("Adaptability")){
		    return saveAdaptability(candidateId, batchId, reqList);
		}
		if(assessmentType.equals("Time management")){
		    return saveTimeManagement(candidateId, batchId, reqList);
		}
		if(assessmentType.equals("Decision Making")){
		    return saveDecisionMaking(candidateId, batchId, reqList);
		}
		
		return new ResponseEntity<>("not found", null, HttpStatus.NOT_FOUND);
	}
}
