package xenhire.service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import xenhire.dto.BatchCandidatesStatusDTO;
import xenhire.dto.CandidateDTPAccessDTO;
import xenhire.model.Assessments;
import xenhire.model.BatchAssessmentCandidates;
import xenhire.model.ClientAssessmentBatch;
import xenhire.model.ClientAssessmentPerBatch;
import xenhire.model.JobAssignedCandidates;
import xenhire.model.Role;
import xenhire.model.User;
import xenhire.repository.AssessmentsRepository;
import xenhire.repository.BatchAssessmentCandidatesRepository;
import xenhire.repository.ClientAssessmentBatchRepository;
import xenhire.repository.ClientAssessmentPerBatchRepository;
import xenhire.repository.JobAssignedCandidatesRepository;
import xenhire.repository.RoleRepository;
import xenhire.repository.UserRepository;
import xenhire.request.AssessmentBatchAddCandidateRequest;
import xenhire.request.AssessmentBatchCandidateResponse;
import xenhire.request.CandidatesBatchRequest;
import xenhire.request.ClientAssessmentBatchRequest;
import xenhire.response.AssessmentsResponse;
import xenhire.response.BatchCandidateResponse;
import xenhire.response.BatchCandidatesStatusResponse;
import xenhire.response.BatchListResponse;
import xenhire.response.ClientAssessmentBatchResponse;
import xenhire.response.PaginatedResponse;


@Service
public class ClientAssessmentService {
	
    
	@Autowired
	AssessmentsRepository assessmentsRepository;
	
	@Autowired
	ClientAssessmentBatchRepository clientAssessmentBatchRepository;
	
	@Autowired
	ClientAssessmentPerBatchRepository clientAssessmentPerBatchRepository;
	
	@Autowired
	BatchAssessmentCandidatesRepository batchAssessmentCandidatesRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UtilityService utilityService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	JobAssignedCandidatesRepository jobAssignedCandidatesRepository;
	
	
	
	public ResponseEntity<Object> saveAssessmentBatchWithAssignCandidates(long clientId, ClientAssessmentBatchRequest req){
		ClientAssessmentBatch cab = clientAssessmentBatchRepository.findByClientIdAndBatchName(clientId, req.getBatchName());
		if(cab != null) {
			return new ResponseEntity<>("record exist on batch name", null, HttpStatus.OK);
		}
		String username = utilityService.getLoggedInUsername(clientId);
		cab = new ClientAssessmentBatch();
		
		cab.setClientId(clientId);
		cab.setBatchName(req.getBatchName());
		cab.setCreatedBy(username);
		
		clientAssessmentBatchRepository.save(cab);

		for(AssessmentsResponse asses : req.getSelectedAssignment()) {
			for(BatchCandidateResponse capb : req.getAssignCandidate()) {
				
				BatchAssessmentCandidates bac = new BatchAssessmentCandidates();
				bac.setBatchId(cab.getId());
				bac.setCandidateId(capb.getId());
				bac.setClientId(clientId);
				bac.setAssessmentId(asses.getId());
				bac.setAssessmentName(asses.getName());
				bac.setStatus("Assigned");
				
				batchAssessmentCandidatesRepository.save(bac);
				
			}
		}
		
		Map m = new HashMap();
		
		m.put("batchId", cab.getId());
		
		return new ResponseEntity( m, null, HttpStatus.OK);
		
	}
	
	
	
	public ResponseEntity<Object> saveAssessmentBatchWithAddCandidate(long clientId, AssessmentBatchAddCandidateRequest req){
		ClientAssessmentBatch cab = clientAssessmentBatchRepository.findByClientIdAndBatchName(clientId, req.getBatchName());
		if(cab != null) {
			return new ResponseEntity<>("record exist on batch name", null, HttpStatus.OK);
		}
		String username = utilityService.getLoggedInUsername(clientId);
		cab = new ClientAssessmentBatch();
		
		cab.setClientId(clientId);
		cab.setBatchName(req.getBatchName());
		cab.setCreatedBy(username);
		
		clientAssessmentBatchRepository.save(cab);
		
		User u = new User();
		u.setUsername(req.getCandidateName());
		u.setEmail(req.getCandidateEmail());
		u.setLinkedIn(req.getCandidateLinkedin());
		u.setMobile(req.getCandidateNo());
		u.setPassword(bCryptPasswordEncoder.encode(utilityService.generatePassword(8)));
		Role r = roleRepository.findByName("CANDIDATE");
		Set s = new HashSet();
		s.add(r);
		u.setRoles(s);
		userRepository.save(u);
		for(AssessmentsResponse asses : req.getSelectedAssignments()) {
				BatchAssessmentCandidates bac = new BatchAssessmentCandidates();
				bac.setBatchId(cab.getId());
				bac.setCandidateId(u.getId());
				bac.setClientId(clientId);
				bac.setAssessmentId(asses.getId());
				bac.setAssessmentName(asses.getName());
				bac.setStatus("Assigned");
				
				batchAssessmentCandidatesRepository.save(bac);
		}
		
		Map m = new HashMap();
		
		m.put("userId", u.getId());
		
		return new ResponseEntity( m, null, HttpStatus.OK);
		
	}
	
	
	public ResponseEntity<Object> getAllAsessmentBatch(long clientId, int pageNo, int pageSize) {

		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
//		if (direction.equals("DESC")) {
//			paging = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
//		}
		Page<ClientAssessmentBatch> pagedResult = clientAssessmentBatchRepository.findByClientId(clientId, paging);

		List<ClientAssessmentBatchResponse> respList = new ArrayList();
		for(ClientAssessmentBatch batch : pagedResult.getContent()) {
			List<BatchAssessmentCandidates> bacList = batchAssessmentCandidatesRepository.findAllByBatchId(batch.getId());
			//System.out.println(bacList);
			List<AssessmentsResponse> arList = new ArrayList();
			List<AssessmentBatchCandidateResponse> bcrList = new ArrayList();
			String[] assessmentNameHolder = new String[1];
			for(BatchAssessmentCandidates bac : bacList) {
				boolean exists = arList.stream().anyMatch(person -> person.getName().equals(bac.getAssessmentName()));
				if(exists == false) {
					AssessmentsResponse ar = new AssessmentsResponse();
					ar.setId(bac.getAssessmentId());
					ar.setName(bac.getAssessmentName());
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm a", Locale.ENGLISH);
					ar.setDate(bac.getCreatedAt().format(formatter));
					
					arList.add(ar);
				}
				
				AssessmentBatchCandidateResponse bcr = new AssessmentBatchCandidateResponse();
				Optional<User> uOpt = userRepository.findById(bac.getCandidateId());
				bcr.setEmailId(uOpt.get().getEmail());
				bcr.setId(uOpt.get().getId());
				bcr.setMobileNo(uOpt.get().getMobile());
				bcr.setName(uOpt.get().getUsername());
				bcr.setAssessmentName(bac.getAssessmentName());
				bcr.setStatus(bac.getStatus());
				bcr.setScore(bac.getResult());
				bcrList.add(bcr);
				
			}

			ClientAssessmentBatchResponse resp = new ClientAssessmentBatchResponse();
			resp.setBatchName(batch.getBatchName());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm a", Locale.ENGLISH);
			resp.setId(batch.getId());
			resp.setCreatedBy(batch.getCreatedAt().format(formatter));
			resp.setCreatedBy(batch.getCreatedBy());
			resp.setAssignCandidate(bcrList);
			resp.setSelectedAssignment(arList);
			
			respList.add(resp);
		}
		PaginatedResponse paginationResponse = PaginatedResponse.builder().data(respList)
				.pageNo(pagedResult.getNumber() + 1).pageSize(pagedResult.getSize())
				.totalCount(pagedResult.getTotalElements()).message("found").result("success")
				.build();

		return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);
	}
	

	private String getAssessmentScore(long clientId, String assessmentName, long batchId) {
		// TODO Auto-generated method stub
		
		return null;
	}



	public ResponseEntity<Object> assignCandidatesToBatch(long clientId, long batchId, CandidatesBatchRequest req){
		
		for(int id : req.getCandidates()) {
			
			List<ClientAssessmentPerBatch> capbList = clientAssessmentPerBatchRepository.findByBatchId(batchId);
			
			for(ClientAssessmentPerBatch capb : capbList) {
				
				BatchAssessmentCandidates bac = new BatchAssessmentCandidates();
				bac.setBatchId(batchId);
				bac.setCandidateId(id);
				bac.setClientId(clientId);
				bac.setAssessmentId(capb.getId());
				bac.setAssessmentName(capb.getAssessmentName());
				bac.setStatus("Assigned");
				
				batchAssessmentCandidatesRepository.save(bac);
				
			}
			
		}
		
		return new ResponseEntity<>("saved", null, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> getCandidates(long clientId, int pageNo, int pageSize) {
		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
		Page<JobAssignedCandidates> jacList = jobAssignedCandidatesRepository.findAllByClientId(clientId, paging);
		List<Long> ids = jacList.getContent().stream().map(jac -> jac.getCandidateId()).collect(Collectors.toList());
		List<User> userList = userRepository.findAllById(ids);
		List<BatchCandidateResponse> resp = new ArrayList();
		for(User u : userList){
			BatchCandidateResponse bcr = new BatchCandidateResponse();
			bcr.setEmailId(u.getEmail());
			bcr.setMobileNo(u.getMobile());
			bcr.setName(u.getUsername());
			bcr.setId(u.getId());
			bcr.setLinkedIn(u.getLinkedIn());
			resp.add(bcr);
		}
		
		PaginatedResponse paginationResponse = PaginatedResponse.builder().data(resp)
				.pageNo(jacList.getNumber() + 1).pageSize(jacList.getSize())
				.totalCount(jacList.getTotalElements()).message("found").result("success")
				.build();
		
		return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);
	}
	
	
	
	public ResponseEntity<Object> getBatchList(long clientId, int pageNo, int pageSize){
		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());
		Page<ClientAssessmentBatch> pagedResult = clientAssessmentBatchRepository.findByClientId(clientId, paging);
		List<BatchListResponse> respList = new ArrayList();
		for(ClientAssessmentBatch batch : pagedResult.getContent()) {
			BatchListResponse resp = new BatchListResponse();
			resp.setBatchName(batch.getBatchName());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm a", Locale.ENGLISH);
			resp.setDate(batch.getCreatedAt().format(formatter));
			resp.setId(batch.getId());
			respList.add(resp);
		}
		
		PaginatedResponse paginationResponse = PaginatedResponse.builder().data(respList)
				.pageNo(pagedResult.getNumber() + 1).pageSize(pagedResult.getSize())
				.totalCount(pagedResult.getTotalElements()).message("found").result("success")
				.build();
		
		return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> getBatchCandidatesData(long clientId, long batchId, int pageNo, int pageSize){
		List<ClientAssessmentPerBatch> capbList = clientAssessmentPerBatchRepository.findByBatchId(batchId);
		int assessmentCount = capbList.size();
		pageNo = pageNo - 1;
		Pageable paging = PageRequest.of(pageNo, pageSize * assessmentCount, Sort.by("id").ascending());
		Page<Object[]> pagedResult = batchAssessmentCandidatesRepository.getCandidateAssessmentStatus(batchId, paging); 
		List<BatchCandidatesStatusResponse> respList = new ArrayList();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy hh:mm a", Locale.ENGLISH);
		List<BatchCandidatesStatusDTO> resp = pagedResult.getContent().stream()
                .map(result -> new BatchCandidatesStatusDTO((String) result[0], ((Timestamp) result[1]).toLocalDateTime().format(formatter), ((Integer) result[2] == 1) ? "Not Taken" : "Taken", (long) result[3]+ " days", (String) result[4], (long) result[5]))                  
                .collect(Collectors.toList());
		
		List<BatchCandidatesStatusResponse> groupedCandidatesList = resp.stream()
                .collect(Collectors.groupingBy(BatchCandidatesStatusDTO::getCandidateName))
                .entrySet().stream()
                .map(entry -> new BatchCandidatesStatusResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
		
		
		System.out.println(groupedCandidatesList);
		
		int count = groupedCandidatesList.get(0).getAssessments().size();
		
		long totalCount = pagedResult.getTotalElements() / count;
		
		PaginatedResponse paginationResponse = PaginatedResponse.builder().data(groupedCandidatesList)
				.pageNo(pagedResult.getNumber() + 1).pageSize(pagedResult.getSize())
				.totalCount(totalCount).message("found").result("success")
				.build();
		
		return new ResponseEntity<>(paginationResponse, null, HttpStatus.OK);

	}
	
	
	

}
