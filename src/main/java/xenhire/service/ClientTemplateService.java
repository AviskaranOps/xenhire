package xenhire.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import xenhire.model.CandidateAssessmentRanking;
import xenhire.model.ClientAssessmentData;
import xenhire.model.ClientAssessmentRanking;
import xenhire.model.ClientAssessmentVersion;
import xenhire.model.ClientIcpTemplate;
import xenhire.model.ClientOptions;
import xenhire.model.ClientPreferenceTemplate;
import xenhire.model.ClientSkills;
import xenhire.model.ClientTeamTemplate;
import xenhire.model.ClientValueAssessmentData;
import xenhire.model.ClientValues;
import xenhire.model.Competency;
import xenhire.model.JobPreferencesTemplate;
import xenhire.model.JobTemplate;
import xenhire.model.PillarOrderText;
import xenhire.repository.ClientAssessmentRankingRepository;
import xenhire.repository.ClientIcpTemplateRepository;
import xenhire.repository.ClientOptionsRepository;
import xenhire.repository.ClientPreferenceTemplateRepository;
import xenhire.repository.ClientQuestionnaireRepository;
import xenhire.repository.ClientSkillsRepository;
import xenhire.repository.ClientTeamTemplateRepository;
import xenhire.repository.ClientValuesDataRepository;
import xenhire.repository.ClientValuesRepository;
import xenhire.repository.CompetencyRepository;
import xenhire.repository.JobPreferenceTemplateRepository;
import xenhire.repository.JobTemplateRepository;
import xenhire.repository.PillarOrderTextRepository;
import xenhire.request.ClientAssessmentRequest;
import xenhire.request.ClientIcpRequest;
import xenhire.request.ClientValuesRequest;
import xenhire.request.ClinetPreferenceTemplateRequest;
import xenhire.request.JobDetailsRequest;
import xenhire.request.ValuesRating;
import xenhire.response.CandidateSpectrumResults;
import xenhire.response.ClientValueResponse;
import xenhire.response.JobTemplateResponse;
import xenhire.response.PaginatedResponse;
import xenhire.response.SpectrumPillar2Components;
import xenhire.response.SpectrumPillarComponents;

@Service
public class ClientTemplateService {
	
	
	@Autowired
	JobTemplateRepository jobTemplateRepository;
	
	@Autowired
	ClientTeamTemplateRepository clientTeamTemplateRepository;
	
	@Autowired
	JobPreferenceTemplateRepository jobPreferenceTemplateRepository;
	
	@Autowired
	UtilityService utilityService;
	
	@Autowired
	ClientValuesDataRepository clientValuesDataRepository;
	
	@Autowired
	ClientValuesRepository clientValuesRepository;
	
	@Autowired
	ClientPreferenceTemplateRepository clientPreferenceTemplateRepository;
	
	@Autowired
	ClientSkillsRepository clientSkillsRepository;
	
	@Autowired
	ClientQuestionnaireRepository clientQuestionnaireRepository;
	
	@Autowired
	ClientOptionsRepository clientOptionsRepository;
	
	@Autowired
	ClientIcpTemplateRepository clientIcpTemplateRepository;
	
	@Autowired
	ClientAssessmentRankingRepository clientAssessmentRankingRepository;
	
	@Autowired
	PillarOrderTextRepository pillarOrderTextRepository;
	
	@Autowired
	CompetencyRepository competencyRepository;
	

	public JobDetailsRequest getJobTemplate(long clientId, long jobTemplateId) {
		// TODO Auto-generated method stub
		Optional<JobTemplate> jtOpt = jobTemplateRepository.findById(jobTemplateId);
		if(jtOpt.isEmpty()) {
			return  null;//new ResponseEntity<>("record not found", null, HttpStatus.OK);
		}
		JobTemplate jt = jtOpt.get();
		JobDetailsRequest jtr = new JobDetailsRequest();
		List<String> certifications = new ArrayList();
		for(String cert : jt.getCertifications().split(",")) {
			certifications.add(cert);
		}
		List<String> softwares = new ArrayList();
		for(String cert : jt.getSoftwares().split(",")) {
			softwares.add(cert);
		}
		jtr.setCreatedBy(jt.getCreatedBy());
		jtr.setBenefits(jt.getBenefits());
		jtr.setCertifications(certifications);
		jtr.setCompanyInfo(jt.getCompanyInfo());
		jtr.setDifferentAcademic(jt.getDifferentAcademic());
		jtr.setEnvision(jt.getEnvision());
		jtr.setEqualEmployeeOpportunity(jt.getEqualEmployeeOpportunity());
		jtr.setIndustryKnowledge(jt.getIndustryKnowledge());
		jtr.setJobCode(jt.getJobCode());
		jtr.setWorkSetting(jt.getWorkSetting());
		jtr.setVisa(jt.getVisa());
		jtr.setTemplateTag(jt.getTemplateTag());
		jtr.setTemplateName(jt.getTemplateName());
		jtr.setTemplateDescription(jt.getTemplateDescription());
		jtr.setSpecifyIndustryExp(jt.getSpecifyIndustryExp());
		jtr.setSpecificIndustryExperience(jt.getSpecificIndustryExperience());
		jtr.setSoftwares(softwares);
		jtr.setSalary(jt.getSalary());
		jtr.setRoleType(jt.getRoleType());
		jtr.setRoleTravel(jt.getRoleTravel());
		jtr.setRoleTimings(jt.getRoleTimings());
		jtr.setResponsibilities(jt.getResponsibilities());
		jtr.setRequireRegulatory(jt.getRequireRegulatory());
		jtr.setPositionSummry(jt.getPositionSummry());
		jtr.setMinimumLevelQualification(jt.getMinimumLevelQualification());
		jtr.setJobTitle(jt.getJobTitle());
		jtr.setJobLocation(jt.getJobLocation());
		jtr.setJobFamily(jt.getJobFamily());
		jtr.setJobDepartment(jt.getJobDepartment());	
		jtr.setJobDescription(jt.getJobDescription());
		
		return jtr;
		//return new ResponseEntity<>(jtr, null, HttpStatus.OK);
	}
	
	
	
	public ResponseEntity<Object> getAllJobTemplates(long clientId, int pageNo, int pageSize){
		
		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
		Page<JobTemplate> pagedResult = jobTemplateRepository.findAllByTemplate(true, paging);
		List<JobDetailsRequest> resp = new ArrayList();
		for(JobTemplate jt : pagedResult.getContent()) {
			JobDetailsRequest jtr = new JobDetailsRequest();
			List<String> certifications = new ArrayList();
			for(String cert : jt.getCertifications().split(",")) {
				certifications.add(cert);
			}
			List<String> softwares = new ArrayList();
			for(String cert : jt.getSoftwares().split(",")) {
				softwares.add(cert);
			}
			jtr.setId(jt.getId());
			jtr.setCreatedBy(jt.getCreatedBy());
			jtr.setBenefits(jt.getBenefits());
			jtr.setCertifications(certifications);
			jtr.setCompanyInfo(jt.getCompanyInfo());
			jtr.setDifferentAcademic(jt.getDifferentAcademic());
			jtr.setEnvision(jt.getEnvision());
			jtr.setEqualEmployeeOpportunity(jt.getEqualEmployeeOpportunity());
			jtr.setIndustryKnowledge(jt.getIndustryKnowledge());
			jtr.setJobCode(jt.getJobCode());
			jtr.setWorkSetting(jt.getWorkSetting());
			jtr.setVisa(jt.getVisa());
			jtr.setTemplateTag(jt.getTemplateTag());
			jtr.setTemplateName(jt.getTemplateName());
			jtr.setTemplateDescription(jt.getTemplateDescription());
			jtr.setSpecifyIndustryExp(jt.getSpecifyIndustryExp());
			jtr.setSpecificIndustryExperience(jt.getSpecificIndustryExperience());
			jtr.setSoftwares(softwares);
			jtr.setSalary(jt.getSalary());
			jtr.setRoleType(jt.getRoleType());
			jtr.setRoleTravel(jt.getRoleTravel());
			jtr.setRoleTimings(jt.getRoleTimings());
			jtr.setResponsibilities(jt.getResponsibilities());
			jtr.setRequireRegulatory(jt.getRequireRegulatory());
			jtr.setPositionSummry(jt.getPositionSummry());
			jtr.setMinimumLevelQualification(jt.getMinimumLevelQualification());
			jtr.setJobTitle(jt.getJobTitle());
			jtr.setJobLocation(jt.getJobLocation());
			jtr.setJobFamily(jt.getJobFamily());
			jtr.setJobDepartment(jt.getJobDepartment());	
			jtr.setJobDescription(jt.getJobDescription());
			resp.add(jtr);
		}
		
		PaginatedResponse paginationResponse = PaginatedResponse.builder().data(resp)
				.pageNo(pagedResult.getNumber() + 1).pageSize(pagedResult.getSize())
				.totalCount(pagedResult.getTotalElements()).message("found").result("success")
				.build();
		
		return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> saveJobTemplate(long clientId, JobDetailsRequest req){
		
		JobTemplate jt = jobTemplateRepository.findByClientIdAndTemplateName(clientId, req.getTemplateName());
		String username = utilityService.getLoggedInUsername(clientId);
		System.out.println(username);
		if(jt == null) {
			jt = new JobTemplate();
			//return new ResponseEntity<>("record exists", null, HttpStatus.BAD_REQUEST);
		}

		String certifications = "";
		for(String cert : req.getCertifications()) {
			certifications = certifications + cert + ",";
		}
		String softwares = "";
		for(String cert : req.getSoftwares()) {
			softwares = softwares + cert + ",";
		}
		jt.setClientId(clientId);
		jt.setCreatedBy(username);
		jt.setTemplate(true);
		jt.setBenefits(req.getBenefits());
		jt.setCertifications(certifications);
		jt.setCompanyInfo(req.getCompanyInfo());
		jt.setDifferentAcademic(req.getDifferentAcademic());
		jt.setEnvision(req.getEnvision());
		jt.setEqualEmployeeOpportunity(req.getEqualEmployeeOpportunity());
		jt.setIndustryKnowledge(req.getIndustryKnowledge());
		jt.setJobCode(req.getJobCode());
		jt.setWorkSetting(req.getWorkSetting());
		jt.setVisa(req.getVisa());
		jt.setTemplateTag(req.getTemplateTag());
		jt.setTemplateName(req.getTemplateName());
		jt.setTemplateDescription(req.getTemplateDescription());
		jt.setSpecifyIndustryExp(req.getSpecifyIndustryExp());
		jt.setSpecificIndustryExperience(req.getSpecificIndustryExperience());
		jt.setSoftwares(softwares);
		jt.setSalary(req.getSalary());
		jt.setRoleType(req.getRoleType());
		jt.setRoleTravel(req.getRoleTravel());
		jt.setRoleTimings(req.getRoleTimings());
		jt.setResponsibilities(req.getResponsibilities());
		jt.setRequireRegulatory(req.getRequireRegulatory());
		jt.setPositionSummry(req.getPositionSummry());
		jt.setMinimumLevelQualification(req.getMinimumLevelQualification());
		jt.setJobTitle(req.getJobTitle());
		jt.setJobLocation(req.getJobLocation());
		jt.setJobFamily(req.getJobFamily());
		jt.setJobDepartment(req.getJobDepartment());	
		jt.setJobDescription(req.getJobDescription());
		jobTemplateRepository.save(jt);
		
		return new ResponseEntity<>("saved", null, HttpStatus.OK);
		
	}
	
	
	public ClientTeamTemplate getTeamTemplate(long clientId, long jobTemplateId) {
		// TODO Auto-generated method stub
		Optional<ClientTeamTemplate> jt = clientTeamTemplateRepository.findById(jobTemplateId);
		if(jt == null) {
			return null; //new ResponseEntity<>("record not found", null, HttpStatus.OK);
		}
		
		return jt.get(); //new ResponseEntity<>(jt.get(), null, HttpStatus.OK);
	}
	
	
	
	public ResponseEntity<Object> getAllTeamTemplates(long clientId, int pageNo, int pageSize){
		
		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
		Page<ClientTeamTemplate> pagedResult = clientTeamTemplateRepository.findAllByTemplate(true, paging);
		
		PaginatedResponse paginationResponse = PaginatedResponse.builder().data(pagedResult.getContent())
				.pageNo(pagedResult.getNumber() + 1).pageSize(pagedResult.getSize())
				.totalCount(pagedResult.getTotalElements()).message("found").result("success")
				.build();
		
		return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> saveTeamTemplate(long clientId, ClientTeamTemplate req){
		ClientTeamTemplate ct = null;
		System.out.println(req);
		String username = utilityService.getLoggedInUsername(clientId);
		if(req.getId() != 0) {
			Optional<ClientTeamTemplate> ctOpt = clientTeamTemplateRepository.findById(req.getId());
			ct = ctOpt.get();

		}
		int maxId = clientTeamTemplateRepository.getMaxId();
		if(ct == null) {
			ct = req;
			ct.setClientId(clientId);
			ct.setTemplateName("Team" + (maxId + 1));
			ct.setCreatedBy(username);
		}
		ct.setTemplate(true);
		clientTeamTemplateRepository.save(ct);
		
		return new ResponseEntity<>("saved", null, HttpStatus.OK);
	}
	
	
	public List<Map> getValueTemplate(long clientId, long templateId){
		Optional<ClientValueAssessmentData> cvadOpt =  clientValuesDataRepository.findById(templateId);
		ClientValueAssessmentData cvd = cvadOpt.get();
		List<Map> respList = new ArrayList();
		List<ClientValues> valuesList = clientValuesRepository.findAll();
		
		for(ClientValues values : valuesList) {
			Map m = new HashMap();
			System.out.println(values.getClientValue());
			if(values.getClientValue().equals("Stimulation")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getStimulation());}
			if(values.getClientValue().equals("Self-Direction")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getSelfDirection());}
			if(values.getClientValue().equals("Hedonism")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getHedonism());}
			if(values.getClientValue().equals("Achievement")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getAchievement());}
			if(values.getClientValue().equals("Power")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getPower());}
			if(values.getClientValue().equals("Security")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getSecurity());}
			if(values.getClientValue().equals("Conformity")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getConformity());}
			if(values.getClientValue().equals("Tradition")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getTradition());}
			if(values.getClientValue().equals("Benevolence")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getBenevolence());}
			if(values.getClientValue().equals("Universalism")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getUniversalism());}
			
			respList.add(m);
		}
		return  respList; //new ResponseEntity<>(respList, null, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> getAllValueTemplates(long clientId, int pageNo, int pageSize){
		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
		Page<ClientValueAssessmentData> pagedResult = clientValuesDataRepository.findByClientIdAndTemplate(clientId, true, paging);
		List<ClientValues> valuesList = clientValuesRepository.findAll();
		System.out.println(valuesList);
		List<ClientValueResponse> respList = new ArrayList();
		for(ClientValueAssessmentData cvd : pagedResult) {
			System.out.println(cvd);
			ClientValueResponse resp = new ClientValueResponse();
			List<Map> valuesMap = new ArrayList();
			for(ClientValues values : valuesList) {
				Map m = new HashMap();
				System.out.println(values.getClientValue());
				if(values.getClientValue().equals("Stimulation")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getStimulation());}
				if(values.getClientValue().equals("Self-Direction")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getSelfDirection());}
				if(values.getClientValue().equals("Hedonism")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getHedonism());}
				if(values.getClientValue().equals("Achievement")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getAchievement());}
				if(values.getClientValue().equals("Power")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getPower());}
				if(values.getClientValue().equals("Security")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getSecurity());}
				if(values.getClientValue().equals("Conformity")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getConformity());}
				if(values.getClientValue().equals("Tradition")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getTradition());}
				if(values.getClientValue().equals("Benevolence")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getBenevolence());}
				if(values.getClientValue().equals("Universalism")) { m.put("value", values.getClientValue()); m.put("statement", values.getStatement()); m.put("rating", cvd.getUniversalism());}
				
				valuesMap.add(m);
			}
			
			resp.setId(cvd.getId());
			resp.setTemplateName(cvd.getTemplateName());
			resp.setValuesData(valuesMap);
			resp.setCreatedBy(cvd.getCreatedBy());
			resp.setTemplateDescription(cvd.getTemplateDescription());
			resp.setTemplateTag(cvd.getTemplateTag());
			
			respList.add(resp);
			
		}
		
		PaginatedResponse paginationResponse = PaginatedResponse.builder().data(respList)
				.pageNo(pagedResult.getNumber() + 1).pageSize(pagedResult.getSize())
				.totalCount(pagedResult.getTotalElements()).message("found").result("success")
				.build();
		
		
		return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> saveClientValues(long clientId, ClientValuesRequest reqList){
		ClientValueAssessmentData cvd = new ClientValueAssessmentData();
		int maxId = clientValuesDataRepository.getMaxId();
		String username = utilityService.getLoggedInUsername(clientId);
		for(ValuesRating req : reqList.getRatingList()) {
				if(req.getValue().equals("Stimulation")) cvd.setStimulation(req.getRating());
				if(req.getValue().equals("Self-Direction")) cvd.setSelfDirection(req.getRating());
				if(req.getValue().equals("Hedonism")) cvd.setHedonism(req.getRating());
				if(req.getValue().equals("Achievement")) cvd.setAchievement(req.getRating());
				if(req.getValue().equals("Power")) cvd.setPower(req.getRating());
				if(req.getValue().equals("Security")) cvd.setSecurity(req.getRating());
				if(req.getValue().equals("Conformity")) cvd.setConformity(req.getRating());
				if(req.getValue().equals("Tradition")) cvd.setTradition(req.getRating());
				if(req.getValue().equals("Benevolence")) cvd.setBenevolence(req.getRating());
				if(req.getValue().equals("Universalism")) cvd.setUniversalism(req.getRating());
		}
		cvd.setCreatedBy(username);
		cvd.setClientId(clientId);
		cvd.setTemplateName(reqList.getTemplateName());
		cvd.setTemplateTag(reqList.getTemplateTag());
		cvd.setTemplateDescription(reqList.getTemplateDescription());
		cvd.setTemplate(true);
		clientValuesDataRepository.save(cvd);
		
		return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
		
	}
	
	

	public ResponseEntity<Object> getJobPreferenceTemplate(long clientId, long jobTemplateId) {
		// TODO Auto-generated method stub
		Optional<ClientPreferenceTemplate> jt = clientPreferenceTemplateRepository.findById(jobTemplateId);
		if(jt == null) {
			return new ResponseEntity<>("record not found", null, HttpStatus.OK);
		}
		ClientPreferenceTemplate cpt = jt.get();
		ClinetPreferenceTemplateRequest resp = new ClinetPreferenceTemplateRequest();
		List<ClientSkills> skills = clientSkillsRepository.findByClientPreferencesId(cpt.getId());
		List<ClientSkills> primarySkills = skills.stream().filter(skill -> skill.getSkillType().equals("primary")).collect(Collectors.toList());
		List<ClientSkills> secondarySkills = skills.stream().filter(skill -> skill.getSkillType().equals("secondary")).collect(Collectors.toList());
		List<Map<String,String>> primarySkillsList = new ArrayList();
		for(ClientSkills skill : primarySkills) {
			Map m = new HashMap();
			m.put("skill", skill.getSkill());
			m.put("expertise", skill.getSkillLevel());
			primarySkillsList.add(m);
		}
		List<Map<String,String>> secondarySkillsList = new ArrayList();
		for(ClientSkills skill : secondarySkills) {
			Map m = new HashMap();
			m.put("skill", skill.getSkill());
			m.put("expertise", skill.getSkillLevel());
			secondarySkillsList.add(m);
		}
		List<Map<String,String>> certifications = new ArrayList();
		for(String cert : cpt.getCertifications().split(",")) {
			Map m = new HashMap();
			m.put("certificate", cert);
			certifications.add(m);
		}
		List<Map<String,String>> softwares = new ArrayList();
		for(String cert : cpt.getSoftwares().split(",")) {
			Map m = new HashMap();
			m.put("tools", cert);
			softwares.add(m);
		}
		resp.setCertificationsOrLicenses(certifications);
		resp.setCompensationOffered(cpt.getCompensation());
		resp.setCreatedBy(cpt.getCreatedBy());
		resp.setDepthKnowledge(cpt.getIndustrySpecificIndepthKnowledge());
		resp.setDifferentAcademic(cpt.getDifferentAcademic());
		resp.setExperianceInIndustry(cpt.getCrossFunctinality());
		resp.setId(cpt.getId());
		resp.setJobDescription(cpt.getJobDescription());
		resp.setLocationRole(cpt.getRoleLocation());
		resp.setMinimumLevelQualification(cpt.getMinQualification());
		resp.setPrimarySkills(primarySkillsList);
		resp.setRelocation(cpt.getRelocation());
		resp.setRelocationBudget(cpt.getRelocationBudget());
		resp.setRequireAcademicQualification(cpt.getRequireAcademicQualification());
		resp.setScopOfRole(cpt.getResponsesibilities());
		resp.setSecoundrySkills(secondarySkillsList);
		resp.setSpecifyIndusrtyExp(cpt.getIndustrySpecificExperience());
		resp.setSuccessThreeyear(cpt.getEnvision());
		resp.setTemplateName(cpt.getTemplateName());
		resp.setTimeOfRole(cpt.getRoletimings());
		resp.setToolsOrSoftwaresetToolsOrSoftware(softwares);
		resp.setTravelRole(cpt.getRoleTravel());
		resp.setTypeOfRoles(cpt.getRoleType());
		resp.setVisa(cpt.getVisa());
		resp.setWorkSetting(cpt.getWorkSetting());	
		
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
	}

	
	public ResponseEntity<Object> getAllPreferenceTemplate(long clientId, int pageNo, int pageSize) {
		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
		Page<ClientPreferenceTemplate> pagedResult = clientPreferenceTemplateRepository.findAllByClientId(clientId, paging);
		List<ClinetPreferenceTemplateRequest> respList = new ArrayList();
		for(ClientPreferenceTemplate cpt : pagedResult.getContent()) {
			ClinetPreferenceTemplateRequest resp = new ClinetPreferenceTemplateRequest();
			List<ClientSkills> skills = clientSkillsRepository.findByClientPreferencesId(cpt.getId());
			List<ClientSkills> primarySkills = skills.stream().filter(skill -> skill.getSkillType().equals("primary")).collect(Collectors.toList());
			List<ClientSkills> secondarySkills = skills.stream().filter(skill -> skill.getSkillType().equals("secondary")).collect(Collectors.toList());
			List<Map<String,String>> primarySkillsList = new ArrayList();
			for(ClientSkills skill : primarySkills) {
				Map m = new HashMap();
				m.put("skill", skill.getSkill());
				m.put("expertise", skill.getSkillLevel());
				primarySkillsList.add(m);
			}
			List<Map<String,String>> secondarySkillsList = new ArrayList();
			for(ClientSkills skill : secondarySkills) {
				Map m = new HashMap();
				m.put("skill", skill.getSkill());
				m.put("expertise", skill.getSkillLevel());
				secondarySkillsList.add(m);
			}
			List<Map<String,String>> certifications = new ArrayList();
			for(String cert : cpt.getCertifications().split(",")) {
				Map m = new HashMap();
				m.put("certificate", cert);
				certifications.add(m);
			}
			List<Map<String,String>> softwares = new ArrayList();
			for(String cert : cpt.getSoftwares().split(",")) {
				Map m = new HashMap();
				m.put("tools", cert);
				softwares.add(m);
			}
			resp.setCertificationsOrLicenses(certifications);
			resp.setCompensationOffered(cpt.getCompensation());
			resp.setCreatedBy(cpt.getCreatedBy());
			resp.setDepthKnowledge(cpt.getIndustrySpecificIndepthKnowledge());
			resp.setDifferentAcademic(cpt.getDifferentAcademic());
			resp.setExperianceInIndustry(cpt.getCrossFunctinality());
			resp.setId(cpt.getId());
			resp.setJobDescription(cpt.getJobDescription());
			resp.setLocationRole(cpt.getRoleLocation());
			resp.setMinimumLevelQualification(cpt.getMinQualification());
			resp.setPrimarySkills(primarySkillsList);
			resp.setRelocation(cpt.getRelocation());
			resp.setRelocationBudget(cpt.getRelocationBudget());
			resp.setRequireAcademicQualification(cpt.getRequireAcademicQualification());
			resp.setScopOfRole(cpt.getResponsesibilities());
			resp.setSecoundrySkills(secondarySkillsList);
			resp.setSpecifyIndusrtyExp(cpt.getIndustrySpecificExperience());
			resp.setSuccessThreeyear(cpt.getEnvision());
			resp.setTemplateName(cpt.getTemplateName());
			resp.setTimeOfRole(cpt.getRoletimings());
			resp.setToolsOrSoftwaresetToolsOrSoftware(softwares);
			resp.setTravelRole(cpt.getRoleTravel());
			resp.setTypeOfRoles(cpt.getRoleType());
			resp.setVisa(cpt.getVisa());
			resp.setWorkSetting(cpt.getWorkSetting());	
			
			respList.add(resp);;
		}
		
		
		PaginatedResponse paginationResponse = PaginatedResponse.builder().data(respList)
				.pageNo(pagedResult.getNumber() + 1).pageSize(pagedResult.getSize())
				.totalCount(pagedResult.getTotalElements()).message("found").result("success")
				.build();
		
		return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> savePreferenceTemplate(long clientId, ClinetPreferenceTemplateRequest req) {
		ClientPreferenceTemplate cpt = null;
		int maxId = clientPreferenceTemplateRepository.getMaxId();
		String username = utilityService.getLoggedInUsername(clientId);
		if(req.getId() != 0) {	
			Optional<ClientPreferenceTemplate> cptOpt = clientPreferenceTemplateRepository.findById(req.getId());
			cpt = cptOpt.get();
		}
		else {
			cpt = new ClientPreferenceTemplate();
			cpt.setClientId(clientId);
			cpt.setTemplateName("preferences"+ (maxId + 1));
		}
		String certifications = "";
		for(Map<String, String> cert : req.getCertificationsOrLicenses()) {
			certifications = certifications + cert.get("certificate").toString() + ",";
		}
		String softwares = "";
		for(Map<String, String> cert : req.getToolsOrSoftwaresetToolsOrSoftware()) {
			softwares = softwares + cert.get("tools").toString() + ",";
		}
		cpt.setTemplateName(req.getTemplateName());
		cpt.setTemplateDescription(req.getTemplateDescription());
		cpt.setTemplateTag(req.getTemplateTag());
		cpt.setCertifications(certifications.substring(0, certifications.length() -1));
		cpt.setCompensation(req.getCompensationOffered());
		cpt.setCrossFunctinality(req.getExperianceInIndustry());
		cpt.setDifferentAcademic(req.getDifferentAcademic());
		cpt.setEnvision(req.getSuccessThreeyear());
		cpt.setIndustrySpecificExperience(req.getSpecifyIndusrtyExp());
		cpt.setIndustrySpecificIndepthKnowledge(req.getDepthKnowledge());
		cpt.setJobDescription(req.getJobDescription());
		cpt.setMinQualification(req.getMinimumLevelQualification());
		cpt.setRelocation(req.getRelocation());
		cpt.setRelocationBudget(req.getRelocationBudget());
		cpt.setRequireAcademicQualification(req.getRequireAcademicQualification());
		cpt.setResponsesibilities(req.getScopOfRole());
		cpt.setRoleLocation(req.getLocationRole());
		cpt.setRoletimings(req.getTimeOfRole());
		cpt.setRoleTravel(req.getTravelRole());
		cpt.setRoleType(req.getTypeOfRoles());
		cpt.setSoftwares(softwares.substring(0, softwares.length() - 1));
		cpt.setVisa(req.getVisa());
		cpt.setWorkSetting(req.getWorkSetting());
		cpt.setCreatedBy(username);
		
		clientPreferenceTemplateRepository.save(cpt);
		
		
		for(Map<String,String> skill : req.getPrimarySkills()) {
			ClientSkills cs = new ClientSkills();
			cs.setClientId(clientId);
			cs.setClientPreferencesId(cpt.getId());
			cs.setSkill(skill.get("skill"));
			cs.setSkillLevel(skill.get("expertise"));
			cs.setSkillType("primary");
			clientSkillsRepository.save(cs);
		}
		for(Map<String,String> skill : req.getSecoundrySkills()) {
			ClientSkills cs = new ClientSkills();
			cs.setClientId(clientId);
			cs.setClientPreferencesId(cpt.getId());
			cs.setSkill(skill.get("skill"));
			cs.setSkillLevel(skill.get("expertise"));
			cs.setSkillType("secondary");
			clientSkillsRepository.save(cs);
		}
		
		
		return new ResponseEntity<>("saved", null, HttpStatus.OK);
	}
	
	
	
	public ResponseEntity<Object> saveClientICPTempalte(ClientIcpRequest reqList, long clientId) {
		System.out.println(clientId);
		int maxTemplateNo = clientIcpTemplateRepository.getMaxTemplateNo();
		String username = utilityService.getLoggedInUsername(clientId);
		for(ClientAssessmentRequest req : reqList.getQuestionList()) {
			if(req.getQuestionType().equals("RANKING")) {
				for(String opts : req.getOptions()) {
					System.out.println("questionNo = " + req.getQuestionNo() + " option = " + opts);
					ClientOptions co = clientOptionsRepository.findByQuestionnaireNoAndOptionDesc(req.getQuestionNo(), opts);
					ClientIcpTemplate cad = new ClientIcpTemplate();
					cad.setClientId(clientId);
					cad.setTemplateNo(maxTemplateNo + 1);
					cad.setTemplate(true);
					cad.setCreatedBy(username);
					cad.setQuestionNo(req.getQuestionNo());
					cad.setOptionType(req.getOptionCategory());
					cad.setOptionNo(co.getId());	
					cad.setQuestion(req.getQuestion());
					cad.setOptionDesc(opts);
					cad.setTemplateName(reqList.getTemplateName());
					cad.setTemplateDescription(reqList.getTemplateDescription());
					cad.setTemplateTag(reqList.getTemplateTag());
					clientIcpTemplateRepository.save(cad);
				}
			}
			else {
				System.out.println("questionNo = " + req.getQuestionNo() + " option = " + req.getSelectedOption());
				//ClientOptions co = clientOptionsRepository.findByQuestionnaireNoAndOptionDesc(req.getQuestionNo(), req.getSelectedOption());
				ClientOptions co = clientOptionsRepository.findByOptionDesc(req.getSelectedOption());
				ClientIcpTemplate cad = new ClientIcpTemplate();
				cad.setClientId(clientId);
				cad.setTemplateNo(maxTemplateNo + 1);
				cad.setCreatedBy(username);
				cad.setTemplate(true);
				cad.setQuestionNo(req.getQuestionNo());
				cad.setOptionType(req.getOptionCategory());
				cad.setOptionNo(co.getId());
				cad.setQuestion(req.getQuestion());
				cad.setOptionDesc(req.getSelectedOption());
				cad.setTemplateName(reqList.getTemplateName());
				cad.setTemplateDescription(reqList.getTemplateDescription());
				cad.setTemplateTag(reqList.getTemplateTag());
				clientIcpTemplateRepository.save(cad);
			}
			
			
		}
		
		utilityService.extractClientAssessmentScoreAndSave(clientId, maxTemplateNo + 1, 1);
		
		return new ResponseEntity<>("data saved successfully", null, HttpStatus.CREATED);
	}
	
	
	public CandidateSpectrumResults getICPResult(long clientId, long templateNo){
		
		List<ClientAssessmentRanking> dataList = clientAssessmentRankingRepository.findByClientIdAndTemplateNo(clientId, templateNo);
		CandidateSpectrumResults results = new CandidateSpectrumResults();
		Map sbarGraph = new HashMap();
		Map cbarGraph = new HashMap();
		Map ebarGraph = new HashMap();
		List<Map> sCircular = new ArrayList();
		List<Map> cCircular = new ArrayList();
		List<Map> eCircular = new ArrayList();
		for(ClientAssessmentRanking data : dataList) {
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
				circularGraph.put("attribute", attribute);
				circularGraph.put("percentage", (rating/5) * 100);
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
				circularGraph.put("attribute", attribute);
				circularGraph.put("percentage", (rating/5) * 100);
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
		//List<ClientAssessmentRanking> carList = clientAssessmentRankingRepository.findByclientId(clientId);
		
		String pillar = "";
		String pillarOrder = "";
		List<String> pillars = new ArrayList();
		for(ClientAssessmentRanking car : dataList) {
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
	
	
	public ResponseEntity<Object> getAllIcpTemplate(long clientId, int pageNo, int pageSize){
		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize*13, Sort.by("id").ascending());
		Page<Object[]> pagedResult = clientAssessmentRankingRepository.getTemplateNos(clientId, paging);
		List<CandidateSpectrumResults> respList = new ArrayList();
		long templateNo = 0;
		for(Object[] obj : pagedResult.getContent()) {
			if(templateNo != (long) obj[0]) {
				CandidateSpectrumResults resp = getICPResult(clientId, (long) obj[0] );
				ClientIcpTemplate icp = clientIcpTemplateRepository.getOneRecord((long) obj[0]);
				resp.setCreatedBy(obj[2].toString());
				resp.setTemplateName(obj[1].toString());
				resp.setId((long) obj[0]);
				resp.setTemplateTag(icp.getTemplateTag());
				resp.setTemplateDescription(icp.getTemplateDescription());
				respList.add(resp);
				templateNo = (long) obj[0];
			}
		}
		
		PaginatedResponse paginationResponse = PaginatedResponse.builder().data(respList)
				.pageNo(pagedResult.getNumber() + 1).pageSize(pageSize)
				.totalCount(pagedResult.getTotalElements()/13).message("found").result("success")
				.build();
		
		return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	

}
